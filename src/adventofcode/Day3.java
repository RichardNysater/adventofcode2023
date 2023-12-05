package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day3 {

    public static void main(String[] argv) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/day3.txt"));
        day3(lines);

    }

    public static void day3(List<String> lines) {
        List<List<Node>> schematic = lines.stream().map(line -> line.chars().mapToObj(i -> new Node((char) i)).toList()).toList();

        calculatePartNumberSum(schematic);
        calculateGearRatio(schematic);
    }

    private static void calculateGearRatio(List<List<Node>> schematic) {
        List<Long> gearRatios = new ArrayList<>();
        for (int column = 0; column < schematic.size(); column++) {
            for (int row = 0; row < schematic.get(column).size(); row++) {
                if (schematic.get(column).get(row).isGear()) {
                    List<Long> gearNumbers = new ArrayList<>();
                    Optional<Long> tl = getNumber(column - 1, row - 1, schematic);
                    tl.ifPresent(gearNumbers::add);

                    Optional<Long> t = getNumber(column - 1, row, schematic);
                    if (tl.isEmpty()) { // If top left exists, then top would be part of top left
                        t.ifPresent(gearNumbers::add);
                    }

                    if (t.isEmpty()) { // If top exists, then top right would be part of top
                        Optional<Long> tr = getNumber(column - 1, row + 1, schematic);
                        tr.ifPresent(gearNumbers::add);
                    }

                    Optional<Long> l = getNumber(column, row - 1, schematic);
                    l.ifPresent(gearNumbers::add);

                    Optional<Long> r = getNumber(column, row + 1, schematic);
                    r.ifPresent(gearNumbers::add);

                    Optional<Long> bl = getNumber(column + 1, row - 1, schematic);
                    bl.ifPresent(gearNumbers::add);

                    Optional<Long> b = getNumber(column + 1, row, schematic);
                    if (bl.isEmpty()) {  // If bot left exists, then bot would be part of bot left
                        b.ifPresent(gearNumbers::add);
                    }

                    if (b.isEmpty()) { // If bot exists, then bot right would be part of bot
                        Optional<Long> br = getNumber(column + 1, row + 1, schematic);
                        br.ifPresent(gearNumbers::add);
                    }

                    if (gearNumbers.size() == 2) {
                        gearRatios.add(gearNumbers.get(0) * gearNumbers.get(1));
                    }
                }
            }
        }
        System.out.println(gearRatios.stream().reduce(Long::sum));
    }


    private static Optional<Long> getNumber(int column, int row, List<List<Node>> schematic) {
        if (column < 0 || row < 0 || column == schematic.size() || row == schematic.get(column).size() || !schematic.get(column).get(row).isDigit()) {
            return Optional.empty();
        }

        int curRow = row;
        while (curRow > 0 && schematic.get(column).get(curRow - 1).isDigit()) {
            curRow--;
        }

        List<Character> digits = new ArrayList<>();
        while (curRow < schematic.get(column).size() && schematic.get(column).get(curRow).isDigit()) {
            digits.add(schematic.get(column).get(curRow).character);
            curRow++;
        }
        return digits.stream().map(c -> Character.toString(c)).reduce(String::concat).map(Long::parseLong);
    }

    private static void calculatePartNumberSum(List<List<Node>> schematic) {
        List<Long> adjacentNumbers = new ArrayList<>();
        for (int column = 0; column < schematic.size(); column++) {
            for (int row = 0; row < schematic.get(column).size(); row++) {
                boolean adjacentNumber = false;
                List<Node> digitNodes = new ArrayList<>();
                while (true) {
                    Node currentNode = schematic.get(column).get(row);
                    if (currentNode.isDigit()) {
                        adjacentNumber = adjacentNumber || isAdjacentNumber(column, row, schematic);
                        digitNodes.add(currentNode);
                        row++;
                    }
                    if (!currentNode.isDigit() || row == schematic.get(column).size()) {
                        if (!digitNodes.isEmpty() && adjacentNumber) {
                            adjacentNumbers.add(digitNodes.stream().map(node -> Character.toString(node.getCharacter())).reduce(String::concat).map(Long::parseLong).orElseThrow());
                        }
                        break;
                    }

                }
            }
        }
        System.out.println(adjacentNumbers.stream().reduce(Long::sum));
    }


    private static boolean isAdjacentNumber(int column, int row, List<List<Node>> schematic) {
        if (column != 0) {
            // left
            if (schematic.get(column - 1).get(row).isSymbol()) {
                return true;
            }

            // top left
            if (row != 0) {
                if (schematic.get(column - 1).get(row - 1).isSymbol()) {
                    return true;
                }
            }

            // bottom left
            if (row != schematic.get(column).size() - 1) {
                if (schematic.get(column - 1).get(row + 1).isSymbol()) {
                    return true;
                }
            }
        }

        if (column != schematic.size() - 1) {
            // right
            if (schematic.get(column + 1).get(row).isSymbol()) {
                return true;
            }

            // top right
            if (row != 0) {
                if (schematic.get(column + 1).get(row - 1).isSymbol()) {
                    return true;
                }
            }

            // bottom right
            if (row != schematic.get(column).size() - 1) {
                if (schematic.get(column + 1).get(row + 1).isSymbol()) {
                    return true;
                }
            }
        }

        if (row != 0) {
            // top
            if (schematic.get(column).get(row - 1).isSymbol()) {
                return true;
            }
        }

        if (row != schematic.get(column).size() - 1) {
            // bottom
            if (schematic.get(column).get(row + 1).isSymbol()) {
                return true;
            }
        }
        return false;
    }

    public static class Node {
        private Character character;
        private boolean symbol;
        private boolean digit;
        private boolean gear;

        public Node(Character c) {
            this.character = c;
            this.digit = (c >= '0' && c <= '9');
            this.symbol = !this.digit && !c.equals('.');
            this.gear = c.equals('*');
        }

        public boolean isSymbol() {
            return symbol;
        }

        public boolean isDigit() {
            return digit;
        }

        public boolean isGear() {
            return gear;
        }

        public Character getCharacter() {
            return character;
        }
    }
}
