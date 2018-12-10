package io.github.gokborg.mcava.parsing;

import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.MemoryHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.lexing.TokenBuffer;

public class Parser 
{
	private TokenBuffer tokBuff;
	private VariableParser varParser;
	
	private VariableHandler varhdlr;
	private RegisterHandler reghdlr;
	private MemoryHandler memhdlr;
	private InstructionHandler instrhdlr;
	private ScopeHandler scopehdlr;
	
	public Parser(TokenBuffer tokBuff, InstructionHandler instrhdlr, MemoryHandler memhdlr, RegisterHandler reghdlr)
	{
		this.tokBuff = tokBuff;
		this.memhdlr = memhdlr;
		this.reghdlr = reghdlr;
		this.instrhdlr = instrhdlr;
		this.scopehdlr = new ScopeHandler();
		this.varhdlr = new VariableHandler(memhdlr, reghdlr, instrhdlr, scopehdlr);
		
		this.varParser = new VariableParser(tokBuff, varhdlr, reghdlr, instrhdlr);
	}
	
	public void parse()
	{
		varParser.parse();
	}
}
