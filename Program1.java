/*
Program1.java  –  OOAD Project 1  (Part 2 / Program 1)
Author: Andrew Wood
University of Colorado Boulder
2025/6/15

Generates arrays of [0,1) doubles using three different RNG APIs,
computes basic statistics, and prints the results in a formatted table.

Compile: javac Program1.java
Run: java Program1
*/

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

// Responsible for creating arrays of random doubles.
class RandomArrayCreator {

    // Random.nextDouble()
    double[] withRandom(int n) {
        Random rng = new Random();
        double[] a = new double[n];
        for (int i = 0; i < n; i++) a[i] = rng.nextDouble();
        return a;
    }

    // Math.random()
    double[] withMathRandom(int n) {
        double[] a = new double[n];
        for (int i = 0; i < n; i++) a[i] = Math.random();
        return a;
    }

    // ThreadLocalRandom.current().nextDouble()
    double[] withThreadLocal(int n) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double[] a = new double[n];
        for (int i = 0; i < n; i++) a[i] = tlr.nextDouble();
        return a;
    }
}

// Holds four simple statistics.
class Stats {
    final double mean, stdDev, min, max;
    Stats(double mean, double stdDev, double min, double max) {
        this.mean = mean;
        this.stdDev = stdDev;
        this.min = min;
        this.max = max;
    }
}

// Computes mean, standard deviation, min, and max of a double[].
class StatsAnalyzer {

    // Returns a Stats object for the given data.
    Stats analyze(double[] data) {
        int n = data.length;
        if (n == 0) throw new IllegalArgumentException("Empty array");

        double sum = 0, min = data[0], max = data[0];
        for (double v : data) {
            sum += v;
            if (v < min) min = v;
            if (v > max) max = v;
        }
        double mean = sum / n;

        double sqDiff = 0;
        for (double v : data) {
            double d = v - mean;
            sqDiff += d * d;
        }
        double stdDev = Math.sqrt(sqDiff / n);

        return new Stats(mean, stdDev, min, max);
    }
}

// Driver class – handles user interaction only.
public class Program1 {

    private static final String HEADER =
        "+--------------------+-----------------+-----------------+-------------+-------------+%n" +
        "| %-18s | %-15s | %-15s | %-11s | %-11s |%n" +
        "+--------------------+-----------------+-----------------+-------------+-------------+%n";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RandomArrayCreator creator = new RandomArrayCreator();
        StatsAnalyzer analyzer = new StatsAnalyzer();

        System.out.println("\n=== Random-Number Generator Comparison ===");
        System.out.println("(Press <Enter> on an empty line to quit)\n");

        while (true) {
            System.out.print("Enter number of random values to generate: ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;

            int n;
            try {
                n = Integer.parseInt(line);
                if (n <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a positive integer.\n");
                continue;
            }

            // Generate data
            double[] arrRandom   = creator.withRandom(n);
            double[] arrMath     = creator.withMathRandom(n);
            double[] arrThread   = creator.withThreadLocal(n);

            // Analyze
            Stats sRandom  = analyzer.analyze(arrRandom);
            Stats sMath    = analyzer.analyze(arrMath);
            Stats sThread  = analyzer.analyze(arrThread);

            // Print table
            System.out.printf(HEADER,
                "Generator", "Mean", "Std Deviation", "Min", "Max");
            printRow("java.util.Random",     sRandom);
            printRow("Math.random()",        sMath);
            printRow("ThreadLocalRandom",    sThread);
            System.out.println();
        }
        sc.close();
        System.out.println("Good-bye!");
    }

    private static void printRow(String name, Stats s) {
        System.out.printf("| %-18s | %15.12f | %15.12f | %11.8f | %11.8f |%n",
                name, s.mean, s.stdDev, s.min, s.max);
    }
} 