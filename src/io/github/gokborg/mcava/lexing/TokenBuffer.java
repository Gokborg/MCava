package io.github.gokborg.mcava.lexing;

import java.util.List;

public class TokenBuffer 
{
	private List<Token> tokens;
	public Token current; //Holds the current token being pointed to
	private int pointer = 0;
	
	public TokenBuffer(List<Token> tokens)
	{
		this.tokens = tokens;
	}
	
	/**
	 * Returns the next token.
	 * 
	 * @return	current token at pointer
	 */
	public Token next()
	{
		current = tokens.get(pointer);
		pointer++;
		return current;
	}
	
	/**
	 * Returns the next token's type.
	 * 
	 * @return	current token's type 
	 */
	public TokenType nextType()
	{
		current = tokens.get(pointer);
		pointer++;
		return current.getType();
	}
	
	/**
	 * Returns the next token's name
	 * 
	 * @return	current token's name
	 */
	public String nextName()
	{
		current = tokens.get(pointer);
		pointer++;
		return current.getValue();
	}
	
	/**
	 * Returns the next token without updating 'current'
	 * (Useful for when you want to check tokens ahead but don't want to move the pointer)
	 * 
	 * @param 	offset	how far to look from pointer
	 * @return	token at pointer+offset
	 */
	public Token peek(int offset)
	{
		return tokens.get(pointer+offset);
	}
	
	/**
	 * Moves the pointer right;
	 */
	public void skip()
	{
		pointer++;
	}
	
	/**
	 * Returns the token buffer
	 * 
	 * @return	all tokens in buffer
	 */
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
