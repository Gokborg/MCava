package io.github.gokborg.mcava.parsing;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.lexing.Compare;
import io.github.gokborg.mcava.lexing.Token;
import io.github.gokborg.mcava.lexing.TokenBuffer;

public class ExpressionParser
{
	private TokenBuffer tokBuff;
	private List<String> instructions = new ArrayList<>();
	public ExpressionParser(TokenBuffer tokBuff)
	{
		this.tokBuff = tokBuff;
	}
	public int parseExpression()
	{
		return 0;
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
		}
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
