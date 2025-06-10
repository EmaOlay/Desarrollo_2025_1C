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