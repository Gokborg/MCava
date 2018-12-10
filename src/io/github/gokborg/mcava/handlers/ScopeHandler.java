package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.List;

public class ScopeHandler 
{
	private int scope = 0;
	
	private List<ScopeListener> scopeListeners = new ArrayList<>();
	
	public void addListener(ScopeListener scListener)
	{
		scopeListeners.add(scListener);
	}
	
	public void increaseScope()
	{
		scope++;
	}
	
	public void decreaseScope()
	{
		for(ScopeListener scListener : scopeListeners)
		{
			scListener.leftScope();
		}
		scope--;
	}
	
	public  int getScope()
	{
		return scope;
	}
}
