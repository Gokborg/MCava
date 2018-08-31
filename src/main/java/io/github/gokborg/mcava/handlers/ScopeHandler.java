package io.github.gokborg.mcava.handlers;

public class ScopeHandler {
	private int scope = 0;
	
	/*
	 * Scope
	 * 
	 * - The higher the scope the deeper you are in curly brackets
	 * - Closed curly brackets lets the scope decrease 
	 */
	
	public int getScope() {
		return scope;
	}
	
	public void increaseScope() {
		scope++;
	}
	public void decreaseScope() {
		scope--;
	}
}
