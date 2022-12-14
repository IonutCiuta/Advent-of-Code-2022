package com.ionutciuta.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Puzzle12Test implements PuzzleTest {
    private final Puzzle12 puzzle = new Puzzle12();

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        final var result = puzzle.solvePart1ForTestInput();
        assertEquals(31, result);
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        final var result = puzzle.solvePart1();
        System.out.println(result);
        assertEquals(0, result);
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        final var result = puzzle.solvePart2ForTestInput();
        assertEquals(0, result);
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        final var result = puzzle.solvePart2();
        System.out.println(result);
        assertEquals(0, result);
    }
}