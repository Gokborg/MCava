package io.github.gokborg.mcava.lexing;

public class Compare 
{
	public static boolean isDataType(Token token)
	{
		return token.getType() == TokenType.KEYWORD_INT || token.getType() == TokenType.KEYWORD_CHAR;
	}
	public static boolean isDataType(TokenType type)
	{
		return type == TokenType.KEYWORD_INT || type == TokenType.KEYWORD_CHAR;
	}
	public static boolean isOperator(Token token)
	{
		return token.getType() == TokenType.OPERATOR;
	}
	public static boolean isInteger(Token token)
	{
		return token.getType() == TokenType.LITERAL_INTEGER;
	}
}
