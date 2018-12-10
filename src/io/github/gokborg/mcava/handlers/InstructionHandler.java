package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.List;

public class InstructionHandler 
{
	private List<String> instructions = new ArrayList<>();
	
	public void addInstruction(String instruction)
	{
		instructions.add(instruction);
	}
}
