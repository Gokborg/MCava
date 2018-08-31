package io.github.gokborg.mcava.components;

public enum DataType {
	INTEGER,
	CHAR,
	FLOAT,
	ARRAY_INT,
	ARRAY_CHAR,
	ARRAY_FLOAT,
	NONE;
	
	public static DataType getDataType(String word, boolean isArray) {
		if (word.equalsIgnoreCase("int")) {
			return isArray ? DataType.ARRAY_INT : DataType.INTEGER;
		}
		else if (word.equalsIgnoreCase("char")) {
			return isArray ? DataType.ARRAY_CHAR : DataType.CHAR;
		}
		else if (word.equalsIgnoreCase("float")) {
			return isArray ? DataType.ARRAY_FLOAT : DataType.FLOAT;
		}
		else {
			return DataType.NONE;
		}
	}
}
