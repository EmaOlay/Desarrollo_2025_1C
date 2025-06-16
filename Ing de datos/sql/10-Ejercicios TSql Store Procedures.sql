/*Escribir una sentencia SELECT que devuelva el número de factura, fecha de emisión y el nombre
del día de la semana en español de la fecha de emisión de todas las facturas que no han sido
pagadas.
Nota: SET @DIA = datepart(weekday,@fecha)
Devuelve en la variable @DIA el nro. de día de la semana , comenzando con 1 Domingo hasta 7
Sábado.*/
use uade_a_01;
GO

CREATE PROCEDURE DiaDeLaSemanaFactura
(
    @FacturaNum INT
)
AS
BEGIN
    SELECT f.factura_num,
       f.fecha_emision,
       CASE WHEN DATEPART(weekday, f.fecha_emision) = 1 THEN 'Domingo'
            WHEN DATEPART(weekday, f.fecha_emision) = 2 THEN 'Lunes'
            WHEN DATEPART(weekday, f.fecha_emision) = 3 THEN 'Martes'
            WHEN DATEPART(weekday, f.fecha_emision) = 4 THEN 'Miércoles'
            WHEN DATEPART(weekday, f.fecha_emision) = 5 THEN 'Jueves'
            WHEN DATEPART(weekday, f.fecha_emision) = 6 THEN 'Viernes'
            WHEN DATEPART(weekday, f.fecha_emision) = 7 THEN 'Sábado'
       END AS dia_semana
    FROM facturas f
    WHERE f.factura_num = @FacturaNum 
END
GO

exec DiaDeLaSemanaFactura 1;

/*Escribir un Select que devuelva código de fabricant y todos los Productos que fabrica separados
,
entre sí por coma ( ). Asimismo mostrar en otra columna si el fabricante posee ventas o no (S o
N). Utilizar una función para resolver la columna de productos. Tiene libertad para resolver la de
posee ventas como crea conveniente.*/

use uade_a_01;
GO
CREATE PROCEDURE productosPorFabricante
(
    @FabricanteID VARCHAR(50) -- Asumiendo que el código del fabricante es de tipo VARCHAR
)
AS
BEGIN
    SELECT f.fabricante_cod, 
    STRING_AGG(p.producto_desc,', ') AS productos,
           CASE WHEN EXISTS (
                SELECT 1 
                FROM facturas_det fd
                LEFT JOIN productos p ON p.producto_cod = fd.producto_cod
                LEFT JOIN fabricantes f ON f.fabricante_cod = p.fabricante_cod 
                WHERE p.fabricante_cod = f.fabricante_cod) THEN 'S' 
                    ELSE 'N' 
                        END AS posee_ventas
    FROM fabricantes f
    LEFT JOIN productos p ON f.fabricante_cod = p.fabricante_cod
    WHERE f.fabricante_cod = @FabricanteID
    GROUP BY f.fabricante_cod
END
GO

exec productosPorFabricante 'CASA';

/*Crear la tabla temporal ComprasClientes con los campos cliente_num (int, pk),
CantFacturas(int), MontoTotal (decimal(12,2)).
Crear un procedimiento SumaCompras que inserte (si no existe) o modifique el registro de la
tabla ComprasClientes con la siguiente información:
CantFacturas: Cantidad de órdenes de cada cliente (no nulo).
MontoTotal: Monto total comprado por el cliente (p x q)*/

CREATE TABLE #ComprasClientes(
    cliente_num INT PRIMARY KEY,  
    cantFacturas INT,
    montoTotal DECIMAL(12, 2)
)

CREATE PROCEDURE SumaCompras
(
    @ClienteNum INT
)
AS
BEGIN
    
    


