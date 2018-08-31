package io.github.gokborg.mcava.lexer;

import java.util.LinkedList;
import java.util.List;

public class Lexer{
	private final char[] specialChars = {'+', '>', '<', '-', '*', '/', ',', '(', ')', ':', ';', '{', '}', '[', ']', '=', '"', '\''};
	private final String[] operationChars = {"+", "-", "*", "/"};
	
	public List<Token> lex(String line){
		line = line + " ";
		List<Token> tokens = new LinkedList<Token>();
		List<String> content = new LinkedList<String>();
		List<Character> wordBuild = new LinkedList<Character>();
		for (int i = 0; i < line.length(); i++) {
			char theChar = line.charAt(i);
			if (theChar != '\0' && theChar != '\t') {
				
				if (theChar == ' ') {
					StringBuilder strBuilder = new StringBuilder();
					for (Character c : wordBuild) {
						strBuilder.append(c);
					}
					content.add(strBuilder.toString());
					wordBuild.clear();
				}
				else {
					if (isSpecialChar(theChar)) {
						if (wordBuild.size() >= 1) {
							StringBuilder strBuilder = new StringBuilder();
							for (Character c : wordBuild) {
								strBuilder.append(c);
							}
							content.add(strBuilder.toString());
							wordBuild.clear();
						}
						content.add(Character.toString(theChar));
					}
					else {
						wordBuild.add(theChar);
					}
				}
			}
		}
		
		for (String str : content) {
			
			if (str.length() == 1) {
				
				if (isOperationChar(str)) {
					
					tokens.add(new Token(str, TokenKind.OPERATION));
				}
				else if (isNum(str)) {
					tokens.add(new Token(str, TokenKind.NUMBER));
				}
				else {
					switch(str) {
					case "[":
						tokens.add(new Token(str, TokenKind.OPEN_BRACKET));
						break;
					case "]":
						tokens.add(new Token(str, TokenKind.CLOSE_BRACKET));
						break;
					case "'":
						tokens.add(new Token(str, TokenKind.SINGLE_QUOTE));
						break;
					case "\"":
						tokens.add(new Token(str, TokenKind.DOUBLE_QUOTE));
						break;
					case ">":
						tokens.add(new Token(str, TokenKind.GREATER));
						break;
					case "<":
						tokens.add(new Token(str, TokenKind.LESS));
						break;
					case "{":
						tokens.add(new Token(str, TokenKind.OPEN_BRACE));
						break;
					case "}":
						tokens.add(new Token(str, TokenKind.CLOSE_BRACE));
						break;
					case "(":
						tokens.add(new Token(str, TokenKind.OPEN_PARANTHESIS));
						break;
					case ")":
						tokens.add(new Token(str, TokenKind.CLOSE_PARANTHESIS));
						break;
					case ";":
						tokens.add(new Token(str, TokenKind.SEMI_COLON));
						break;
					case ":":
						tokens.add(new Token(str, TokenKind.COLON));
						break;
					case ".":
						tokens.add(new Token(str, TokenKind.PERIOD));
						break;
					case ",":
						tokens.add(new Token(str, TokenKind.COMMA));
						break;
					case "=":
						tokens.add(new Token(str, TokenKind.EQUAL));
						break;
					default:
						tokens.add(new Token(str, TokenKind.CHARACTER));
						break;
					}
				}
			}
			else if (str.length() > 1){
				if (str.equalsIgnoreCase("int") || str.equalsIgnoreCase("char") || str.equalsIgnoreCase("float")) {
					tokens.add(new Token(str, TokenKind.DATA_TYPE));
				}
				else if (str.equalsIgnoreCase("print")) {
					tokens.add(new Token(str, TokenKind.CMD));
				}
				else {
					tokens.add(new Token(str, TokenKind.WORD));
				}
				
			}
			
		}
		return tokens;
	}
	private boolean isNum(String str) {
		
		try {
		    Integer.parseInt(str);
		    return true;
		} catch (Exception e) {
			return false;
		}	
	}
	private boolean isOperationChar(String str) {
		
		for (String opStr: operationChars) {
			if (opStr.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}
	private boolean isSpecialChar(char Char) {
		for (char specialChar : specialChars) {
			if (specialChar == Char) {
				return true;
			}
		}
		return false;
	}
}
