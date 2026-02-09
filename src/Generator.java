/*
 * Name: Zaur Rajabov
 * Project: Assignment 1
 * Class: Object Oriented Analy.&Design (CRN: 20964)
 * Date: 09.02.2026
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Class definition

/**
 * A class for testing three random number generation methods.
 */
public class Generator {

    // Accessibility and class attribute.
    // In the assignment, we had the predefined bounds, so it made sense to add them as constant private
    // class attributes.
    private final double LOWER = 0;
    private final double UPPER = 1;

    // Method definition

    /**
     * The method for generating an array of `n` random values using one of the three algorithms.
     * @param n the number of random values to generate
     * @param randNumGen indicates which generation algorithm to use (1-3).
     * @return An array of generated random values.
     */
    ArrayList<Double> populate(int n, int randNumGen) {
        // Here we generate an Stream of doubles for each method using Stream API for simplicity and its short form.
        Stream<Double> stream = switch (randNumGen) {
            case 0 -> {
                // Object instantiation
                Random random = new Random();
                yield random
                        .doubles(n, LOWER, UPPER)
                        .boxed();
            }
            case 1 -> Stream
                    .generate(Math::random)
                    .limit(n);
            case 2 -> ThreadLocalRandom
                    .current()
                    .doubles(n, LOWER, UPPER)
                    .boxed();
            default -> throw new IllegalArgumentException("No method with number " + randNumGen);
        };
        // Stream is converted to an ArrayList.
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method computes statistics based on the provided `randomValues` array.
     * @param randomValues the random values that the statistics will be calculated on.
     * @return An array of five statistical metrics: size, mean, stddev, min, max.
     */
    ArrayList<Double> statistics(ArrayList<Double> randomValues) {
        double n = randomValues.size();

        // If an empty array is given as input, we output all zeros.
        if (n == 0) return new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));

        double mean = 0.0, min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for (Double x : randomValues) {
            mean += x;
            min = Math.min(min, x);
            max = Math.max(max, x);
        }
        mean /= n;

        double stddev = 0.0;
        // In the formula, we divide by (n - 1). That's why if n <= 1, we just output 0, as the standard deviation
        // cannot be negative and we cannot divide by 0.
        if (n > 1) {
            for (Double x : randomValues) stddev += Math.pow(x - mean, 2);
            stddev = Math.sqrt(stddev / (n - 1));
        }

        return new ArrayList<>(Arrays.asList(n, mean, stddev, min, max));
    }

    /**
     * Displays the statistics in a tabular format.
     * @param results the statistical metrics from the statistics() method.
     * @param headerOn whether to include the tabular header with the column names.
     */
    void display(ArrayList<Double> results, boolean headerOn) {
        // An arbitrary column width that seemed suitable for this task.
        int columnWidth = 15;
        String columnFormat = "| %-" + columnWidth + "s ";
        String[] columnNames = new String[] {"n", "mean", "stddev", "min", "max"};
        String columnVerticalBorder = "+" + "-".repeat(columnWidth + 2);
        String tableVerticalBorder = columnVerticalBorder.repeat(5) + "+";

        System.out.println(tableVerticalBorder);

        // Print header with column names if headerOn is true.
        if (headerOn) {
            for (String columnName : columnNames)
                System.out.printf(columnFormat, columnName);
            System.out.println("|");
            System.out.println(tableVerticalBorder);
        }

        // Print the values with rounding to four decimal places.
        for (double result : results)
            System.out.printf(columnFormat, String.format("%.4f", result));

        System.out.println("|");
        System.out.println(tableVerticalBorder);
    }

    /**
     * Generates random values for each of the three methods and for three different values of `n`. After that,
     * prints the statistics to the console.
     */
    void execute() {
        String[] randNumGenNames = new String[] {
                "java.util.Random",
                "Math.random()",
                "java.util.concurrent.ThreadLocalRandom"
        };

        int[] nValues = new int[] {10, 100, 10000};

        // Print the results for each method and for three values of n: 10 (small), 100 (medium), 1000 (large).
        // For n = 1000, the statistics are expected to converge to the values mentioned in the problem description.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Method: " + randNumGenNames[i] + ", n = " + nValues[j]);
                ArrayList<Double> randomValues = populate(nValues[j], i);
                ArrayList<Double> stats = statistics(randomValues);
                display(stats, true);
                System.out.println();
            }
        }
    }

    /**
     * The main method of the program.
     */
    public static void main(String[] args) {
        Generator g = new Generator();
        g.execute();
    }
}