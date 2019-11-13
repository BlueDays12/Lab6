import java.math.BigDecimal;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.Math;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.*;
import java.math.BigInteger;
import java.lang.String;
import java.util.Random;

import static java.lang.StrictMath.sqrt;


public class Lab6 {
    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
    static String ResultsFolderPath = "/home/matt/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;
    static int numberOfTrials = 1;
    static int count = 1;

    public static void main(String[] args) {


        System.out.println("X     N     Result         Time");

        program("fib.txt");
    }


    static void program(String resultsFileName) {
        long N = 0;
        //long result = 0;
        double result = 0;
        double phi = (1+Math.sqrt(5))/2;
        double neg = 0;
        //BigDecimal result = new BigDecimal(0);


        // To open a file to write to
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch(Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);
            return;
        }

        ThreadCpuStopWatch stopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        resultsWriter.println("#X     N     Result         AvgTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        for (int i = 0; i < 10; ++i) {
            long elapsedTime = 0;
            System.gc();
            stopwatch.start(); // Start timer in nano secs

            for (int trial = 0; trial < numberOfTrials; ++trial) {
                //stopwatch.start(); // Start timer in nano secs
                neg = Math.pow((1/phi), i);
                if (i == 1)
                    result = 1;
                else
                    //result = fibLoopBig(i);
                    //System.out.println(i + " fibLoopBig: " + result);
                    //result = fibMatrixBig(i);
                    //System.out.println(i + " fibMatrixBig: " + result);
                    result = fibFormula(i, phi);
                    System.out.println(i + " fibFormula: " + result);
                    result = fibFormulaBig(i, phi, neg);
                    ++count;

                if (result <= 1) {
                    N = 1;
                }
                else
                    N = (int) (Math.log(result) / Math.log(2) + 1);

                // Output for testing
                //elapsedTime = stopwatch.elapsedTime(); // Get the elapsed time from ThreadCpuStopWatch class
                //System.out.format("%-5d %-5d %-5d %15d \n", i, N, result, elapsedTime);
            }
            elapsedTime = stopwatch.elapsedTime();
            double averageTimePerTrialInBatch = (double) elapsedTime / (double)numberOfTrials;
            resultsWriter.printf("%-5d %-5d %-5f %20f \n", i, N, result, averageTimePerTrialInBatch);
            resultsWriter.flush();
        }
    }

    public static double fibFormula (int x, double phi) {

        return (Math.round((Math.pow(phi, x) - Math.pow(-phi, -x))/Math.sqrt(5)));
    }


    public static double fibFormulaBig (int x, double phi, double neg) {
        BigDecimal myPhi = new BigDecimal(phi);
        BigDecimal negative = new BigDecimal(neg);
        BigDecimal five = new BigDecimal(sqrt(5));


        if (x == 1) {
            System.out.println(x + " BigDecimal: " + x);
            return 1;
        }
        BigDecimal first = myPhi.pow(x);
        BigDecimal second = negative;

        BigDecimal subtract = first.subtract(second);
        BigDecimal result = subtract.divide(five, 10, RoundingMode.CEILING);

        System.out.println("BigDecimal: " + result);

        return count;
    }

    public static double fibLoopBig (int x) {
        // Declare variables
        BigInteger sum = new BigInteger("000000000");
        BigInteger next = new BigInteger("000000001");
        BigInteger first = new BigInteger("000000000");
        double value = 0;

        // Calculate the fib for the argument
        for (int i = 1; i < x; ++i) {
            BigInteger A = new BigInteger(String.valueOf(first));
            BigInteger B = new BigInteger(String.valueOf(next));

            // BigInteger add class
            sum = A.add(B);

            // Substitute the variables that need to be added next
            first = next;
            next = sum;
        }
        value = Double.parseDouble(sum.toString());
        //value = Long.parseLong(sum.toString());
        return value;
        //result = Arrays.toString(sum);
    }

    public static double fibMatrixBig (int x) {
        BigInteger value = new BigInteger(String.valueOf(0));
        BigInteger zero = new BigInteger(String.valueOf(0));
        BigInteger one = new BigInteger(String.valueOf(1));
        BigInteger sumA = new BigInteger(String.valueOf(0));
        BigInteger sumB = new BigInteger(String.valueOf(0));
        BigInteger sumC = new BigInteger(String.valueOf(0));
        BigInteger sumD = new BigInteger(String.valueOf(0));
        BigInteger myArray[][] = new BigInteger[][]{{one,one},{one,zero}};
        BigInteger myArray2[][] = new BigInteger[][]{{one,one},{one,zero}};

        int i;
        double result;

        if (x == 0)
            return 0;

        for (i = 2; i <= x-1; ++i) {
            sumA = myArray[0][0].multiply(myArray2[0][0]);
            sumA = sumA.add(myArray[0][1].multiply(myArray2[1][0]));
            sumB = myArray[0][0].multiply(myArray2[0][1]);
            sumB = sumB.add(myArray[0][1].multiply(myArray2[1][1]));
            sumC = myArray[1][0].multiply(myArray2[0][0]);
            sumC = sumC.add(myArray[1][1].multiply(myArray2[1][0]));
            sumD = myArray[1][0].multiply(myArray2[0][1]);
            sumD = sumD.add(myArray[1][1].multiply(myArray2[1][1]));
            myArray[0][0] = sumA;
            myArray[0][1] = sumB;
            myArray[1][0] = sumC;
            myArray[1][1] = sumD;
        }

        value = myArray[0][0];
        result = Double.parseDouble(value.toString());
        return result;
    }
}


