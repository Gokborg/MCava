package io.github.gokborg.mcava.validations;

import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;

public class SyntaxChecker {
	private TokenHandler tokenhdlr;
	private int status;
	private String info;
	public SyntaxChecker(TokenHandler tokenhdlr) {
		this.tokenhdlr = tokenhdlr;
	}
	
	public int getStatus() {
		return status;
	}
	public void resetStatus() {
		status = 0;
	}
	public String getInfo() {
		return info;
	}
	public boolean checkLeaveScope() {
		return isCloseBrace(tokenhdlr.getNextToken()) ? true : false;
	}
	public boolean checkIf() {
		if (isIf(tokenhdlr.getNextToken())) {
			if (isOpenParanthesis(tokenhdlr.getNextToken())) {
				if (isWordOrChar(tokenhdlr.getNextToken()) || isNum(tokenhdlr.getCurrentToken())) {
					String firstArg = tokenhdlr.getCurrentToken().getName();
					if (isOpenBracket(tokenhdlr.getNextToken())) {
						if (!isNum(tokenhdlr.getNextToken())) {
							tokenhdlr.resetPointer();
							System.err.println("Missing the index");
							return false;
						}
						if (!isCloseBracket(tokenhdlr.getNextToken())) {
							tokenhdlr.resetPointer();
							System.err.println("Missing close bracket");
							return false;
						}
						firstArg += "[" + tokenhdlr.getLastToken().getName() + "]";
						tokenhdlr.movePointerRight();
					}
					else {
						tokenhdlr.movePointerLeft();
					}
					String condition = tokenhdlr.getNextToken().getName() + tokenhdlr.getNextToken().getName();
					if (isWordOrChar(tokenhdlr.getNextToken()) || isNum(tokenhdlr.getCurrentToken())) {
						String secondArg = tokenhdlr.getCurrentToken().getName();
						if (isOpenBracket(tokenhdlr.getNextToken())) {
							if (!isNum(tokenhdlr.getNextToken())) {
								tokenhdlr.resetPointer();
								System.err.println("Missing the index");
								return false;
							}
							if (!isCloseBracket(tokenhdlr.getNextToken())) {
								System.err.println("Missing close bracket");
								tokenhdlr.resetPointer();
								return false;
							}
							secondArg += "[" + tokenhdlr.getLastToken().getName() + "]";
							tokenhdlr.movePointerRight();
						}
						else {
							tokenhdlr.movePointerLeft();
						}
						if (isCloseParanthesis(tokenhdlr.getNextToken())) {
							if (isOpenBrace(tokenhdlr.getNextToken())) {
								tokenhdlr.resetPointer();
								info = firstArg + " " + secondArg + " " + condition;
								return true;
							}
						}
					}
				}
			}
			System.err.println("[SynChk] Error on line: " + tokenhdlr.getLine());
		}
		tokenhdlr.resetPointer();
		return false;
	}
	
	public boolean checkVar() {
		/*
		 * info sheet: [fast, none] [dataType, none] [word] [expression or value or string]
		 */
		String fast = "none";
		String dataType = "none";
		if (isFast(tokenhdlr.getNextToken())) {
			fast = "fast";
		}
		else {
			tokenhdlr.movePointerLeft();
		}
		if (isDataType(tokenhdlr.getNextToken())) {
			dataType = tokenhdlr.getCurrentToken().getName();
		}
		else {
			if (fast.equalsIgnoreCase("fast")) {
				System.err.println("[SynChk] Missing data type");
				return false;
			}
			tokenhdlr.movePointerLeft();
		}
		
		if (isWordOrChar(tokenhdlr.getNextToken())) {
			
			String varName = tokenhdlr.getCurrentToken().getName();
			
			if (isOpenBracket(tokenhdlr.getNextToken())) {
				if (!isNum(tokenhdlr.getNextToken())) {
					System.err.println("Missing the index");
					return false;
				}
				if (!isCloseBracket(tokenhdlr.getNextToken())) {
					System.err.println("Missing close bracket");
					return false;
				}
				varName += "[" + tokenhdlr.getLastToken().getName() + "]";
				tokenhdlr.movePointerRight();
			}
			else {
				tokenhdlr.movePointerLeft();
			}
			
			if (isEqual(tokenhdlr.getNextToken())) {
				Token tok = tokenhdlr.getNextToken();
				if (tok.getTokenKind() != TokenKind.NONE) {
					String value = "";
					String isCharacter = "none";
					while(tok.getTokenKind() != TokenKind.NONE) {
						if (tok.getTokenKind() == TokenKind.SINGLE_QUOTE) {
							isCharacter = "chararg";
						}
						if (tok.getTokenKind() != TokenKind.SINGLE_QUOTE) {
							if (isSemiColon(tok)) {
								resetTokenHandler();
								if (value != "") {
									//Initializing variable with value
									
									info = fast + " " + dataType + " " + varName + " " + value + " " + isCharacter;
									
									return true;
								}
								else {
									System.err.println("[SynChk] Empty args");
									break;
								}	
							}
							value += tok.getName();
						}
						tok = tokenhdlr.getNextToken();
					}
				}
				else {
					System.err.println("[SynChk] There are no arguments!");
				}
			}
			else if (isSemiColon(tokenhdlr.getCurrentToken())) {
				resetTokenHandler();
				
				//Initializing variable
				info = "none " + dataType + " " + varName + " none none";
				
				return true;
			}
			else {
				System.err.println("[SynChk] Your not intialzing the variable to anything! : " + tokenhdlr.getCurrentToken().getName());
			}
		}
		else {
			//System.err.println("[SynChk] Where is the variable name? : " + tokenhdlr.getCurrentToken().getName());
		}
	
		resetTokenHandler();
		return false;
	}
	
	//TODO: Rewrite this ugly duckling
	public int checkInitArray() {
		/*
		 * Returning 0 : Means that there is an error
		 * Returning 1 : Means that they intialze an array with a given size AND initial values
		 * Returning 2 : Means that they intialze an array with no given size AND initial values
		 * Returning 3 : Means that they intialze an array with a given size AND no initial values
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
							System.err.println("[SynChk] You did not close the bracket!");
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
							System.err.println("[SynChk] Must give a size to the array!");
							resetTokenHandler();
							status = 0;
							return 0;
						}
					}
					else {
						System.err.println("[SynChk] Where is the variable name? : " + tokenhdlr.getCurrentToken().getName());
					}
				}
			}
		}
		else{
			//System.err.println("[SynChk] Where is the data type? : " + tokenhdlr.getCurrentToken().getName());
		}
		resetTokenHandler();
		status = 0;
		return 0;
	}
	
	private void resetTokenHandler() {
		tokenhdlr.resetPointer();
	}
	public static boolean isOpenParanthesis(Token tok) {
		return tok.getTokenKind() == TokenKind.OPEN_PARANTHESIS;
	}
	public static boolean isCloseParanthesis(Token tok) {
		return tok.getTokenKind() == TokenKind.CLOSE_PARANTHESIS;
	}
	public static boolean isIf(Token tok) {
		return tok.getTokenKind() == TokenKind.IF;
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
	
	public static boolean isFast(Token tok) {
		return tok.getTokenKind() == TokenKind.FAST;
	}
	
	public static boolean isEqual(Token tok) {
		return tok.getTokenKind() == TokenKind.EQUAL;
	}
	
	public static boolean isWordOrChar(Token tok) {
		return tok.getTokenKind() == TokenKind.WORD || tok.getTokenKind() == TokenKind.CHARACTER;
	}
}
