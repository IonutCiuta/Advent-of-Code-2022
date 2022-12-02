package com.ionutciuta.day01;

import com.ionutciuta.puzzles.Puzzle02;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Puzzle02Test implements PuzzleTest {
    private final Puzzle02 puzzle = new Puzzle02();

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        assertEquals(15, puzzle.solvePart1ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        assertEquals(17189, puzzle.solvePart1());
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        assertEquals(12, puzzle.solvePart2ForTestInput());
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        assertEquals(13490, puzzle.solvePart2());
    }
}
