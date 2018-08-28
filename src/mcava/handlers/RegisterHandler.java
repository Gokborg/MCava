package mcava.handlers;

import mcava.exceptions.RegisterFullException;

public class RegisterHandler {
	private boolean[] regStates;
	public RegisterHandler(final int AMOUNT_OF_REGS) {
		regStates = new boolean[AMOUNT_OF_REGS];
		for (int i = 0; i < AMOUNT_OF_REGS; i++) {
			regStates[i] = false;
		}
	}
	
	/* Returns an available register but if none it returns 0
	 * Makes the avaiable register unavaialable
	 */
	public int findSpace() throws RegisterFullException{
		for (int i = 0; i < regStates.length; i++) {
			if (regStates[i] == false) {
				regStates[i] = true;
				return i+1;
			}
		}
		throw new RegisterFullException("No available registers!");
	}
	
	//Sets the register to the available state
	public void deallocate(int reg) {
		regStates[reg-1] = false;
	}
}
