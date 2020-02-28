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
import Autograder.*;
import code.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main{

	public static void main(String[] args) throws RuntimeException, Throwable {

		System.out.println("Assignment 1");
		Autograder.init();

		//Code that compiles gets 2!
		double grade = 2;


		//Check if iBehavior is correctly implemented
		Map<String, agMethod> iBehMap = new HashMap<String, agMethod>();
		iBehMap.put("isWild", new agMethod("isWild",new Class<?>[0], Boolean.TYPE, 1));
		iBehMap.put("run", new agMethod("run",new Class<?>[0], Void.TYPE, 1));
		Class<?>[] tmp = {int.class};
		iBehMap.put("sleep", new agMethod("sleep", tmp, Void.TYPE, 1));

		Class<?> cCat = Cat.class;

		try {
			// //Interface name
			Class<?> iBeh = Class.forName("code.iBehavior");
			if(iBeh.isInterface())
				grade += 5;
			else
				Autograder.Log("iBehavior is not implemented as an interface");

			int iBehMethodMatch = Autograder.numMethodMatches(iBehMap, iBeh);
			if(iBehMethodMatch < iBehMap.size())
				Autograder.Log("iBehavior methods do not match the desired ones");
			grade += 3*iBehMethodMatch;

			if (Autograder.testIfImplemented(cCat, iBeh)) {
				grade += 5;
			}
			else
				Autograder.Log("Cat does not implement the iBehavior interface");

		}
		catch(ClassNotFoundException e) {
			Autograder.Log("There is no interface named iBehavior");
		}

		//Check Cat constructors
		List<Class<?>[]> catConstParams = new ArrayList<Class<?>[]>();
		Class<?>[] catConst1  = new Class<?>[0];
		Class<?>[] catConst2  = {String.class, String.class};
		catConstParams.add(catConst1);
		catConstParams.add(catConst2);
		int cCatConstMatch = Autograder.numConstructorMatches(catConstParams, cCat);
		if(cCatConstMatch < catConstParams.size())
			Autograder.Log("Cat constructors do not match the desired ones");
		grade += 1*cCatConstMatch;

		Map<String, agMethod> catMethodList = new HashMap<String, agMethod>();
		catMethodList.put("food", new agMethod("food",new Class<?>[0], String.class, 1));
		catMethodList.put("dietaryStyle", new agMethod("dietaryStyle",new Class<?>[0], String.class, 1));
		catMethodList.put("location", new agMethod("location", new Class<?>[0], String.class, 1));
		catMethodList.put("temperature", new agMethod("temperature", new Class<?>[0], String.class, 1));

		int cCatMethodMatch = Autograder.numMethodMatches(catMethodList, cCat);
		if(cCatMethodMatch < catMethodList.size())
			Autograder.Log("Cat methods do not match the desired ones");
		grade += 3*cCatMethodMatch;

		Map<String, agField> catFieldList = new HashMap<String, agField>();
		catFieldList.put("breed", new agField("breed",String.class));
		catFieldList.put("sound", new agField("sound",String.class));
		catFieldList.put("name", new agField("name",String.class));
		catFieldList.put("moveSpeed", new agField("moveSpeed",int.class));

		Cat cat1 = new Cat();
		Map<String, Object> cat1ValueList = new HashMap<String, Object>();
		cat1ValueList.put("breed", "common cat");
		cat1ValueList.put("sound", "meow");
		cat1ValueList.put("name", "cat");
		cat1ValueList.put("moveSpeed", 17);

		int constTest1 = Autograder.numFieldAndValueMatches(cat1, catFieldList, cat1ValueList, true);
		if (constTest1 < cat1ValueList.size())
			Autograder.Log("Default cat constructor is not implemented correctly");
		grade += 4*constTest1;


		Cat cat2 = new Cat("Van","meeeow");
		Map<String, Object> cat2ValueList = new HashMap<String, Object>();
		cat2ValueList.put("breed", "Van");
		cat2ValueList.put("sound", "meeeow");
		cat2ValueList.put("name", "cat");
		cat2ValueList.put("moveSpeed", 17);

		int constTest2 = Autograder.numFieldAndValueMatches(cat2, catFieldList, cat2ValueList, true);
		if (constTest2 < cat2ValueList.size())
			Autograder.Log("Overloaded cat constructor is not implemented correctly");
		grade += 4*constTest2;

		if(!cat1.isWild())
			grade += 1;
		else
			Autograder.Log("Cats are not usually wild!");

		if(!cat1.isMigratory())
			grade += 1;
		else
			Autograder.Log("Cats are not migratory!");

		if(cat1.food().equals("cat food"))
			grade += 1;
		else
			Autograder.Log("Cats eat cat food!");

		if(cat1.dietaryStyle().equals("carnivorous"))
			grade += 1;
		else
			Autograder.Log("Cats are carnivorous!");

		if(cat1.location().equals("land"))
			grade += 1;
		else
			Autograder.Log("Cats live on land!");

		if(cat1.temperature().equals("mild"))
			grade += 1;
		else
			Autograder.Log("Cats like mild temperatures!");

		try {
			cat1.sleep(-1);
			Autograder.Log("Wrong sleep values must cause exception");
		} catch(Exception e) {
			grade += 3;
		}

		Autograder.coc.start();
		cat1.sleep(3);
		String sleepStr = Autograder.coc.stop();
		if(sleepStr.equals("Zzzz" + System.lineSeparator()))
			grade += 3;
		else
			Autograder.Log("Not sleeping well?");


		Caracal uzunKulak = new Caracal();
		Map<String, Object> caracalValueList = new HashMap<String, Object>();
		caracalValueList.put("breed", "Caracal");
		caracalValueList.put("sound", "hiss");
		caracalValueList.put("moveSpeed", 22);

		int constTestC = Autograder.numFieldAndValueMatches(uzunKulak, catFieldList, caracalValueList, true);
		if (constTestC < caracalValueList.size())
			Autograder.Log("Caracal constructor is not implemented correctly");
		grade += 3*constTestC;

		if(uzunKulak.isWild())
			grade += 1;
		else
			Autograder.Log("Caracals are wild!");

		Autograder.coc.start();
		uzunKulak.description();
		String uzunKulakDesc = Autograder.coc.stop();

		if(uzunKulakDesc.toLowerCase().contains("Caracal cats have longer ears".toLowerCase()))
			grade += 3;
		else
			Autograder.Log("Check Caracal description");

		Angora  tekGoz = new Angora();
		Map<String, Object> angoraValueList = new HashMap<String, Object>();
		angoraValueList.put("breed", "Angora");
		angoraValueList.put("sound", "meow");
		angoraValueList.put("moveSpeed", 15);

		int constTestA = Autograder.numFieldAndValueMatches(tekGoz, catFieldList, angoraValueList, true);
		if (constTestA < angoraValueList.size())
			Autograder.Log("Angora constructor is not implemented correctly");
		grade += 2*constTestA;

		Autograder.coc.start();
		tekGoz.description();
		String tekGozDesc = Autograder.coc.stop();

		if(tekGozDesc.toLowerCase().contains("Angora cats may have differently colored eyes".toLowerCase()))
			grade += 2;
		else
			Autograder.Log("Check Angora description");

		System.out.println();
		Autograder.printLog();
		System.out.println("Grade: " + grade + "/100");
	}

}
