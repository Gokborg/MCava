package mcava.handlers;

import java.util.ArrayList;
import java.util.List;

import mcava.components.Array;
import mcava.exceptions.IndexException;
import mcava.exceptions.NoArrayException;
import mcava.exceptions.NoVariableException;
import mcava.exceptions.OutOfMemory;

public class ArrayHandler {
	List<Array> arrays = new ArrayList<Array>();
	private VariableHandler varhdlr;
	public ArrayHandler(VariableHandler varhdlr) {
		this.varhdlr = varhdlr;
	}
	public void createArray(String name, int SIZE) throws OutOfMemory, NoVariableException {
		Array arr = new Array(name, SIZE);
		
		int[] addrRegion = arr.getAddressRegion();
		for (int i = 0; i < SIZE; i++) {
			varhdlr.createVariable(name + "[" + i + "]");
			addrRegion[i] = varhdlr.getVariable(name + "[" + i + "]").getAddress();
		}
		arr.setAddressRegion(addrRegion);
		arrays.add(arr);
	}
	
	public int getAddress(String nameOfArray, int index) throws NoArrayException, IndexException {
		
		if (index >= this.getArray(nameOfArray).getSize()) {
			throw new IndexException("Index error!");
		}
		return this.getArray(nameOfArray).getAddressRegion()[index];
	}

	public Array getArray(String name) throws NoArrayException {
		for (Array arr : arrays) {
			if (arr.getName().equalsIgnoreCase(name)) {
				return arr;
			}
		}
		throw new NoArrayException("The array with the name: '" + name + "' does not exist!");
	}
}
