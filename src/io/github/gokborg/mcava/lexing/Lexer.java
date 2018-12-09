package io.github.gokborg.mcava.lexing;

import java.util.ArrayList;
import java.util.List;

public class Lexer 
{
	public static List<Token> lex(List<String> lines)
	{
		List<Token> tokens = new ArrayList<>();
		LineBuffer buffer = new LineBuffer();
		for(String line : lines)
		{
			buffer.setLine(line);
			System.out.println(line);
			//Keep going until we hit an ending letter
			while(!buffer.done())
			{
				//Sees digit -> builds digit till end
				if(Character.isDigit(buffer.current))
            	{
	            	String number = "";
	            	while(Character.isDigit(buffer.current))
	            	{
	            		number += buffer.current;
	            		buffer.next();
	            	}
	            	tokens.add(new Token(TokenType.LITERAL_INTEGER, number));
            	}
				//Sees quote -> builds string literal
            	else if(buffer.current == '"')
            	{
            		buffer.next(); //eat the '"'
            		String string = "";
            		while(buffer.current != '"') //eats the ending '"'
            		{
            			string += buffer.current;
            			buffer.next();
            		}
            		tokens.add(new Token(TokenType.LITERAL_STRING, string));
            	}
            	//Sees letter -> builds identifier
            	else if(Character.isLetter(buffer.current))
            	{
            		String identifier = "";
            		while(Character.isLetter(buffer.current))
            		{
            			identifier += buffer.current;
            			buffer.next();
            		}
            		
            		//Have to see if its a keyword!
            		TokenType type = TokenType.IDENTIFIER;
            		switch(identifier)
            		{
		        		case "int":
		        			type = TokenType.KEYWORD_INT;
		        			break;
		        		case "char":
		        			type = TokenType.KEYWORD_CHAR;
		        			break;
		        		case "if":
		        			type = TokenType.KEYWORD_IF;
		        			break;
		        		case "elif":
		        			type = TokenType.KEYWORD_ELIF;
		        			break;
		        		case "else":
		        			type = TokenType.KEYWORD_ELSE;
		        			break;
		        		case "while":
		        			type = TokenType.KEYWORD_WHILE;
		        			break;
            		}
            		
            		tokens.add(new Token(type, identifier));
            	}
				//Sees operation symbol -> builds operation EX:++, --
            	else if(isOperator(buffer.current))
            	{
            		String operator = "";
            		while(isOperator(buffer.current))
            		{
            			operator += buffer.current;
            			buffer.next();
            		}
            		tokens.add(new Token(TokenType.OPERATOR, operator));
            	}
				//SYMBOLS
				//-------------------------------------------
            	else if(buffer.current == '=')
            	{
            		tokens.add(new Token(TokenType.EQUAL, "="));
            		buffer.next();
            	}
            	else if(buffer.current == '[')
            	{
            		tokens.add(new Token(TokenType.BRACKET_OPEN, "["));
            		buffer.next();
            	}  
            	else if(buffer.current == ']')
            	{
            		tokens.add(new Token(TokenType.BRACKET_OPEN, "]"));
            		buffer.next();
            	}
            	else if(buffer.current == '{')
            	{
            		tokens.add(new Token(TokenType.BRACE_CLOSE, "{"));
            		buffer.next();
            	}
            	else if(buffer.current == '}')
            	{
            		tokens.add(new Token(TokenType.BRACE_OPEN, "}"));
            		buffer.next();
            	}
            	else if(buffer.current == ';')
            	{
            		tokens.add(new Token(TokenType.SEMI_COLON, ";"));
            		buffer.next();
            	}
				//---------------------------------------------
				//END SYMBOLS
				
				//Unknown symbol -> moves on :P (EVENTUALLY ERROR CHECK THIS STUFF)
            	else if(buffer.current != '\0')
        		{
            		buffer.next();
        		}
				
				//DEBUG : System.out.println(buffer);
			}
		}
		
		return tokens;
	}
	
	private static boolean isOperator(char c) {return c == '+' || c == '-' || c == '*' || c == '/';}
}
