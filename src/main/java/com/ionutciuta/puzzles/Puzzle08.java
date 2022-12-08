package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class Puzzle08 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var forest = parseInput(file);

        int counter = 0;
        counter += 2 * forest.length;
        counter += 2 * (forest[0].length - 2);

        for (int i = 1; i < forest.length - 1; i++) {
            final var row = forest[i];

            for (int j = 1; j < row.length - 1; j++) {
                final var tree = row[j];

                var visibleOnTheLeft = true;
                for (var left = 0; left < j && visibleOnTheLeft; left++) {
                    visibleOnTheLeft = tree > row[left];
                }
                if (visibleOnTheLeft) {
                    counter++;
                    continue;
                }

                var visibleOnTheRight = true;
                for (var right = j + 1; right < row.length && visibleOnTheRight; right++) {
                    visibleOnTheRight = tree > row[right];
                }
                if (visibleOnTheRight) {
                    counter++;
                    continue;
                }

                var visibleFromTheTop = true;
                for (var top = 0; top < i && visibleFromTheTop; top++) {
                    visibleFromTheTop = tree > forest[top][j];
                }
                if (visibleFromTheTop) {
                    counter++;
                    continue;
                }

                var visibleFromTheBottom = true;
                for (var bottom = i + 1; bottom < forest.length && visibleFromTheBottom; bottom++) {
                    visibleFromTheBottom = tree > forest[bottom][j];
                }
                if (visibleFromTheBottom) {
                    counter++;
                }
            }

        }
        return counter;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        final var file = new File(inputFile);
        final var forest = parseInput(file);

        var maxScenicScore = 0;
        for (int i = 0; i < forest.length; i++) {
            final var row = forest[i];

            for (int j = 0; j < row.length; j++) {
                final var tree = row[j];

                var visibleOnTheLeft = 0;
                for (var left = j - 1; left >= 0; left--) {
                    if (row[left] >= tree) {
                        visibleOnTheLeft += 1;
                        break;
                    }

                    if (row[left] < tree) {
                        visibleOnTheLeft += 1;
                    } else {
                        break;
                    }
                }

                var visibleOnTheRight = 0;
                for (var right = j + 1; right < row.length; right++) {
                    if (row[right] >= tree) {
                        visibleOnTheRight += 1;
                        break;
                    }

                    if (row[right] < tree) {
                        visibleOnTheRight += 1;
                    } else {
                        break;
                    }
                }

                var visibleFromTheTop = 0;
                for (var top = i - 1; top >= 0; top--) {
                    if (forest[top][j] >= tree) {
                        visibleFromTheTop += 1;
                        break;
                    }

                    if (forest[top][j] < tree) {
                        visibleFromTheTop += 1;
                    } else {
                        break;
                    }
                }

                var visibleFromTheBottom = 0;
                for (var bottom = i + 1; bottom < forest.length; bottom++) {
                    if (forest[bottom][j] >= tree) {
                        visibleFromTheBottom += 1;
                        break;
                    }

                    if (forest[bottom][j] < tree) {
                        visibleFromTheBottom += 1;
                    } else {
                        break;
                    }
                }

                final var score = visibleOnTheLeft * visibleOnTheRight * visibleFromTheTop * visibleFromTheBottom;
                if (score > maxScenicScore) {
                    maxScenicScore = score;
                }
            }

        }
        return maxScenicScore;
    }

    private int[][] parseInput(File file) {
        var lines = new ArrayList<String>();
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                lines.add(it.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final var data = new int[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            final var line = lines.get(i);
            for (var j = 0; j < line.length(); j++) {
                data[i][j] = Integer.parseInt(line.substring(j, j + 1));
            }
        }

        return data;
    }
}
