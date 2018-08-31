package io.github.gokborg.mcava.parsers;

import io.github.gokborg.mcava.handlers.ArrayHandler;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.validations.SyntaxChecker;

public class Parser {
	private VariableParser varParser;
	private ArrayParser arrParser;
	public Parser(InstructionHandler instrhdlr, ArrayHandler arrhdlr, RegisterHandler reghdlr, VariableHandler varhdlr, TokenHandler tokenhdlr, ScopeHandler scopehdlr) {
		this.varParser = new VariableParser(instrhdlr, reghdlr, tokenhdlr, varhdlr, scopehdlr);
		this.arrParser = new ArrayParser(tokenhdlr, reghdlr, arrhdlr, scopehdlr, instrhdlr);
	}
	public void parse(SyntaxChecker synChk) {
		
		//Step 4 : Check for variable parse
		if (synChk.checkVar() > 0) {
			varParser.parse(synChk.getStatus());
		}
		//Step 5 : Check for array parse *not implemented*
		else if (synChk.checkInitArray() > 0) {
			arrParser.parse(synChk.getStatus());
		}
		else {
			System.err.println("Hwat is dat shit");
		}
		
		//Step 6 : Look for command parse *not implemented*
	}
}
