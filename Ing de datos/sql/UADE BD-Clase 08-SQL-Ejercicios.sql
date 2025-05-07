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
GROUP BY f.fabricante_cod having sum(fd.cantidad * p.precio_unit) > any(SELECT sum(fd2.cantidad * fd2.precio_unit)
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
