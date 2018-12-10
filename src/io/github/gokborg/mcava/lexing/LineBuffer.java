package io.github.gokborg.mcava.lexing;

public class LineBuffer 
{
	private String line;
	public char current;
	private int pointer;
	private boolean done = false;
	
	/**
	 * Sets a line for the line buffer to use. Resets variables.
	 * 
	 * @param 	line	the line you want in the buffer
	 */
	public void setLine(String line)
	{
		this.line = line;
		current = line.charAt(0);
		pointer = 1;
		done = false;
	}
	
	/**
	 * Returns the char at pointer. Increments pointer.
	 * 
	 * @return	current char
	 */
	public char next()
	{
		if(pointer == line.length())
		{
			done = true;
			return '\0';
		}
		current = line.charAt(pointer);
		pointer++;
		return current;
	}
	
	/**
	 * Returns whether or not the pointer reached the end.
	 * 
	 * @return	pointer is at the end of 'line'.
	 */
	public boolean done()
	{
		return done;
	}
	
	/**
	 * DEBUG: Prints the char & where the pointer is going next
	 */
	public String toString()
	{
		return "Char: " + current + ", " + (pointer);
	}
}
