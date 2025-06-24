-- PArcial de 17/06/2025
--6.
WITH CTE AS (
	Select 
	fab.fabricante_cod,fabricante_nom,
	c.cliente_num,c.nombre as nombre_cliente,
	SUM(cantidad * d.precio_unit) as monto_total_vendido,
	COUNT(DISTINCT f.factura_num) as cantidad_de_ordenes
	 from clientes c 
	 join facturas f on c.cliente_num = f.cliente_num
	 join facturas_det d on d.factura_num = f.factura_num
	 JOIN productos p on d.producto_cod = p.producto_cod
	 JOIN fabricantes fab ON fab.fabricante_cod = p.fabricante_cod
	WHERE fab.tiempo_entrega < 5 -- filtro tiempo de entrega
	group by fab.fabricante_cod,fabricante_nom,
	c.cliente_num,c.nombre
)
SELECT *
FROM CTE
WHERE monto_total_vendido > 3000 -- Filtro monto total vendido
ORDER BY fabricante_cod ASC, monto_total_vendido DESC
GO

--7.
CREATE PROCEDURE procesarCleintesPR
AS
BEGIN
	-- Agrego funcionalidad para depurar Novedades?
	DECLARE 
	@cliente_num	INT,
    @apellido		VARCHAR(15),
	@nombre			VARCHAR(15),
	@empresa		VARCHAR(20),
    @provincia_cod	VARCHAR(2);

	DECLARE c_nuevos CURSOR FOR
	select cliente_num,apellido,nombre,empresa,provincia_cod
	from #NovedadesClientes;
	OPEN c_nuevos;
	FETCH NEXT FROM c_nuevos into @cliente_num,@apellido,@nombre,@empresa,@provincia_cod	
	WHILE (@@FETCH_STATUS = 0)
	BEGIN
		BEGIN TRY
		BEGIN TRANSACTION
		IF EXISTS (SELECT 1 FROM clientes WHERE cliente_num = @cliente_num)
		BEGIN
			-- Si el cliente existe, lo actualizamos
			UPDATE clientes
			SET 
				apellido = @apellido, 
				nombre = @nombre, 
				empresa = @empresa, 
				provincia_cod =@provincia_cod
			WHERE cliente_num = @cliente_num;
		END
		ELSE
		BEGIN
			-- Si el cliente NO existe, lo insertamos
			INSERT INTO clientes(cliente_num,apellido,nombre,empresa,provincia_cod)
			VALUES(@cliente_num,@apellido,@nombre,@empresa,@provincia_cod);
		END
		commit
		end try
		begin catch
			 rollback
			 print 'Nro. Error:' + cast(ERROR_NUMBER() as varchar);
			 print 'Mensaje:' + ERROR_MESSAGE();
			 print 'Status:' + cast(ERROR_STATE() as varchar);
		end catch
		FETCH NEXT FROM c_nuevos into @cliente_num,@apellido,@nombre,@empresa,@provincia_cod
	END
	CLOSE c_nuevos;
	DEALLOCATE c_nuevos; 
END

-- Creo la tabla
SELECT cliente_num,apellido,nombre,empresa,provincia_cod
--INTO #NovedadesClientes
FROM clientes
--WHERE 1=2;
-- Inserto el 101 en noveddes con otro apellido
INSERT INTO #NovedadesClientes
VALUES(101,	'De Marco2',	'Horacio',	'Ganadera',	'CF')
-- Que falle la FK sobre provincia
INSERT INTO #NovedadesClientes
VALUES(101,	'De Marco2',	'Horacio',	'Ganadera',	'ZZ')

SELECT *
FROM #NovedadesClientes

EXEC procesarCleintesPR

SELECT cliente_num,apellido,nombre,empresa,provincia_cod
FROM clientes
WHERE cliente_num = 101


--8.
CREATE TABLE ListaPreciosHist(
producto_cod	INT,
Fecha_modif		DATETIME,
precioViejo		DECIMAL(12,2),
precioNuevo		DECIMAL(12,2)
)

GO
CREATE TRIGGER tgr_append_ListaPreciosHist 
on productos
AFTER UPDATE AS
begin
	 declare @producto_cod	INT,
			 @Fecha_modif	DATETIME,	
			 @precioViejo	DECIMAL(12,2),	
			 @precioNuevo	DECIMAL(12,2);

	DECLARE miCursor CURSOR FOR
	select 
	i.producto_cod,
	CURRENT_TIMESTAMP,
	d.precio_unit, -- precio viejo
	i.precio_unit -- precio nuevo
	 from inserted i
	 JOIN deleted d ON i.producto_cod = d.producto_cod
	 AND d.precio_unit <> i.precio_unit; -- Esto me filtra solo los que cambian de precio

	 OPEN miCursor;
	 FETCH NEXT FROM miCursor into @producto_cod,@Fecha_modif,@precioViejo,@precioNuevo
	WHILE (@@FETCH_STATUS = 0)
	BEGIN
		INSERT INTO ListaPreciosHist(producto_cod,Fecha_modif,precioViejo,precioNuevo)
		VALUES(@producto_cod,@Fecha_modif,@precioViejo,@precioNuevo)
	FETCH NEXT FROM miCursor into @producto_cod,@Fecha_modif,@precioViejo,@precioNuevo
	END
	CLOSE miCursor
	DEALLOCATE miCursor
END

SELECT *
FROM productos
-- No hace nada en mi tabla de historico
UPDATE productos
SET producto_desc = 'Tornillos 2'
WHERE producto_cod = 1000;
SELECT *
FROM ListaPreciosHist;

-- No hace nada en mi tabla de historico
UPDATE productos
SET precio_unit = 150.00
WHERE producto_cod = 1000;
SELECT *
FROM ListaPreciosHist;

-- No hace nada en mi tabla de historico para el producto_cod 1000
UPDATE productos
SET precio_unit = 150.00
WHERE producto_cod IN (1000,1001);
SELECT *
FROM ListaPreciosHist;

-- Actualizacion Masiva
UPDATE productos
SET precio_unit = 150.00
WHERE producto_cod IN (
1003,
1004,
1005,
1006
);
SELECT *
FROM ListaPreciosHist;