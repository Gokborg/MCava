package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.components.DataType;
import io.github.gokborg.mcava.components.Variable;


public class VariableHandler {
	private List<Variable> variables = new ArrayList<Variable>();
	private MemoryHandler memhdlr;
	
	public VariableHandler(MemoryHandler memhdlr) {
		this.memhdlr = memhdlr;
	}
	
	//Creates a new variable in memory
	public void createVariable(String NAME, DataType DATA_TYPE, int SCOPE){
		variables.add(new Variable(NAME, memhdlr.findSpace(), DATA_TYPE, SCOPE));
	}
	
	//Returns a variable based on its name in code file
	public Variable getVariable(String name){
		for (Variable var : variables) {
			if (var.getName().equalsIgnoreCase(name)) {
				return var;
			}
		}
		System.err.println("'" + name + "' is not a variable");
		return null;
	}
	
	//Deallocates a variable from memory based on its name in code file
	public void removeVariable(String name) {
		for (int i = 0; i < variables.size(); i++) {
			if (variables.get(i).getName() == name) {
				memhdlr.deallocate(variables.get(i).getAddress());
				variables.remove(i);
				break;
			}
		}
	}
}
