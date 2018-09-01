package io.github.gokborg.mcava.handlers;

import java.util.ArrayList;
import java.util.List;

public class ScopeHandler {
	private int scope = 0;
	private List<String> onLeaveScopeInstructions = new ArrayList<String>();
	/*
	 * Scope
	 * 
	 * - The higher the scope the deeper you are in curly brackets
	 * - Closed curly brackets lets the scope decrease 
	 */
	private InstructionHandler instrhdlr;
	public ScopeHandler(InstructionHandler instrhdlr) {
		this.instrhdlr = instrhdlr;
	}
	
	public int getScope() {
		return scope;
	}
	
	public void addOnLeaveInstruction(String instruction) {
		onLeaveScopeInstructions.add(instruction);
	}
	
	public void increaseScope() {
		scope++;
	}
	public void decreaseScope() {
		scope--;
		for (String instruction : onLeaveScopeInstructions) {
			instrhdlr.addInstruction(instruction);
		}
	}
}
