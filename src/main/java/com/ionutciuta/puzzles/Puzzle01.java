package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class Puzzle01 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        var file = new File(inputFile);
        int max = 0;
        int current = 0;
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                String line = it.nextLine();

                if (line.isEmpty()) {
                    if (current >= max) {
                        max = current;
                    }
                    current = 0;
                } else {
                    current += Integer.parseInt(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        var file = new File(inputFile);
        int current = 0;
        var totals = new ArrayList<Integer>();

        try (LineIterator it = FileUtils.lineIterator(file);) {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (line.isEmpty()) {
                    totals.add(current);
                    current = 0;
                } else {
                    current += Integer.parseInt(line);
                }
            }
            totals.add(current);
        } catch (Exception e) {
            e.printStackTrace();
        }

        totals.sort(Comparator.naturalOrder());

        var len = totals.size();
        return totals.get(len - 1)
                + totals.get(len - 2)
                + totals.get(len - 3);
    }
}
