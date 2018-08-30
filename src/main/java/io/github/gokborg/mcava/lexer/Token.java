package io.github.gokborg.mcava.lexer;

public class Token {
	private final String NAME;
	private final TokenKind TOKEN_KIND;
	
	public Token(String NAME, TokenKind TOKEN_KIND) {
		this.NAME = NAME;
		this.TOKEN_KIND = TOKEN_KIND;
	}
	
	public String getName() {
		return NAME;
	}
	public TokenKind getTokenKind() {
		return TOKEN_KIND;
	}
}
