package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    public static void main(String[] argv) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/day4.txt"));
        day4(lines);

    }

    public static void day4(List<String> lines) {
        calculatePoints(lines);
    }

    private static void calculatePoints(List<String> lines) {
        System.out.println(lines.stream().map(Day4::calculatePoints).reduce(Long::sum));
    }

    private static Long calculatePoints(String card) {
        Set<Long> winningNumbers = Arrays.stream(card.split("Card\\s+\\d+: ")[1].split("\\|")[0].split("\\s")).map(String::strip).filter(s -> !s.isEmpty()).map(Long::parseLong).collect(Collectors.toSet());
        long amountOfWins = Arrays.stream(card.split("Card\\s+\\d+: ")[1].split("\\|")[1].split("\\s")).map(String::strip).filter(s -> !s.isEmpty()).map(Long::parseLong).filter(winningNumbers::contains).count();
        if (amountOfWins == 0) {
            return 0L;
        }

        long points = 1;
        for (int i = 1; i < amountOfWins; i++) {
            points *= 2;
        }
        return points;
    }

}
