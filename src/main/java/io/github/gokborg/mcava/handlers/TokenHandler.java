package io.github.gokborg.mcava.handlers;

import java.util.List;

import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.lexer.TokenKind;

public class TokenHandler {
	private List<Token> tokens;
	private int pointer;
	public TokenHandler(List<Token> tokens) {
		this.tokens = tokens;
		pointer = -1;
	}
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	public List<Token> getTokens(){
		return tokens;
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
		return tokens.get(pointer);
	}
	
	public Token getLastToken() {
		pointer--;
		if (pointer < 0) {
			return new Token("null", TokenKind.NONE);
		}
		return tokens.get(pointer);
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
		if (index >= tokens.size()) {
			return new Token("null", TokenKind.NONE);
		}
		return tokens.get(index);
	}
}
