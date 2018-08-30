package io.github.gok.mcava.components;

public enum DataType {
	INTEGER,
	CHAR,
	FLOAT,
	NONE;
	
	public static DataType getDataType(String word) {
		if (word.equalsIgnoreCase("int")) {
			return DataType.INTEGER;
		}
		else if (word.equalsIgnoreCase("char")) {
			return DataType.CHAR;
		}
		else if (word.equalsIgnoreCase("float")) {
			return DataType.FLOAT;
		}
		else {
			return DataType.NONE;
		}
	}
}
