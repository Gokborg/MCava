package io.github.gokborg.mcava.handlers;

public class ScopeHandler 
{
	private int scope = 0;
	
	public  void increaseScope()
	{
		scope++;
	}
	
	public  void decreaseScope()
	{
		scope--;
	}
	
	public  int getScope()
	{
		return scope;
	}
}
