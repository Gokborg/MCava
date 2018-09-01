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
					resetTokenHandler();
					return false;
				}
				if (!isCloseBracket(tokenhdlr.getNextToken())) {
					System.err.println("Missing close bracket");
					resetTokenHandler();
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
									resetTokenHandler();
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
	
	public boolean checkInitArray() {
		
		// Info string : [data_type] [name] [size] [intial values]
		
		String arraySize = "none";
		String dataType = "none";
		if (isDataType(tokenhdlr.getNextToken())) {
			dataType = tokenhdlr.getCurrentToken().getName();
			if (isOpenBracket(tokenhdlr.getNextToken())) {
				
				if (isCloseBracket(tokenhdlr.getNextToken()) || isNum(tokenhdlr.getCurrentToken())) {
					
					//Do they have a size for their array?
					if (isNum(tokenhdlr.getCurrentToken())) {
						arraySize = tokenhdlr.getCurrentToken().getName();
						if (!isCloseBracket(tokenhdlr.getNextToken())) {
							//Meaning they did not close the bracket around the number
							resetTokenHandler();
							System.err.println("[SynChk] You did not close the bracket!");
							return false;
						}
					}
					//Did they put a name for the array?
					if (isWordOrChar(tokenhdlr.getNextToken())) {
						String name = tokenhdlr.getCurrentToken().getName();
						//Meaning they have some numbers to fill the array with
						if (isEqual(tokenhdlr.getNextToken())) {
							if (isOpenBrace(tokenhdlr.getNextToken())) {
								String args = "";
								int argCounter = 0;
								Token tok = tokenhdlr.getNextToken();
								while (tok.getTokenKind() != TokenKind.NONE) {
									if (!isComma(tok) && !isSingleQuote(tok) && (isNum(tok) || isCharacter(tok))) {
										if (dataType.equalsIgnoreCase("char")) {
											if (isSingleQuote(tokenhdlr.getLastToken())) {
												
												tokenhdlr.movePointerRight();
												if (isSingleQuote(tokenhdlr.getNextToken())) {
													args += tok.getName() + "/";
													argCounter++;
													tokenhdlr.movePointerLeft();
												}
											}
											else {
												System.err.println("[SynChk] Missing quotation around char(s) : " + tokenhdlr.getLine());
												resetTokenHandler();
												return false;
											}
										}
										else if (dataType.equalsIgnoreCase("int")) {
											args += tok.getName() + "/";
											argCounter++;
										}
										else {
											System.err.println("[SynChk] Unknown array data type! : " + tokenhdlr.getLine());
											resetTokenHandler();
											return false;
										}
										
									}
									if (isCloseBrace(tok)) {
										if (isSemiColon(tokenhdlr.getNextToken())) {
											if (arraySize.equalsIgnoreCase("none")) {
												arraySize = String.valueOf(argCounter);
											}
											if (Integer.parseInt(arraySize) == argCounter) {
												info = dataType + " " + name + " " + arraySize + " " + args;
												resetTokenHandler();
												return true;
											}
											else {
												System.err.println("[SynChk] Not enough arguments for size: " + tokenhdlr.getLine());
												resetTokenHandler();
												return false;
											}
										}
									}
									tok = tokenhdlr.getNextToken();
								}
								System.err.println("[SynChk] Bad arguements: " + tokenhdlr.getLine());
							}
						}
						
						//Meaning there just defining the array  Ex: int a[5];
						else if (isSemiColon(tokenhdlr.getCurrentToken())) {
							if(!arraySize.equalsIgnoreCase("none")) {
								resetTokenHandler();
								info = dataType + " " + name + " " + arraySize + " none";
								return true;
							}
							System.err.println("[SynChk] Must give a size to the array!");
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
		return false;
	}
	
	private void resetTokenHandler() {
		tokenhdlr.resetPointer();
	}
	public static boolean isNum(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	public static boolean isSingleQuote(Token tok) {
		return tok.getTokenKind() == TokenKind.SINGLE_QUOTE;
	}
	public static boolean isComma(Token tok) {
		return tok.getTokenKind() == TokenKind.COMMA;
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
