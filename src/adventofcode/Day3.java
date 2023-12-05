package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {

    public static void main(String[] argv) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/day3.txt"));
        day3(lines);

    }

    public static void day3(List<String> lines) {
        List<List<Node>> schematic = lines.stream().map(line -> line.chars().mapToObj(i -> new Node((char) i)).toList()).toList();

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

        public Node(Character c) {
            this.character = c;
            this.digit = (c >= '0' && c <= '9');
            this.symbol = !this.digit && !c.equals('.');
        }

        public boolean isSymbol() {
            return symbol;
        }

        public boolean isDigit() {
            return digit;
        }

        public Character getCharacter() {
            return character;
        }
    }
}
