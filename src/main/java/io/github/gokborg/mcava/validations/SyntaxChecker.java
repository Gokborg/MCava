package io.github.gokborg.mcava.validations;

import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;

public class SyntaxChecker {
	private TokenHandler tokenhdlr;
	private int status;
	public SyntaxChecker(TokenHandler tokenhdlr) {
		this.tokenhdlr = tokenhdlr;
	}
	
	public int getStatus() {
		return status;
	}
	public void resetStatus() {
		status = 0;
	}
	
	public int checkVar() {
		
		/*
		 * Returning 0 : Means that no variable is being intialized or we failed to intialize
		 * Returning 1 : Means that I am intialzing a variable with an expression or value to the right
		 * Returning 2 : Means that I am intialzing a variable with no expression or value to the right
		 * Returning 3 : Means that I am setting a variable equal to an expression or value
		 */
		boolean isInitializing = false;
		if (isDataType(tokenhdlr.getNextToken())) {
			isInitializing = true;
		}
		else {
			isInitializing = false;
			tokenhdlr.movePointerLeft();
		}
		if (isWordOrChar(tokenhdlr.getNextToken())) {
			
			if (isEqual(tokenhdlr.getNextToken())) {
				
				//I move the pointer right to ignore a value
				tokenhdlr.movePointerRight();
				Token tok = tokenhdlr.getNextToken();
				boolean isOperation = false;
				while(tok.getTokenKind() != TokenKind.NONE) {
					
					
					//Looking for proper syntax on expressions
					if (!isOperation && isNum(tok) || isWordOrChar(tok)) {
						isOperation = false;
					}
					else if (isOperation(tok)) {
						isOperation = true;
					}
					else if (isSemiColon(tok)) {
						resetTokenHandler();
						status = !isInitializing ? 3 : 1;
						return !isInitializing ? 3 : 1;
					}
					else {
						System.err.println("There is an error in the expression!");
						resetTokenHandler();
						status = 0;
						return 0;
					}
					
					tok = tokenhdlr.getNextToken();
				}
			}
			else if (isSemiColon(tokenhdlr.getLastToken())) {
				resetTokenHandler();
				if (!isInitializing) {
					System.err.println("You cannot intialze a variable with no given data type!");
					status = 0;
					return 0;
				}
				status = 2;
				return 2;
			}
			else {
				System.err.println("Your not intialzing the variable to anything! : " + tokenhdlr.getCurrentToken().getName());
				resetTokenHandler();
				status = 0;
				return 0;
			}
		}
		else {
			System.err.println("Where is the variable name? : " + tokenhdlr.getCurrentToken().getName());
		}
	
		resetTokenHandler();
		status = 0;
		return 0;
	}
	
	public int checkInitArray() {
		
		/*
		 * Returning 0 : Means that there is an error
		 * Returning 1 : Means that they intialze an array with a given size AND intial values
		 * Returning 2 : Means that they intialze an array with no given size AND intial values
		 * Returning 3 : Means that they intialze an array with a given size AND no intial values
		 */
		boolean givenSize = false;
		Integer arraySize = null;
		if (isDataType(tokenhdlr.getNextToken())) {
			if (isOpenBracket(tokenhdlr.getNextToken())) {
				
				if (isCloseBracket(tokenhdlr.getNextToken()) || isNum(tokenhdlr.getCurrentToken())) {
					
					//Do they have a size for their array?
					if (isNum(tokenhdlr.getCurrentToken())) {
						givenSize = true;
						arraySize = Integer.parseInt(tokenhdlr.getCurrentToken().getName());
						if (!isCloseBracket(tokenhdlr.getNextToken())) {
							//Meaning they did not close the bracket around the number
							resetTokenHandler();
							System.err.println("You did not close the bracket!");
							status = 0;
							return 0;
						}
					}
					//Did they put a name for the array?
					if (isWordOrChar(tokenhdlr.getNextToken())) {
						
						//Meaning they have some numbers to fill the array with
						if (isEqual(tokenhdlr.getNextToken())) {
							if (isOpenBrace(tokenhdlr.getNextToken())) {
								
								tokenhdlr.movePointerRight();
								int argCounter = 1;
								Token tok = tokenhdlr.getNextToken();
								while (tok.getTokenKind() != TokenKind.NONE) {
									if (isCloseBrace(tok)) {
										
										if (arraySize != null && argCounter != arraySize) {
											resetTokenHandler();
											//Meaning they put more intial values than the size of the array
											status = 0;
											return 0;
										}
										if (isSemiColon(tokenhdlr.getNextToken())) {
											resetTokenHandler();
											if (givenSize) {
												resetTokenHandler();
												status = 1;
												return 1;
											}
											resetTokenHandler();
											status = 2;
											return 2;
										}
										resetTokenHandler();
										status = 0;
										return 0;
									}
									if (isNum(tok) || isCharacter(tok)) {
										argCounter++;
									}
									tok = tokenhdlr.getNextToken();
								}
							}
						}
						
						//Meaning there just defining the array  Ex: int a[5];
						else if (isSemiColon(tokenhdlr.getCurrentToken())) {
							if(givenSize) {
								resetTokenHandler();
								status = 3;
								return 3;
							}
							System.err.println("Must give a size to the array!");
							resetTokenHandler();
							status = 0;
							return 0;
						}
					}
					else {
						System.err.println("Where is the variable name? : " + tokenhdlr.getCurrentToken().getName());
					}
				}
			}
		}
		else{
			System.err.println("Where is the data type? : " + tokenhdlr.getCurrentToken().getName());
		}
		resetTokenHandler();
		status = 0;
		return 0;
	}
	
	private void resetTokenHandler() {
		tokenhdlr.resetPointer();
	}
	public static boolean isCharacter(Token tok) {
		return tok.getTokenKind() == TokenKind.CHARACTER;
	}
	public static boolean isOpenBrace(Token tok) {
		return tok.getTokenKind() == TokenKind.OPEN_BRACE;
	}
	public static boolean isCloseBrace(Token tok) {
		return tok.getTokenKind() == TokenKind.CLOSE_BRACE;
	}
	
	public static boolean isCloseBracket(Token tok) {
		return tok.getTokenKind() == TokenKind.CLOSE_BRACKET;
	}
	
	public static boolean isOpenBracket(Token tok) {
		return tok.getTokenKind() == TokenKind.OPEN_BRACKET;
	}
	
	public static boolean isSemiColon(Token tok) {
		return tok.getTokenKind() == TokenKind.SEMI_COLON;
	}
	
	public static boolean isOperation(Token tok) {
		return tok.getTokenKind() == TokenKind.OPERATION;
	}
	
	public static boolean isNum(Token tok) {
		return tok.getTokenKind() == TokenKind.NUMBER;
	}
	
	public static boolean isDataType(Token tok) {
		return tok.getTokenKind() == TokenKind.DATA_TYPE;
	}
	
	public static boolean isEqual(Token tok) {
		return tok.getTokenKind() == TokenKind.EQUAL;
	}
	
	public static boolean isWordOrChar(Token tok) {
		return tok.getTokenKind() == TokenKind.WORD || tok.getTokenKind() == TokenKind.CHARACTER;
	}
}
