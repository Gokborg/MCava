package mcava.components;

public class Array {
	private final int SIZE;
	private int address;
	private int[] addressRegion;
	private String name;
	public Array(String name, int SIZE) {
		this.SIZE = SIZE;
		this.name = name;
		addressRegion = new int[SIZE];
	}
	public int getSize() {
		return SIZE;
	}
	public String getName() {
		return name;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public int[] getAddressRegion() {
		return addressRegion;
	}
	public void setAddressRegion(int[] addressRegion) {
		this.addressRegion = addressRegion;
		address = addressRegion[0];
	}
	
}
