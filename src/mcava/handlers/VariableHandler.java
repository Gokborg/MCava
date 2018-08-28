package mcava.handlers;

import java.util.ArrayList;
import java.util.List;

import mcava.exceptions.NoVariableException;
import mcava.exceptions.OutOfMemory;
import mcava.exceptions.RegisterFullException;
import mcava.components.Variable;
import mcava.components.Type;

public class VariableHandler {
	private List<Variable> variables = new ArrayList<Variable>();
	private MemoryHandler memhdlr;
	private RegisterHandler reghdlr;
	public VariableHandler(MemoryHandler memhdlr, RegisterHandler reghdlr) {
		this.memhdlr = memhdlr;
		this.reghdlr = reghdlr;
	}
	
	//Creates a new variable in memory
	public void createVariable(String name, Type dataType) throws OutOfMemory {
		variables.add(new Variable(name, memhdlr.findSpace(), dataType));
	}
	//Creates a new variable in memory
	public void createVariable(String name, Type dataType, boolean isReg) throws OutOfMemory, RegisterFullException {
		if (isReg)	{
			variables.add(new Variable(name, reghdlr.findSpace(), dataType, isReg));
		}
		else {
			variables.add(new Variable(name, memhdlr.findSpace(), dataType));
		}
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
