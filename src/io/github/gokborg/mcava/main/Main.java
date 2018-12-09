package io.github.gokborg.mcava.main;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.lexing.Lexer;
import io.github.gokborg.mcava.lexing.TokenBuffer;

public class Main 
{
	public static void main(String[] args)
	{
		List<String> lines = new ArrayList<>();
		lines.add("int a = 5 + 5;");
		TokenBuffer tokBuf = new TokenBuffer(Lexer.lex(lines));
		System.out.println(tokBuf);
	}
}
