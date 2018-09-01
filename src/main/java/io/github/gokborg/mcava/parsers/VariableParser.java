package io.github.gokborg.mcava.parsers;

import io.github.gokborg.mcava.components.DataType;
import io.github.gokborg.mcava.components.Variable;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.validations.SyntaxChecker;

public class VariableParser {
	private TokenHandler tokenhdlr;
	private VariableHandler varhdlr;
	private ScopeHandler scopehdlr;
	private InstructionHandler instrhdlr;
	private RegisterHandler reghdlr;
	public VariableParser(InstructionHandler instrhdlr, RegisterHandler reghdlr, TokenHandler tokenhdlr, VariableHandler varhdlr, ScopeHandler scopehdlr) {
		this.tokenhdlr = tokenhdlr;
		this.varhdlr = varhdlr;
		this.scopehdlr = scopehdlr;
		this.instrhdlr = instrhdlr;
		this.reghdlr = reghdlr;
	}
	
	public void parse(String info) {
		
		String[] varInfo = info.split(" ");
		
		if (varInfo[0].equalsIgnoreCase("none")) {
			if (varhdlr.isVariable(varInfo[1])) {
				assignVariable(varhdlr.getVariable(varInfo[1]), varInfo);
			}
		}
		else if (varInfo[2].equalsIgnoreCase("none")) {
			makeVariable(varInfo);
		}
		else {
			assignVariable(makeVariable(varInfo), varInfo);
		}
	}
	
	private void assignVariable(Variable destVar, String[] info) {
		//Allocating space on the register file
		int register = reghdlr.findSpace();
		
		/*
		 * Assignment of a variable to a single value.
		 * Ex: int a = 3;
		 * Ex: int a = b;
		 */
		
		if (info[2].length() == 1) {			
			if(isNum(info[2])) {
				instrhdlr.addInstruction("li r" + register + ", " + info[2]);
				instrhdlr.addInstruction("str $" + destVar.getAddress() + ", r" + register);
			}
			else if (info[2].matches("[a-zA-Z]+")) {
				Variable srcVar = varhdlr.getVariable(info[2]);
				if (srcVar != null) {
					if (destVar.getDataType() == srcVar.getDataType() && destVar.getScope() >= srcVar.getScope()) {
						instrhdlr.addInstruction("ld r" + register + ", $" + srcVar.getAddress());
						instrhdlr.addInstruction("str $" + destVar.getAddress() + ", r" + register);
					}
					else {
						System.err.println("Variable : '" + destVar.getName() + "' and Variable : '" + srcVar.getName() + "' are not of the same data type or not in the same scope.");
					}
				}
			}
			
			//Clearing the space I allocated on the register file
			reghdlr.deallocate(register);
		}
	}
	private Variable makeVariable(String[] info) {
		DataType dataType = DataType.getDataType(info[0], false);
		String name = info[1];
		varhdlr.createVariable(name, dataType, scopehdlr.getScope());
		tokenhdlr.resetPointer();
		//instrhdlr.addInstruction("; Intialized '" + name + "'");
		return varhdlr.getVariable(name);
	}
	private boolean isNum(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
