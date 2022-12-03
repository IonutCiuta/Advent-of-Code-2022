package com.ionutciuta.puzzles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Puzzle03Test implements PuzzleTest {
    private final Puzzle03 puzzle = new Puzzle03();

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        assertEquals(157, puzzle.solvePart1ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        final var result = puzzle.solvePart1();
        System.out.println(result);
        assertEquals(7716, result);
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        assertEquals(70, puzzle.solvePart2ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        final var result = puzzle.solvePart2();
        System.out.println(result);
        assertEquals(2973, result);
    }
}
