package mcava.main;

import java.util.List;

import mcava.lexer.Lexer;
import mcava.lexer.Token;

public class Main {
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		List<Token> toks = lexer.lex("int a = 3;");
		for (Token tok : toks) {
			System.out.println(tok.getTokenString() + " -> " + tok.getTokenType());
		}
	}
}
