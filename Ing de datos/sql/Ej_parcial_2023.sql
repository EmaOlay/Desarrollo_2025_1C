/*
Desarrollar un PROCEDURE que realice la insercion o modificacion de un producto determinado.
Parametros de entrada: producto_cod, producto_desc, fabricante_cod, fabricante_nom, precio_unit.

Previamente a realizar algunas operaciones validar:
    La existencia del fabricante en la tabla respectiva - si no existe, crearlo
Hechas las verificaciones, si el producto no existe, insertarlo. En caso de que el producto ya existe, actualizar los atributos no clave. 

En caso de error deshacer TODAS las opereraciones que se pudieran haber realizado. 
*/
USE [UADE_A_01];
SELECT *
FROM Productos
WHERE producto_cod = 9999;

SELECT *
FROM Fabricantes
WHERE fabricante_cod = 'YO';

DROP PROCEDURE IF EXISTS insertar_o_modificar_producto;
CREATE PROCEDURE insertar_o_modificar_producto(
    @producto_cod INT,
    @producto_desc VARCHAR(255),
    @fabricante_cod VARCHAR(5),
    @fabricante_nom VARCHAR(255),
    @precio_unit DECIMAL(10, 2)
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Fabricantes WHERE fabricante_cod = @fabricante_cod)
    BEGIN
        -- Si el fabricante no existe, lo insertamos
        INSERT INTO Fabricantes (fabricante_cod, fabricante_nom)
        VALUES (@fabricante_cod, @fabricante_nom);
    END
    IF NOT EXISTS (SELECT 1 FROM Productos WHERE producto_cod = @producto_cod)
    BEGIN
        -- Si el producto no existe, lo insertamos
        INSERT INTO Productos (producto_cod, producto_desc, fabricante_cod, precio_unit)
        VALUES (@producto_cod, @producto_desc, @fabricante_cod, @precio_unit);
    END
    ELSE
    BEGIN
        -- Si el producto ya existe, lo actualizamos
        UPDATE Productos
        SET
            producto_desc = @producto_desc,
            fabricante_cod = @fabricante_cod,
            precio_unit = @precio_unit
        WHERE producto_cod = @producto_cod;
    END
END

EXEC insertar_o_modificar_producto
    @producto_cod = 9999,
    @producto_desc = 'Falopa',
    @fabricante_cod = 'YO',
    @fabricante_nom = 'Ema',
    @precio_unit = 999.00;