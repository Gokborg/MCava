package io.github.gokborg.mcava.main;

import java.util.ArrayList;
import java.util.List;

import io.github.gokborg.mcava.handlers.MemoryHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.lexing.Lexer;
import io.github.gokborg.mcava.lexing.TokenBuffer;
import io.github.gokborg.mcava.parsing.Parser;

public class Main 
{
	public static void main(String[] args)
	{
		List<String> lines = new ArrayList<>();
		lines.add("int a = 5 + 5;");
		TokenBuffer tokBuf = new TokenBuffer(Lexer.lex(lines));
		System.out.println(tokBuf);
		
		Parser parser = new Parser(tokBuf, new MemoryHandler(64), new RegisterHandler(16));
		
		parser.parse();
		
		//TODO: Make it so that register handler just handles deallocations and allocations. 
		//Variable handler should have where each variable is stored in the registers.
		//Data handler should have where a piece of data/number is stored inside the registers.
	}
}
