use [uade_a_01]


CREATE TABLE #ComprasHist (
    cliente_num INT,
    total_compra DECIMAL(10, 2)
);
CREATE TABLE #Errores (
    cliente_num INT,
    mensaje NVARCHAR(255)
);

/*
Crear un procedimiento totalCompraPr al cual se le envíe como parámetro un número de cliente
“desde” y un número de cliente “hasta”. El procedimiento deberá calcular y guardar en la tabla
ComprasHist el número de cliente y el monto total comprado de cada cliente. Si el cliente ya
existe, registrar en una tabla de errores el número de cliente y el mensaje de error “Cliente ya
existente” y continuar con el procesamiento de los demás clientes. Devolver por pantalla un
total de control que indique la cantidad de clientes leídos, la cantidad de clientes grabados y la
cantidad de erróneos.
*/

CREATE PROCEDURE totalCompraPr(@num_cliente_desde INT, @num_cliente_hasta INT)
AS 
BEGIN

    -- Create ComprasHist temporal as
    DECLARE mi_cursor CURSOR FOR 
        SELECT c.cliente_num,
       SUM(fd.cantidad * fd.precio_unit) AS total_compra
        FROM   clientes c
            INNER JOIN facturas f
                ON c.cliente_num = f.cliente_num
            INNER JOIN facturas_det fd
                ON f.factura_num = fd.factura_num
            INNER JOIN productos p
                ON fd.producto_cod = p.producto_cod
        WHERE c.cliente_num BETWEEN @num_cliente_desde AND @num_cliente_hasta
        GROUP BY c.cliente_num, c.nombre, c.apellido;
    DECLARE @num_cliente INT, @total_compra DECIMAL(10, 2);
    DECLARE @cantidad_leidos INT = 0, @cantidad_grabados INT = 0, @cantidad_errores INT = 0;
    OPEN mi_cursor;
    FETCH NEXT FROM mi_cursor INTO @num_cliente, @total_compra;
    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @cantidad_leidos = @cantidad_leidos + 1;
        IF EXISTS (SELECT 1 FROM #ComprasHist WHERE cliente_num = @num_cliente)
        BEGIN
            INSERT INTO #Errores (cliente_num, mensaje)
            VALUES (@num_cliente, 'Cliente ya existente');
            SET @cantidad_errores = @cantidad_errores + 1;
        END
        ELSE
        BEGIN
            INSERT INTO #ComprasHist (cliente_num, total_compra)
            VALUES (@num_cliente, @total_compra);
            SET @cantidad_grabados = @cantidad_grabados + 1;
        END
        FETCH NEXT FROM mi_cursor INTO @num_cliente, @total_compra;
    END
    CLOSE mi_cursor;
    DEALLOCATE mi_cursor;
    -- Mostrar resultados
    PRINT 'Total de clientes leídos: ' + CAST(@cantidad_leidos AS NVARCHAR(10));
    PRINT 'Total de clientes grabados: ' + CAST(@cantidad_grabados AS NVARCHAR(10));
    PRINT 'Total de errores: ' + CAST(@cantidad_errores AS NVARCHAR(10));
END;

EXEC totalCompraPr 101, 104;

EXEC totalCompraPr 103, 106;


SELECT *
FROM #ComprasHist

SELECT *
FROM #Errores;



/*
Seleccionar número de cliente, nombre, monto total comprado, y número, nombre y monto
total comprado de un cliente 2, y comparándolos y mostrándolos de a pares. El monto del
primer cliente deberá ser mayor al del segundo. Mostrar la consulta ordenada por número de
cliente y monto del segundo cliente.
*/
WITH CTE_cliente1 AS (
    SELECT 
        c.cliente_num,
        c.nombre,
        SUM(fd.cantidad * fd.precio_unit) AS total_compra
    FROM 
        clientes c
        INNER JOIN facturas f ON c.cliente_num = f.cliente_num
        INNER JOIN facturas_det fd ON f.factura_num = fd.factura_num
    GROUP BY 
        c.cliente_num, c.nombre
),
CTE_cliente2 AS (
        SELECT 
        c.cliente_num,
        c.nombre,
        SUM(fd.cantidad * fd.precio_unit) AS total_compra
    FROM 
        clientes c
        INNER JOIN facturas f ON c.cliente_num = f.cliente_num
        INNER JOIN facturas_det fd ON f.factura_num = fd.factura_num
    GROUP BY 
        c.cliente_num, c.nombre
)
SELECT 
    c1.cliente_num AS cliente1_num,
    c1.nombre AS cliente1_nombre,
    c1.total_compra AS cliente1_total_compra,
    c2.cliente_num AS cliente2_num,
    c2.nombre AS cliente2_nombre,
    c2.total_compra AS cliente2_total_compra
FROM
    CTE_cliente1 c1
    INNER JOIN CTE_cliente2 c2 ON c1.total_compra > c2.total_compra
ORDER BY
    c2.cliente_num, c2.total_compra;


/*Se requiere listar para la provincia de Buenos Aires
 el par de clientes que sean los que suman el
mayor monto facturado, con el formato de salida:
'Nombre Provincia', 'Apellido, Nombre', 'Apellido, Nombre', 'Total Solicitado' (*)
(*) El total solicitado contendrá la suma de los dos clientes. */
WITH CTE AS (
    SELECT TOP 2
        c.cliente_num,
        c.nombre,
        SUM(fd.cantidad * fd.precio_unit) AS total_compra
    FROM 
        clientes c
        INNER JOIN facturas f ON c.cliente_num = f.cliente_num
        INNER JOIN facturas_det fd ON f.factura_num = fd.factura_num
    WHERE c.provincia_cod = 'BA'
    GROUP BY 
        c.cliente_num, c.nombre
    ORDER BY 
        total_compra DESC
)
SELECT
        c.cliente_num,
        c.nombre,
        SUM(total_compra) OVER () AS total_compra
    FROM 
        CTE c



/*Seleccionar los clientes de mayor monto comprador de cada provincia o,
 lo que es lo mismo, por
cada provincia el cliente de mayor facturación.*/

WITH CTE AS (
    SELECT 
        c.cliente_num,
        c.provincia_cod,
        SUM(fd.cantidad * fd.precio_unit) AS total_compra,
        ROW_NUMBER() OVER (PARTITION BY c.provincia_cod ORDER BY SUM(fd.cantidad * fd.precio_unit) DESC) AS rn
    FROM 
        clientes c
        INNER JOIN facturas f ON c.cliente_num = f.cliente_num
        INNER JOIN facturas_det fd ON f.factura_num = fd.factura_num
    GROUP BY 
        c.cliente_num, c.nombre, c.apellido, c.provincia_cod
)
SELECT c.cliente_num,
        c.provincia_cod,
        c.total_compra
FROM
    CTE c
WHERE 
    c.rn = 1


/*
Crear un trigger que ante cambios (inserts, borrados, modificaciones)
 en las filas factura_detalle,
mantenga un atributo montoTOTAL 
de la tabla facturas con el monto correcto (p x q).
*/
SELECT TOP 0 *, 0 AS montoTOTAL
INTO factura_detalle_mio
FROM facturas_det;

CREATE TRIGGER trg_UpdateMontoTotal
ON factura_detalle_mio
AFTER INSERT, UPDATE, DELETE
AS 
BEGIN
    DECLARE @total DECIMAL(10, 2);
    -- Calcular el monto total de la factura
    SELECT @total = SUM(fd.cantidad * fd.precio_unit)
    FROM factura_detalle_mio fd;
    -- Actualizar el monto total en la tabla facturas
    UPDATE factura_detalle_mio
    SET montoTOTAL = @total;
END;

SELECT TOP 1 *, 0 AS montoTOTAL
FROM facturas_det;

INSERT INTO factura_detalle_mio 
VALUES(1,1,1000,10,140.00,0)

SELECT *
FROM factura_detalle_mio;

/*
Crear un trigger que ante compras efectuadas por los clientes, valide lo siguiente:
Que los fabricantes de CABA o Buenos Aires 
puedan vender a clientes de todo el país excepto a
los de Tierra del Fuego.
Que los demás fabricantes de otras provincias solo puedan vender mercaderías
a clientes de las mismas provincias.
Asuma que las operaciones son de una misma factura que puede tener varios renglones.
*/
DROP TRIGGER trg_ValidarCompras;
CREATE TRIGGER trg_ValidarCompras
ON factura_detalle_mio
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @cliente_num INT, @provincia_cod NVARCHAR(2), @fabricante_provincia NVARCHAR(2);
    DECLARE @fabricante_cod VARCHAR(5),@producto_cod smallint;

    -- Validar cada fila insertada
    DECLARE cur CURSOR FOR 
        SELECT cliente_num, producto_cod 
        FROM inserted
        JOIN facturas f ON inserted.factura_num = f.factura_num;
    
    OPEN cur;
    FETCH NEXT FROM cur INTO @cliente_num ,@producto_cod;
    
    WHILE @@FETCH_STATUS = 0
    BEGIN

        SELECT @cliente_num = f.cliente_num FROM inserted
        JOIN facturas f ON inserted.factura_num = f.factura_num;
    
        -- Obtener la provincia del cliente
        SELECT @provincia_cod = provincia_cod 
        FROM clientes 
        WHERE cliente_num = @cliente_num;

        -- Obtener la provincia del Fabricante
        SELECT @fabricante_provincia = provincia_cod 
        FROM fabricantes f
        JOIN productos p ON f.fabricante_cod = p.fabricante_cod
        WHERE producto_cod = @producto_cod;

        IF (@provincia_cod  = 'TF' AND @fabricante_provincia IN ('CABA', 'BA')) OR
           (@provincia_cod NOT IN ('CABA', 'BA') AND @fabricante_provincia <> @provincia_cod)
        BEGIN
            RAISERROR('Compra no permitida: El fabricante de la provincia %s no puede vender al cliente de la provincia %s.', 16, 1, @fabricante_provincia, @provincia_cod);
            RETURN; -- Detener la ejecución del trigger
        END
        RAISERROR('Compra no permitida: El fabricante de la provincia %s no puede vender al cliente de la provincia %s.', 16, 1, @fabricante_provincia, @provincia_cod);
        -- Insertar a factura_det_mio si las validaciones pasan
        INSERT INTO facturas_det_mio (factura_num, producto_cod, cantidad, precio_unit, montoTotal)
        SELECT factura_num, @producto_cod, cantidad, precio_unit, montoTotal
        FROM inserted


        FETCH NEXT FROM cur INTO @producto_cod, @fabricante_provincia;
    END
    
    CLOSE cur;
    DEALLOCATE cur;
END;

INSERT INTO factura_detalle_mio   
    SELECT *,0  
    FROM facturas_det
    WHERE factura_num IN (1,2,3);

SELECT *
FROM facturas_det;