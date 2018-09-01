package io.github.gokborg.mcava.handlers;

import java.util.Arrays;

public class RegisterHandler {
	private boolean[] regStates;
	public RegisterHandler(final int AMOUNT_OF_REGS) {
		regStates = new boolean[AMOUNT_OF_REGS];
		Arrays.fill(regStates, false);
	}
	
	/* 
	 * Returns an available register but if none it returns null
	 * Makes the available register unavaialable
	 */
	public Integer findSpace(){
		for (int i = 0; i < regStates.length; i++) {
			if (!regStates[i]) {
				regStates[i] = true;
				return i+1;
			}
		}
		System.err.println("There are no available registers!");
		return null;
	}
	
	//Sets the register to the available state
	public void deallocate(int reg) {
		regStates[reg-1] = false;
	}
	
	public int getSize() {
		return regStates.length;
	}
}
