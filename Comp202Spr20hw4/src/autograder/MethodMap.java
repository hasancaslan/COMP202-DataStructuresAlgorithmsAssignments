package autograder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import autograder.Autograder.GenerateInputParameter;

public class MethodMap {
  public Map<String, agMethod> metMap; //making it public instead of spending the time to wrap it
  public List<Method> methodList;
  
  public Map<String, Autograder.GenerateInputParameter> paramGenMap;
  
  public MethodMap(String[] methodNames, List<Class<?>[]> methodParams, Class<?>[] methodReturnRypes, Integer[] methodAccessMods) throws Exception {
    commonInit();
    if(methodNames.length != methodParams.size())
      throw new Exception("Method names and parameter list must have the same size");
    for(int i = 0; i < methodNames.length; i++) {
      metMap.put(methodNames[i], new agMethod(methodNames[i], methodParams.get(i), methodReturnRypes[i], methodAccessMods[i]));
    }
  }
  
  public MethodMap(Class<?> classType, boolean getAll) {
    commonInit();
    if(getAll)
      methodList = Autograder.getAllMethods(methodList, classType);
    else 
      methodList = Arrays.asList(classType.getMethods()); //or declared methods?
    methodListToMap();
  }
  
  public MethodMap(Class<?> classType) {
    this(classType, false);
  }
  
  private void commonInit() {
    methodList = new ArrayList<Method>();
    metMap = new HashMap<String, agMethod>();
    paramGenMap = new HashMap<String, Autograder.GenerateInputParameter>();
    
  }
  
  private void methodListToMap() {
    for(Method m : methodList) {
      metMap.put(m.getName(), new agMethod(m));
    }
  }
  
  //Need to change it if we have overloaded methods!
  public Object[] getParams(String methodName) {
    GenerateInputParameter tmp = paramGenMap.get(methodName);
    if(tmp == null)
      return new Object[0];
    else
      return tmp.generate();
    
  }

}
