package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Puzzle07Test implements PuzzleTest {
    private final Puzzle07 puzzle = new Puzzle07();

    @Test
    public void shouldParseTestDirectoryStructure() throws Exception {
        final var testFile = new File(puzzle.testInputFilePath);
        final var it = FileUtils.lineIterator(testFile);

        final var rootDir = puzzle.parseFromFile(it);
        final var totalSize = rootDir.computeSize();

        assertEquals(48381165L, totalSize);

        assertEquals("/", rootDir.name);
        assertEquals(4, rootDir.nodes.size());

        final var aDir = rootDir.nodes.get("a");
        assertEquals("a", aDir.name);
        assertEquals(4, aDir.nodes.size());
        assertEquals(94853L, aDir.size);

        final var eDir = aDir.nodes.get("e");
        assertEquals("e", eDir.name);
        assertEquals(1, eDir.nodes.size());
        assertEquals(584, eDir.size);

        final var iFile = eDir.nodes.get("i");
        assertTrue(iFile instanceof Puzzle07.MyFile);
        assertEquals(584L, iFile.size);
        assertNull(iFile.nodes);
        assertFalse(iFile.isFolder());

        var aFiles = Map.of(
            "f",  29116L,
            "g", 2557L,
            "h.lst", 62596L
        );

        aFiles.keySet().forEach(k -> {
            assertEquals(aFiles.get(k), aDir.nodes.get(k).size);
        });

        assertEquals(14848514, rootDir.nodes.get("b.txt").size);
        assertEquals(8504156, rootDir.nodes.get("c.dat").size);
        assertEquals(24933642, rootDir.nodes.get("d").size);

        it.close();
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        final var result = puzzle.solvePart1ForTestInput();
        assertEquals(95437, result);
    }

    @Test
    @Override
    public void shouldReturnResult_part1() {
        final var result = puzzle.solvePart1();
        System.out.println(result);
        assertEquals(1297683, result);
    }

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part2() {
        final var result = puzzle.solvePart2ForTestInput();
        assertEquals(24933642, result);
    }

    @Test
    @Override
    public void shouldReturnResult_part2() {
        final var result = puzzle.solvePart2();
        System.out.println(result);
        assertEquals(5756764, result);
    }

    @Test
    public void testCdDirCommandSplit() {
        var dirName = "$ cd abc".split("\\$ cd ")[1];
        assertEquals("abc", dirName);
    }
}