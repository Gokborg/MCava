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
	private IfParser ifparser;
	private ExpressionParser expParser;
	private ScopeHandler scopehdlr;
	private TokenHandler tokenhdlr;
	public Parser(InstructionHandler instrhdlr, ArrayHandler arrhdlr, RegisterHandler reghdlr, VariableHandler varhdlr, TokenHandler tokenhdlr, ScopeHandler scopehdlr) {
		this.expParser = new ExpressionParser(instrhdlr, reghdlr, varhdlr);
		this.varParser = new VariableParser(instrhdlr, expParser, reghdlr, tokenhdlr, varhdlr, scopehdlr);
		this.arrParser = new ArrayParser(tokenhdlr, reghdlr, arrhdlr, scopehdlr, instrhdlr);
		this.ifparser = new IfParser(varhdlr, instrhdlr, reghdlr, scopehdlr);
		this.scopehdlr = scopehdlr;
		this.tokenhdlr = tokenhdlr;
	}
	public void parse(SyntaxChecker synChk) {
		
		//Step 4 : Check for variable parse
		if (synChk.checkVar()) {
			varParser.parse(synChk.getInfo());
		}
		//Step 5 : Check for array parse *not implemented*
		else if (synChk.checkInitArray()) {
			arrParser.parse(synChk.getInfo());
		}
		else if (synChk.checkIf()) {
			ifparser.parse(synChk.getInfo());
		}
		else if (synChk.checkLeaveScope()) {
			scopehdlr.decreaseScope();
			tokenhdlr.resetPointer();
		}
		else {
			System.err.println("[Parser] Failed to parse line: " + tokenhdlr.getLine());
		}
		
		//Step 6 : Look for command parse *not implemented*
		
		
	}
}
