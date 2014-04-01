USE [master]
GO
/****** Object:  Database [ErpData]    Script Date: 01.04.2014 16:08:00 ******/
CREATE DATABASE [ErpData]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ErpData', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\ErpData.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'ErpData_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\ErpData_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [ErpData] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ErpData].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ErpData] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ErpData] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ErpData] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ErpData] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ErpData] SET ARITHABORT OFF 
GO
ALTER DATABASE [ErpData] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ErpData] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [ErpData] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ErpData] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ErpData] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ErpData] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ErpData] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ErpData] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ErpData] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ErpData] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ErpData] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ErpData] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ErpData] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ErpData] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ErpData] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ErpData] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ErpData] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ErpData] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ErpData] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ErpData] SET  MULTI_USER 
GO
ALTER DATABASE [ErpData] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ErpData] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ErpData] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ErpData] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [ErpData]
GO
/****** Object:  User [test_user]    Script Date: 01.04.2014 16:08:00 ******/
CREATE USER [test_user] FOR LOGIN [test_user] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[contact]    Script Date: 01.04.2014 16:08:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[contact](
	[contactID] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[uid] [int] NULL,
	[title] [varchar](50) NULL,
	[firstName] [varchar](50) NULL,
	[lastName] [varchar](50) NULL,
	[suffix] [varchar](50) NULL,
	[birthDate] [date] NULL,
	[address] [varchar](50) NULL,
	[invoiceAddress] [varchar](50) NULL,
	[shippingAddress] [varchar](50) NULL,
	[isActive] [bit] NULL,
 CONSTRAINT [PK_customer] PRIMARY KEY CLUSTERED 
(
	[contactID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[invoice]    Script Date: 01.04.2014 16:08:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[invoice](
	[invoiceID] [int] IDENTITY(1,1) NOT NULL,
	[issueDate] [date] NULL,
	[dueDate] [date] NULL,
	[comment] [varchar](200) NULL,
	[message] [varchar](200) NULL,
	[contactID] [int] NULL,
 CONSTRAINT [PK_invoice] PRIMARY KEY CLUSTERED 
(
	[invoiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[invoiceItem]    Script Date: 01.04.2014 16:08:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[invoiceItem](
	[invoiceItemID] [int] IDENTITY(1,1) NOT NULL,
	[invoiceID] [int] NULL,
	[quantity] [int] NULL,
	[nettoPrice] [decimal](18, 2) NULL,
	[pricePerUnit] [decimal](18, 2) NULL,
	[tax] [int] NULL,
	[description] [varchar](50) NULL,
 CONSTRAINT [PK_invoiceItem] PRIMARY KEY CLUSTERED 
(
	[invoiceItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[contact] ON 

INSERT [dbo].[contact] ([contactID], [name], [uid], [title], [firstName], [lastName], [suffix], [birthDate], [address], [invoiceAddress], [shippingAddress], [isActive]) VALUES (1, N'Alexander Grafl', NULL, N'Dr.', N'Alexander', N'Grafl', N'Msc', CAST(0x57380B00 AS Date), N'Bergengasse 6/5/14 1220 Wien', N'Bergengasse 6/5/14 1220 Wien', N'Bergengasse 6/5/14 1220 Wien', 1)
INSERT [dbo].[contact] ([contactID], [name], [uid], [title], [firstName], [lastName], [suffix], [birthDate], [address], [invoiceAddress], [shippingAddress], [isActive]) VALUES (2, N'Grafl GmbH', 1, NULL, NULL, NULL, NULL, NULL, N'Bergengasse 6/5/14 1220 Wien', N'Bergengasse 6/5/14 1220 Wien', N'Bergengasse 6/5/14 1220 Wien', 1)
SET IDENTITY_INSERT [dbo].[contact] OFF
SET IDENTITY_INSERT [dbo].[invoice] ON 

INSERT [dbo].[invoice] ([invoiceID], [issueDate], [dueDate], [comment], [message], [contactID]) VALUES (1, CAST(0x57380B00 AS Date), CAST(0x57380B00 AS Date), N'This is an invoice issued to Alexander Grafl.', N'This is a message.', 1)
INSERT [dbo].[invoice] ([invoiceID], [issueDate], [dueDate], [comment], [message], [contactID]) VALUES (2, CAST(0x57380B00 AS Date), CAST(0x57380B00 AS Date), N'This is an invoice issued to Grafl GmbH.', N'This is a message.', 2)
SET IDENTITY_INSERT [dbo].[invoice] OFF
SET IDENTITY_INSERT [dbo].[invoiceItem] ON 

INSERT [dbo].[invoiceItem] ([invoiceItemID], [invoiceID], [quantity], [nettoPrice], [pricePerUnit], [tax], [description]) VALUES (1, 1, 2, CAST(10.60 AS Decimal(18, 2)), CAST(5.30 AS Decimal(18, 2)), 20, N'Some stuff purchased twice')
INSERT [dbo].[invoiceItem] ([invoiceItemID], [invoiceID], [quantity], [nettoPrice], [pricePerUnit], [tax], [description]) VALUES (2, 1, 1, CAST(18.90 AS Decimal(18, 2)), CAST(18.90 AS Decimal(18, 2)), 20, N'Some stuff purchased once')
INSERT [dbo].[invoiceItem] ([invoiceItemID], [invoiceID], [quantity], [nettoPrice], [pricePerUnit], [tax], [description]) VALUES (3, 2, 4, CAST(8.40 AS Decimal(18, 2)), CAST(2.10 AS Decimal(18, 2)), 20, N'Some stuff purchased four times')
INSERT [dbo].[invoiceItem] ([invoiceItemID], [invoiceID], [quantity], [nettoPrice], [pricePerUnit], [tax], [description]) VALUES (4, 2, 100, CAST(100.00 AS Decimal(18, 2)), CAST(1.00 AS Decimal(18, 2)), 20, N'Some stuff purchased a hundred times')
SET IDENTITY_INSERT [dbo].[invoiceItem] OFF
ALTER TABLE [dbo].[invoice]  WITH CHECK ADD  CONSTRAINT [FK_invoice_contact] FOREIGN KEY([contactID])
REFERENCES [dbo].[contact] ([contactID])
GO
ALTER TABLE [dbo].[invoice] CHECK CONSTRAINT [FK_invoice_contact]
GO
ALTER TABLE [dbo].[invoiceItem]  WITH CHECK ADD  CONSTRAINT [FK_invoiceItem_invoice] FOREIGN KEY([invoiceID])
REFERENCES [dbo].[invoice] ([invoiceID])
GO
ALTER TABLE [dbo].[invoiceItem] CHECK CONSTRAINT [FK_invoiceItem_invoice]
GO
USE [master]
GO
ALTER DATABASE [ErpData] SET  READ_WRITE 
GO
