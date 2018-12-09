package io.github.gokborg.mcava.parsing;

import io.github.gokborg.mcava.lexing.TokenBuffer;
import io.github.gokborg.mcava.lexing.TokenType;

public class VariableParser 
{
	private TokenBuffer tokBuff;
	private ExpressionParser expParser;
	
	
	public VariableParser(TokenBuffer tokBuff)
	{
		this.tokBuff = tokBuff;
		this.expParser = new ExpressionParser(tokBuff);
	}
	
	public void parseInitialization()
	{
		//DATA_TYPE + NAME + = + EXPRESSION
		TokenType varDataType = tokBuff.nextType();
		String varName = tokBuff.nextName();
		tokBuff.skip();
		
		//Were at the first position of the expression
		//I will now pass it to the expression parser!
		//Whatever instructions it returns the final product will be in r1
		//In the end I'll have to move r1 to the memory spot
		int expression = expParser.parseTerm();
		
		//Create a new variable
		
	}
}
