package io.github.gokborg.mcava.parsers;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.components.DataType;
import io.github.gokborg.mcava.components.Variable;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;
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
	
	public void parse(int checkStatus) {
		switch (checkStatus) {
		case 1:
			//Allocating space for the variable AND assigning it a value
			Variable destinationVariable = makeVariable();
			tokenhdlr.removeToken(0);
			assignVariable(destinationVariable);
			break;
		
		case 2:
			//Simply just allocating space for the variable
			makeVariable();
			break;
		}
	}
	
	private void assignVariable(Variable destVar) {
		/*
		 * int a = 3;
		 * 3 is an arguement
		 * 
		 * int b = 3 + 3;
		 * 3 + 3 is an arguement
		 * 
		 * Those are what I am collecting in the list of args
		 */
		List<Token> args = new ArrayList<Token>();
		tokenhdlr.movePointer(2);
		
		Token tok = tokenhdlr.getNextToken();
		while(tok.getTokenKind() != TokenKind.SEMI_COLON) {
			args.add(tok);
			tok = tokenhdlr.getNextToken();
		}
		tokenhdlr.resetPointer();
		
		//Allocating space on the register file
		int register = reghdlr.findSpace();
		
		/*
		 * Assignment of a variable to a single value.
		 * Ex: int a = 3;
		 * Ex: int a = b;
		 */
		
		if (args.size() == 1) {
			
			Token value = args.get(0);
			
			if(SyntaxChecker.isNum(value)) {
				instrhdlr.addInstruction("li r" + register + ", " + value.getName());
				instrhdlr.addInstruction("str $" + destVar.getAddress() + ", r" + register);
			}
			else if (SyntaxChecker.isWordOrChar(value)) {
				Variable srcVar = varhdlr.getVariable(value.getName());
				if (srcVar != null) {
					if (destVar.getDataType() == srcVar.getDataType() && destVar.getScope() <= srcVar.getScope()) {
						instrhdlr.addInstruction("ld r" + register + ", $" + srcVar.getAddress());
						instrhdlr.addInstruction("str $" + destVar.getAddress() + ", r" + register);
					}
					else {
						System.err.println("Variable : '" + destVar.getName() + "' and Variable : '" + srcVar.getName() + "' are not of the same data type.");
					}
				}
			}
			
			//Clearing the space I allocated on the register file
			reghdlr.deallocate(register);
		}
	}
	private Variable makeVariable() {
		DataType dataType = DataType.getDataType(tokenhdlr.getNextToken().getName(), false);
		String name = tokenhdlr.getNextToken().getName();
		varhdlr.createVariable(name, dataType, scopehdlr.getScope());
		tokenhdlr.resetPointer();
		instrhdlr.addInstruction("; Intialized '" + name + "'");
		return varhdlr.getVariable(name);
	}
}
