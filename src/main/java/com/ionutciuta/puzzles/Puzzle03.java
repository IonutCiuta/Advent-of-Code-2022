package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Puzzle03 extends Puzzle<Integer> {
    private final Map<Character, Integer> itemsValue = new HashMap<>();

    Puzzle03() {
        for (char letter = 97; letter < 123; letter++) {
            itemsValue.put(letter, letter - 96);
        }
        for (char letter = 65; letter < 91; letter++) {
            itemsValue.put(letter, letter - 38);
        }
    }

    @Override
    protected Integer solvePart1(String inputFile) {
        var file = new File(inputFile);
        var total = 0;

        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                var line = it.nextLine();
                var items = line.toCharArray();
                var len = items.length;
                var firstCompartment = new HashSet<Character>();

                for (int i = 0; i < len / 2; i++) {
                    firstCompartment.add(items[i]);
                }

                var commonItems = new HashSet<Character>();
                for (int i = len / 2; i < len; i++) {
                    var item = items[i];
                    if (firstCompartment.contains(item) && !commonItems.contains(item)) {
                        commonItems.add(item);
                    }
                }

                for (char item : commonItems) {
                    total += itemsValue.get(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        return null;
    }
}
