package given;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import autograder.Autograder;
import code.CountingSort;
import code.HeapSort;
import code.InsertionSort;
import code.MergeSort;
import code.QuickSort;

public class SortGrade<K extends Comparable<K>> {

  public static void report(String sortCase, AbstractArraySort<Integer> alg, Integer[] intArray) throws IOException{
    int n = intArray.length;
    BufferedWriter w = new BufferedWriter(new FileWriter(alg+sortCase+n+".txt"));
    w.write(alg.getSwaps()+"\n");
    w.write(alg.getCompares()+"\n");
    for(int i = 0; i < n; i++){
      w.write(intArray[i]+"\n");
    }
    w.flush();
    w.close();
  }
  
  //Instead of printing we should just compare them 
  public static void report(String sortCase, AbstractArraySort<Integer> alg) throws IOException{
    BufferedWriter w = new BufferedWriter(new FileWriter(alg+"_"+sortCase+".txt"));
    w.write(alg.getSwaps()+"\n");
    w.write(alg.getCompares()+"\n");
    w.flush();
    w.close();
  }
  
  public boolean isSorted(K[] array) {
    return isSorted(array, 0, array.length - 1);
  }

  public boolean isSorted(K[] array, int lo, int hi) {
    for (int i = lo; i < hi; i++) {
      if (array[i].compareTo(array[i + 1]) > 0) {
        //System.out.println(array[i] + " " + array[i + 1] + " " + Integer.toString(i));
        return false;
      }
    }
    return true;
  }

  public boolean isHeap(K[] inputArray) {
    return _isHeap(inputArray, 0, inputArray.length);
  }

  private boolean _isHeap(K[] inputArray, int i, int size) {
    int lc = 2 * i + 1;
    int rc = 2 * i + 2;

    boolean lcExists = lc < size;
    boolean rcExists = rc < size;

    if (!lcExists && !rcExists)
      return true;

    boolean retVal = true;

    if (lcExists)
      retVal = retVal && (inputArray[i].compareTo(inputArray[lc]) >= 0) && _isHeap(inputArray, 2 * i + 1, size);
    if (rcExists)
      retVal = retVal && (inputArray[i].compareTo(inputArray[rc]) >= 0) && _isHeap(inputArray, 2 * i + 2, size);
    return retVal;
  }

  public boolean isPartitioned(K[] array, int p) {
    return isPartitioned(array, 0, array.length - 1, p);
  }

  public boolean isPartitioned(K[] array, int lo, int hi, int p) {
    for (int i = lo; i < p; i++) {
      if (array[i].compareTo(array[p]) > 0) {
        //System.out.println(array[i] + " " + array[p] + " " + Integer.toString(i) + " " + Integer.toString(p));
        //AbstractArraySort.printArray(array);
        return false;
      }
    }
    for (int i = p; i < hi; i++) {
      if (array[i].compareTo(array[p]) < 0) {
        //System.out.println(array[i] + " " + array[p] + " " + Integer.toString(i)+ " " + Integer.toString(p));
        //AbstractArraySort.printArray(array);
        return false;
      }
    }
    return true;
  }

  //public static HashMap<String, ArrayList<String>> multiTestCompSwap = new HashMap<String, ArrayList<String>>();
  
  
  public static boolean comparesAndSwaps(AbstractArraySort<Integer> alg, String type)
      throws NumberFormatException, IOException {
    
    int numTries=1;
    
    switch (alg.name) {
    case "Mergesort":
      numTries = 2;
      break;
    case "Heapsort":
    case "Quicksort":
    case "Insertionsort":
      break;
    }
    boolean res = false;
    for(int i = 0; i < numTries; i ++) {
      String filename;
      if (i == 0)
        filename = "compare/" + alg.toString() + "_" + type + ".txt";
      else
        filename = "compare/" + alg.toString() + "_" + type + i + ".txt";
      
      BufferedReader fileReader = new BufferedReader(new FileReader(filename));
      int swaps = Integer.parseInt(fileReader.readLine());
      int compares = Integer.parseInt(fileReader.readLine());
      fileReader.close();
      res = swaps == alg.getSwaps() && compares == alg.getCompares();
      if(res)
        break;
    }
    return res;
  }

  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    Autograder.init();

    SortGrade<Integer> integerGrader = new SortGrade<Integer>();
    SortGrade<Double> doubleGrader = new SortGrade<Double>();
    SortGrade<String> stringGrader = new SortGrade<String>();

    String[] algsToGrade = { "isort", "qsort", "msort", "hsort", "csort" };
    int dataLengths[] = { 50, 256, 1000, 10000 };

    // Type of data to test, look at the pdf for descriptions
    String runTypes[] = { "uniform", "randomizedDuplicates",  "staggered", "sortedChunks", "constant", "sorted", "ladder", "sortedReverse" };

    QuickSort<Integer> qsort = new QuickSort<Integer>();
    MergeSort<Integer> msort = new MergeSort<Integer>();
    HeapSort<Integer> hsort = new HeapSort<Integer>();
    
    InsertionSort<Integer> isort = new InsertionSort<Integer>();
    CountingSort<Integer> csort = new CountingSort<Integer>();

    int numData = 256;
    Integer[] data = new Integer[numData];

    // In case you are initializing anything in the sort functions
    Integer[] dataTmp = DataGenerator.generateIntegers(numData, "uniform");
    Integer[] dataTmp2 = new Integer[numData];
    
    System.arraycopy(dataTmp, 0, dataTmp2, 0, numData);
    qsort.sort(dataTmp2);
    boolean qsorted = integerGrader.isSorted(dataTmp2);
    
    System.arraycopy(dataTmp, 0, dataTmp2, 0, numData);
    msort.sort(dataTmp2);
    boolean msorted = integerGrader.isSorted(dataTmp2);
    
    System.arraycopy(dataTmp, 0, dataTmp2, 0, numData);
    hsort.sort(dataTmp2);
    boolean hsorted = integerGrader.isSorted(dataTmp2);
    
    System.arraycopy(dataTmp, 0, dataTmp2, 0, numData);
    isort.sort(dataTmp2);
    boolean isorted = integerGrader.isSorted(dataTmp2);
    
    System.arraycopy(dataTmp, 0, dataTmp2, 0, numData);
    csort.sort(dataTmp2);
    boolean csorted = integerGrader.isSorted(dataTmp2);

    // Testing quicksort partition
    boolean allPartitioned = true;
    for (String type : runTypes) {
      try {
        data = DataGenerator.generateIntegers(numData, type);
        Method partitionMethod = qsort.getClass().getDeclaredMethod("partition", java.lang.Comparable[].class,
            int.class, int.class, int.class);

        int p;
        Object obj = qsort.partition(data, 0, data.length - 1, data.length / 2);
        if (int.class.equals(partitionMethod.getReturnType())
            || Integer.class.equals(partitionMethod.getReturnType())) {
          p = (Integer) obj;
        } else {
          @SuppressWarnings("unchecked")
          QuickSort<Integer>.indexPair tmp = (QuickSort<Integer>.indexPair) obj;
          p = tmp.p1;
        }

        boolean partitioned = integerGrader.isPartitioned(data, p);
        if(qsorted && !partitioned)
          partitioned = integerGrader.isPartitioned(data, p+1);
        allPartitioned = allPartitioned && partitioned;
        if (partitioned) {
          Autograder.addGrade(1);
        }
        else
          Autograder.Log("Array not partitioned with quicksort partition. Data Type: " + type);
      } catch (Exception e) {
        Autograder.Log("Exception caught while trying to partition. Data Type: " + type);
        allPartitioned = false;
      }
    }
    if (allPartitioned)
      Autograder.addGrade(2);

    // Testing mergesort merge, 10 points
    boolean allMerged = true;
    for (String type : runTypes) {
      try {
        data = DataGenerator.generateIntegers(numData, type);

        Integer[] data1 = new Integer[numData / 2];
        Integer[] data2 = new Integer[data.length - data1.length];
        System.arraycopy(data, 0, data1, 0, data1.length);
        System.arraycopy(data, data1.length, data2, 0, data2.length);
        Arrays.sort(data1);
        Arrays.sort(data2);
        System.arraycopy(data1, 0, data, 0, data1.length);
        System.arraycopy(data2, 0, data, data1.length, data2.length);
        msort.merge(data, 0, data1.length - 1, data.length - 1);

        boolean merged = integerGrader.isSorted(data);
        allMerged = allMerged && merged;
        if (merged) {
          if(type.equals("constant") || type.equals("sorted") || type.equals("ladder")) {
            if(msorted)
              Autograder.addGrade(1);
            else
              Autograder.Log("Cannot reliable decide on correct merging with data " + type + " if sorting fails");
          }
          else
            Autograder.addGrade(1);
        }
        else
          Autograder.Log("Array not merged with mergesort merge. Data Type: " + type);
      } catch (Exception e) {
        Autograder.Log("Exception caught while trying to merge. Data Type: " + type);
        allMerged = false;
      }
    }
    if (allMerged)
      Autograder.addGrade(2); 

    // Testing heapsort heapify, 10 points
    boolean allHeapified = true;
    for (String type : runTypes) {
      try {
        data = DataGenerator.generateIntegers(numData, type);

        hsort.heapify(data);

        boolean heapified = integerGrader.isHeap(data);
        allHeapified = allHeapified && heapified;
        if (heapified) {
          if(type.equals("constant") || type.equals("sortedReverse")) {
            if(hsorted)
              Autograder.addGrade(1);
            else
              Autograder.Log("Cannot reliable decide on correct heapification with data " + type + " if sorting fails");
          }
          else
            Autograder.addGrade(1);
        }
        else
          Autograder.Log("Array not heapified with heapsort heapify. Data Type: " + type);
      } catch (Exception e) {
        Autograder.Log("Exception caught while trying to heapify.  Data Type: " + type);
        allHeapified = false;
      }
    }
    if (allHeapified)
      Autograder.addGrade(2);

    // Sorting with integer, 32 + 12 points
    AbstractArraySort<Integer> algToGrade;

    Integer[] randIntegers;
    for (String alg : algsToGrade) {
      switch (alg) {
      case "isort":
        algToGrade = new InsertionSort<Integer>();
        break;
      case "qsort":
        algToGrade = new QuickSort<Integer>();
        break;
      case "msort":
        algToGrade = new MergeSort<Integer>();
        break;
      case "hsort":
        algToGrade = new HeapSort<Integer>();
        break;
      case "csort":
      default:
        algToGrade = new CountingSort<Integer>();
        break;
      }

      for (int n : dataLengths) {

        randIntegers = new Integer[n];
        for (String type : runTypes) {
          if (alg.equals("Insertion sort") && n > 10000) {
            System.out.println("Skipping insertion sort due to size of data!");
            continue;
          }
          if(type.equals("constant") || type.equals("ladder") || type.equals("sorted") ) {
            if ((alg.equals("isort") && !isorted) || 
                (alg.equals("qsort") && !qsorted) ||
                (alg.equals("msort") && !msorted) ||
                (alg.equals("hsort") && !hsorted) ||
                (alg.equals("csort") && !csorted)) {
              Autograder.Log("Cannot reliable decide on sortedness with data " + type + " if sorting fails");
              continue;
            }
          }
          randIntegers = DataGenerator.generateIntegers(n, type);

          algToGrade.initTest();
          try {
            algToGrade.sort(randIntegers);

            boolean sorted = integerGrader.isSorted(randIntegers);
            if (sorted) {
              Autograder.addGrade(0.2f); 
              if (n == dataLengths[dataLengths.length - 1] && !alg.equals("csort") && !alg.equals("qsort") ) {
                report(type,algToGrade);
                if (comparesAndSwaps(algToGrade, type)) {
                  Autograder.addGrade(0.5f);
                }
                else {
                  Autograder.Log("Number of compares and swaps do not match: " + alg + " Data Type: " + type);
                }
              }
            } else
              Autograder.Log("Array not sorted. Algorithm: " + alg + " Data Type: " + type
                  + " Data Length: " + n);

          } catch (Exception e) {
            e.printStackTrace();
            Autograder.Log("Exception Caught. Algorithm: " + alg + " Data Type: " + type
                + " Data Length: " + n);
          }
        }
      }
    }

    //12 points
    AbstractArraySort<Double> algToGradeD;

    Double[] randDoubles;
    for (String alg : algsToGrade) {
      switch (alg) {
      case "isort":
        algToGradeD = new InsertionSort<Double>();
        break;
      case "qsort":
        algToGradeD = new QuickSort<Double>();
        break;
      case "msort":
        algToGradeD = new MergeSort<Double>();
        break;
      case "hsort":
        algToGradeD = new HeapSort<Double>();
        break;
      default:
        continue;
      }

      for (int n : dataLengths) {

        randDoubles = new Double[n];
        algToGradeD.initTest();
        try {
          randDoubles = DataGenerator.randomDoubleRange(-10, 10, n);
          algToGradeD.sort(randDoubles);

          boolean sorted = doubleGrader.isSorted(randDoubles);
          if (sorted)
            Autograder.addGrade(0.75f);
          else
            Autograder.Log("Array not sorted. Algorithm: " + alg
                + " Data Type: uniform doubles Data Length: " + n);
        } catch (Exception e) {
          Autograder.Log("Exception Caught. Algorithm: " + alg
              + " Data Type: uniform doubles Data Length: " + n);
        }
      }
    }

    //12 points
    AbstractArraySort<String> algToGradeS;

    String[] randStrings;
    for (String alg : algsToGrade) {
      switch (alg) {
      case "isort":
        algToGradeS = new InsertionSort<String>();
        break;
      case "qsort":
        algToGradeS = new QuickSort<String>();
        break;
      case "msort":
        algToGradeS = new MergeSort<String>();
        break;
      case "hsort":
        algToGradeS = new HeapSort<String>();
        break;
      default:
        continue;
      }

      for (int n : dataLengths) {

        randStrings = new String[n];
        randStrings = DataGenerator.randomString(3, 12, n);
        algToGradeS.initTest();
        try {
          algToGradeS.sort(randStrings);

          boolean sorted = stringGrader.isSorted(randStrings);
          if (sorted)
            Autograder.addGrade(0.75f);
          else
            Autograder.Log("Array not sorted. Algorithm: " + alg
                + " Data Type: random string Data Length: " + n);
        } catch (Exception e) {
          Autograder.Log(
              "Exception Caught. Algorithm: " + alg + " Data Type: random string Data Length: " + n);
        }
      }
    }
    Autograder.addGrade(2);
    Autograder.printLog();
    Autograder.printGrade();
  }
}
