package io.github.gokborg.mcava.parsing;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.lexing.Compare;
import io.github.gokborg.mcava.lexing.Token;
import io.github.gokborg.mcava.lexing.TokenBuffer;

public class ExpressionParser
{
	private TokenBuffer tokBuff;
	public ExpressionParser(TokenBuffer tokBuff)
	{
		this.tokBuff = tokBuff;
	}
	
	//Converts everything to instructions in the end : stores in register specified
	public List<String> parseExpression(int register)
	{
		List<String> instructions = new ArrayList<>();
		int end = parseTerm();
		instructions.add("li r" + register + ", " + end);
		return instructions;
	}
	public int parseTerm()
	{
		int fact1 = parseFactor();
		String operator = tokBuff.nextName();
		while(operator.equals("*"))
		{
			if(operator.equals("*"))
				fact1 *= parseFactor();
			else if(operator.equals("/"))
				fact1 /= parseFactor();
			operator = tokBuff.nextName();
		}
		System.out.println("[ExpressionParser] Heres result: " + fact1);
		return fact1;
	}
	public int parseFactor()
	{
		Token factor = tokBuff.next();
		if(Compare.isInteger(factor))
		{
			return Integer.parseInt(factor.getValue());
		}
		return 0;
	}
}
