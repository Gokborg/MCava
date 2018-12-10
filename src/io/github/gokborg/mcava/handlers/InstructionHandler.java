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
	
	@Override
	public String toString()
	{
		String allInstructions = "";
		for(int i = 0; i < instructions.size(); i++)
		{
			allInstructions += i + ": " + instructions.get(i) + "\n";
		}
		return allInstructions;
	}
}
