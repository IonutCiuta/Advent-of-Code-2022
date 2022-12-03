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
        System.out.println(puzzle.solvePart1());
    }

    @Override
    public void shouldReturnExpectedTestResult_part2() {

    }

    @Override
    public void shouldReturnResult_part2() {
        puzzle.solvePart2();
    }
}
