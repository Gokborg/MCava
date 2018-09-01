package io.github.gokborg.mcava.parsers;

import io.github.gokborg.mcava.components.Variable;
import io.github.gokborg.mcava.handlers.InstructionHandler;
import io.github.gokborg.mcava.handlers.RegisterHandler;
import io.github.gokborg.mcava.handlers.VariableHandler;
import io.github.gokborg.mcava.validations.SyntaxChecker;

public class ExpressionParser{
	private InstructionHandler instrhdlr;
	private RegisterHandler reghdlr;
	private VariableHandler varhdlr;
	public ExpressionParser(InstructionHandler instrhdlr, RegisterHandler reghdlr, VariableHandler varhdlr) {
		this.instrhdlr = instrhdlr;
		this.reghdlr = reghdlr;
		this.varhdlr = varhdlr;
	}
	public void parse(String info, Variable destVar) {
		/*
		 * info = [arg operation (arg operation (arg operation arg))]
		 * info = [arg operation] [arg operation] [arg operation arg]
		 * Build a tree
		 * 
		 * TODO: Build the complex expression parser
		 */
		String[] expInfo = info.split("/");
		if (expInfo.length != 3) {
			System.err.println("[ExpParse] Failed to translate expression");
			return;
		}
		String operation = expInfo[1];
		String[] operands = {expInfo[0], expInfo[2]};
		int[] registers = new int[2];
		for (int i = 0; i < operands.length; i++) {
			if (varhdlr.isVariable(operands[i])) {
				registers[i] = varhdlr.ldVariable(varhdlr.getVariable(operands[i]));
			}
			else if (SyntaxChecker.isNum(operands[i])) {
				registers[i] = reghdlr.findSpace();
				instrhdlr.addInstruction("li r" + registers[i] + ", " + operands[i]);
			}
			else {
				System.err.println("[ExpParse] Unknown operand!");
				return;
			}
		}
		int destReg = varhdlr.ldVariable(destVar);
		switch(operation) {
		case "+":
			instrhdlr.addInstruction("add r" + destReg + ", r" + registers[1] + ", r" + registers[0]);
			break;
		case "-":
			instrhdlr.addInstruction("sub r" + destReg  + ", r" + registers[1] + ", r" + registers[0]);
			break;
		case "*":
			instrhdlr.addInstruction("mult r" + destReg  + ", r" + registers[1] + ", r" + registers[0]);
			break;
		case "/":
			instrhdlr.addInstruction("div r" + destReg  + ", r" + registers[1] + ", r" + registers[0]);
			break;
		}
		
		varhdlr.tryRegDeallocate(registers[0]);
		varhdlr.tryRegDeallocate(registers[1]);
	}
}
