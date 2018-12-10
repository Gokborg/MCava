package io.github.gokborg.mcava.handlers;

public interface ScopeListener 
{
	//Used for pushing instructions when the scope is lessened / Freeing memory
	public void leftScope();
}
