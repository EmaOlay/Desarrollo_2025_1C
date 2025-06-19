/*Crear la vista ProductosFabricanteV que tenga los siguientes atributos: producto_cod,
producto_desc, precio_unit, fabricante_cod, fabricante_nom, provincia_cod. Crear un trigger
sobre la vista anterior que ante un insert, inserte una fila en la tabla Productos. Si el fabricante no
existe, inserte una fila en dicha tabla con el campo lead_time en 1 validando también que exista
la provincia del fabricante. Los inserts pueden ser masivos, SI hay un error informarlo.*/
use uade_a_01;
Create view ProductosFabricanteV as
select producto_cod, producto_desc, precio_unit,
f.fabricante_cod, fabricante_nom, provincia_cod
from productos p 
join fabricantes f on p.fabricante_cod = f.fabricante_cod
WHERE 1=0;
 

SELECT *
FROM ProductosFabricanteV;

SELECT producto_cod, producto_desc, precio_unit,
f.fabricante_cod, fabricante_nom, provincia_cod
-- INTO ProductosFabricanteV
from productos p 
join fabricantes f on p.fabricante_cod = f.fabricante_cod
WHERE producto_cod = 1000

INSERT INTO ProductosFabricanteV
VALUES(1000	,'Tornillos',	150.00,	'LACO',	'Laminados',	'BA')

ALTER trigger insertProdFabricanteTr on ProductosFabricanteV
INSTEAD OF Insert 
AS
begin
    declare @producto_cod int, @producto_desc varchar(30),
    @precio_unit decimal(10,2),
    @fabricante_cod varchar(5), @fabricante_nom varchar(20),
    @provincia_cod char(2)
    --
    DECLARE ProdFab_c CURSOR FOR
    select producto_cod, producto_desc, precio_unit,
    fabricante_cod, fabricante_nom, provincia_cod
    from INSERTED;
    --
    OPEN ProdFab_c
    fetch ProdFab_c into @producto_cod, @producto_desc, @precio_unit,
    @fabricante_cod, @fabricante_nom, @provincia_cod
    while @@FETCH_STATUS = 0
    begin
        if not exists (select 1 from fabricantes
        where fabricante_cod = @fabricante_cod)
        begin
            if not exists (select 1 from provincias
            where provincia_cod = @provincia_cod)
                THROW 50001, 'Provincia Inexistente', 1
            -- Probar raiserror
            -- RAISERROR('Provincia Inexistente', 16, 1);
            insert into fabricantes (fabricante_cod, fabricante_nom,
            tiempo_entrega, provincia_cod)
            values (@fabricante_cod, @fabricante_nom, 1, @provincia_cod);
        end

        if exists (select 1 from productos
        where producto_cod = @producto_cod)
            BEGIN
                THROW 50002, 'Producto ya existe', 1
            -- Probar raiserror
                -- RAISERROR('Producto ya existe', 16, 1);
                -- RETURN;
            END;
        insert into productos (producto_cod, producto_desc,
        precio_unit, fabricante_cod)
        values (@producto_cod, @producto_desc, @precio_unit,
        @fabricante_cod);
        fetch ProdFab_c into @producto_cod, @producto_desc, @precio_unit,
        @fabricante_cod, @fabricante_nom, @provincia_cod
    end
    CLOSE ProdFab_c
    DEALLOCATE ProdFab_c
    --
END