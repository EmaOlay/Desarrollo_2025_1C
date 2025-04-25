GO
CREATE DATABASE [UADE_A_01];
GO
USE [UADE_A_01];
GO
/****** Object:  Table [dbo].[borrar]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
/****** Object:  Table [dbo].[clientes]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;


go create table [dbo].[clientes]([cliente_num] [int] not null, [nombre] [varchar](15) not null, [apellido] [varchar](15) not null, [empresa] [varchar](20) null, [domicilio] [varchar](20) null, [ciudad] [varchar](25) null, [provincia_cod] [char](2) null, [codpostal] [char](5) null, [telefono] [varchar](18) null, [cliente_ref] [int] null, [estado] [char](1) null, primary key clustered ([cliente_num] asc)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks =
        ON, allow_page_locks =
        ON)
        ON [primary])
        ON [primary];
GO
/****** Object:  Table [dbo].[fabricantes]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;


go create table [dbo].[fabricantes]([fabricante_cod] [varchar](5) not null, [fabricante_nom] [varchar](20) not null, [tiempo_entrega] [smallint] null, [provincia_cod] [char](2) null, primary key clustered ([fabricante_cod] asc)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks =
        ON, allow_page_locks =
        ON)
        ON [primary])
        ON [primary];
GO
/****** Object:  Table [dbo].[facturas]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;


go create table [dbo].[facturas]([factura_num] [int] not null, [cliente_num] [int] null, [fecha_emision] [date] not null, [fecha_vto] [date] null, [fecha_pago] [datetime] null, primary key clustered ([factura_num] asc)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks =
        ON, allow_page_locks =
        ON)
        ON [primary])
        ON [primary];
GO
/****** Object:  Table [dbo].[facturas_det]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;


go create table [dbo].[facturas_det]([factura_num] [int] not null, [renglon] [smallint] not null, [producto_cod] [smallint] not null, [cantidad] [smallint] not null, [precio_unit] [decimal](10, 2) not null, primary key clustered ([factura_num] asc, [renglon] asc)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks =
        ON, allow_page_locks =
        ON)
        ON [primary])
        ON [primary];
GO
/****** Object:  Table [dbo].[productos]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;


go create table [dbo].[productos]([producto_cod] [smallint] not null, [producto_desc] [varchar](30) not null, [fabricante_cod] [varchar](5) null, [precio_unit] [decimal](10, 2) null, primary key clustered ([producto_cod] asc)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks =
        ON, allow_page_locks =
        ON)
        ON [primary])
        ON [primary];
GO
/****** Object:  Table [dbo].[provincias]    Script Date: 1/4/2024 16:18:13 ******/
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;


go create table [dbo].[provincias]([provincia_cod] [char](2) not null, [provincia_desc] [varchar](20) not null, primary key clustered ([provincia_cod] asc)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks =
        ON, allow_page_locks =
        ON)
        ON [primary])
        ON [primary];
GO
ALTER TABLE [dbo].[productos] ADD  DEFAULT ((0)) FOR [precio_unit];
GO
ALTER TABLE [dbo].[clientes]  WITH CHECK ADD FOREIGN KEY([cliente_ref])
REFERENCES [dbo].[clientes] ([cliente_num]);
GO
ALTER TABLE [dbo].[clientes]  WITH CHECK ADD FOREIGN KEY([provincia_cod])
REFERENCES [dbo].[provincias] ([provincia_cod]);
GO
ALTER TABLE [dbo].[fabricantes]  WITH CHECK ADD FOREIGN KEY([provincia_cod])
REFERENCES [dbo].[provincias] ([provincia_cod]);
GO
ALTER TABLE [dbo].[facturas]  WITH CHECK ADD FOREIGN KEY([cliente_num])
REFERENCES [dbo].[clientes] ([cliente_num]);
GO
ALTER TABLE [dbo].[facturas]  WITH CHECK ADD FOREIGN KEY([cliente_num])
REFERENCES [dbo].[clientes] ([cliente_num]);
GO
ALTER TABLE [dbo].[facturas_det]  WITH CHECK ADD FOREIGN KEY([factura_num])
REFERENCES [dbo].[facturas] ([factura_num]);
GO
ALTER TABLE [dbo].[facturas_det]  WITH CHECK ADD FOREIGN KEY([producto_cod])
REFERENCES [dbo].[productos] ([producto_cod]);
GO
ALTER TABLE [dbo].[productos]  WITH CHECK ADD FOREIGN KEY([fabricante_cod])
REFERENCES [dbo].[fabricantes] ([fabricante_cod]);
GO
