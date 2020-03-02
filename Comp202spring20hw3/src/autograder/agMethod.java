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

import java.lang.reflect.Method;

public class agMethod {
	  String name;
      Class<?>[] parameterTypes;
      Class<?> returnType;
      int modifiers;
      
      //Not used for now.
      Class<?>[] checkedExceptions;
      
      public agMethod(String name,
    		  		  Class<?>[] parameterTypes,
    		  		  Class<?> returnType,
    		  		  int modifiers) {
    	  this.name = name;
    	  this.parameterTypes = parameterTypes;
  		  this.returnType = returnType;
  		  this.modifiers = modifiers;
      }
      
      public agMethod(String name,
	  		  Class<?> returnType,
	  		  int modifiers) {
          this(name, new Class<?>[0], returnType, modifiers);
      }
      
      public agMethod(Method m) {
    	  this(m.getName(), m.getParameterTypes(), m.getReturnType(), m.getModifiers());
      }
      
      public String getName()
      {
    	 return name; 
      }
      
      public Class<?> getReturnType() {
          return returnType;
      }
      
      public Class<?>[] getParameterTypes() {
          return parameterTypes.clone();
      }

      
      public boolean equals(Object obj) {
          if (obj != null && obj instanceof Method)  {
              Method other = (Method)obj;
              if ( (getName() == other.getName())) {
                  if (!returnType.equals(other.getReturnType()))
                      return false;
                  return Autograder.equalParamTypes(parameterTypes, other.getParameterTypes());
              }
          } 
          else if (obj != null && obj instanceof agMethod) {
        	  agMethod other = (agMethod)obj;
              if ( (getName() == other.getName())) {
                  if (!returnType.equals(other.getReturnType()))
                      return false;
                  return Autograder.equalParamTypes(parameterTypes, other.getParameterTypes());
              }
          }
		  return false;
      }

      //later on change this so that it saves and loads methods for easier autograding
}
