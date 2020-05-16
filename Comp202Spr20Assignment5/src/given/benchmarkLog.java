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
 * AUTHORS: Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that holds benchmarking data
 * 
 * @author baakgun
 *
 */
class benchmarkLog 
{
  class runInfo
  {
    int _runDataLength;
    String _type;
    String _algName;
    
    runInfo(String algorithm, String dataType, int runDataLength)
    {
      _runDataLength = runDataLength;
      _type = dataType;
      _algName = algorithm;
    }
    
    @Override
    public int hashCode()
    {
      return (Integer.toString(_runDataLength) + _type + _algName).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
     if (obj == null)
        return false;
     if (getClass() != obj.getClass())
       return false;
     runInfo tmp = (runInfo) obj;
     return (this._algName.equals(tmp._algName)) && (this._type.equals(tmp._type)) && (this._runDataLength == tmp._runDataLength);
    }
    
    @Override
    public String toString()
    {
      return "Algorithm: " + _algName + ", Data Type: " + _type + ", Data Length: " + Integer.toString(_runDataLength);
    }
  }
    
  HashMap<runInfo, ArrayList<Long>> runResults;

  // Could be <runInfo, runStats> when we add more statistics!
  HashMap<runInfo, Double> runStats;
  
  benchmarkLog()
  {
    runResults = new HashMap<runInfo, ArrayList<Long>>();
    runStats = new HashMap<runInfo, Double>();
  }
  
  void add(String algorithm, String type, int runDataLength, long result)
  {
    runInfo tmpRI = new runInfo(algorithm, type, runDataLength); 
    ArrayList<Long> tmp = runResults.get(tmpRI);

    if(tmp == null)
    {
      tmp = new  ArrayList<Long>();
      runResults.put(tmpRI, tmp);
    }
    tmp.add(result);
  }
  
  public void updateMean(String algorithm, String type, int runDataLength)
  {
    runInfo tmpRI = new runInfo(algorithm, type, runDataLength);
    ArrayList<Long> tmp = runResults.get(tmpRI);;
    if(tmp == null || tmp.isEmpty())
    {
      return;
    }
    double sum = 0;
    for (Long mark : tmp) {
        sum += mark;
    }
    
    double mean = sum / tmp.size();
    runStats.put(tmpRI, mean);
  }
  
  public double getMean(String algorithm, String type, int runDataLength)
  {

    runInfo tmpRI = new runInfo(algorithm, type, runDataLength);
    if(runStats.get(tmpRI) == null)
      return -1;
    else
      return runStats.get(tmpRI);
  }
  
  public ArrayList<Long> get(String algorithm, String type, int runDataLength)
  {
    runInfo tmpRI = new runInfo(algorithm, type, runDataLength);
    return runResults.get(tmpRI);
  }
  
  public benchmarkLog getByAlgorithm(String algorithm)
  {
    benchmarkLog ret = new benchmarkLog();
    for(runInfo key : runResults.keySet())
    {
      if(key._algName.equals(algorithm))
        for(Long res:runResults.get(key))
          ret.add(key._algName, key._type, key._runDataLength, res);
    }
    return ret;
  }
  
  public benchmarkLog getByRunLength(int runDataLength)
  {
    benchmarkLog ret = new benchmarkLog();
    for(runInfo key : runResults.keySet())
    {
      if(key._runDataLength == runDataLength)
        for(Long res:runResults.get(key))
          ret.add(key._algName, key._type, key._runDataLength, res);
    }
    return ret;
  }
  
  public benchmarkLog getByDataType(String type)
  {
    benchmarkLog ret = new benchmarkLog();
    for(runInfo key : runResults.keySet())
    {
      if(key._type.equals(type))
        for(Long res:runResults.get(key))
          ret.add(key._algName, key._type, key._runDataLength, res);
    }
    return ret;
  }
  
  @Override
  public String toString()
  {
    StringBuffer strBuff = new StringBuffer();
    for(runInfo ri : runResults.keySet())
    {
      strBuff.append(ri.toString());
      ArrayList<Long> arrRes = runResults.get(ri);
      for(Long res : arrRes)
      {
        strBuff.append(" ");
        strBuff.append(res.toString());
      }
      strBuff.append(System.lineSeparator());
    }
    return strBuff.toString();
  }
}

