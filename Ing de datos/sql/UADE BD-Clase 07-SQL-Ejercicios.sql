-- UADE BD-Clase 07-SQL-Ejercicios


use [uade_a_01]
-- 1. Seleccionar cliente_num, nombre, apellido y número de factura de los clientes.
SELECT i.cliente_num,
       nombre,
       apellido,
       factura_num
FROM   [dbo].[clientes] i
    INNER JOIN [dbo].[facturas] j
        ON i.cliente_num = j.cliente_num
ORDER BY i.cliente_num
-- Seleccionar cliente_num, nombre, apellido y número de facturas de los clientes hayan
-- comprado algo o no.
SELECT i.cliente_num,
       nombre,
       apellido,
       factura_num,
       case when factura_num is null then 'No compro'
            else 'compro' end
FROM   [dbo].[clientes] i
    LEFT JOIN [dbo].[facturas] j
        ON i.cliente_num = j.cliente_num
ORDER BY i.cliente_num
-- Seleccionar cliente_num, nombre, apellido y número de factura de los clientes hayan comprado
-- algo o no. Mostrar también aquellas facturas que no tengan un cliente asociado.
SELECT i.cliente_num,
       nombre,
       apellido,
       factura_num,
       case when factura_num is null then 'No compro'
            else 'compro' end as tienefactura,
       case when j.cliente_num is null then 'No tiene cliente'
            else 'Tiene Cliente' end as tienecliente
FROM   [dbo].[clientes] i full
    OUTER JOIN [dbo].[facturas] j
        ON i.cliente_num = j.cliente_num
ORDER BY i.cliente_num
-- Seleccionar el cliente, nombre y apellido concatenados y separados por una coma, y monto total
-- comprado por cliente para aquellos clientes de ‘BA’ que hayan comprado por mas de $15000.
SELECT i.cliente_num,
       nombre+', '+apellido,
       sum(cantidad*precio_unit) as tot_comprado
FROM   [dbo].[clientes] i
    INNER JOIN [dbo].[facturas] j
        ON i.cliente_num = j.cliente_num
    LEFT JOIN [dbo].[facturas_det] k
        ON j.factura_num = k.factura_num
WHERE  trim(i.provincia_cod) = 'BA'
GROUP BY i.cliente_num, nombre+', '+apellido having sum(cantidad*precio_unit) > 15000
ORDER BY i.cliente_num
-- Seleccionar monto total comprado por cliente y el monto promedio de sus facturas para
-- aquellos clientes que tengan más de 2 facturas. Ordenado por total comprado de manera
-- descendente.
SELECT i.cliente_num,
       nombre+', '+apellido,
       sum(cantidad*precio_unit) as tot_comprado,
       sum(cantidad*precio_unit)/count(distinct j.factura_num) as promedio_factura
FROM   [dbo].[clientes] i
    INNER JOIN [dbo].[facturas] j
        ON i.cliente_num = j.cliente_num
    LEFT JOIN [dbo].[facturas_det] k
        ON j.factura_num = k.factura_num
GROUP BY i.cliente_num, nombre+', '+apellido having count(distinct j.factura_num) > 2
ORDER BY tot_comprado desc
-- 6. Seleccionar cliente_num, nombre, apellido, codigo y descripción de producto y monto total de
-- los productos comprados por cliente ordenados por cliente y producto en forma ascendente y
-- monto en forma descendente.
SELECT i.cliente_num,
       i.nombre,
       i.apellido,
       k.producto_cod,
       p.producto_desc,
       sum(cantidad*k.precio_unit) as tot_comprado
FROM   [dbo].[clientes] i
    INNER JOIN [dbo].[facturas] j
        ON i.cliente_num = j.cliente_num
    LEFT JOIN [dbo].[facturas_det] k
        ON j.factura_num = k.factura_num
    LEFT JOIN [dbo].[productos] p
        ON p.producto_cod = k.producto_cod
GROUP BY i.cliente_num, i.nombre, i.apellido, k.producto_cod, p.producto_desc
ORDER BY i.cliente_num desc, k.producto_cod desc, tot_comprado asc
-- 7. Seleccionar los nombres y apellidos de los clientes junto con los de sus referentes.
SELECT i.nombre,
       i.apellido,
       j.nombre,
       j.apellido
FROM   [dbo].[clientes] i
    INNER JOIN [dbo].[clientes] j
        ON i.cliente_ref = j.cliente_num
        -- 8. Seleccionar una lista de los códigos de productos identificando con una leyenda si fue comprado
        -- o no.
SELECT DISTINCT p.producto_cod,
                case when factura_num is null then 'No Compraron'
                     else 'Compraron' end as tienefactura
FROM   [dbo].[productos] p
    LEFT JOIN [dbo].[facturas_det] k
        ON p.producto_cod = k.producto_cod
ORDER BY p.producto_cod
-- 9. Seleccionar la lista de productos del fabricante ‘CASA’ que fueron comprados.
SELECT DISTINCT p.producto_cod,
                case when factura_num is null then 'No Compraron'
                     else 'Compraron' end as tienefactura
FROM   [dbo].[productos] p
    LEFT JOIN [dbo].[facturas_det] k
        ON p.producto_cod = k.producto_cod
    LEFT JOIN [dbo].[fabricantes] j
        ON p.fabricante_cod = j.fabricante_cod
WHERE  upper(trim(j.fabricante_cod)) = 'CASA'
ORDER BY p.producto_cod
-- 10. Seleccionar código de producto y descripción de aquellos productos del fabricante ‘EXPO’ que
-- no hayan sido comprados.
SELECT DISTINCT p.producto_cod,
                p.producto_desc
FROM   [dbo].[productos] p
    LEFT JOIN [dbo].[facturas_det] k
        ON p.producto_cod = k.producto_cod
    LEFT JOIN [dbo].[fabricantes] j
        ON p.fabricante_cod = j.fabricante_cod
WHERE  upper(trim(j.fabricante_cod)) = 'EXPO'
   and factura_num is null
ORDER BY p.producto_cod
-- No entendi consigna o empty dataset?
-- 11. Seleccione los códigos y descripciones de los productos de los fabricantes ‘CASA’ y ‘DOTO’ de 3
-- maneras distintas.
-- Alterno duplas?
-- 12. Seleccione los productos en común que hayan comprado los clientes 103, 114 y 106.
SELECT DISTINCT p.producto_cod,
                p.producto_desc
FROM   [dbo].[productos] p
    LEFT JOIN [dbo].[facturas_det] k
        ON p.producto_cod = k.producto_cod
    LEFT JOIN [dbo].[facturas] i
        ON i.factura_num = k.factura_num
    LEFT JOIN [dbo].[fabricantes] j
        ON p.fabricante_cod = j.fabricante_cod
WHERE  cliente_num in (103, 114, 106) -- Claro bolas intersect no IN jajajja
   and i.factura_num is not null
ORDER BY p.producto_cod;
