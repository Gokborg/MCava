package io.github.gokborg.mcava.parsers;

import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.validations.SyntaxChecker;

public class Parser {
	private VariableParser varParser;
	private VariableHandler varhdlr;
	public Parser(InstructionHandler instrhdlr, RegisterHandler reghdlr, VariableHandler varhdlr, TokenHandler tokenhdlr, ScopeHandler scopehdlr) {
		this.varParser = new VariableParser(instrhdlr, reghdlr, tokenhdlr, varhdlr, scopehdlr);
		this.varhdlr = varhdlr;
	}
	public void parse(SyntaxChecker synChk) {
		int checkStatus = synChk.checkVar();
		
		//Step 4 : Check for variable parse
		if (checkStatus > 0) {
			varParser.parse(checkStatus);
		}
		
		//Step 5 : Check for array parse *not implemented*
		
		//Step 6 : Look for command parse *not implemented*
	}
}
