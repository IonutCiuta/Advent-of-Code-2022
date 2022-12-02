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

    enum Result {
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
        var file = new File(inputFile);
        var score = 0;
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                var line = it.nextLine();
                var parts = line.split(" ");
                var theirMove = parts[0];
                var expectedResult = parts[1];
                score += cheat(theirMove, expectedResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }

    enum Move {
        ROCK(1), PAPER(2), SCISSORS(3);

        final int points;

        Move(int points) {
            this.points = points;
        }
    }

    private final Map<String, Move> nextMove = Map.of(
            "AX", Move.SCISSORS,
            "BX", Move.ROCK,
            "CX", Move.PAPER,

            "AY", Move.ROCK,
            "BY", Move.PAPER,
            "CY", Move.SCISSORS,

            "AZ", Move.PAPER,
            "BZ", Move.SCISSORS,
            "CZ", Move.ROCK
    );

    private int getResultValue(String result) {
        return switch (result) {
            case "X" -> 0; // loss
            case "Y" -> 3; // draw
            case "Z" -> 6; // win
            default -> 0;
        };
    }

    private int cheat(String theirMove, String expectedResult) {
        var setup = theirMove + expectedResult;
        var myMove = nextMove.get(setup);
        return myMove.points + getResultValue(expectedResult);
    }
}
