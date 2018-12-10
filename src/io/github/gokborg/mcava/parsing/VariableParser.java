package io.github.gokborg.mcava.parsing;

import java.util.List;

import io.github.gokborg.mcava.data.DataType;
import io.github.gokborg.mcava.data.Variable;
import io.github.gokborg.mcava.exceptions.OutOfRegisterSpace;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.lexing.Compare;
import io.github.gokborg.mcava.lexing.Token;
import io.github.gokborg.mcava.lexing.TokenBuffer;

public class VariableParser 
{
	private TokenBuffer tokBuff;
	private ExpressionParser expParser;
	private VariableHandler varhdlr;
	private InstructionHandler instrhdlr;
	private RegisterHandler reghdlr;
	
	
	public VariableParser(TokenBuffer tokBuff, VariableHandler varhdlr, RegisterHandler reghdlr, InstructionHandler instrhdlr)
	{
		this.tokBuff = tokBuff;
		this.expParser = new ExpressionParser(tokBuff);
		this.varhdlr = varhdlr;
		this.instrhdlr = instrhdlr;
		this.reghdlr = reghdlr;
	}
	
	public void parse()
	{
		//Call the next token: its either a data type OR an identifier
		Token nextToken = tokBuff.next();
		Variable var = varhdlr.getVariable(nextToken.getValue());
		
		//Means variable does not exist and I need to make one
		if(var == null)
		{
			DataType varType = parseDataType();
			String varName = tokBuff.nextName();
			varhdlr.createVariable(varName, varType);
			tokBuff.skip(); //eat the '='
			
			//Were at the first position of the expression
			//I will now pass it to the expression parser!
			//Whatever instructions it returns the final product will be in the reg specified
			
			
			//TODO: Change this debug stuff plz
			int registerOfVar = reghdlr.searchAddress(varName);
			if(registerOfVar == -1)
			{
				try {
					instrhdlr.addInstruction("ld r" + reghdlr.allocate(varName) + ", $" + varhdlr.getVariable(varName).getAddress());
				} catch (OutOfRegisterSpace e) {
					e.printStackTrace();
				}
			}
			List<String> instrFromExpressionParsing = expParser.parseExpression(registerOfVar);
			
			
			
			
		}
		else
		{
			
		}
		
	}
	
	private DataType parseDataType()
	{
		if(Compare.isOpenBracket(tokBuff.peek(1)))
		{
			if(Compare.isDataTypeInt(tokBuff.current))
			{
				return DataType.ARR_INT;
			}
			else if(Compare.isDataTypeChar(tokBuff.current))
			{
				return DataType.ARR_CHAR;
			}
		}
		else
		{
			if(Compare.isDataTypeInt(tokBuff.current))
			{
				return DataType.INT;
			}
			else if(Compare.isDataTypeChar(tokBuff.current))
			{
				return DataType.CHAR;
			}
		}
		System.err.println("[VariableParser] Failed to parse data type");
		return null;
	}
}
