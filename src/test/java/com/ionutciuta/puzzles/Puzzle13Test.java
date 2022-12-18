package com.ionutciuta.puzzles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.ionutciuta.puzzles.Puzzle13.Node;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Puzzle13Test implements PuzzleTest {
    private final Puzzle13 puzzle = new Puzzle13();

    @Test
    @Override
    public void shouldReturnExpectedTestResult_part1() {
        final var result = puzzle.solvePart1ForTestInput();
        assertEquals(13, result);
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

    @Test
    public void nodeToStringShouldPrintNiceStuff() {
        Node n1 = Node.listNode()
                .addChild(Node.valueNode(1))
                .addChild(Node.valueNode(2))
                .addChild(Node.valueNode(3))
                .addChild(Node.listNode());
        System.out.println(n1);
        assertEquals("[1,2,3,[]]", n1.toString());

        Node n2 = Node.listNode().addChild(Node.listNode().addChild(Node.listNode()));
        System.out.println(n2);
        assertEquals("[[[]]]", n2.toString());

        Node n3 = Node.listNode()
                .addChild(Node.valueNode(1))
                .addChild(Node.listNode()
                        .addChild(Node.valueNode(2))
                        .addChild(Node.listNode()
                                .addChild(Node.valueNode(3))
                                .addChild(Node.listNode()
                                        .addChild(Node.valueNode(4))
                                        .addChild(Node.listNode()
                                                .addChild(Node.valueNode(5))
                                                .addChild(Node.valueNode(6))
                                                .addChild(Node.valueNode(0))
                                        )
                                )
                        )
                )
                .addChild(Node.valueNode(8))
                .addChild(Node.valueNode(9));
        System.out.println(n3);
        assertEquals("[1,[2,[3,[4,[5,6,0]]]],8,9]", n3.toString());
    }

    @Test
    public void parseLineShouldDoNiceThings() {
        var r = puzzle.parse("[1,2,3,[]]");
        System.out.println(r);
        assertEquals("[1,2,3,[]]", r.toString());

        r = puzzle.parse("[1,[2,[3,[4,[5,6,0]]]],8,9]");
        System.out.println(r);
        assertEquals("[1,[2,[3,[4,[5,6,0]]]],8,9]", r.toString());
    }

    @ParameterizedTest
    @MethodSource("ordered")
    public void shouldBeInOrder(String l1, String l2) {
        // [1,1,3,1,1]
        // [1,1,5,1,1]
        var n1 = puzzle.parse(l1);
        var n2 = puzzle.parse(l2);

        assertTrue(puzzle.checkOrder(n1, n2));
    }

    public static Stream<Arguments> ordered() {
        return Stream.of(
                Arguments.of("[[1],[2,3,4]]", "[[1],4]")
        );
    }
}