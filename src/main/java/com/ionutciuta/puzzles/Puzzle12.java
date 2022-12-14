package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class Puzzle12 extends Puzzle<Integer> {
    private final List<Point> adjacentOffsets = List.of(
            new Point(-1, 0, 0), // up
            new Point(1, 0, 0),  // down
            new Point(0, -1, 0), // left
            new Point(0, 1, 0)   // right
    );

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var map = readMap(file);
        final var results = new HashSet<Integer>();
        final var visited = new HashSet<String>();

        final var start = findStart(map);
        final var next = new LinkedList<Point>();
        next.add(start);

        while (!next.isEmpty()) {
            System.out.println(next);
            final var point = next.removeFirst();

            if (visited.contains(point.key())) {
                continue;
            }

            for (var offset : adjacentOffsets) {
                final var adjacent = point.offsetBy(offset);

                if (!isValidAdjacentPoint(adjacent, map)) {
                    continue;
                }

                if (map[point.x][point.y] == 'z' && map[adjacent.x][adjacent.y] == 'E') {
                    results.add(adjacent.d);
                    continue;
                }

                final var diff = Math.abs(map[point.x][point.y] - map[adjacent.x][adjacent.y]);
                if (diff <= 1 || map[point.x][point.y] == 'S') {
                    if (!visited.contains(adjacent.key())) {
                        next.addLast(adjacent);
                        visited.add(adjacent.key());
                    }
                }
            }
        }

        return results.stream().min(Integer::compareTo).orElse(-1);
    }

    boolean isValidAdjacentPoint(Point adjacent, char[][] map) {
        var adjX = adjacent.x;
        var adjY = adjacent.y;
        return adjX >= 0 && adjX < map.length && adjY >= 0 && adjY < map[0].length;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        final var file = new File(inputFile);
        var result = 0;
        try (var it = FileUtils.lineIterator(file)) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private char[][] readMap(File file) {
        final var mapLines = new ArrayList<char[]>();
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                mapLines.add(it.nextLine().toCharArray());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        final var map = new char[mapLines.size()][mapLines.get(0).length];
        for (int i = 0; i < mapLines.size(); i++) {
            map[i] = mapLines.get(i);
        }

        return map;
    }

    Point findStart(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    return new Point(i, j, 0);
                }
            }
        }
        throw new RuntimeException("Start not found.");
    }

    record Point(int x, int y, int d) {
        Point offsetBy(Point offset) {
            return new Point(this.x + offset.x, this.y + offset.y, this.d + 1);
        }

        String key() {
            return this.x + ":" + this.y;
        }
    }
}
