package io.github.gok.mcava.components;

public class Variable {
	private final String NAME;
	private final int ADDRESS;
	private final DataType DATA_TYPE;
	private final int SCOPE;
	
	public Variable(String NAME, int ADDRESS, DataType DATA_TYPE, int SCOPE) {
		this.NAME = NAME;
		this.ADDRESS = ADDRESS;
		this.SCOPE = SCOPE;
		this.DATA_TYPE = DATA_TYPE;
	}
	public String getName() {
		return NAME;
	}

	public int getAddress() {
		return ADDRESS;
	}

	public DataType getDataType() {
		return DATA_TYPE;
	}

	public int getScope() {
		return SCOPE;
	}

	
	
}
