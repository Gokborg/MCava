package io.github.gokborg.mcava.lexing;

public class LineBuffer 
{
	private String line;
	public char current;
	private int pointer;
	private boolean done = false;
	public void setLine(String line)
	{
		this.line = line;
		current = line.charAt(0);
		pointer = 1;
	}
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
	
	public boolean done()
	{
		return done;
	}
	
	public String toString()
	{
		return "Char: " + current + ", " + (pointer);
	}
}
