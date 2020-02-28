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

import java.lang.reflect.*;
import java.util.*;

import given.Util.myVector;

//Very badly written needs to overhauling
public class Autograder {
  
  public static interface GenerateInputParameter {
    Object [] generate();
  }
  
  public static void init() {
    ClearLog();
    initP2W();
    initW2P();
  }
  
  static StringBuilder gradingLog;
  static float grade;
  public static ConsoleOutputCapturer coc = new ConsoleOutputCapturer();
  
  public static void addGrade(float update) {
    grade += update;
  }
  
  public static Map<Class<?>,Class<?>> PrimitiveToWrapper = new HashMap<Class<?>,Class<?>>();
  private static void initP2W() {
    PrimitiveToWrapper.put(boolean.class, Boolean.class);
    PrimitiveToWrapper.put(byte.class, Byte.class);
    PrimitiveToWrapper.put(char.class, Character.class);
    PrimitiveToWrapper.put(double.class, Double.class);
    PrimitiveToWrapper.put(float.class, Float.class);
    PrimitiveToWrapper.put(int.class, Integer.class);
    PrimitiveToWrapper.put(long.class, Long.class);
    PrimitiveToWrapper.put(short.class, Short.class);
    PrimitiveToWrapper.put(void.class, Void.class);
  }
  
  public static Map<Class<?>,Class<?>> WrapperToPrimitive = new HashMap<Class<?>,Class<?>>();
  private static void initW2P() {
    WrapperToPrimitive.put(Boolean.class, boolean.class);
    WrapperToPrimitive.put(Byte.class, byte.class);
    WrapperToPrimitive.put(Character.class, char.class);
    WrapperToPrimitive.put(Double.class, double.class);
    WrapperToPrimitive.put(Float.class, float.class);
    WrapperToPrimitive.put(Integer.class, int.class);
    WrapperToPrimitive.put(Long.class, long.class);
    WrapperToPrimitive.put(Short.class, short.class);
    WrapperToPrimitive.put(Void.class, void.class);
  }
  
  public static void ClearLog()
  {
    gradingLog = new StringBuilder(1000);
  }
  
  public static void Log(String str)
  {
    gradingLog.append(str);
    gradingLog.append(System.lineSeparator());
  }
  
  public static void printLog()
  {
    if(gradingLog.length() == 0)
      System.out.println("Grading log is clear");
    else
      System.out.println(gradingLog.toString());
  }
  
  public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
      fields.addAll(Arrays.asList(type.getDeclaredFields()));

      if (type.getSuperclass() != null) {
          getAllFields(fields, type.getSuperclass());
      }

      return fields;
  }
  
  public static List<Method> getAllMethods(List<Method> methods, Class<?> type) {
    methods.addAll(Arrays.asList(type.getDeclaredMethods()));

      if (type.getSuperclass() != null) {
        getAllMethods(methods, type.getSuperclass());
      }

      return methods;
  }
  
  private static List<Class<?>> getAllInterfaces(List<Class<?>> interfaces, Class<?> type) {
    interfaces.addAll(Arrays.asList(type.getInterfaces()));

      if (type.getSuperclass() != null) {
        getAllInterfaces(interfaces, type.getSuperclass());
      }

      return interfaces;
  }
  
  private static List<Class<?>> getAllAncestors(List<Class<?>> ancestors, Class<?> type) {
    Class<?> superClazz = type.getSuperclass();

      if (superClazz != null) {
        ancestors.add(superClazz);
        getAllAncestors(ancestors, superClazz);
      }

      return ancestors;
  }
  
  public static boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        /* Avoid unnecessary cloning */
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }
  
  public static boolean compareMethods(Method m1, Method m2)
  {
        if ( (m1.getName() == m2.getName())) {
            if (!m1.getReturnType().equals(m2.getReturnType()))
                return false;
            return equalParamTypes(m1.getParameterTypes(), m2.getParameterTypes());
        }
        return false;
  }
  
  public static boolean compareFields(Field f1, Field f2)
  {
        return (f1.getName() == f2.getName()) && (f1.getType() == f2.getType());
  }
  
  public static boolean compareConstructors(Constructor<?> c1, Constructor<?> c2) {
        //Default equals should work since classes should also match
    return c1.equals(c2);
  }
  
  //Does not check inherited interfaces!!! To do get we need to get all the interface which we know how
  public static boolean testIfImplemented(Class<?> clazz, Class<?> interfaze)
  {
    return testIfImplemented(clazz, interfaze, false);
  }
  
  public static boolean testIfImplemented(Class<?> clazz, String interfazeName) throws ClassNotFoundException
  {
    return testIfImplemented(clazz, interfazeName, false);
  }
  
  public static boolean testIfImplemented(Class<?> clazz, String interfazeName, boolean allInterfaces) throws ClassNotFoundException
  {
    Class<?> interfaze = Class.forName(interfazeName);
    return testIfImplemented(clazz, interfaze, allInterfaces);
  }
  
  public static boolean testIfImplemented(Class<?> clazz, Class<?> interfaze, boolean allInterfaces)
  {
    if(!interfaze.isInterface())
      return false;
    
    List<Class<?>> interfazeList; 
    
    if(allInterfaces) {
      interfazeList = new ArrayList<Class<?>>();
      getAllInterfaces(interfazeList, clazz);
    }
    else
      interfazeList =  Arrays.asList(clazz.getInterfaces());
    
    return interfazeList.contains(interfaze);
  }

  public static int numMethodMatches(Map<String,agMethod> methodList, Class<?> clazz)
  {
    int hits = 0;
    for(String key : methodList.keySet())
    {
      try {
        Method m = clazz.getDeclaredMethod(key, methodList.get(key).getParameterTypes());
        System.out.println(key + " " + m.getModifiers());
        if(methodList.get(key).equals(m))
          hits++;
        else
          Log("Method " + key + " does not match in class " + clazz.getName());
      } catch (NoSuchMethodException e) {
        Log("Method " + key + " is not available in class " + clazz.getName());
        //Log things in the future :)
      }
    }
    return hits;
  }
    
  public static int numConstructorMatches(List<Class<?>[] > constructorParatemeterTypes, Class<?> clazz)
  {
    int hits = 0;
    for(Class<?>[] cp : constructorParatemeterTypes)
    {
      try {
        clazz.getConstructor(cp);
        hits++;
      } catch (NoSuchMethodException e) {
        Log("No constructor match in class " + clazz.getName());
      }
    }
    return hits;
  }
  
  //Update to be able to use all the methods
  public static int numFieldMatches(Map<String, agField> fieldList, Class<?> clazz)
  {
    int hits = 0;
    for(String key : fieldList.keySet())
    {
      try {
        Field f = clazz.getField(key);
        if(fieldList.get(key).equals(f))
          hits++;
        else
          Log("Field " + key + " does not match in class " + clazz.getName());
      } catch (NoSuchFieldException e) {
        Log("Field " + key + " is not available in class " + clazz.getName());
      }
    }
    return hits;
  }
  public static int numFieldAndValueMatches(Object obj, Map<String, agField> fieldList, Map<String, Object> valueList) throws IllegalArgumentException, IllegalAccessException
  {
    return numFieldAndValueMatches(obj, fieldList, valueList, false);
  }
  
  public static int declaredFieldMatches(String[] fieldNames, Class<?> clazz) {
    int hits = 0;
    Field[] f = clazz.getDeclaredFields();
    for (int j = 0; j < f.length; j++) {
      for (String fieldName : fieldNames) {
        if (f[j].getName().equals(fieldName)) {
          Log(fieldName + " declared again in Class " + clazz.getName());
          hits++;
        }
      }
    }
    return hits;
  }
  
  public static int numFieldAndValueMatches(Object obj, Map<String, agField> fieldList, Map<String, Object> valueList, boolean allFields) throws IllegalArgumentException, IllegalAccessException
  {
    //not checking a lot of potential errors!
    int hits = 0;
    Field[] clazzFields;
    if(allFields) {
      ArrayList<Field> f = new ArrayList<Field>();
      getAllFields(f, obj.getClass());
      clazzFields = f.toArray(new Field[f.size()]);
    }
    else
      clazzFields = obj.getClass().getDeclaredFields();
    
    for(String key : valueList.keySet())
    {
      agField f1 = fieldList.get(key);
      for(Field f : clazzFields)
      {
        if(f1.equals(f)) {
          f.setAccessible(true);
          if( valueList.get(key).equals(f.get(obj)) )
            hits++;
          else
            Log("Field " + key + " matches but not the values. Current: " + f.get(obj) + " Desired:" + valueList.get(key));
        }
      }
    }
    return hits;
  }
  
  public static int testDS(Object tested, Object reference, MethodMap mm, String[] operationsArray, myVector operationsProb, int numRand) 
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {  
    int op;
    
    Method methodT;
    Method methodR;    
   
    int missMatchedOutputs = 0;
    
    for(int i = 0; i < numRand; i++) {
      
      op = operationsProb.sampleIndex();
      agMethod curMet = mm.metMap.get(operationsArray[op]);
      methodT = tested.getClass().getMethod(operationsArray[op], curMet.getParameterTypes());
      methodR = reference.getClass().getMethod(operationsArray[op], curMet.getParameterTypes());
      
      Object[] a = mm.getParams(operationsArray[op]);
      Object t = methodT.invoke(tested, a);
      Object r = methodR.invoke(reference, a);
      if(t != null && r != null)
      {

        if(!methodT.getReturnType().isPrimitive() || !methodR.getReturnType().isPrimitive()) {
          if(!methodT.getReturnType().cast(t).equals(methodT.getReturnType().cast(r))) {
            Log("Method return mismath: " + methodT);
            missMatchedOutputs++;
          }
        } 
        else {
          if(!Autograder.PrimitiveToWrapper.get(methodT.getReturnType()).cast(t).equals(Autograder.PrimitiveToWrapper.get(methodR.getReturnType()).cast(r))) {
            Log("Method return mismath: " + methodT);
            missMatchedOutputs++;
          }
        }
      }
    }
    return missMatchedOutputs;
  }
  
}
