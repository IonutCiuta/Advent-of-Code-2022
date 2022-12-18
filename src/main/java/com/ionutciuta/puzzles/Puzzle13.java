package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Puzzle13 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        var result = 0;
        try (var it = FileUtils.lineIterator(file)) {
            var pairIndex = 1;
            String firstPacket = null;
            String secondPacket = null;

            while (it.hasNext()) {
                final var line = it.nextLine();
                if (line.isEmpty()) {
                    final var n1 = parse(firstPacket);
                    final var n2 = parse(secondPacket);
                    final var inOrder = checkOrder(n1, n2);

                    if (inOrder) {
                        result += pairIndex;
                    }

                    System.out.println("Pair " + pairIndex + ", ordered: " + inOrder);
                    System.out.println(n1);
                    System.out.println(n2);
                    System.out.println();

                    firstPacket = null;
                    secondPacket = null;
                    pairIndex += 1;
                } else {
                    if (firstPacket == null) {
                        firstPacket = line;
                    } else {
                        secondPacket = line;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean checkOrder(Node n1, Node n2) {
        return false;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        final var file = new File(inputFile);
        var result = -1;
        try (var it = FileUtils.lineIterator(file)) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Node parse(String line) {
        final var node = Node.listNode();
        parseLine(line.toCharArray(), 1, node);
        return node;
    }

    @SuppressWarnings("rawtypes")
    private void parseLine(char[] chars, int i, Node node) {
        if (i == chars.length) {
            return;
        }

        final var current = chars[i];
        if (current == ',') {
            parseLine(chars, i + 1, node);
            return;
        }

        if (current == '[') {
            final var newNode = Node.listNode();
            node.addChild(newNode);
            parseLine(chars, i + 1, newNode);
            return;
        }

        if (current == ']') {
            parseLine(chars, i + 1, node.parent);
            return;
        }

        node.addChild(Node.valueNode(current - '0'));
        parseLine(chars, i + 1, node);
    }

    static class Node {
        Node parent;
        List<Node> next;
        int value;

        Node(Node parent, List<Node> next, int value) {
            this.parent = parent;
            this.next = next;
            this.value = value;
        }

        static Node valueNode(int value, Node parent) {
            return new Node(parent, null, value);
        }

        static Node valueNode(int value) {
            return new Node(null, null, value);
        }

        static Node listNode(Node parent) {
            return new Node(parent, new ArrayList<>(), -1);
        }

        static Node listNode() {
            return new Node(null, new ArrayList<>(), -1);
        }

        boolean isValueNode() {
            return this.next == null;
        }

        Node addChild(Node n) {
            if (!this.isValueNode()) {
                n.parent = this;
                this.next.add(n);
                return this;
            }
            throw new UnsupportedOperationException("Node must be list!");
        }

        @Override
        public String toString() {
            if (this.isValueNode()) {
                return Integer.toString(value);
            } else {
                final var sb = new StringBuilder();
                sb.append("[");


                final var it = next.iterator();
                while (it.hasNext()) {
                    final var node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext()) {
                        sb.append(",");
                    }
                }

                sb.append("]");
                return sb.toString();
            }
        }
    }
}
