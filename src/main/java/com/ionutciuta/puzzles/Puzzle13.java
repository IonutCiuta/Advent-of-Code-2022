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

                    if (inOrder != Order.NOT_ORDERED) {
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

    enum Order {
        ORDERED, EQUAL, NOT_ORDERED
    }

    Order checkOrder(Node n1, Node n2) {
        if (n1.isValueNode() && n2.isValueNode()) {
            final var diff = n1.value - n2.value;
            if (diff < 0) return Order.ORDERED;
            if (diff == 0) return Order.EQUAL;
            return Order.NOT_ORDERED;
        }

        if (!n1.isValueNode() && n1.next.isEmpty()
                && !n2.isValueNode() && n2.next.isEmpty()) {
            return Order.EQUAL;
        }

        if (n1.isValueNode() && !n2.isValueNode()) {
            return checkOrder(Node.listNode().addChild(n1), n2);
        }

        if (!n1.isValueNode() && n2.isValueNode()) {
            return checkOrder(n1, Node.listNode().addChild(n2));
        }

        final var n1Size = n1.next.size();
        final var n2Size = n2.next.size();
        final var len = Math.min(n1Size, n2Size);


        Order status = Order.EQUAL;
        for (int i = 0; i < len; i++) {
            status = checkOrder(n1.next.get(i), n2.next.get(i));
            if (status == Order.NOT_ORDERED) {
                return Order.NOT_ORDERED;
            }
        }

        if (status == Order.EQUAL) {
            if (n1Size > n2Size) {
                return Order.NOT_ORDERED;
            } else {
                return Order.ORDERED;
            }
        }

        return status;
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

        static Node valueNode(int value) {
            return new Node(null, null, value);
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
