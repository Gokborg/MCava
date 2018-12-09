package io.github.gokborg.mcava.handlers;

import java.util.HashMap;

import io.github.gokborg.mcava.data.DataType;
import io.github.gokborg.mcava.data.Variable;

public class VariableHandler 
{
	private HashMap<String, Variable> variables = new HashMap<>();
	private MemoryHandler memhdlr;
	private RegisterHandler reghdlr;
	private ScopeHandler scopehdlr;
	
	private final int MAXIMUM_REGISTER_ALLOCATIONS;
	private int currentAllocations = 0;
	
	public VariableHandler(MemoryHandler memhdlr, RegisterHandler reghdlr, ScopeHandler scopehdlr)
	{
		this.memhdlr = memhdlr;
		this.reghdlr = reghdlr;
		this.scopehdlr = scopehdlr;
		
		//TODO: Make this public so people can edit this
		MAXIMUM_REGISTER_ALLOCATIONS = 1;
	}
	
	public void createVariable(String name, DataType type)
	{
		if(currentAllocations == MAXIMUM_REGISTER_ALLOCATIONS)
		{
			//We ran out of register space for variables! Switch to using RAM :O
			variables.put(name, new Variable(type, name, memhdlr.allocate(), scopehdlr.getScope()));
		}
		else
		{
			int regSpace = reghdlr.allocate(name);
			currentAllocations++;
			if(regSpace == -1)
			{
				System.err.println("We ran out of register space!");
				
			}
		}
	}
	
	public Variable getVariable(String name)
	{
		return variables.get(name);
	}
	
	public void storeVariable()
	{
		
	}
}
