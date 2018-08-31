package io.github.gokborg.mcava.parsers;

import io.github.gokborg.mcava.components.DataType;
import io.github.gokborg.mcava.handlers.ArrayHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;

public class ArrayParser {
	private TokenHandler tokenhdlr;
	private ArrayHandler arrhdlr;
	private ScopeHandler scopehdlr;
	private int argTotal = 0;
	private String arrayName;
	public ArrayParser(TokenHandler tokenhdlr, ArrayHandler arrhdlr, ScopeHandler scopehdlr) {
		this.tokenhdlr = tokenhdlr;
		this.arrhdlr = arrhdlr;
		this.scopehdlr = scopehdlr;
	}
	public void parse(int status) {
		
		/* 1 : Means that they intialze an array with a given size AND initial values
		 * 2 : Means that they intialze an array with no given size AND initial values
		 * 3 : Means that they intialze an array with a given size AND no initial values
		 */
		switch (status){
		case 1:
			intializeArray(status);
			ldValues();
		case 3:
			intializeArray(status);
			break;
		}
		
		
	}
	private void ldValues() {
		
	}
	
	private void intializeArray(int status) {
		DataType dataType = DataType.getDataType(tokenhdlr.getNextToken().getName(), true);
		Integer size = null;
	    tokenhdlr.movePointerRight();
	    if (status == 1 || status == 3) {
	    	size = Integer.parseInt(tokenhdlr.getNextToken().getName());
	    	tokenhdlr.movePointerRight();
	    }
	    String name = tokenhdlr.getNextToken().getName();
	    
	    if (size == null) {
		    tokenhdlr.movePointer(2);
		    Token tok = tokenhdlr.getNextToken();
		    int argCounter = 0;
		    while (tok.getTokenKind() != TokenKind.CLOSE_BRACE) {
		    	argCounter++;
		    }
		    size = argCounter;
		    argTotal = argCounter;
	    }
	    arrayName = name;
	    
	    arrhdlr.createArray(name, size, dataType, scopehdlr.getScope());
	    tokenhdlr.resetPointer();
	    
	}
	
}
