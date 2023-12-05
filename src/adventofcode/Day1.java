package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day1 {
    private static Trie<Character> charTrie;

    public static void main(String[] argv) throws IOException {
        initializeTrie();

        List<String> lines = Files.readAllLines(Path.of("resources/day1.txt"));
        day1(lines, false);

        initializeTrie();
        day1(lines, true);

    }

    private static void initializeTrie() {
        charTrie = new Trie<>();
        charTrie.addString(getAsListOfCharacters("one"), '1');
        charTrie.addString(getAsListOfCharacters("two"), '2');
        charTrie.addString(getAsListOfCharacters("three"), '3');
        charTrie.addString(getAsListOfCharacters("four"), '4');
        charTrie.addString(getAsListOfCharacters("five"), '5');
        charTrie.addString(getAsListOfCharacters("six"), '6');
        charTrie.addString(getAsListOfCharacters("seven"), '7');
        charTrie.addString(getAsListOfCharacters("eight"), '8');
        charTrie.addString(getAsListOfCharacters("nine"), '9');
    }

    private static List<Character> getAsListOfCharacters(String one) {
        return one.chars().mapToObj(c -> (char) c).toList();
    }

    public static void day1(List<String> lines, boolean allowStringNumbers) {
        System.out.println(getSummedCalibrationValues(lines, allowStringNumbers));
    }

    private static Integer getSummedCalibrationValues(List<String> lines, boolean allowStringNumbers) {
        return lines.stream().map(line -> getCalibrationValue(line, allowStringNumbers)).reduce(Integer::sum).orElseThrow();
    }

    private static int getCalibrationValue(String line, boolean allowStringNumbers) {
        Character firstValue = getCharacters(line, allowStringNumbers).getFirst();
        Character lastValue = getCharacters(line, allowStringNumbers).getLast();

        return Integer.parseInt(firstValue.toString() + lastValue.toString());
    }

    private static List<Character> getCharacters(String line, boolean allowStringNumbers) {
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            char curChar = line.charAt(i);
            if (curChar >= '0' && curChar <= '9') {
                characters.add(curChar);
            } else if (allowStringNumbers) {
                getStringValue(i, line).ifPresent(characters::add);
            }
        }
        return characters;
    }

    private static Optional<Character> getStringValue(int index, String line) {
        Optional<Trie.Node<Character>> startNode = charTrie.getNode(line.charAt(index));
        if (startNode.isEmpty()) {
            return Optional.empty();
        }

        Trie.Node<Character> curNode = startNode.get();
        int curIndex = index;
        while (true) {
            if (curNode.getEndValue().isPresent()) {
                return curNode.getEndValue();
            }
            curIndex++;
            if (curIndex >= line.length()) {
                return Optional.empty();
            }

            if (curNode.getNode(line.charAt(curIndex)).isPresent()) {
                curNode = curNode.getNode(line.charAt(curIndex)).get();
            } else {
                return Optional.empty();
            }
        }
    }
}
