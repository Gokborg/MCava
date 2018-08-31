package io.github.gokborg.mcava.handlers;

import java.util.Arrays;

public class MemoryHandler {
	private boolean[] mem;
	public MemoryHandler(final int AMOUNT_OF_MEM) {
		mem = new boolean[AMOUNT_OF_MEM];
		Arrays.fill(mem, false);
	}
	public Integer findSpace(){
		for (int i = 0; i < mem.length; i++) {
			if (!mem[i]) {
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
