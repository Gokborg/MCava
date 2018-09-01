package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.components.DataType;
import io.github.gokborg.mcava.components.Variable;


public class VariableHandler {
	private List<Variable> variables = new ArrayList<Variable>();
	private MemoryHandler memhdlr;
	private RegisterHandler reghdlr;
	private InstructionHandler instrhdlr;
	private int reg = 0;
	private int totalRegAllocations = 0;
	private int regAllocationLimit;
	public VariableHandler(MemoryHandler memhdlr, RegisterHandler reghdlr, InstructionHandler instrhdlr, int regAllocationLimit) {
		this.memhdlr = memhdlr;
		this.reghdlr = reghdlr;
		this.instrhdlr = instrhdlr;
		this.regAllocationLimit = regAllocationLimit;
	}
	
	//Creates a new variable in memory
	public void createVariable(String NAME, DataType DATA_TYPE, int SCOPE){
		variables.add(new Variable(NAME, memhdlr.findSpace(), DATA_TYPE, SCOPE, false));
	}
	
	//Creates a variable to be allocated in registers
	public void createRegVariable(String NAME, DataType DATA_TYPE, int SCOPE){
		totalRegAllocations++;
		if (totalRegAllocations <= regAllocationLimit) {
			variables.add(new Variable(NAME, reghdlr.findSpace(), DATA_TYPE, SCOPE, true));
		}
		else {
			createVariable(NAME, DATA_TYPE, SCOPE);
		}
		
	}
	
	//Checks if a variable has the name
	public boolean isVariable(String name) {
		for (Variable var : variables) {
			if (var.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	//Returns a variable based on its name in code file
	public Variable getVariable(String name){
		for (Variable var : variables) {
			if (var.getName().equalsIgnoreCase(name)) {
				return var;
			}
		}
		//System.err.println("'" + name + "' is not a variable");
		return null;
	}

	//Puts a variable into a register
	public int ldVariable(Variable theVar) {
		if (theVar.isReg()) {
			reg = theVar.getAddress();
			return theVar.getAddress();
		}
		else {
			int reg = reghdlr.findSpace();
			instrhdlr.addInstruction("ld r" + reg + ", $" + theVar.getAddress());
			return reg;
		}
	}
	
	public void tryRegDeallocate(int reg) {
		for (Variable var : variables) {
			if (var.isReg() && var.getAddress() == reg) {
				return;
			}
		}
		reghdlr.deallocate(reg);
	}
	
	//Stores a variable into a register location
	public void strVariable(Variable theVar, int otherReg) {
		if (theVar.isReg() && theVar.getAddress() != otherReg) {
			instrhdlr.addInstruction("add r" + theVar.getAddress() + ", r" + otherReg + ", r0");
		}
		else if (!theVar.isReg()){
			instrhdlr.addInstruction("str $" + theVar.getAddress() + ", r" + otherReg);
		}
	}
	
	//Stores a variable back into memory
	public void strVariable(Variable theVar) {
		if (!theVar.isReg()) {
			instrhdlr.addInstruction("str $" + theVar.getAddress() + ", r" + reg);
		}
	}
	
	//Deallocates a variable from memory based on its name in code file
	public void removeVariable(String name) {
		for (int i = 0; i < variables.size(); i++) {
			if (variables.get(i).getName() == name) {
				memhdlr.deallocate(variables.get(i).getAddress());
				variables.remove(i);
				totalRegAllocations = totalRegAllocations >= 1 ? totalRegAllocations-- : 0;
				break;
			}
		}
	}
}
