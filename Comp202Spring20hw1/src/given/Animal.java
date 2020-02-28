package given;

/*
 * Copyright 2018 Baris Akgun
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of 
 * conditions and the following disclaimer in the documentation and/or other materials provided 
 * with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to 
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Most importantly, this software is provided for educational purposes and should not be used for
 * anything else.
 * 
 * ASSIGNMENT 1
 * AUTHORS: Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

public abstract class Animal implements iHabitat {
	
	protected String name;
	protected int moveSpeed;
	
	//Example static variable, it is setup such as not to be modified outside the constructor!
	private static int number = 0;
	
	//Example default constructor
	public Animal()
	{
		this(0);
	}
	
	//Constructor overloading
	public Animal(int moveSpeed)
	{
		name = "Generic animal";
		number++;
		if(moveSpeed < 0)
			throw new IllegalArgumentException("Move speed of an animal cannot be less than 0!");
		this.moveSpeed = moveSpeed;
	}
	
	/*
	 * Methods with default implementation
	 */
	
	//Example static method
	public static int totalNumberOfAnimals()
	{
		return number;
	}
	
	//Protected method. Visible to all of its subclasses but not the outside world
	protected void move()
	{
		System.out.println("Animals can move");
	}
	
	public int getMovespeed()
	{
		return moveSpeed;
	}
	
	public void description()
	{
		System.out.println("This animal is a " + name);
		if(moveSpeed == 0)
			System.out.println("This animal cannot move!");
		else
		{
			move();
		}
	}
	
	//Providing a default implementation for one of the interface methods
	public boolean isMigratory()
	{
		return false; //assuming most animals are not migratory, can override in the child classes
	}

	/*
	 *   Abstract methods. 
	 */
	
	public abstract String food(); //what do they eat?
	public abstract String dietaryStyle(); //is it carnivorous, herbivorous or omnivorous
	
	// Interface methods
	public abstract String location();
	public abstract String temperature();
	
}
