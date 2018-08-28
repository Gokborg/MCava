package mcava.main;

import java.util.List;

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
		Parser parser = new Parser(instrhdlr, new VariableHandler(new MemoryHandler(64)));
		String[] lines = {"a = 3", "b = a"};
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
