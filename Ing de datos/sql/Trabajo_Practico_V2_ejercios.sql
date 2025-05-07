use [trabajo_practico]
/*Dada una tabla PERSONAS que contenga la siguiente estructura, seleccione las filas
   
   
   de la tabla tal que los valores del atributo candidato a ser primary key estén repetidos más de una vez. id (clave candidata) nombre varchar(20) apellido varchar(20)*/
SELECT id,
       nombre,
       apellido
FROM   dbo.personas
WHERE  id in (SELECT id
              FROM   dbo.personas
              GROUP BY id having count(*) > 1)
/*Realice una sentencia DELETE que borre las filas de la tabla PERSONAS del ejercicio
   1 que estén duplicadas dejando sólo una fila con cada valor de la clave candidata.*/delete delete
FROM   dbo.personas
WHERE  concat(id, nombre, apellido) in (SELECT concat(id, nombre, apellido)
                                        FROM   (SELECT id,
                                                       nombre,
                                                       apellido,
                                                       row_number() OVER (PARTITION BY id
                                                                          ORDER BY (SELECT null)) as rownum
                                                FROM   personas) as duplicates
                                        WHERE  rownum > 1);


SELECT count(*)
FROM   dbo.personas
/*Realizar una consulta que devuelva el monto total comprado por cliente en cada
   provincia de los fabricantes. Las columnas a mostrar son número de cliente, nombre
   del cliente, apellido del cliente, provincia_desc (de los fabricantes) y monto total
   comprado. Ordenar la información por número de cliente en forma ascendente y monto
   total comprado en forma descendente.*/use [uade_a_01];


SELECT c.cliente_num,
       c.nombre,
       c.apellido,
       prov.provincia_desc,
       sum(f.precio_unit*f.cantidad) as monto_total_comprado
FROM   dbo.clientes c join dbo.facturas i
        ON c.cliente_num = i.cliente_num join dbo.facturas_det f
        ON i.factura_num = f.factura_num
    LEFT JOIN dbo.productos p
        ON p.producto_cod = f.producto_cod
    LEFT JOIN dbo.fabricantes fa
        ON p.fabricante_cod = fa.fabricante_cod
    LEFT JOIN dbo.provincias prov
        ON prov.provincia_cod = fa.provincia_cod
GROUP BY c.cliente_num, c.nombre, c.apellido, prov.provincia_desc
ORDER BY c.cliente_num asc, monto_total_comprado desc;

/*Sea una tabla VALORES que tiene un campo numérico secuencial Id, realizar una
consulta que obtenga el valor del primer espacio libre de ese campo.*/

/*Mostrar por cada Producto (producto_cod) las cantidades totales mensuales
vendidas (según la fecha de emisión de la factura). No importa si hay meses de
diferentes años, se suman como si fueran del mismo año. La información se deberá
mostrar en forma tabular como se muestra en la figura, ordenada por código de producto*/

/*Realizar UN query que asegure que dos tablas con la misma estructura tengan
exactamente la misma cantidad de filas y exactamente los mismos datos en cada
columna. Es decir, que las tablas sean exactamente iguales.*/

/*Realizar una consulta que tenga el siguiente formato (los datos son solo de ejemplo)
que muestre para cada cliente que tenga referente los distintos niveles de referentes
que posee. Cada referente se deberá mostrar en una columna diferente y debe
mostrar hasta el tercer nivel si es que existe.*/

/*Dado el siguiente modelo, realice una consulta que permita averiguar en qué equipo juega
o jugó una jugadora en una fecha determinada.*/

/*Realizar UN query sin utilizar subqueries, que muestre las cantidades de cada producto
vendidas en cada provincia. La provincia se refiere a la del Fabricante. Mostrar código de
provincia, código de producto, cantidad vendida del producto en la provincia, cantidad
total de productos vendidos en la provincia y porcentaje del total vendido en la provincia
respecto a la cantidad total de productos vendidos en el país.
Mostrar la información ordenada por provincia en forma ascendente y cantidad total del
producto vendido en forma descendente.*/


/*Dada el siguiente modelo, desarrolle e implemente un mecanismo que cuando se dé de
alta un nuevo pase de una jugadora, controle y evite que la jugadora esté fichada para
dos equipos al mismo tiempo (inclusive al mismo). Es decir, que no permita que dos filas
en la tabla Pases se superpongan dentro de un periodo para una misma jugadora.*/
