package autograder;

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
 * AUTHORS: Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

import java.lang.reflect.Field;

public class agField {
	String name;
	Class<?> type;
	Object value;
	
	public agField(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}
	
	public agField(Field f) {
		this(f.getName(), f.getType());
	}
	
	public boolean isPrimitive() {
		return type.isPrimitive();
	}
	
	public String getName()	{
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Field) {
            Field other = (Field)obj;
            return (name == other.getName())
                && (type == other.getType());
        }
        else if (obj != null && obj instanceof agField){
        	agField other = (agField)obj;
            return (name == other.getName())
                && (type == other.getType());
        }
        return false;
    }

}
