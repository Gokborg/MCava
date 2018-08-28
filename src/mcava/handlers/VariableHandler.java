package mcava.handlers;

import java.util.ArrayList;
import java.util.List;

import mcava.components.Variable;
import mcava.exceptions.NoVariableException;
import mcava.exceptions.OutOfMemory;

public class VariableHandler {
	private List<Variable> variables = new ArrayList<Variable>();
	private MemoryHandler memhdlr;
	
	public VariableHandler(MemoryHandler memhdlr) {
		this.memhdlr = memhdlr;
	}
	
	//Creates a new variable in memory
	public void createVariable(String name) throws OutOfMemory {
		variables.add(new Variable(name, memhdlr.findSpace()));
	}
	
	//Returns a variable based on its name in code
	public Variable getVariable(String name) throws NoVariableException {
		for (Variable var : variables) {
			if (var.getName().equalsIgnoreCase(name)) {
				return var;
			}
		}
		throw new NoVariableException("Variable : '" + name + "' does not exist!");
	}
	
	//Deallocates a variable from memory
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
