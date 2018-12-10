package io.github.gokborg.mcava.data;

public class Variable 
{
	private int address;
	private int scope;
	private String name;
	private DataType type;
	
	public Variable(DataType type, String name, int address, int scope)
	{
		this.address = address;
		this.scope = scope;
		this.name = name;
		this.type = type;
	}
	
	public int getAddress()
	{
		return address;
	}

	public void setAddress(int address) 
	{
		this.address = address;
	}

	public int getScope() 
	{
		return scope;
	}

	public void setScope(int scope) 
	{
		this.scope = scope;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public DataType getType() 
	{
		return type;
	}

	public void setType(DataType type) 
	{
		this.type = type;
	}
	
	public String toString()
	{
		return "Var: name = '" + name + "', type = '" + type + "'";
	}
}
