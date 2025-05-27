use [uade_a_01]
-- 1. Mostrar el código y nombre de las provincias
-- y la cantidad de clientes que contienen utilizando subqueries.
SELECT p.provincia_cod,
       p.provincia_desc,
       (SELECT count(*)
 FROM   clientes c
 WHERE  c.provincia_cod = p.provincia_cod) as client_count
FROM   provincias p;


-- 2. Seleccionar producto_cod, producto_desc, de los productos cuyas cantidades totales vendidas
-- superen las 150 unidades. Utilizar un subquery en el FROM para su resolución. De qué otra
-- manera se puede solucionar este ejercicio?
SELECT *
FROM   (SELECT p.producto_cod,
               p.producto_desc,
               sum(fd.cantidad) as total_vendido
        FROM   productos p
            INNER JOIN facturas_det fd
                ON p.producto_cod = fd.producto_cod
        GROUP BY p.producto_cod, p.producto_desc having sum(fd.cantidad) > 150) as subquery
-- WHERE
-- total_vendido > 150;
-- 3. Mostrar los datos de las provincias que no posean fabricantes.
SELECT *
FROM   provincias p
WHERE  not exists (SELECT 1
                   FROM   fabricantes f
                   WHERE  f.provincia_cod = p.provincia_cod);


-- 4. Mostrar código y tiempo de entrega de todos los fabricantes
-- que no sean de ´BA´ cuyo plazo
-- entrega sea menor o igual a los plazos de entrega de todos los fabricantes de ‘BA’
SELECT f.fabricante_cod,
       f.tiempo_entrega
FROM   fabricantes f
WHERE  f.provincia_cod <> 'BA'
   and f.tiempo_entrega <= (SELECT min(tiempo_entrega)
                         FROM   fabricantes
                         WHERE  provincia_cod = 'BA');


-- OPCION 2
-- AND f.tiempo_entrega <= ALL(
-- SELECT tiempo_entrega
-- FROM fabricantes
-- WHERE provincia_cod = 'BA'
-- AND tiempo_entrega is not null
--);
-- 5. Mostrar el código y suma vendida de todos los fabricantes que no sean de BA
-- que hayan
-- vendido productos por un monto mayor al de algún fabricante de ‘BA’.
SELECT f.fabricante_cod,
       sum(fd.cantidad * p.precio_unit) as total_vendido
FROM   fabricantes f
    INNER JOIN productos p
        ON f.fabricante_cod = p.fabricante_cod
    INNER JOIN facturas_det fd
        ON p.producto_cod = fd.producto_cod
WHERE  f.provincia_cod <> 'BA'
GROUP BY f.fabricante_cod having sum(fd.cantidad * p.precio_unit) > any(
    SELECT sum(fd2.cantidad * fd2.precio_unit)
    FROM   fabricantes f2
        INNER JOIN productos p2
            ON f2.fabricante_cod = p2.fabricante_cod
        INNER JOIN facturas_det fd2
            ON p2.producto_cod = fd2.producto_cod
    WHERE  f2.provincia_cod = 'BA'
    GROUP BY f2.fabricante_cod);


-- 6. Seleccionar aquellos clientes cuya facturación supere el promedio facturado
-- de todos los clientes que realizaron compras.
SELECT c.cliente_num,
       c.nombre,
       c.apellido,
       sum(fd.cantidad * p.precio_unit) as total_facturado
FROM   clientes c
    INNER JOIN facturas f
        ON c.cliente_num = f.cliente_num
    INNER JOIN facturas_det fd
        ON f.factura_num = fd.factura_num
    INNER JOIN productos p
        ON fd.producto_cod = p.producto_cod
GROUP BY c.cliente_num, c.nombre, c.apellido having sum(fd.cantidad * fd.precio_unit) > all(SELECT sum(fd2.cantidad * fd2.precio_unit)/count(distinct fd2.factura_num) as promedio_facturado
                                                                                            FROM   clientes c2
                                                                                                INNER JOIN facturas f2
                                                                                                    ON c2.cliente_num = f2.cliente_num
                                                                                                INNER JOIN facturas_det fd2
                                                                                                    ON f2.factura_num = fd2.factura_num
                                                                                                INNER JOIN productos p2
                                                                                                    ON fd2.producto_cod = p2.producto_cod
                                                                                            GROUP BY c2.cliente_num, c2.nombre, c2.apellido);


-- 7. Seleccionar aquellos productos que no hayan tenido ventas.
-- Resolverlo de dos maneras diferentes: Con subquery y con un query correlacionado.
-- OPCION 1
SELECT p.producto_cod,
       p.producto_desc
FROM   productos p
WHERE  not exists (SELECT 1
                   FROM   facturas_det fd
                   WHERE  fd.producto_cod = p.producto_cod);


-- OPCION 2
SELECT p.producto_cod,
       p.producto_desc
FROM   productos p
    LEFT JOIN facturas_det fd
        ON p.producto_cod = fd.producto_cod
WHERE  fd.producto_cod is null; -- Si no joinea, no tiene ventas


-- 9. Seleccionar número, nombre y apellido de todos los clientes
-- que NO compraron productos del fabricante ‘DOTO’.
SELECT c.cliente_num,
       c.nombre,
       c.apellido
FROM   clientes c
WHERE  not exists (SELECT 1
                   FROM   facturas f
                       INNER JOIN facturas_det fd
                           ON f.factura_num = fd.factura_num
                       INNER JOIN productos p
                           ON fd.producto_cod = p.producto_cod
                       INNER JOIN fabricantes fa
                           ON p.fabricante_cod = fa.fabricante_cod
                   WHERE  c.cliente_num = f.cliente_num
                      and fa.fabricante_cod = 'DOTO');


-- 10. Seleccionar número, nombre y apellido de los clientes que hayan
-- comprado TODOS los productos del fabricante ‘DOTO’.
SELECT c.cliente_num,
       c.nombre,
       c.apellido
FROM   clientes c
WHERE  (SELECT count(distinct fd.producto_cod)
        FROM   facturas f
            INNER JOIN facturas_det fd
                ON f.factura_num = fd.factura_num
            INNER JOIN productos p2
                ON fd.producto_cod = p2.producto_cod
        WHERE  c.cliente_num = f.cliente_num
           and p2.fabricante_cod = 'DOTO') = (SELECT count(*)
                                   FROM   productos p
                                   WHERE  p.fabricante_cod = 'DOTO');

-- 11. Tome el ejemplo del apunte de query recursivo, 
-- qué debería modificar para que el query muestre todos
--  los “clientes referentes hacia arriba” de un cierto cliente?

WITH CTERecursivo AS ( -- CTE common table expression
    -- SELECT RAIZ (ROOT) - El cliente inicial del que queremos encontrar los referentes hacia arriba
    SELECT c.cliente_num, c.nombre, c.apellido, c.cliente_ref, r.nombre AS nombre_referido, r.apellido AS apellido_referido,
           0 AS LEVEL, RIGHT('000000' + CAST(c.cliente_num AS varchar(MAX)), 6) AS sort
    FROM clientes c LEFT JOIN clientes r ON (c.cliente_ref = r.cliente_num)
    WHERE c.cliente_num = 109 -- Aquí especificas el cliente inicial

    UNION ALL -- SELECTS RECURSIVOS (Resto del Arbol - Buscando hacia arriba)
    SELECT r.cliente_num, r.nombre, r.apellido, r.cliente_ref, c.nombre, c.apellido,
           LEVEL + 1, c.sort + '/' + RIGHT('000000' + CAST(r.cliente_num AS varchar(20)), 6) AS sort
    FROM clientes r JOIN CTERecursivo c ON r.cliente_num = c.cliente_ref
)
-- SELECT que muestra resultado del WITH
SELECT replicate('-', level * 4) + CAST(cliente_num as varchar) as arbol
, *
FROM CTERecursivo
ORDER BY sort DESC; -- Ordena para mostrar la cadena de referencia desde el nivel más alto



-- 12. Seleccionar el número, nombre y apellido de los clientes, Monto Total Comprado (p*q) y
-- cantidad de facturas por cliente del producto (producto_cod) con mayor monto total vendido.
-- Mostrar también el monto total del producto mas vendido.
-- Para el cálculo del mayor producto vendido como de las facturas, se deben considerar
-- solamente las facturas posteriores al 15 de marzo del 2021.
-- Mostrar la información ordenada por el monto total comprado del producto por cliente en
-- forma descendente y por cantidad de facturas en forma ascendente.
-- Notas: No usar Store procedures, ni funciones de usuarios, ni tablas temporales. Una orden
-- puede tener varios Items con el mismo producto.
WITH prod_mas_vendido AS (
    SELECT TOP 1 fd.producto_cod,
           sum(fd.cantidad) as unidades_vendidas
    FROM   productos p
        INNER JOIN facturas_det fd
            ON p.producto_cod = fd.producto_cod
        INNER JOIN facturas f
            ON f.factura_num = fd.factura_num
    WHERE  f.fecha_emision > '2021-03-15'
    GROUP BY fd.producto_cod
    ORDER BY unidades_vendidas DESC
),
facturas_por_cliente_producto AS (
    SELECT c.cliente_num,
           fd.producto_cod,
           count(distinct f.factura_num) as cantidad_facturas
    FROM   clientes c
        INNER JOIN facturas f
            ON c.cliente_num = f.cliente_num
        INNER JOIN facturas_det fd
            ON f.factura_num = fd.factura_num
        INNER JOIN productos p
            ON fd.producto_cod = p.producto_cod
    WHERE  f.fecha_emision > '2021-03-15'
    GROUP BY c.cliente_num, fd.producto_cod
),
tot_vendido_producto AS (
    SELECT fd.producto_cod,
           sum(fd.cantidad * p.precio_unit) as total_vendido
    FROM   productos p
        INNER JOIN facturas_det fd
            ON p.producto_cod = fd.producto_cod
        INNER JOIN facturas f
            ON f.factura_num = fd.factura_num
    WHERE  f.fecha_emision > '2021-03-15'
    GROUP BY fd.producto_cod
)
SELECT c.cliente_num,
       c.nombre,
       c.apellido,
       sum(fd.cantidad * p.precio_unit) as total_comprado,
       MAX(fpcp.cantidad_facturas) as cantidad_facturas, -- Este max es solo para no ponerlo en el group by
       tvp.total_vendido as Monto_producto
FROM   clientes c
    INNER JOIN facturas f
        ON c.cliente_num = f.cliente_num
    INNER JOIN facturas_det fd
        ON f.factura_num = fd.factura_num
    INNER JOIN productos p
        ON fd.producto_cod = p.producto_cod
    -- Este Inner me fuerza a 1 solo producto
    INNER JOIN prod_mas_vendido pmv
        ON fd.producto_cod = pmv.producto_cod
    INNER JOIN facturas_por_cliente_producto fpcp
        ON c.cliente_num = fpcp.cliente_num
        AND fd.producto_cod = fpcp.producto_cod
    LEFT JOIN tot_vendido_producto tvp
        ON fd.producto_cod = tvp.producto_cod
GROUP BY c.cliente_num, c.nombre, c.apellido, tvp.total_vendido
ORDER BY total_comprado DESC, c.cliente_num DESC,
         cantidad_facturas ASC


-- 13. Crear una consulta que devuelva lo siguiente:
-- Número, Apellido y Nombre del cliente,
-- Monto total comprado del cliente
-- Número, Apellido, Nombre del Cliente Referido (ojo, No el referente ¡!)
-- Monto total comprado por el referido mas un 10% de comisión
-- Consideraciones.En el caso que un Cliente no tenga Referidos deberá mostrar los datos del referido en NULL.
-- Para calcular la comisión del cliente se deberán sumar (cant*precio) de todos los productos
-- comprados por el Cliente Referido cuyo código de producto sea menor a 1010
-- Se deberá ordenar la salida por el nro de cliente y nro de referido ambos ascendentes.

SELECT c.cliente_num,
       c.apellido,
       c.nombre,
       sum(fd.cantidad * p.precio_unit) as total_comprado,
       r.cliente_num as cliente_ref_num,
       r.apellido as cliente_ref_apellido,
       r.nombre as cliente_ref_nombre,
       sum(fd2.cantidad * p2.precio_unit) * 1.1 as comision
FROM   clientes c
    LEFT JOIN clientes r
        ON c.cliente_ref = r.cliente_num
    INNER JOIN facturas f
        ON c.cliente_num = f.cliente_num
    INNER JOIN facturas_det fd
        ON f.factura_num = fd.factura_num
    INNER JOIN productos p
        ON fd.producto_cod = p.producto_cod
    -- Joins apuntando al referido
    LEFT JOIN facturas f2
        ON r.cliente_num = f2.cliente_num
    LEFT JOIN facturas_det fd2
        ON f2.factura_num = fd2.factura_num
    LEFT JOIN productos p2
        ON fd2.producto_cod = p2.producto_cod
WHERE  p2.producto_cod < 1010
GROUP BY c.cliente_num, c.apellido, c.nombre, r.cliente_num, r.apellido, r.nombre
ORDER BY c.cliente_num, r.cliente_num;

-- 14.
-- Crear una consulta que devuelva: Para aquellos 3 estados de mayor facturación el
-- Código de estado, número cliente, nombre, apellido, promedio de orden de compra por
-- cliente, total comprado por cliente y total comprado por estado. Solo tener en cuenta
-- aquellas líneas de ítems que hayan facturado mas de $1000.
-- Ordenar la consulta por el monto facturado total por estado en forma descendente y por
-- monto facturado por cliente también en forma descendente.
WITH CTE1 AS (
SELECT
            f.factura_num,
            fa.provincia_cod,
            SUM(fd.cantidad * fd.precio_unit) OVER (PARTITION BY fa.provincia_cod) AS total_provincia,
            c.cliente_num,
            c.nombre,
            c.apellido,
            fd.cantidad,
            fd.precio_unit
    FROM   facturas_det fd
        INNER JOIN facturas f
            ON f.factura_num = fd.factura_num
        INNER JOIN clientes c
            ON c.cliente_num = f.cliente_num
        INNER JOIN productos p
            ON fd.producto_cod = p.producto_cod
        INNER JOIN fabricantes fa
            ON p.fabricante_cod = fa.fabricante_cod
        INNER JOIN provincias prov
            ON prov.provincia_cod = fa.provincia_cod
    WHERE (fd.cantidad * fd.precio_unit) > 1000
    GROUP BY f.factura_num, fa.provincia_cod, c.cliente_num, c.nombre, c.apellido, fd.cantidad, fd.precio_unit
),
CTE2 AS (
    SELECT             
            fa.provincia_cod,
            SUM(fd.cantidad * fd.precio_unit),-- Innecesario pero lindo
            ROW_NUMBER() OVER (ORDER BY sum(fd.cantidad * fd.precio_unit) DESC, fa.provincia_cod) AS rn
    FROM   facturas_det fd
        INNER JOIN facturas f
            ON f.factura_num = fd.factura_num
        INNER JOIN clientes c
            ON c.cliente_num = f.cliente_num
        INNER JOIN productos p
            ON fd.producto_cod = p.producto_cod
        INNER JOIN fabricantes fa
            ON p.fabricante_cod = fa.fabricante_cod
        INNER JOIN provincias prov
            ON prov.provincia_cod = fa.provincia_cod
    GROUP BY fa.provincia_cod
)
SELECT 
    CTE1.provincia_cod,
    cliente_num,
    nombre,
    apellido,
    SUM(cantidad * precio_unit)/COUNT(DISTINCT factura_num) AS promedio_orden_compra,
    SUM(cantidad * precio_unit) AS total_comprado_cliente
    FROM CTE1
    INNER JOIN CTE2
        ON CTE1.provincia_cod = CTE2.provincia_cod
    WHERE rn <= 3
GROUP BY CTE1.provincia_cod, cliente_num, nombre, apellido