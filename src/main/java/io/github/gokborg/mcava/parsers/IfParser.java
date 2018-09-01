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
		scopehdlr.increaseScope();
		String[] ifArgs = args.split(" ");
		Variable arg1 = null;
		Variable arg2 = null;
		if (varhdlr.isVariable(ifArgs[0]) && varhdlr.isVariable(ifArgs[1])){
			arg1 = varhdlr.getVariable(ifArgs[0]);
			arg2 = varhdlr.getVariable(ifArgs[1]);
		}
		else {
			System.err.println("[IfParser] The two arguements in the if statement are not variables");
			return;
		}
		
		String cmpInstruction = "";
		
		//Getting the opposition compare instruction
		switch (ifArgs[2]) {
		case "==":
			cmpInstruction = "jne";
			break;
		case ">":
			cmpInstruction = "jl";
			break;
		case "<":
			cmpInstruction = "jg";
			break;
		case ">=":
			cmpInstruction = "jle";
			break;
		case "<=":
			cmpInstruction = "jge";
			break;
		case "!=":
			cmpInstruction = "je";
			break;
		}
		
		int registerOne = varhdlr.ldVariable(arg1);
		int registerTwo = varhdlr.ldVariable(arg2);
		instrhdlr.addInstruction("cmp r" + registerOne + ", r" + registerTwo);
		instrhdlr.addInstruction(cmpInstruction + " OUTIF" + id);
		scopehdlr.addOnLeaveInstruction("OUTIF" + id + ":");
		id++;
		varhdlr.tryRegDeallocate(registerOne);
		varhdlr.tryRegDeallocate(registerTwo);
	}
	
}
