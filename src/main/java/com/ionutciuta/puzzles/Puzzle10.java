package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Puzzle10 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        var file = new File(inputFile);

        var cycle = 0;
        var reg = 1;

        final var regValues = new HashMap<Integer, Integer>();
        final var sampleCycles = Set.of(20, 60, 100, 140, 180, 220);

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

        /**
         * During the 20th cycle, register X has the value 21, so the signal strength is 20 * 21 = 420. (The 20th cycle occurs in the middle of the second addx -1, so the value of register X is the starting value, 1, plus all of the other addx values up to that point: 1 + 15 - 11 + 6 - 3 + 5 - 1 - 8 + 13 + 4 = 21.)
         * During the 60th cycle, register X has the value 19, so the signal strength is 60 * 19 = 1140.
         * During the 100th cycle, register X has the value 18, so the signal strength is 100 * 18 = 1800.
         * During the 140th cycle, register X has the value 21, so the signal strength is 140 * 21 = 2940.
         * During the 180th cycle, register X has the value 16, so the signal strength is 180 * 16 = 2880.
         * During the 220th cycle, register X has the value 18, so the signal strength is 220 * 18 = 3960.
         */
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
