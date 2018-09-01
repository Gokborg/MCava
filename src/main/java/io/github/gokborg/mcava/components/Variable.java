package io.github.gokborg.mcava.components;

public class Variable {
	private final String NAME;
	private int address;

	private final DataType DATA_TYPE;
	private final int SCOPE;
	private boolean reg = false;
	
	public Variable(String NAME, int address, DataType DATA_TYPE, int SCOPE, boolean reg) {
		this.NAME = NAME;
		this.address = address;
		this.SCOPE = SCOPE;
		this.DATA_TYPE = DATA_TYPE;
		this.reg = reg;
	}
	
	public boolean isReg() {
		return reg;
	}
	public void setReg(boolean reg) {
		this.reg = reg;
	}
	public String getName() {
		return NAME;
	}

	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}

	public DataType getDataType() {
		return DATA_TYPE;
	}

	public int getScope() {
		return SCOPE;
	}

	
	
}
