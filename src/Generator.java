import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

    public static void main(String[] args) {

    }
}