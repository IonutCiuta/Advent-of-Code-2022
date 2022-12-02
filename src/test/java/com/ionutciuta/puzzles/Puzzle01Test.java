package com.ionutciuta.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Puzzle01Test implements PuzzleTest {
    private final Puzzle01 puzzle = new Puzzle01();

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        assertEquals(24000, puzzle.solvePart1ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        assertEquals(75501, puzzle.solvePart1());
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        assertEquals(45000, puzzle.solvePart2ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        assertEquals(215594, puzzle.solvePart2());
    }
}