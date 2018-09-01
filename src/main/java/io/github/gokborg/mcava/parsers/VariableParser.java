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
		if (varInfo[1].equalsIgnoreCase("none")) {
			if (varhdlr.isVariable(varInfo[2])) {
				assignVariable(varhdlr.getVariable(varInfo[2]), varInfo);
				return;
			}
		}
		else if (varInfo[3].equalsIgnoreCase("none")) {
			makeVariable(varInfo);
		}
		else {
			assignVariable(makeVariable(varInfo), varInfo);
		}
	}
	
	private void assignVariable(Variable destVar, String[] info) {
		//Allocating space on the register file
		if (destVar == null) {
			System.err.println("No destination variable!");
			return;
		}
		int register = destVar.isReg() ? destVar.getAddress() : reghdlr.findSpace();
		
		/*
		 * Assignment of a variable to a single value.
		 * Ex: int a = 3;
		 * Ex: int a = b;
		 */
		
		if (info[3].length() == 1) {			
			if(SyntaxChecker.isNum(info[3]) || info[4].equalsIgnoreCase("chararg")) {
				int value = info[4].equalsIgnoreCase("chararg") ? info[3].charAt(0) : Integer.parseInt(info[3]);
				instrhdlr.addInstruction("li r" + register + ", " + value);
				varhdlr.strVariable(destVar, register);
			}
			else if (info[3].matches("[a-zA-Z]+")) {
				Variable srcVar = varhdlr.getVariable(info[3]);
				if (srcVar != null) {
					int reg = varhdlr.ldVariable(srcVar);
					if (destVar.getDataType() == srcVar.getDataType() && destVar.getScope() >= srcVar.getScope()) {
						varhdlr.strVariable(destVar, reg);
					}
					else {
						System.err.println("Variable : '" + destVar.getName() + "' and Variable : '" + srcVar.getName() + "' are not of the same data type or not in the same scope.");
					}
					varhdlr.tryRegDeallocate(reg);
				}
				else {
					System.err.println("The variable : '" + info[3] + "' has not been intialized!");
				}
			}
			
			//Clearing the space I allocated on the register file
			varhdlr.tryRegDeallocate(register);
		}
	}
	private Variable makeVariable(String[] info) {
		DataType dataType = DataType.getDataType(info[1], false);
		String name = info[2];
		if (info[0].equalsIgnoreCase("fast")) {
			varhdlr.createRegVariable(name, dataType, scopehdlr.getScope());	
		}
		else {
			varhdlr.createVariable(name, dataType, scopehdlr.getScope());
		}
		tokenhdlr.resetPointer();
		//instrhdlr.addInstruction("; Intialized '" + name + "'");
		return varhdlr.getVariable(name);
	}
	
}
