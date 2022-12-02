package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Map;

public class Puzzle02 extends Puzzle<Integer> {
    @Override
    protected Integer solvePart1(String inputFile) {
        var file = new File(inputFile);
        var score = 0;
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                var line = it.nextLine();
                var parts = line.split(" ");
                var theirMove = parts[0];
                var myMove = parts[1];
                score += play(theirMove, myMove);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }

    private enum Result {
        LOSS(0),
        DRAW(3),
        WIN(6);

        final int score;

        Result(int score) {
            this.score = score;
        }
    }

    private final Map<String, Result> results = Map.of(
            "XA", Result.DRAW,
            "YB", Result.DRAW,
            "ZC", Result.DRAW,

            "XB", Result.LOSS,
            "YC", Result.LOSS,
            "ZA", Result.LOSS,

            "XC", Result.WIN,
            "YA", Result.WIN,
            "ZB", Result.WIN
    );

    private int play(String theirMove, String myMove) {
        int myMoveValue = getMoveValue(myMove);
        var result = results.get(myMove + theirMove);
        return myMoveValue + result.score;
    }

    private int getMoveValue(String myMove) {
        return switch (myMove) {
            case "X" -> 1; // rock      // A
            case "Y" -> 2; // paper     // B
            case "Z" -> 3; // scissors  // C
            default -> 0;
        };
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        return null;
    }
}
