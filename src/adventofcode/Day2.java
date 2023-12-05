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

    public static void main(String[] argv) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/day2.txt"));
        day2(lines);

    }

    public static void day2(List<String> lines) {
        System.out.println(lines.stream().filter(Day2::isGamePossible).map(Day2::getGameId).reduce(Integer::sum).orElseThrow());
    }

    private static boolean isGamePossible(String s) {
        return Arrays.stream((s.split("Game \\d+: ")[1]).split(";")).map(String::strip).allMatch(game -> Arrays.stream(game.split(",\\s")).allMatch(Day2::isDrawPossible));
    }

    private static int getGameId(String s) {
        return Integer.parseInt(s.split("\\s")[1].split(":")[0]);
    }

    private static boolean isDrawPossible(String s) {
        int number = Integer.parseInt(s.split("\\s")[0]);
        String color = s.split("\\s")[1];
        if (color.equals("green")) {
            return MAX_GREEN >= number;
        } else if (color.equals("blue")) {
            return MAX_BLUE >= number;
        } else if (color.equals("red")) {
            return MAX_RED >= number;
        }
        throw new IllegalStateException("expected color");
    }
}
