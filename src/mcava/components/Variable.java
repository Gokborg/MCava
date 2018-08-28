package mcava.components;

public class Variable {
	private String name;
	private int address;
	private Type dataType;
	private boolean isReg;
	public Variable(String name, int address, Type dataType) {
		this.name = name;
		this.address = address;
		this.dataType = dataType;
		this.isReg = false;
	}
	public Variable(String name, int address, Type dataType, boolean isReg) {
		this.name = name;
		this.address = address;
		this.dataType = dataType;
		this.isReg = true;
	}
	public boolean isReg() {
		return isReg;
	}
	public void setIsReg(boolean state) {
		isReg = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public Type getDataType() {
		return dataType;
	}
	public void setDataType(Type dataType) {
		this.dataType = dataType;
	}
}
