package io.github.gokborg.mcava.lexing;

import java.util.List;

public class TokenBuffer 
{
	private List<Token> tokens;
	public Token current;
	private int pointer = 0;
	
	public TokenBuffer(List<Token> tokens)
	{
		this.tokens = tokens;
	}
	
	public Token next()
	{
		current = tokens.get(pointer);
		pointer++;
		return current;
	}
	
	public TokenType nextType()
	{
		current = tokens.get(pointer);
		pointer++;
		return current.getType();
	}
	
	public String nextName()
	{
		current = tokens.get(pointer);
		pointer++;
		return current.getValue();
	}
	
	public void skip()
	{
		pointer++;
	}
	
	@Override
	public String toString()
	{
		String tokBufString = "";
		for(Token token : tokens)
		{
			tokBufString += token + "\n";
		}
		return tokBufString;
	}
}
