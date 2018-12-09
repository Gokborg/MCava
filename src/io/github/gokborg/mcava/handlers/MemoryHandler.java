package io.github.gokborg.mcava.handlers;

import java.util.Arrays;

public class MemoryHandler 
{
	private boolean[] memory;
	
	public MemoryHandler(int size)
	{
		memory = new boolean[size];
		Arrays.fill(memory, false);
	}
	
	public int allocate()
	{
		for(int i = 0; i < memory.length; i++)
		{
			if(memory[i] == false)
			{
				memory[i] = true;
				return i;
			}
		}
		System.err.println("Not enough memory!");
		return -1;
	}
	
	public void deallocate(int address)
	{
		memory[address] = false;
	}
}
