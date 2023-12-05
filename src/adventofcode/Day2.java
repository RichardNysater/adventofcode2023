package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day2 {
    private static final int MAX_RED = 12;
    private static final int MAX_GREEN = 13;
    private static final int MAX_BLUE = 14;

    private static long minRed = -1;
    private static long minBlue = -1;
    private static long minGreen = -1;

    public static void main(String[] argv) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/day2.txt"));
        day2(lines);

    }

    public static void day2(List<String> lines) {
        System.out.println(lines.stream().filter(Day2::isGamePossible).map(Day2::getGameId).reduce(Integer::sum).orElseThrow());
        System.out.println(lines.stream().map(Day2::getPowerOfFewestNumberOfCubes).reduce(Long::sum).orElseThrow());
    }

    private static long getPowerOfFewestNumberOfCubes(String game) {
        minRed = 0;
        minBlue = 0;
        minGreen = 0;
        Arrays.stream((game.split("Game \\d+: ")[1])
                .split(";")).map(String::strip)
                .forEach(draw ->
                        Arrays.stream(draw.split(",\\s"))
                                .forEach(Day2::calculateMinimumCubes));

        return minRed * minBlue * minGreen;
    }

    private static void calculateMinimumCubes(String draw) {
        int number = Integer.parseInt(draw.split("\\s")[0]);
        String color = draw.split("\\s")[1];
        switch (color) {
            case "green" -> minGreen = Math.max(minGreen, number);
            case "blue" -> minBlue = Math.max(minBlue, number);
            case "red" -> minRed = Math.max(minRed, number);
        }
    }

    private static boolean isGamePossible(String game) {
        return Arrays.stream((game.split("Game \\d+: ")[1]).split(";"))
                .map(String::strip)
                .allMatch(draw ->
                        Arrays.stream(draw.split(",\\s"))
                                .allMatch(Day2::isDrawPossible));
    }

    private static int getGameId(String s) {
        return Integer.parseInt(s.split("\\s")[1].split(":")[0]);
    }

    private static boolean isDrawPossible(String s) {
        int number = Integer.parseInt(s.split("\\s")[0]);
        String color = s.split("\\s")[1];
        switch (color) {
            case "green" -> {
                return MAX_GREEN >= number;
            }
            case "blue" -> {
                return MAX_BLUE >= number;
            }
            case "red" -> {
                return MAX_RED >= number;
            }
        }
        throw new IllegalStateException("expected color");
    }
}
