package com.bms.util;

public interface ColumnDefinition {
	
	String BIGINT = "bigint";
	String BIT = "bit not null default 0";
	String BIT_TRUE = "bit not null default 1";
	String CHAR = "Char";
	String DECIMAL18_4 = "decimal(18,4)";
	String DECIMAL3_2 = "decimal(3,2)";
	String DECIMAL5_2 = "decimal(5,2) not null";
	String MONEY = "money";
	String NVARCHAR350 = "nvarchar(350)";
	String NVARCHAR3 = "nvarchar(3)";
	String NVARCHAR8 = "nvarchar(8)";
	String NVARCHAR50= "nvarchar(50)";
	String NVARCHAR_MAX = "nvarchar(max)";
	String UNIQUEIDENTIFIER = "uniqueidentifier";
	String NUMERIC38_4 = "numeric(38,4)";
	String NTEXT = "ntext not null";
}
