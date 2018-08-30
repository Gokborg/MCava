package io.github.gokborg.mcava.handlers;

public class MemoryHandler {
	private boolean[] mem;
	public MemoryHandler(final int AMOUNT_OF_MEM) {
		mem = new boolean[AMOUNT_OF_MEM];
		for (int i = 0; i < AMOUNT_OF_MEM; i++) {
			mem[i] = false;
		}
	}
	public Integer findSpace(){
		for (int i = 0; i < mem.length; i++) {
			if (mem[i] == false) {
				mem[i] = true;
				return i+1;
			}
		}
		System.out.println("Out of memory!");
		return null;
	}
	public void deallocate(int address) {
		mem[address-1] = false;
	}
}
