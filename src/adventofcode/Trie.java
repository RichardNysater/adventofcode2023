package adventofcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Trie<T> {
    private final Map<T, Node<T>> storage;

    public Trie() {
        storage = new HashMap<>();
    }

    public void addString(List<T> input, T endValue) {
        storage.putIfAbsent(input.get(0), new Node<>());
        if (input.size() > 1) {
            storage.get(input.get(0)).addString(input, 1, endValue);
        }
    }

    public Optional<Node<T>> getNode(T c) {
        if (!storage.containsKey(c)) {
            return Optional.empty();
        }
        return Optional.of(storage.get(c));
    }

    public static class Node<T> {
        private final Map<T, Node<T>> storage;
        private T endValue;

        public Node() {
            storage = new HashMap<>();
        }

        public void addString(List<T> input, int index, T endValue) {
            storage.putIfAbsent(input.get(index), new Node<>());
            if (index+1 < input.size()) {
                storage.get(input.get(index)).addString(input, index + 1, endValue);
            } else {
                storage.get(input.get(index)).setEndValue(endValue);
            }
        }

        public Optional<Node<T>> getNode(T c) {
            if (!storage.containsKey(c)) {
                return Optional.empty();
            }
            return Optional.of(storage.get(c));
        }

        public Optional<T> getEndValue(){
            return Optional.ofNullable(endValue);
        }

        public void setEndValue(T endValue){
            this.endValue = endValue;
        }
    }
}
