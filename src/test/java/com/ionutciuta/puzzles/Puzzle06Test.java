package com.ionutciuta.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Puzzle06Test implements PuzzleTest {
    private final Puzzle06 puzzle = new Puzzle06();

    @Test
    @Override
    public void     shouldReturnExpectedTestResult_part1() {
        final var result = puzzle.solvePart1ForTestInput();
        assertEquals(10, result);
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        final var result = puzzle.solvePart1();
        System.out.println(result);
        assertEquals(1965, result);
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        final var result = puzzle.solvePart2ForTestInput();
        assertEquals(29, result);
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        final var result = puzzle.solvePart2();
        System.out.println(result);
        assertEquals(2773, result);
    }
}