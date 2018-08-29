package mcava.parsers;

import java.util.List;

import mcava.components.Array;
import mcava.exceptions.IndexException;
import mcava.exceptions.NoArrayException;
import mcava.exceptions.NoVariableException;
import mcava.exceptions.OutOfMemory;
import mcava.handlers.ArrayHandler;
import mcava.handlers.InstructionHandler;
import mcava.lexer.Token;
import mcava.lexer.TokenKind;

public class ArrayParser {
	private ArrayHandler arrhdlr;
	private VariableParser varparser;
	private InstructionHandler instrhdlr;
	public ArrayParser(ArrayHandler arrhdlr, InstructionHandler instrhdlr, VariableParser varparser) {
		this.arrhdlr = arrhdlr;
		this.varparser = varparser;
		this.instrhdlr = instrhdlr;
	}
	
	public void parse(List<Token> tokens) throws OutOfMemory, NoArrayException, NoVariableException, IndexException {
		if (!tokens.isEmpty() && tokens.size() >= 4) {
			
			/*
			 * Validation for the destination array
			 */
			Array destArr = null;
			try {
				destArr = arrhdlr.getArray(tokens.get(0).getTokenString());
			} catch (NoArrayException e) {
				/*
				 * Proper syntax
				 * Ex: a[4] = blah blah
				 */
				if (tokens.size() >= 7 || tokens.size() == 4 && tokens.get(2).getTokenType() == TokenKind.NUMBER && tokens.get(1).getTokenType() == TokenKind.OPEN_BRACKET && tokens.get(3).getTokenType() == TokenKind.CLOSE_BRACKET) {
					try {
						arrhdlr.createArray(tokens.get(0).getTokenString(), Integer.parseInt(tokens.get(2).getTokenString()));
						destArr = arrhdlr.getArray(tokens.get(0).getTokenString());
					} catch (NumberFormatException e1) {
						System.err.println("[ArrParser] Not a valid size for the array!");
					}
				}
				else {
					throw new NoArrayException("[ArrParser] The array does not exist");
				}
			}
			if (tokens.size() == 4) {
				/*
				 * Means your just intializing the array not doing anything with it
				 */
			}
			else {
				/*
				 * Your setting it equal to something
				 */
				
				
				/*
				 * Intialzing array with given values
				 * Ex: a[3] = {1, 2, 3}
				 */
				if (tokens.get(5).getTokenType() == TokenKind.OPEN_BRACE) {
					if (isEven(tokens.size())) {
						int availableIndex = 0;
						Integer value = null;
						for (int i = 5; tokens.get(i).getTokenType() != TokenKind.CLOSE_BRACE; i++) {
							if (tokens.get(i).getTokenType() == TokenKind.CHARACTER) {
								value = (int) tokens.get(i).getTokenString().charAt(0);
							}
							else if (tokens.get(i).getTokenType() == TokenKind.NUMBER) {
								value = Integer.parseInt(tokens.get(i).getTokenString());
							}
							if (value != null) {
								instrhdlr.addInstruction("li r1, " + value);
								instrhdlr.addInstruction("str $" + arrhdlr.getAddress(destArr.getName(), availableIndex) + ", r1");
								availableIndex++;
								value = null;
							}
						}
						
						
					}
					else {
						System.err.println("[ArrParser] Intialize array like:  name[size] = {value, value, value}");
					}
				}
				else {
					arrhdlr.getAddress(destArr.getName(), Integer.parseInt(tokens.get(2).getTokenString()));
					
					String accessName = tokens.get(0).getTokenString() + tokens.get(1).getTokenString() + tokens.get(2).getTokenString() + tokens.get(3).getTokenString();
					for (int i = 0; i < 3; i++) {
						tokens.remove(0);
					}
					tokens.set(0, new Token(accessName, TokenKind.WORD));
					
					
					varparser.parse(tokens);
				}
				
				
			}
			
			
			
			
			
		}
	}

	private boolean isEven(int size) {return size%2 == 0 ? true : false;}
}
