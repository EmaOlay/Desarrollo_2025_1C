/*UADE BD-Clase 06-SQL-Ejercicios.pdf*/

/*Seleccionar los fabricantes que tengan una ‘E’ en el código y su tiempo de entrega sea mayor a 5
días.*/

SELECT *
FROM [UADE_A_01].[dbo].[fabricantes]
WHERE UPPER(fabricante_cod) LIKE '%E%'
AND tiempo_entrega > 5

/*Seleccionar los fabricantes que contengan como segundo carácter del nombre las letras e, f, g,
h, i o j.*/

SELECT *
FROM [UADE_A_01].[dbo].[fabricantes]
WHERE UPPER(fabricante_nom) LIKE '_[ehij]%'

/*Listar el número de factura, línea_num, precio total (p x q) de 
todo lo facturado ordenado por
número de factura y renglón.*/

SELECT factura_num,
renglon as linea_num,
precio_unit * cantidad as 'precio total'
FROM UADE_A_01.dbo.facturas_det
ORDER BY factura_num, renglon

/*Obtener la cantidad total de facturas.
También la fecha de emisión de la primera y la última.*/
SELECT COUNT(*),MIN(fecha_emision),MAX(fecha_emision)
FROM UADE_A_01.dbo.facturas

/*Obtener el número de factura y el monto total por factura*/
SELECT factura_num,
SUM(precio_unit * cantidad) as 'precio total'
FROM UADE_A_01.dbo.facturas_det
GROUP BY factura_num

/*Mostrar la cantidad total de Clientes por Provincia*/
SELECT COUNT(*),p.provincia_desc
FROM UADE_A_01.dbo.Clientes c
LEFT JOIN UADE_A_01.dbo.provincias p ON c.Provincia_cod = p.Provincia_cod
GROUP BY p.provincia_desc

/*Obtener el monto total promedio de facturación.*/
SELECT SUM(precio_unit * cantidad)/COUNT( DISTINCT factura_num) as 'precio promedio'
FROM UADE_A_01.dbo.facturas_det

/*Seleccionar la cantidad de facturas 
por cliente para aquellos números de clientes mayores a 108*/
SELECT COUNT(1),cliente_num
FROM UADE_A_01.dbo.facturas
WHERE cliente_num > 108
GROUP BY cliente_num

/*Seleccionar la cantidad de facturas 
por cliente para aquellos números de clientes mayores a 104
que tengan 2 facturas o mas.*/
SELECT COUNT(1),cliente_num
FROM UADE_A_01.dbo.facturas
WHERE cliente_num > 104
GROUP BY cliente_num
HAVING COUNT(1)>1

/*Seleccionar nombre, apellido y teléfono de los clientes. 
Si él cliente no tiene teléfono mostrar el
mensaje SIN TELEFONO*/
SELECT nombre, apellido, COALESCE(telefono, 'SIN TELEFONO')
FROM UADE_A_01.dbo.Clientes

/*Seleccionar nombre, apellido y domicilio de los clientes. 
Si el cliente no tiene domicilio, mostrar
el teléfono y si no tiene ninguno de los dos mostrar el mensaje SIN DATOS.*/
SELECT nombre, apellido, COALESCE(domicilio,telefono, 'SIN DATOS')
FROM UADE_A_01.dbo.Clientes

/*Seleccionar el código y nombre del fabricante y el mensaje LENTO, NORMAL o RÁPIDO
dependiendo si su tiempo de entrega es menor a 5, igual a 5 o mayor a 5.*/
SELECT Fabricante_cod,fabricante_nom,
CASE WHEN tiempo_entrega > 5 THEN 'LENTO'
WHEN tiempo_entrega = 5 THEN 'NORMAL'
WHEN tiempo_entrega < 5 THEN 'RAPIDO'
ELSE 'INDEFINIDO'
END
FROM UADE_A_01.[dbo].[fabricantes]

/*Mostrar el número de factura y el promedio facturado por renglón (p x q)
 de cada factura,
ordenado por el promedio en forma descendente.*/

SELECT AVG(precio_unit * cantidad) as 'precio promedio por factura',
factura_num
FROM UADE_A_01.dbo.facturas_det
GROUP BY factura_num
ORDER BY 1 DESC

/*Seleccionar número de cliente, código de fabricante y monto total comprado por cada par
cliente-fabricante. 
Mostrar la información ordenada por cliente en forma ascendente y total
comprado en forma descendente*/
SELECT cliente_num, fabricante_cod,SUM(a.precio_unit * cantidad)
FROM UADE_A_01.dbo.facturas_det a
LEFT JOIN UADE_A_01.dbo.productos p ON p.producto_cod = a.producto_cod
LEFT JOIN UADE_A_01.dbo.facturas b ON b.factura_num = a.factura_num
GROUP BY cliente_num, fabricante_cod