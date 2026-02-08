import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {

    ArrayList<Double> populate(int n, int randNumGen) {
        double lower = 0;
        double upper = 1;
        Stream<Double> stream = switch (randNumGen) {
            case 1 -> {
                Random random = new Random();
                yield random
                        .doubles(n, lower, upper)
                        .boxed();
            }
            case 2 -> Stream
                    .generate(Math::random)
                    .limit(n);
            case 3 -> ThreadLocalRandom
                    .current()
                    .doubles(n, lower, upper)
                    .boxed();
            default -> throw new IllegalArgumentException("No method with number " + randNumGen);
        };
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }

    ArrayList<Double> statistics(ArrayList<Double> randomValues) {
        double n = randomValues.size();

        if (n == 0) return new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));

        double mean = 0.0, min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for (Double x : randomValues) {
            mean += x;
            min = Math.min(min, x);
            max = Math.max(max, x);
        }
        mean /= n;

        double stddev = 0.0;
        for (Double x : randomValues) stddev += Math.pow(x - mean, 2);
        if (n > 1) stddev = Math.sqrt(stddev / (n - 1));

        return new ArrayList<>(Arrays.asList(n, mean, stddev, min, max));
    }

    public static void main(String[] args) {

    }
}