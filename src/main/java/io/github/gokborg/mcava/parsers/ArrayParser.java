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

	public ArrayParser(TokenHandler tokenhdlr, RegisterHandler reghdlr, ArrayHandler arrhdlr, ScopeHandler scopehdlr, InstructionHandler instrhdlr) {
		this.tokenhdlr = tokenhdlr;
		this.arrhdlr = arrhdlr;
		this.scopehdlr = scopehdlr;
		this.instrhdlr = instrhdlr;
		this.reghdlr = reghdlr;
	}
	public void parse(String info) {
		String[] arrInfo = info.split(" ");
		String[] arrArgInfo = arrInfo[3].split("/");
		
		if (!arrInfo[0].equalsIgnoreCase("none")) {
			intializeArray(arrInfo);
			ldValues(arrInfo, arrArgInfo);
		}
		
	}
	private void ldValues(String[] arrInfo, String[] arrArgInfo) {
		Array theArray = arrhdlr.getArray(arrInfo[1]);
		for (int i = 0; i < theArray.getSize(); i++) {
			int register = reghdlr.findSpace();
			int value = SyntaxChecker.isNum(arrArgInfo[i]) ? Integer.parseInt(arrArgInfo[i]) : arrArgInfo[i].charAt(0); 
			instrhdlr.addInstruction("li r" + register + ", " + value);
			instrhdlr.addInstruction("str $" + theArray.getVariableOfElement(i).getAddress() + ", r" + register);
			reghdlr.deallocate(register);
		}
	}
	
	private String intializeArray(String[] arrInfo) {
		DataType dataType = DataType.getDataType(arrInfo[0], true);
		String name = arrInfo[1];
		Integer size = Integer.parseInt(arrInfo[2]);
	    arrhdlr.createArray(name, size, dataType, scopehdlr.getScope());
	    tokenhdlr.resetPointer();
	    return name;
	}
	
	
}
