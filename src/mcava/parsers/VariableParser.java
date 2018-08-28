package mcava.parsers;

import java.util.List;

import mcava.components.Variable;
import mcava.exceptions.NoVariableException;
import mcava.exceptions.OutOfMemory;
import mcava.handlers.InstructionHandler;
import mcava.handlers.VariableHandler;
import mcava.lexer.Token;
import mcava.lexer.TokenKind;

public class VariableParser {
	private InstructionHandler instrhdlr;
	private VariableHandler varhdlr;

	public VariableParser(InstructionHandler instrhdlr, VariableHandler varhdlr) {
		this.instrhdlr = instrhdlr;
		this.varhdlr = varhdlr;

	}
	public void parse(List<Token> tokens) throws NoVariableException, OutOfMemory{
		Variable destVar;
		try{
			destVar = varhdlr.getVariable(tokens.get(0).getTokenString());
		}
		catch(NoVariableException e) {
			varhdlr.createVariable(tokens.get(0).getTokenString());
			destVar = varhdlr.getVariable(tokens.get(0).getTokenString());
		}
		
		/* Means we have a single assignment
		 * EX: a = 4; 
		 * EX: a = b;
		 */
		if (tokens.size() <= 5) {
			
			/*
			 * Assignment to a number
			 * EX: a = 3;
			 * Assembly:
			 * li availableRegister, 3
			 * str $a, availableRegister
			 */
			if (tokens.get(2).getTokenType() == TokenKind.NUMBER) {			
					/*
					 * Assembly goes here:
					 *        int                     a                           =                               4                             
					 * {destVar.getDataType()} {destVar.getName()} {tokens.get(1).getTokenString()} {tokens.get(2).getTokenString()}
					 */
					
				instrhdlr.addInstruction("li r1, " + tokens.get(2).getTokenString());
				instrhdlr.addInstruction("str $" + destVar.getAddress() + ", r1"); 
			}
			
			
			/*
			 * Single assignment to a variable
			 * EX: a = b;
			 */
			else if (tokens.get(2).getTokenType() == TokenKind.CHARACTER || tokens.get(2).getTokenType() == TokenKind.WORD) {
				try {
					Variable srcVar = varhdlr.getVariable(tokens.get(2).getTokenString());

					instrhdlr.addInstruction("ld r1, $" + srcVar.getAddress());
					instrhdlr.addInstruction("str $" + destVar.getAddress() + ", r1");
					
					
				}
				catch (NoVariableException e) {
					e.printStackTrace();
				}
			}
			
			else if (tokens.get(3).getTokenType() == TokenKind.CHARACTER) {
				int asciiValue = tokens.get(3).getTokenString().charAt(0);
				
				//instrhdlr.addInstruction();
			}
		}
		else {
			/*
			 * Some expression parser
			 */
		}
	}
}

