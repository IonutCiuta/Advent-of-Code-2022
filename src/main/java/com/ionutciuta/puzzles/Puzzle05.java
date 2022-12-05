package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.Stack;
import java.util.function.BiFunction;

public class Puzzle05 extends Puzzle<String> {

    @Override
    public String solvePart1(String inputFile) {
        var file = new File(inputFile);
        var result = "";
        try (var it = FileUtils.lineIterator(file)) {
            result = doCraneStuff(it, (stacks, operation) -> {
                for (int i = 0; i < operation.itemsToMove; i++) {
                    stacks[operation.destination].push(stacks[operation.source].pop());
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected String solvePart2(String inputFile) {
        var file = new File(inputFile);
        var result = "";
        try (var it = FileUtils.lineIterator(file)) {
            result = doCraneStuff(it, (stacks, operation) -> {
                var aux = new Stack<>();
                for (int i = 0; i < operation.itemsToMove; i++) {
                    aux.push(stacks[operation.source].pop());
                }
                for (int i = 0; i < operation.itemsToMove; i++) {
                    stacks[operation.destination].push(aux.pop());
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private record CraneOperation(int itemsToMove, int source, int destination) {
    }

    private String doCraneStuff(LineIterator it, BiFunction<Stack[], CraneOperation, Void> craneAction) {
        var stackSetup = true;
        var rawStacks = new Stack<String>();
        Stack<Character>[] stacks = null;

        while (it.hasNext()) {
            var line = it.nextLine();
            if (stackSetup) {
                // read stack setup
                if (!line.isEmpty()) {
                    if (line.trim().startsWith("[")) { // is stack
                        rawStacks.push(line); // just store for now
                    } else {
                        var numberOfStacks = line.charAt(line.length() - 1) - '0'; // find the number of stacks
                        stacks = new Stack[numberOfStacks];
                        for (int i = 0; i < numberOfStacks; i++) {
                            stacks[i] = new Stack<>();
                        }
                    }
                } else {
                    assert stacks != null;
                    while (!rawStacks.isEmpty()) {
                        var rawStack = rawStacks.pop();
                        for (int i = 0; i < rawStack.length(); i += 4) {
                            int stackIndex = i / 4;
                            if (rawStack.charAt(i) == '[') {
                                var stackItem = rawStack.charAt(i + 1);
                                stacks[stackIndex].push(stackItem);
                            }
                        }
                    }
                    stackSetup = false; // stop setup, swe reached the empty line before moves
                }
            } else {
                // read moves
                var moveParts = line.split(" from ");
                assert moveParts.length == 2;

                var location = moveParts[1].split(" to ");
                assert location.length == 2;

                var itemsToMove = Integer.parseInt(moveParts[0].substring(5));
                var source = Integer.parseInt(location[0]) - 1;
                var destination = Integer.parseInt(location[1]) - 1;

                var craneOperation = new CraneOperation(itemsToMove, source, destination);
                craneAction.apply(stacks, craneOperation);
            }
        }

        assert stacks != null;
        var resultBuilder = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            resultBuilder.append(stack.pop());
        }

        return resultBuilder.toString();
    }
}
