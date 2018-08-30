package io.github.gokborg.mcava.handlers;

import java.util.LinkedList;
import java.util.List;

public class InstructionHandler {
	private List<String> instructions = new LinkedList<String>();
	
	public void addInstruction(String instruction) {

		instructions.add(instruction);
	}
	
	public void addInstructions(String[] instructions) {
		for (String instruction : instructions) {
			this.instructions.add(instruction);
		}
	}
	
	public List<String> getInstructions(){
		return instructions;
	}
}
