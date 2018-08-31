package io.github.gokborg.mcava.components;

import java.util.Arrays;

import io.github.gokborg.mcava.handlers.VariableHandler;

public class Array {
	private final String NAME;
	private final int SIZE;
	private final DataType DATA_TYPE;
	private final int SCOPE;
	private VariableHandler varhdlr;
	public Array(String NAME, int SIZE, DataType DATA_TYPE, int SCOPE, VariableHandler varhdlr) {
		this.NAME = NAME;
		this.SIZE = SIZE;
		this.DATA_TYPE = DATA_TYPE;
		this.SCOPE = SCOPE;
		this.varhdlr = varhdlr;
		for (int i = 0; i < SIZE; i++) {
			varhdlr.createVariable(NAME + "[" + i + "]", DATA_TYPE, SCOPE);
		}
	}
	public String getName() {
		return NAME;
	}
	public int getSize() {
		return SIZE;
	}
	public DataType getDataType() {
		return DATA_TYPE;
	}
	public int getScope() {
		return SCOPE;
	}
	public Variable getVariableOfElement(int index) {
		if (index >= SIZE) {
			System.err.println("Index Error!");
			return null;
		}
		else {
			return varhdlr.getVariable(NAME + "[" + index + "]");
		}
	}
	public void deallocateArray() {
		for (int i = 0; i < SIZE; i++) {
			varhdlr.removeVariable(NAME + "[" + i + "]");
		}
	}
}
