package io.github.gokborg.mcava.handlers;

import java.util.List;

import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;

public class TokenHandler {
	private List<Token> tokens;
	private int pointer;
	private String line;
	public TokenHandler(List<Token> tokens, String line) {
		this.tokens = tokens;
		pointer = -1;
		this.line = line;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	public List<Token> getTokens(){
		return tokens;
	}
	public int getPointer() {
		return pointer;
	}
	public void removeToken(int index) {
		tokens.remove(index);
	}
	public void removeTokenAtPointer() {
		tokens.remove(pointer != -1 ? pointer : 0);
	}
	public void movePointer(int amount) {
		pointer += amount;
	}
	
	public void movePointerRight() {
		pointer++;
	}
	public void movePointerLeft() {
		pointer--;
	}
	public Token getCurrentToken() {
		return pointer != -1 ? tokens.get(pointer) : tokens.get(0);
	}
	
	public Token getLastToken() {
		pointer--;
		return pointer < 0 ? new Token("null", TokenKind.NONE) : tokens.get(pointer);
	}
	public void resetPointer() {
		pointer = -1;
	}
	public Token getNextToken() {
		pointer++;
		if (pointer >= tokens.size()) {
			pointer--;
			return new Token("null", TokenKind.NONE);
		}
		return tokens.get(pointer);
	}
	public Token getNextToken(int index) {
		pointer = index-1;
		return index >= tokens.size() ? new Token("null", TokenKind.NONE) : tokens.get(index);

		
	}
}
