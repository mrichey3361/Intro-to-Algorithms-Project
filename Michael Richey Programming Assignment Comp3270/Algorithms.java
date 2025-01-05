import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.lang.System;
import java.util.ArrayList;
import java.io.PrintWriter;
/*
*@author Michael Richey
*@version 07/21/2023
*/
public class Algorithms {
/*
*main function for class.
*reads array given by phw_input.txt, runs array through each
*algorithm, and prints the final results.
*the results are also output onto a file creating a graph.
*/
   public static void main(String[] args) throws IOException {
//reads input file
      File inputfile= new File("phw_input.txt");
      Scanner scan = new Scanner(inputfile);
      int[] inputarray = new int[10];
      String line = scan.nextLine();
      String[] separatednumber = line.split(",");
      for (int i = 0; i < inputarray.length; i++) {
         inputarray[i] = Integer.parseInt(separatednumber[i]);
      }
/*
*runs the input array in each algorithm to determine the mscs of 
*said array and prints results.
*/
      Algorithms spellingishard = new Algorithms();
      System.out.println("algorithm1: " + spellingishard.algorithm1(inputarray));
      System.out.println("algorithm2: " + spellingishard.algorithm2(inputarray));
      System.out.println("algorithm3: " + spellingishard.maxSum(inputarray, 0, 9));
      System.out.println("algorithm4: " + spellingishard.algorithm4(inputarray));
/*
*creates 19 arrays with various lengths ranging from 10 to 100.
*/
      int[][] array = new int[19][];
      int arraynumber = 0;
      for (int i = 10; i <= 100; i += 5) {
         int[] newarray = new int[i];
         /* Generation of random numbers between -300 and 300 */
         for (int j = 0; j < i; j++) {
            int random = new Random().nextInt(300 + 1000) - 300;
            newarray[j] = random;
         }
         array[arraynumber] = newarray;
         arraynumber++;
      } 
/*
*creates matrix that holds avg completion times and t(n) values
*https://www.geeksforgeeks.org/java-system-nanotime-vs-system-currenttimemillis/
*/
         long[][] matrix = new long[19][8];
//runs algorithms
         for (int i = 0; i < 19; i++) {
         long t1 = System.nanoTime();
         long result = 0;
         for (int j = 0; j < 6000; j++) {
            spellingishard.algorithm1(array[i]);
            long t2 = System.nanoTime();
            long elapsed = t2 - t1;
            result += elapsed;
         }         
         long average = result / 6000;
         matrix[i][0] = average;
      }
      for (int i = 0; i < 19; i++) {
         long t1 = System.nanoTime();
         long result = 0;
         for (int j = 0; j < 6000; j++) {
            spellingishard.algorithm2(array[i]);
            long t2 = System.nanoTime();
            long elapsed = t2 - t1;
            result += elapsed;
         }         
         long average = result / 6000;
         matrix[i][1] = average;
      } 
      for (int i = 0; i < 19; i++) {
         long t1 = System.nanoTime();
         long result = 0;
         for (int j = 0; j < 6000; j++) {
            spellingishard.maxSum(array[i], 0, array[i].length - 1);
            long t2 = System.nanoTime();
            long elapsed = t2 - t1;
            result += elapsed;
         }        
         long average = result / 6000;
         matrix[i][2] = average;
      }
      for (int i = 0; i < 19; i++) {
         long t1 = System.nanoTime();
         long result = 0;
         for (int j = 0; j < 6000; j++) {
            spellingishard.algorithm4(array[i]);
            long t2 = System.nanoTime();
            long elapsed = t2 - t1;
            result += elapsed;
         }
         long average = result / 6000;
         matrix[i][3] = average;
      }
/*
*calculates complexity.
*https://www.geeksforgeeks.org/java-ceil-method-examples/
*/
         int index = 0;
         for (int i = 10; i<= 100; i += 5) {
            matrix[index][4] = (long) Math.ceil(Math.pow(i, 3) * 1000000); 
            matrix[index][5] = (long) Math.ceil(Math.pow(i, 2) * 1000000);
            matrix[index][6] = (long) Math.ceil(i * (Math.log(i) / Math.log(2)) * 1000000);
            matrix[index][7] = (long) Math.ceil(i * 1000000);
            index++;
         }
         String[] output = new String[19];
         for (int i = 0; i < 19; i++) {
         String lineout = "";
         for (int j = 0; j <= 6; j++) {
            lineout = lineout + matrix[i][j] + ",";
         }
         lineout = lineout + matrix[i][7];
         output[i] = lineout;
      }
/*
* creates and writes file.
*https://www.geeksforgeeks.org/java-io-printwriter-class-java-set-1/
*/
      try {
         PrintWriter outputfile = new PrintWriter("michaelrichey_phw_output.txt");
         outputfile.println("algorithm1,algorithm2,algorithm3,algorithm4,T1(n),T2(n),T3(n),T4(n)");
         for (String myfingershurt : output) {
            outputfile.println(myfingershurt);
         }
         outputfile.close();
      }
      catch (IOException mercymilord) {
         System.out.println("Error writing to file. *visible anger*");
      }
   }
//algorithm1 O(n^3).
   public int algorithm1(int[] X) {
      int maxSoFar = 0;
      int P = 0;
      int Q = X.length;
      for (int L = P; L <= Q; L++) {
         for (int U = L; U <= Q; U++) {
            int sum = 0;
            for (int I = L; I < U; I++) {
               sum = sum + X[I];
               /* sum now contains the sum of X[L..U] */
            }
            maxSoFar = max(maxSoFar, sum);
         } 
      } 
      return maxSoFar;
   }
//algorithm2 O(n^2)
   public int algorithm2(int[] X) {
      int maxSoFar = 0;
      int P = 0;
      int Q = X.length;
      for (int L = P; L <= Q; L++) {
         int sum = 0;
         for (int U = L; U < Q; U++) {
            sum = sum + X[U];
            /* sum now contains the sum of X[L..U] */
            maxSoFar = max(maxSoFar, sum);
         }
      }
      return maxSoFar;
   }
//maxsum O(nlogn)
   public int maxSum(int[] X, int L, int U) {
      if (L > U) {
         return 0; /* zero-element vector */
      }
      if (L == U) {
         return max(0, X[L]); /* one-element vector */
      }
      
      int M = (L + U) / 2; /* A is X[L..M], B is X[M+1..U] */
      /* Find max crossing to left */
      int sum = 0;
      int maxToLeft = 0;
      for (int I = M; I >= L; I--) {
         sum = sum + X[I];
         maxToLeft = max(maxToLeft, sum);
      }
      /* Find max crossing to right */
      sum = 0;
      int maxToRight = 0;
      for (int I = M + 1; I <= U; I++) {
         sum = sum + X[I];
         maxToRight = max(maxToRight, sum);
      }
      int maxCrossing = maxToLeft + maxToRight; 
      
      int maxInA = maxSum(X, L, M);
      int maxInB = maxSum(X, M + 1, U);
      return max(maxCrossing, maxInA, maxInB);
   }
//algorithm4 O(n)
public int algorithm4(int[] X) {
      int P = 0;
      int Q = X.length;
      int maxSoFar = 0;
      int maxEndingHere = 0;
      for (int I = P; I < Q; I++) {
         maxEndingHere = max(0, maxEndingHere + X[I]);
         maxSoFar = max(maxSoFar, maxEndingHere);
      }
      return maxSoFar; 
   }
//added functions
/*
*return max of 2 integers with worst case cost:4
*/
public int max(int x, int y) {
      if (x >= y) {
         return x;
      }
      else {
         return y;
      }
   }
/*
* return max of 3 integers with worst case cost:13
*/
   public int max(int x, int y, int z) {
      if (x >= y && x >= z) {
         return x;
      }
      else if (y >= x && y >= z) {
         return y;
      }
      else {
         return z;
      }
   }                   
   
}