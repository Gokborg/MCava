package mcava.handlers;

import mcava.exceptions.OutOfMemory;

public class MemoryHandler {
	private boolean[] mem;
	public MemoryHandler(final int AMOUNT_OF_MEM) {
		mem = new boolean[AMOUNT_OF_MEM];
		for (int i = 0; i < AMOUNT_OF_MEM; i++) {
			mem[i] = false;
		}
	}
	public int findSpace() throws OutOfMemory{
		for (int i = 0; i < mem.length; i++) {
			if (mem[i] == false) {
				mem[i] = true;
				return i+1;
			}
		}
		throw new OutOfMemory("No memory available!");
	}
	public void deallocate(int address) {
		mem[address-1] = false;
	}
}
