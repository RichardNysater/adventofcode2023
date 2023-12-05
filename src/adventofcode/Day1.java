package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day1 {
    public static void main(String[] argv) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/day1.txt"));
        day1(lines);
    }

    public static void day1(List<String> lines) {
        System.out.println(getSummedCalibrationValues(lines));
    }

    private static Integer getSummedCalibrationValues(List<String> lines) {
        return lines.stream().map(Day1::getCalibrationValue).reduce(Integer::sum).orElseThrow();
    }

    private static int getCalibrationValue(String line) {
        int firstValue = line.chars().filter(i -> i >= '0' && i <= '9').findFirst().orElseThrow();
        int lastValue = line.chars().filter(i -> i >= '0' && i <= '9').boxed().toList().getLast();

        return Integer.parseInt(Character.toString(firstValue) + Character.toString(lastValue));
    }
}
