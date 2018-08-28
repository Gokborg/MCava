package mcava.parsers;

import java.util.List;

import mcava.exceptions.NoVariableException;
import mcava.exceptions.OutOfMemory;
import mcava.handlers.InstructionHandler;
import mcava.handlers.VariableHandler;
import mcava.lexer.Token;
import mcava.lexer.TokenKind;

public class Parser {
	private InstructionHandler instrhdlr;
	private VariableHandler varhdlr;
	private VariableParser varparser;
	public Parser(InstructionHandler instrhdlr, VariableHandler varhdlr) {
		this.instrhdlr = instrhdlr;
		this.varhdlr = varhdlr;
		varparser = new VariableParser(instrhdlr, varhdlr);
	}
	/*
	 * Before you mess around with the parsers assembly heres some info:
	 * 
	 * When you see a region in code called "Assembly Here: " thats where you put your own custom instructions.
	 * There is already default instructions in those locations.
	 * 
	 * Tokens have properties:
	 * 	1) Token String -> Is the actually token as a string
	 * 	2) Token Type -> is the type of token
	 * 
	 * Variables have properties:
	 * 	1) (String) Name -> The name is the variable name in code, EX: int a = 3; -> the name is 'a'
	 * 	2) (Int) Address -> An assigned memory address 
	 * 	3) (Type) DataType -> Either a variable is an integer or a char
	 * 
	 */
	
	public void parse(List<Token> tokens) {
		if (!tokens.isEmpty()) {
			/*
			 * ASSIGNMENT PARSING
			 * 
			 * Ex: a = 5 + 5
			 * First checking if size of the tokens is greater then two
			 * Then Im looking for a word or char as the first token
			 * Then Im looking for an equal sign
			 */
			if (tokens.size() > 2 && (tokens.get(0).getTokenType() == TokenKind.WORD || tokens.get(0).getTokenType() == TokenKind.CHARACTER) && tokens.get(1).getTokenType() == TokenKind.EQUAL) {
				try {
					varparser.parse(tokens);
				} catch (NoVariableException | OutOfMemory e) {
					e.printStackTrace();
				}
			}
			else {
				System.err.println("Failed to parse tokens!");
			}
		}
		else {
			System.err.println("No tokens available!");
		}
	}
	
	
	
}
