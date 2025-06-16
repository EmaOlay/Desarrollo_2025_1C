/*UADE BD-Clase 06-SQL-Ejercicios.pdf*/


/*Seleccionar los fabricantes que tengan una ‘E’ en el código y su tiempo de entrega sea mayor a 5
   días.*/
SELECT *
FROM   [uade_a_01].[dbo].[fabricantes]
WHERE  upper(fabricante_cod) like '%E%'
   and tiempo_entrega > 5;


/*Seleccionar los fabricantes que contengan como segundo carácter del nombre las letras e, f, g,
   h, i o j.*/
SELECT *
FROM   [uade_a_01].[dbo].[fabricantes]
WHERE  upper(fabricante_nom) like '_[ehij]%';


/*Listar el número de factura, línea_num, precio total (p x q) de
   todo lo facturado ordenado por
   número de factura y renglón.*/
SELECT factura_num,
       renglon as linea_num,
       precio_unit * cantidad as 'precio total'
FROM   uade_a_01.dbo.facturas_det
ORDER BY factura_num, renglon;


/*Obtener la cantidad total de facturas.
   También la fecha de emisión de la primera y la última.*/
SELECT count(*),
       min(fecha_emision),
       max(fecha_emision)
FROM   uade_a_01.dbo.facturas;


/*Obtener el número de factura y el monto total por factura*/
SELECT factura_num,
       sum(precio_unit * cantidad) as 'precio total'
FROM   uade_a_01.dbo.facturas_det
GROUP BY factura_num;


/*Mostrar la cantidad total de Clientes por Provincia*/
SELECT count(*),
       p.provincia_desc
FROM   uade_a_01.dbo.clientes c
    LEFT JOIN uade_a_01.dbo.provincias p
        ON c.provincia_cod = p.provincia_cod
GROUP BY p.provincia_desc;


/*Obtener el monto total promedio de facturación.*/
SELECT sum(precio_unit * cantidad)/count(distinct factura_num) as 'precio promedio'
FROM   uade_a_01.dbo.facturas_det;


/*Seleccionar la cantidad de facturas
   por cliente para aquellos números de clientes mayores a 108*/
SELECT count(1),
       cliente_num
FROM   uade_a_01.dbo.facturas
WHERE  cliente_num > 108
GROUP BY cliente_num;


/*Seleccionar la cantidad de facturas
   por cliente para aquellos números de clientes mayores a 104
   que tengan 2 facturas o mas.*/
SELECT count(1),
       cliente_num
FROM   uade_a_01.dbo.facturas
WHERE  cliente_num > 104
GROUP BY cliente_num having count(1) > 1;


/*Seleccionar nombre, apellido y teléfono de los clientes.
   Si él cliente no tiene teléfono mostrar el
   mensaje SIN TELEFONO*/
SELECT nombre,
       apellido,
       coalesce(telefono, 'SIN TELEFONO')
FROM   uade_a_01.dbo.clientes;


/*Seleccionar nombre, apellido y domicilio de los clientes.
   Si el cliente no tiene domicilio, mostrar
   el teléfono y si no tiene ninguno de los dos mostrar el mensaje SIN DATOS.*/
SELECT nombre,
       apellido,
       coalesce(domicilio, telefono, 'SIN DATOS')
FROM   uade_a_01.dbo.clientes;


/*Seleccionar el código y nombre del fabricante y el mensaje LENTO, NORMAL o RÁPIDO
   dependiendo si su tiempo de entrega es menor a 5, igual a 5 o mayor a 5.*/
SELECT fabricante_cod,
       fabricante_nom,
       case when tiempo_entrega > 5 then 'LENTO'
            when tiempo_entrega = 5 then 'NORMAL'
            when tiempo_entrega < 5 then 'RAPIDO'
            else 'INDEFINIDO' end
FROM   uade_a_01.[dbo].[fabricantes];


/*Mostrar el número de factura y el promedio facturado por renglón (p x q)
   de cada factura,
   ordenado por el promedio en forma descendente.*/
SELECT avg(precio_unit * cantidad) as 'precio promedio por factura',
       factura_num
FROM   uade_a_01.dbo.facturas_det
GROUP BY factura_num
ORDER BY 1 desc;


/*Seleccionar número de cliente, código de fabricante y monto total comprado por cada par
   cliente-fabricante.
   Mostrar la información ordenada por cliente en forma ascendente y total
   comprado en forma descendente*/
SELECT cliente_num,
       fabricante_cod,
       sum(a.precio_unit * cantidad)
FROM   uade_a_01.dbo.facturas_det a
    LEFT JOIN uade_a_01.dbo.productos p
        ON p.producto_cod = a.producto_cod
    LEFT JOIN uade_a_01.dbo.facturas b
        ON b.factura_num = a.factura_num
GROUP BY cliente_num, fabricante_cod;
