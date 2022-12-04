package com.ionutciuta.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Puzzle04Test implements PuzzleTest {
    private final Puzzle04 puzzle = new Puzzle04();

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        assertEquals(2, puzzle.solvePart1ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        final var result = puzzle.solvePart1();
        System.out.println(result);
        assertEquals(483, result);
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        assertEquals(4, puzzle.solvePart2ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        final var result = puzzle.solvePart2();
        System.out.println(result);
        assertEquals(874, result);
    }
}