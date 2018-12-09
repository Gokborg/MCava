package io.github.gokborg.mcava.handlers;

import java.util.Arrays;

public class RegisterHandler 
{
	private String[] registerFile;
	private int size;
	public RegisterHandler(int size)
	{
		//Not using reg 0, adding one to the size
		registerFile = new String[size+1];
		this.size = size;
		Arrays.fill(registerFile, "\0");
	}
	//Whenever you allocate you give something to signify whats in there.
	//Example => I want to store a variable in a reg, I give its name as the arg info.
	public int allocate(String info)
	{
		//No! Were not using register 0 .-.
		for(int i = 1; i < registerFile.length; i++)
		{
			if(registerFile[i].equals("\0"))
			{
				registerFile[i] = info;
				return i;
			}
		}
		return -1;
	}
	
	//If you want to see if a variable is already in a register.
	//Also useful for if you want to see if a numerical value is in a register. 
	public boolean search(String arg)
	{
		for(String info : registerFile)
		{
			if(info.equals(arg))
			{
				return true;
			}
		}
		return false;
	}
	
	//Deallocation by name
	public void deallocate(String info)
	{
		for(int i = 1; i < registerFile.length; i++)
		{
			if(registerFile[i].equals(info))
			{
				registerFile[i] = "\0";
				return;
			}
		}
	}
	
	//Deallocation by address
	public void deallocate(int address)
	{
		registerFile[address] = "\0";
	}
	
	public int getSize()
	{
		return size;
	}
}
