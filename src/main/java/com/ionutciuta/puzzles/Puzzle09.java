package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Puzzle09 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        var file = new File(inputFile);
        var result = 0;

        final var visited = new HashSet<String>();
        visited.add("0:0");

        var headX = 0;
        var headY = 0;
        var tailX = 0;
        var tailY = 0;

        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                var move = it.nextLine();
                var moveParts = move.split(" ");
                var direction = moveParts[0];
                var steps = Integer.parseInt(moveParts[1]);

                if (direction.equals("R")) {
                    for (int i = 0; i < steps; i++) {
                        headY += 1;
                        if (Math.abs(headY - tailY) > 1) {
                            tailY++;
                            if (Math.abs(headX - tailX) >= 1) {
                                tailX += 1;
                            } else if (headX < tailX) {
                                tailX -= 1;
                            }
                        }

                        visit(visited, tailX, tailY);
                    }
                    continue;
                }

                if (direction.equals("L")) {
                    for (int i = 0; i < steps; i++) {
                        headY -= 1;
                        if (Math.abs(headY - tailY) > 1) {
                            tailY--;
                            if (Math.abs(headX - tailX) >= 1) {
                                tailX += 1;
                            } else if (headX < tailX) {
                                tailX -= 1;
                            }
                        }

                        visit(visited, tailX, tailY);
                    }
                    continue;
                }

                if (direction.equals("U")) {
                    for (int i = 0; i < steps; i++) {
                        headX += 1;
                        if (Math.abs(headX - tailX) >= 1) {
                            tailX++;

                            if (Math.abs(headY - tailY) >= 1) {
                                tailY += 1;
                            } else if (headY < tailY) {
                                tailY -= 1;
                            }
                        }

                        visit(visited, tailX, tailY);
                    }
                    continue;
                }

                if (direction.equals("D")) {
                    for (int i = 0; i < steps; i++) {
                        headX -= 1;
                        if (Math.abs(headX - tailX) > 1) {
                            tailX--;

                            if (Math.abs(headY - tailY) >= 1) {
                                tailY += 1;
                            } else if (headY < tailY) {
                                tailY -= 1;
                            }
                        }

                        visit(visited, tailX, tailY);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = visited.size();
        System.out.println(visited);

        return result;
    }

    private void visit(Set<String> visited, int x, int y) {
        visited.add(x + ":" + y);
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        var file = new File(inputFile);
        var result = 0;
        try (var it = FileUtils.lineIterator(file)) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
