package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.components.Array;
import io.github.gokborg.mcava.components.DataType;

public class ArrayHandler {
	private List<Array> arrays = new ArrayList<Array>();
	private VariableHandler varhdlr;
	
	public ArrayHandler(VariableHandler varhdlr) {
		this.varhdlr = varhdlr;
	}
	public void createArray(String NAME, int SIZE, DataType DATA_TYPE, int SCOPE) {
		
		arrays.add(new Array(NAME, SIZE, DATA_TYPE, SCOPE, varhdlr));
	}
	public Array getArray(String name) {
		for (Array arr : arrays) {
			if (arr.getName().equalsIgnoreCase(name)) {
				return arr;
			}
		}
		System.err.println("No array with the name : " + name);
		return null;
	}
}
