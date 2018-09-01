package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScopeHandler {
	private int scope = 0;
	private HashMap<Integer, List<String>> onLeaveScopeInstructions = new HashMap<Integer, List<String>>();
	/*
	 * Scope
	 * 
	 * - The higher the scope the deeper you are in curly brackets
	 * - Ex: Closed curly brackets lets the scope decrease 
	 */
	private InstructionHandler instrhdlr;
	public ScopeHandler(InstructionHandler instrhdlr) {
		this.instrhdlr = instrhdlr;
	}
	
	public int getScope() {
		return scope;
	}
	
	public void addOnLeaveInstruction(String instruction) {
		if (onLeaveScopeInstructions.get(scope) != null) {
			onLeaveScopeInstructions.get(scope).add(instruction);
		}
		else {
			onLeaveScopeInstructions.put(scope, new ArrayList<String>());
			addOnLeaveInstruction(instruction);
		}
	}
	
	public void increaseScope() {
		scope++;
	}
	public void decreaseScope() {
		List<String> instructions = onLeaveScopeInstructions.get(scope);
		if (instructions != null) {	
			for (String instruction : instructions) {
				instrhdlr.addInstruction(instruction);
			}
		}
		scope--;
	}
	
}
