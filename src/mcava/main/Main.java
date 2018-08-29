package mcava.main;

import java.util.List;

import mcava.handlers.ArrayHandler;
import mcava.handlers.InstructionHandler;
import mcava.handlers.MemoryHandler;
import mcava.handlers.VariableHandler;
import mcava.lexer.Lexer;
import mcava.lexer.Token;
import mcava.parsers.Parser;

public class Main {
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		InstructionHandler instrhdlr = new InstructionHandler();
		MemoryHandler memhdlr = new MemoryHandler(64);
		VariableHandler varhdlr = new VariableHandler(memhdlr);
		Parser parser = new Parser(instrhdlr, new ArrayHandler(varhdlr), varhdlr);
		String[] lines = {
				"a[5]",
				"a[0] = 3",
				"a[4] = 1"
				
		};
		System.out.println("Lines: ");
		for (String line : lines) {
			List<Token> toks = lexer.lex(line);
			System.out.println(line);
			parser.parse(toks);
			
		}
		
		
		
		System.out.println("\nAssembly:");
		
		
		for (String instruction : instrhdlr.getInstructions()) {
			System.out.println(instruction);
		}
		
	}
}
