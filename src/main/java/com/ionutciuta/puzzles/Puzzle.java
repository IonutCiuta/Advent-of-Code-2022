package com.ionutciuta.puzzles;

public abstract class Puzzle<R> {
    protected final String inputFilePath;
    protected final String testInputFilePath;

    public Puzzle() {
        this.inputFilePath = getInputFilePath();
        this.testInputFilePath = getTestInputFilePath();
    }

    protected abstract R solvePart1(String inputFile);

    public R solvePart1ForTestInput() {
        return solvePart1(testInputFilePath);
    }

    public R solvePart1() {
        return solvePart1(inputFilePath);
    }

    protected abstract R solvePart2(String inputFile);

    public R solvePart2ForTestInput() {
        return solvePart2(testInputFilePath);
    }

    public R solvePart2() {
        return solvePart2(inputFilePath);
    }

    private String getInputFilePath() {
        return "in/%s.in".formatted(this.getClass().getSimpleName().toLowerCase());
    }

    private String getTestInputFilePath() {
        return "in/%s.test.in".formatted(this.getClass().getSimpleName().toLowerCase());
    }
}
