package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class Puzzle06 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        var file = new File(inputFile);
        var result = 0;

        try (var it = FileUtils.lineIterator(file)) {
            var datastream = it.nextLine();
            result = getStartOfMessage(datastream, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        var file = new File(inputFile);
        var result = 0;

        try (var it = FileUtils.lineIterator(file)) {
            var datastream = it.nextLine();
            result = getStartOfMessage(datastream, 14);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private int getStartOfMessage(String datastream, int markerSize) {
        var markerChars = new LinkedList<Character>();

        for (char c : datastream.substring(0, markerSize - 1).toCharArray()) {
            markerChars.addLast(c);
        }

        var markerSet = new HashSet<Character>();

        for (var i = markerSize - 1; i < datastream.length(); i++) {
            var newChar = datastream.charAt(i);
            markerChars.addLast(newChar);
            markerSet.addAll(markerChars);

            if (markerSet.size() == markerSize) {
                System.out.println(markerChars);
                return i + 1;
            } else {
                markerChars.removeFirst();
            }

            markerSet.clear();
        }

        throw new RuntimeException("Oooopsie");
    }
}
