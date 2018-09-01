package io.github.gokborg.mcava.parsers;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.components.Array;
import io.github.gokborg.mcava.components.DataType;
import io.github.gokborg.mcava.handlers.ArrayHandler;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;
import io.github.gokborg.mcava.validations.SyntaxChecker;

public class ArrayParser {
	private TokenHandler tokenhdlr;
	private ArrayHandler arrhdlr;
	private ScopeHandler scopehdlr;
	private InstructionHandler instrhdlr;
	private RegisterHandler reghdlr;
	private List<Integer> values;

	public ArrayParser(TokenHandler tokenhdlr, RegisterHandler reghdlr, ArrayHandler arrhdlr, ScopeHandler scopehdlr, InstructionHandler instrhdlr) {
		this.tokenhdlr = tokenhdlr;
		this.arrhdlr = arrhdlr;
		this.scopehdlr = scopehdlr;
		this.instrhdlr = instrhdlr;
		this.reghdlr = reghdlr;
	}
	public void parse(int status) {
		
		/* 1 : Means that they intialze an array with a given size AND initial values
		 * 2 : Means that they intialze an array with no given size AND initial values
		 * 3 : Means that they intialze an array with a given size AND no initial values
		 */
		String theCreatedArrayName = intializeArray(status);
		if (!values.isEmpty()) {
			ldValues(theCreatedArrayName);
		}
		
	}
	private void ldValues(String name) {
		Array theArray = arrhdlr.getArray(name);
		for (int i = 0; i < theArray.getSize(); i++) {
			int register = reghdlr.findSpace();
			instrhdlr.addInstruction("li r" + register + ", " + values.get(i));
			instrhdlr.addInstruction("str $" + theArray.getVariableOfElement(i).getAddress() + ", r" + register);
			reghdlr.deallocate(register);
		}
	}
	
	private String intializeArray(int status) {
		values = new ArrayList<Integer>();
		DataType dataType = DataType.getDataType(tokenhdlr.getNextToken().getName(), true);
		Integer size = null;
	    tokenhdlr.movePointerRight();
	    if (status == 1 || status == 3) {
	    	size = Integer.parseInt(tokenhdlr.getNextToken().getName());
	    }
	    tokenhdlr.movePointerRight();
	    String name = tokenhdlr.getNextToken().getName();
	   
	    if (size == null || status == 1) {
		    tokenhdlr.movePointer(2);
		    Token tok = tokenhdlr.getNextToken();
		    int argCounter = 0;
		    while (tok.getTokenKind() != TokenKind.CLOSE_BRACE) {
		    	
		    	if (tok.getTokenKind() != TokenKind.COMMA) {
		    		if (dataType == DataType.ARRAY_INT) {
		    			if (SyntaxChecker.isNum(tok)) {
		    				values.add(Integer.parseInt(tok.getName()));
		    				argCounter++;
		    			}
		    		}
		    		else if (dataType == DataType.ARRAY_CHAR) {
		    			if (SyntaxChecker.isCharacter(tok)) {
		    				values.add((int)tok.getName().charAt(0));
		    				argCounter++;
		    			}
		    		}
		    	}
		    	tok = tokenhdlr.getNextToken();
		    	
		    }
		    
		    size = argCounter;
	    }
	    arrhdlr.createArray(name, size, dataType, scopehdlr.getScope());
	    tokenhdlr.resetPointer();
	    return name;
	}
	
}
