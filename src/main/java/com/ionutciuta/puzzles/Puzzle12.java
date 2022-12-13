package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Puzzle12 extends Puzzle<Integer> {
    final int END = 'E' - 'z';

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var map = readMap(file);
        final var results = new HashSet<Integer>();
        final var visited = new HashSet<String>();
        findSignal(0, 0, 0, map, visited, results);
        return results.stream().min(Integer::compareTo).orElse(-1);
    }

    void findSignal(int x, int y, int len, char[][] map, Set<String> visited, Set<Integer> result) {
        if (map[x][y] == 'E') {
            result.add(len);
            return;
        }

        // go down
        if (x - 1 > 0 && Math.abs(map[x][y] - map[x - 1][y]) <= 1) {
            if (visited.contains(keyOf(x - 1, y))) {
                return;
            }
            var newVisited = new HashSet<>(visited);
            newVisited.add(keyOf(x, y));
            findSignal(x - 1, y, len + 1, map, newVisited, result);
        }

        // go up
        if (x + 1 < map.length && Math.abs(map[x][y] - map[x + 1][y]) <= 1) {
            if (visited.contains(keyOf(x + 1, y))) {
                return;
            }
            var newVisited = new HashSet<>(visited);
            newVisited.add(keyOf(x, y));
            findSignal(x + 1, y, len + 1, map, newVisited, result);
        }

        // go left
        if (y - 1 > 0 && Math.abs(map[x][y] - map[x][y - 1]) <= 1) {
            if (visited.contains(keyOf(y - 1, y))) {
                return;
            }
            var newVisited = new HashSet<>(visited);
            newVisited.add(keyOf(x, y));
            findSignal(x, y - 1, len + 1, map, newVisited, result);
        }

        // go right
        if (y + 1 <= map[0].length && Math.abs(map[x][y] - map[x][y + 1]) <= 1) {
            if (visited.contains(keyOf(y + 1, y))) {
                return;
            }
            var newVisited = new HashSet<>(visited);
            newVisited.add(keyOf(x, y));
            findSignal(x, y + 1, len + 1, map, newVisited, result);
        }
    }

    private String keyOf(int x, int y) {
        return x + ":" + y;
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

    record Point(int x, int y, int d) {
    }
}
