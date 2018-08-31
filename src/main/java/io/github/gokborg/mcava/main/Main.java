package io.github.gokborg.mcava.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import io.github.gokborg.mcava.handlers.ArrayHandler;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.MemoryHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.ScopeHandler;
import io.github.gokborg.mcava.handlers.TokenHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.lexer.Lexer;
import io.github.gokborg.mcava.lexer.Token;
import io.github.gokborg.mcava.parsers.Parser;
import io.github.gokborg.mcava.validations.Filter;
import io.github.gokborg.mcava.validations.SyntaxChecker;


public class Main {
	public static void main(String[] args) {
		//Part of Stage 0:
		Filter filter = new Filter();
		
		//Part of Stage 2:
		TokenHandler tokenhdlr = new TokenHandler(null);
		
		/*
		 * Part of Stage 3:
		 */
		
		//Synthax Checker
		SyntaxChecker synChk = new SyntaxChecker(tokenhdlr);
		
		//Handlers
		RegisterHandler reghdlr = new RegisterHandler(8);
		InstructionHandler instrhdlr = new InstructionHandler();
		MemoryHandler memhdlr = new MemoryHandler(64);
		VariableHandler varhdlr = new VariableHandler(memhdlr);
		ArrayHandler arrhdlr = new ArrayHandler(varhdlr);
		ScopeHandler scopehdlr = new ScopeHandler();
		
		//Parser
		Parser parser = new Parser(instrhdlr, arrhdlr, reghdlr, varhdlr, tokenhdlr, scopehdlr);
		Scanner sc;
		try {
			sc = new Scanner(new File("code.mcava"));
			while (sc.hasNextLine()) {
				
				String line = sc.nextLine();
				line = line.substring(0, line.lastIndexOf("//") != -1 ? line.lastIndexOf("//") : line.length());
				
				//Stage 0 : Look for dirty lines
				if (!filter.ignore(line)) {
					
					//Stage 1 : Create the tokens.
					List<Token> tokens = Lexer.lex(line);
					
					//Step 2 : Send the generated tokens to the token handler
					tokenhdlr.setTokens(tokens);
					
					//Stage 3 : Begin parsing 
					parser.parse(synChk);
				}

				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		//Printing out the generated instructions
		for (String instruction : instrhdlr.getInstructions()) {
			System.out.println(instruction);
		}
	}
}

