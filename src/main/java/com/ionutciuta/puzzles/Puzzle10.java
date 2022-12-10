package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class Puzzle10 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var regValues = new HashMap<Integer, Integer>();
        final var sampleCycles = Set.of(20, 60, 100, 140, 180, 220);

        var cycle = 0;
        var reg = 1;

        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                final var cmd = it.nextLine();
                if (cmd.startsWith("a")) {
                    final var toAdd = Integer.parseInt(cmd.substring(5));
                    for (int i = 0; i < 2; i++) {
                        cycle += 1;
                        if (sampleCycles.contains(cycle)) {
                            regValues.put(cycle, reg);
                        }
                    }
                    reg += toAdd;
                } else {
                    cycle += 1;
                    if (sampleCycles.contains(cycle)) {
                        regValues.put(cycle, reg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(regValues);

        return regValues
                .entrySet()
                .stream()
                .map(e -> e.getValue() * e.getKey())
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        var file = new File(inputFile);
        var result = 0;
        try (var it = FileUtils.lineIterator(file)) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
