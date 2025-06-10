use Trabajo_practico_Grupo_1;
GO
/*Dada una tabla PERSONAS que contenga la siguiente estructura, seleccione las filas
de la tabla tal que los valores del atributo candidato a ser primary key estén repetidos más de una vez.
id (clave candidata) nombre varchar(20) apellido varchar(20)*/
SELECT
    id,
    nombre,
    apellido
FROM
    dbo.personas
WHERE
    id in (
        SELECT
            id
        FROM
            dbo.personas
        GROUP BY
            id
        having
            count(*) > 1
    )
ORDER BY id;

/*Realice una sentencia DELETE que borre las filas de la tabla PERSONAS del ejercicio
    1 que estén duplicadas dejando sólo una fila con cada valor de la clave candidata.*/
SELECT
    count(*) -- Cantidad de personas antes del borrado = 21
FROM
    dbo.personas

delete FROM
    personas
WHERE
    concat(id, nombre, apellido) in (
        SELECT
            concat(id, nombre, apellido)
        FROM
            (
                SELECT
                    id,
                    nombre,
                    apellido,
                    row_number() OVER (
                        PARTITION BY id
                        ORDER BY
                            (
                                SELECT
                                    null
                            )
                    ) as rownum
                FROM
                    personas
            ) as duplicates
        WHERE
            rownum > 1
    );

SELECT
    count(*) -- Cantidad de personas despues del borrado = 18, correcto insertamos 3 ids de "mas"
FROM
    dbo.personas
    
/*Realizar una consulta que devuelva el monto total comprado por cliente en cada
    provincia de los fabricantes. Las columnas a mostrar son número de cliente, nombre
    del cliente, apellido del cliente, provincia_desc (de los fabricantes) y monto total
    comprado. Ordenar la información por número de cliente en forma ascendente y monto
    total comprado en forma descendente.*/
-- volvemos a usar la base de datos de la cursada
use uade_a_01;
GO
SELECT
    c.cliente_num,
    c.nombre,
    c.apellido,
    prov.provincia_desc,
    sum(f.precio_unit * f.cantidad) as monto_total_comprado
FROM
    dbo.clientes c
    join dbo.facturas i ON c.cliente_num = i.cliente_num
    join dbo.facturas_det f ON i.factura_num = f.factura_num
    LEFT JOIN dbo.productos p ON p.producto_cod = f.producto_cod
    LEFT JOIN dbo.fabricantes fa ON p.fabricante_cod = fa.fabricante_cod
    LEFT JOIN dbo.provincias prov ON prov.provincia_cod = fa.provincia_cod
GROUP BY
    c.cliente_num,
    c.nombre,
    c.apellido,
    prov.provincia_desc
ORDER BY
    c.cliente_num asc,
    monto_total_comprado desc;

/*Sea una tabla VALORES que tiene un campo numérico secuencial Id, realizar una
 consulta que obtenga el valor del primer espacio libre de ese campo.*/
-- Volvemos a usar la base de datos nuestra para este ejercicio
use Trabajo_practico_Grupo_1;
GO
-- Consideraciones sobre la consulta:
-- 1. la consulta busca el primer id libre de manera ascendente
-- 2. La consulta responde unicamente un id, ya que por consigna queremos unicamente el primer espacio.
-- 3. La consulta no tiene problemas con ids negativos o cero
WITH CTE AS (
        SELECT
            id,
            LAG(id) OVER (
                ORDER BY
                    Id
            ) AS id_anterior
        FROM
            valores
    )
SELECT
    TOP 1 id_anterior + 1 AS primer_espacio_libre
FROM
    CTE
WHERE
    id_anterior IS NOT NULL
    AND id <> id_anterior + 1
ORDER BY
    id_anterior;

/*Mostrar por cada Producto (producto_cod) las cantidades totales mensuales
 vendidas (según la fecha de emisión de la factura). No importa si hay meses de
 diferentes años, se suman como si fueran del mismo año. La información se deberá
 mostrar en forma tabular como se muestra en la figura, ordenada por código de producto*/
-- volvemos a usar la base de datos de la cursada
use uade_a_01;
GO
SELECT -- En la query principal pido el producto y les doy un alias mas legible a los meses.
       -- A su vez agrego la validacion ISNULL para obtener todos resultados numericos.
    producto_cod,
    ISNULL([1],0) AS Enero,
    ISNULL([2],0) AS Febrero,
    ISNULL([3],0) AS Marzo,
    ISNULL([4],0) AS Abril,
    ISNULL([5],0) AS Mayo,
    ISNULL([6],0) AS Junio,
    ISNULL([7],0) AS Julio,
    ISNULL([8],0) AS Agosto,
    ISNULL([9],0) AS Septiembre,
    ISNULL([10],0) AS Octubre,
    ISNULL([11],0) AS Noviembre,
    ISNULL([12],0) AS Diciembre
FROM -- En esta subquery genero los datos que necesito para el pivot.
    (SELECT
        d.producto_cod,
        MONTH(f.fecha_emision) AS mes,
        d.cantidad
    FROM
        facturas f
        JOIN facturas_det d ON f.factura_num = d.factura_num
    ) AS SourceTable
PIVOT -- aplico la funcion pivot para trasponer los meses como columnas
(
    SUM(cantidad) -- Sumo las cantidades por mes,producto
    FOR mes IN ([1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12])
) AS PivotTable
ORDER BY
    producto_cod;

/*Realizar UN query que asegure que dos tablas con la misma estructura tengan
 exactamente la misma cantidad de filas y exactamente los mismos datos en cada
 columna. Es decir, que las tablas sean exactamente iguales.*/
use Trabajo_practico_Grupo_1;
GO
WITH DIFERENCIAS as(
    SELECT
        *
    FROM
        tabla1
    EXCEPT
    SELECT
        *
    FROM
        tabla2
    UNION -- Hacemos union del except por los casos de 1 que puedan estar en el otro
    SELECT
        *
    FROM
        tabla2
    EXCEPT
    SELECT
        *
    FROM
        tabla1
)
SELECT
    CASE
        WHEN EXISTS (
            SELECT
                1
            FROM
                DIFERENCIAS
        ) THEN 'son distintas'
        ELSE 'son iguales'
    END as resultado;

/*Realizar una consulta que tenga el siguiente formato (los datos son solo de ejemplo)
que muestre para cada cliente que tenga referente los distintos niveles de referentes
que posee. Cada referente se deberá mostrar en una columna diferente y debe
mostrar hasta el tercer nivel si es que existe.*/
use uade_a_01;
GO
SELECT
    c1.cliente_num, -- Nivel 0 mi cliente
    c1.cliente_ref, -- Nivel 1 referente del cliente
    c2.cliente_ref, -- Nivel 2 referente del referente
    c3.cliente_ref  -- Nivel 3 referente del referente del referente del cliente
FROM
    clientes c1 -- Driver o nivel 0 y nivel 1
    LEFT JOIN clientes c2 ON c1.cliente_ref = c2.cliente_num -- Nivel 2
    LEFT JOIN clientes c3 ON c2.cliente_ref = c3.cliente_num -- Nivel 3
WHERE
    c1.cliente_ref IS NOT NULL
ORDER BY
    c1.cliente_num


/*Dado el siguiente modelo, realice una consulta que permita averiguar en qué equipo juega
o jugó una jugadora en una fecha determinada.*/
use Trabajo_practico_Grupo_1;
GO
WITH fechita as( -- Pongo la fecha aca por prolijidad, para no tener que repetirla N veces en la query
    -- El legajo lo pongo constante pero podria ponerse aca y joiner por ejemplo.
    SELECT
        '2023-05-10' as miFechita
    )
SELECT
    j.Nombre,
    j.Apellido,
    c.Nombre AS Club,
    p.FechaDesde
FROM
    Jugadoras j
    JOIN Pases p ON j.Legajo = p.Legajo
    JOIN Clubes c ON p.CodigoClub = c.Codigo
WHERE
    j.Legajo = 1
    AND p.FechaDesde <= (
        SELECT
            miFechita
        FROM
            fechita
    )
    AND NOT EXISTS (
        SELECT
            1
        FROM
            Pases p2
        WHERE
            p2.Legajo = p.Legajo
            AND p2.FechaDesde <= (
                SELECT
                    miFechita
                FROM
                    fechita
            )
            AND p2.FechaDesde > p.FechaDesde
    );

/*Realizar UN query sin utilizar subqueries, que muestre las cantidades de cada producto
 vendidas en cada provincia. La provincia se refiere a la del Fabricante. Mostrar código de
 provincia, código de producto, cantidad vendida del producto en la provincia, cantidad
 total de productos vendidos en la provincia y porcentaje del total vendido en la provincia
 respecto a la cantidad total de productos vendidos en el país.
 Mostrar la información ordenada por provincia en forma ascendente y cantidad total del
 producto vendido en forma descendente.*/
use uade_a_01;
GO
SELECT --Los cast as int son por cuestiones de redondeo.
    prov.provincia_cod AS Provincia,
    fd.producto_cod AS Producto,
    SUM(fd.cantidad) AS cantidadProductoProvincia,
    SUM(SUM(fd.cantidad)) OVER (PARTITION BY prov.provincia_cod) AS totalProductosProvincia,-- Sumo todas las filas del producto en la provincia
    CAST(
        SUM(fd.cantidad) * 100.0 / SUM(SUM(fd.cantidad)) OVER (PARTITION BY prov.provincia_cod) AS INT
    ) AS [% Cant/total por Provincia],
    CAST(
        SUM(SUM(fd.cantidad)) OVER (PARTITION BY prov.provincia_cod) * 100.0 / SUM(SUM(fd.cantidad)) OVER () AS INT
    ) AS [% Total Prov / Total país] -- Notese el ultimo OVER que no tiene particionamiento, es el total del pais
FROM
    facturas f
    LEFT JOIN facturas_det fd ON f.factura_num = fd.factura_num
    LEFT JOIN productos p ON fd.producto_cod = p.producto_cod
    LEFT JOIN fabricantes fab ON fab.fabricante_cod = p.fabricante_cod
    LEFT JOIN provincias prov ON prov.provincia_cod = fab.provincia_cod
GROUP BY
    prov.provincia_cod,
    fd.producto_cod
ORDER BY
    prov.provincia_cod;


/*Dada el siguiente modelo, desarrolle e implemente un mecanismo que cuando se dé de
alta un nuevo pase de una jugadora, controle y evite que la jugadora esté fichada para
dos equipos al mismo tiempo (inclusive al mismo). Es decir, que no permita que dos filas
en la tabla Pases se superpongan dentro de un periodo para una misma jugadora.*/
use Trabajo_practico_Grupo_1;
go
-- DROP TRIGGER trg_evitar_pases_superpuestos;
CREATE TRIGGER trg_evitar_pases_superpuestos
ON Pases
INSTEAD OF INSERT, UPDATE
AS
BEGIN
    -- Declarar variables para las fechas de inicio y fin del pase a insertar/actualizar
    DECLARE @FechaDesde_Insertado DATE;
    DECLARE @FechaHasta_Insertado DATE;
    DECLARE @Legajo_Insertado INT;

    -- Obtener los valores del pase que se intenta insertar/actualizar
    SELECT @Legajo_Insertado = Legajo, @FechaDesde_Insertado = FechaDesde, @FechaHasta_Insertado = FechaHasta
    FROM INSERTED;

    -- 1. Chequear superposición con datos existentes
    IF EXISTS (
        SELECT 1
        FROM Pases p
        WHERE p.Legajo = @Legajo_Insertado
          -- Excluir el registro que se está actualizando en caso de UPDATE para evitar falsos positivos
          AND NOT EXISTS (SELECT 1 FROM DELETED d WHERE d.Legajo = p.Legajo AND d.FechaDesde = p.FechaDesde)
          AND (
                -- Superposición: el pase existente empieza durante el pase nuevo
                (p.FechaDesde <= ISNULL(@FechaHasta_Insertado, GETDATE()) AND p.FechaDesde >= @FechaDesde_Insertado)
                OR
                -- Superposición: el pase existente termina durante el pase nuevo
                (ISNULL(p.FechaHasta, GETDATE()) >= @FechaDesde_Insertado AND ISNULL(p.FechaHasta, GETDATE()) <= ISNULL(@FechaHasta_Insertado, GETDATE()))
                OR
                -- Superposición: el pase nuevo está contenido completamente dentro de un pase existente
                (@FechaDesde_Insertado >= p.FechaDesde AND ISNULL(@FechaHasta_Insertado, GETDATE()) <= ISNULL(p.FechaHasta, GETDATE()))
                OR
                -- Superposición: un pase existente está contenido completamente dentro del pase nuevo
                (p.FechaDesde >= @FechaDesde_Insertado AND ISNULL(p.FechaHasta, GETDATE()) <= ISNULL(@FechaHasta_Insertado, GETDATE()))
                OR
                -- Superposición: el pase nuevo termina después del inicio del pase existente y empieza antes del fin del pase existente
                (@FechaDesde_Insertado < ISNULL(p.FechaHasta, GETDATE()) AND ISNULL(@FechaHasta_Insertado, GETDATE()) > p.FechaDesde)
              )
    )
    BEGIN
        RAISERROR('Error: la jugadora ya tiene un pase que se superpone con las fechas especificadas.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    -- 2. Eliminar registros anteriores si se trata de un UPDATE
    -- Esto es importante para que el UPDATE se comporte como un DELETE + INSERT,
    -- y no deje el registro antiguo coexistiendo con el nuevo en caso de que solo se modifique una fecha.
    DELETE p
    FROM Pases p
    JOIN DELETED d
      ON p.Legajo = d.Legajo AND p.FechaDesde = d.FechaDesde;

    -- 3. Insertar nuevos registros (sean de INSERT o UPDATE)
    INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
    SELECT Legajo, FechaDesde, FechaHasta, CodigoClub
    FROM INSERTED;
END;
GO

