package mcava.lexer;

public class Token {
	private String tokenString;
	private TokenKind tokenType;
	public Token(String tokenString, TokenKind tokenType) {
		this.tokenString = tokenString;
		this.tokenType = tokenType;
	}
	public String getTokenString() {
		return tokenString;
	}
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
	public TokenKind getTokenType() {
		return tokenType;
	}
	public void setTokenType(TokenKind tokenType) {
		this.tokenType = tokenType;
	}
}
