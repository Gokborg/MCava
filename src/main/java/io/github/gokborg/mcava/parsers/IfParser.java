package io.github.gokborg.mcava.parsers;

import io.github.gokborg.mcava.components.Variable;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;

public class IfParser {
	private VariableHandler varhdlr;
	private ScopeHandler scopehdlr;
	private InstructionHandler instrhdlr;
	private RegisterHandler reghdlr;
	private int id = 0;
	public IfParser(VariableHandler varhdlr, InstructionHandler instrhdlr, RegisterHandler reghdlr, ScopeHandler scopehdlr) {
		this.varhdlr = varhdlr;
		this.scopehdlr = scopehdlr;
		this.instrhdlr = instrhdlr;
		this.reghdlr = reghdlr;
	}
	public void parse(String args) {
		String[] ifArgs = args.split(" ");
		int arg1Adr = 0;
		int arg2Adr = 0;
		if (varhdlr.isVariable(ifArgs[0]) && varhdlr.isVariable(ifArgs[1])){
			arg1Adr = varhdlr.getVariable(ifArgs[0]).getAddress();
			arg2Adr = varhdlr.getVariable(ifArgs[1]).getAddress();
		}
		else {
			System.err.println("[IfParser] The two arguements in the if statement are not variables");
		}
		
		String cmpInstruction = "";
		switch (ifArgs[2]) {
		case "==":
			cmpInstruction = "jne";
			break;
		}
		int registerOne = reghdlr.findSpace();
		int registerTwo = reghdlr.findSpace();
		instrhdlr.addInstruction("ld r" + registerOne + ", $" + arg1Adr);
		instrhdlr.addInstruction("ld r" + registerTwo + ", $" + arg2Adr);
		instrhdlr.addInstruction("cmp r" + registerOne + ", r" + registerTwo);
		instrhdlr.addInstruction(cmpInstruction + " OUTIF" + id);
		scopehdlr.addOnLeaveInstruction("OUTIF" + id + ":");
		id++;
		scopehdlr.increaseScope();
		reghdlr.deallocate(registerOne);
		reghdlr.deallocate(registerTwo);
	}
	
}
