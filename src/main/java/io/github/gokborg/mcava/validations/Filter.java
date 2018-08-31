package io.github.gokborg.mcava.validations;

public class Filter {
	public boolean ignore(String line) {
		return line.isEmpty() ? true : false;
	}
}
