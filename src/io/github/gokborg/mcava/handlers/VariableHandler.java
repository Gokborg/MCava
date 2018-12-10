package io.github.gokborg.mcava.handlers;

import java.util.HashMap;

import io.github.gokborg.mcava.data.DataType;
import io.github.gokborg.mcava.data.Variable;
import io.github.gokborg.mcava.exceptions.OutOfRegisterSpace;

public class VariableHandler implements ScopeListener
{
	/**
	 * THIS CLASS WILL BE CHANGED SOON
	 */
	private HashMap<String, Variable> varsByName = new HashMap<>();
	
	private MemoryHandler memhdlr;
	private RegisterHandler reghdlr;
	private ScopeHandler scopehdlr;
	private InstructionHandler instrhdlr;
	
	private final int MAXIMUM_REGISTER_ALLOCATIONS;
	private int currentAllocations = 0;
	
	public VariableHandler(MemoryHandler memhdlr, RegisterHandler reghdlr, InstructionHandler instrhdlr, ScopeHandler scopehdlr)
	{
		this.memhdlr = memhdlr;
		this.reghdlr = reghdlr;
		this.scopehdlr = scopehdlr;
		this.instrhdlr = instrhdlr;
		
		//TODO: Make this public so people can edit this
		MAXIMUM_REGISTER_ALLOCATIONS = 1;
	}
	
	public void createVariable(String name, DataType type)
	{
		if(currentAllocations == MAXIMUM_REGISTER_ALLOCATIONS)
		{
			Variable var = new Variable(type, name, memhdlr.allocate(), scopehdlr.getScope());
			
			//We ran out of register space for variables! Switch to using RAM :O
			varsByName.put(name, var);
		}
		else
		{
			int regSpace;
			try 
			{
				regSpace = reghdlr.allocate(name); //get some of that juicy reg space
				currentAllocations++; //increase our usage of regs :c
				varsByName.put(name, new Variable(type, name, regSpace, scopehdlr.getScope()));
				
			} 
			catch (OutOfRegisterSpace e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Frees memory used by variables in previous scopes.
	 * 
	 * Runs whenever the scope changes from inside out.
	 * If were leaving an if statement, for example, this function will run.
	 * However, if were entering an if statment this function WON'T run.
	 * 
	 */
	@Override
	public void leftScope() 
	{
		for(Variable var : varsByName.values())
		{
			memhdlr.deallocate(var.getAddress());
			varsByName.remove(var.getName(), var);
		}
	}
	
	//Returns an integer representing the register that the variable has been stored in
	public int loadVariable(String name)
	{
		//First search registers incase the variable is already in one
		if(reghdlr.search(name))
		{
			return varsByName.get(name).getAddress();
		}
		else
		{
			int regSpace;
			try
			{
				regSpace = reghdlr.allocate(name);
				currentAllocations++;
				instrhdlr.addInstruction("ld r" + regSpace + ", $" + varsByName.get(name).getAddress());
				return regSpace;
			}
			catch (OutOfRegisterSpace e) 
			{
				e.printStackTrace();
				return 0;
			}
		}
	}
	
	public Variable getVariable(String name)
	{
		return varsByName.get(name);
	}
	
	public int getVariableAddress(String name)
	{
		return reghdlr.searchAddress(name);
	}
}
