package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class Puzzle10 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var regValues = new HashMap<Integer, Integer>();
        final var sampleCycles = Set.of(20, 60, 100, 140, 180, 220);

        var cycle = 0;
        var reg = 1;

        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                final var cmd = it.nextLine();
                if (cmd.startsWith("a")) {
                    final var toAdd = Integer.parseInt(cmd.substring(5));
                    for (int i = 0; i < 2; i++) {
                        cycle += 1;
                        if (sampleCycles.contains(cycle)) {
                            regValues.put(cycle, reg);
                        }
                    }
                    reg += toAdd;
                } else {
                    cycle += 1;
                    if (sampleCycles.contains(cycle)) {
                        regValues.put(cycle, reg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(regValues);

        return regValues
                .entrySet()
                .stream()
                .map(e -> e.getValue() * e.getKey())
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        var file = new File(inputFile);

        var result = 0;
        var cycle = 0;

        final var screen = new Screen();
        final var sprite = new Sprite();

        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                final var cmd = it.nextLine();

                if (cmd.startsWith("a")) {
                    final var toAdd = Integer.parseInt(cmd.substring(5));
                    for (int i = 0; i < 2; i++) {
                        cycle += 1;
                        screen.updateScreen(cycle, sprite);
                    }
                    sprite.updateSprite(toAdd);
                } else {
                    cycle += 1;
                    screen.updateScreen(cycle, sprite);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        screen.show();
        return result;
    }

    static class Sprite {
        int left = 0, center = 1, right = 2;

        void updateSprite(int value) {
            center += value;
            left = center - 1;
            right = center + 1;
        }
    }

    class Screen {
        static final int ROWS = 6;
        static final int PIXELS_PER_ROW = 40;
        final char[][] pixels = new char[ROWS][PIXELS_PER_ROW];

        void pixelOn(int x, int y) {
            pixels[x][y] = '#';
        }

        void pixelOff(int x, int y) {
            pixels[x][y] = '.';
        }

        void show() {
            final var sb = new StringBuilder();
            for (char[] row : pixels) {
                for (char c : row) {
                    sb.append(c);
                }
                sb.append('\n');
            }
            System.out.println(sb);
        }

        void updateScreen(int cycle, Sprite sprite) {
            int pixelX = (cycle - 1) / PIXELS_PER_ROW;
            int pixelY = Math.max((cycle - 1) % PIXELS_PER_ROW, 0);

            if (pixelY == sprite.left || pixelY == sprite.center || pixelY == sprite.right) {
                pixelOn(pixelX, pixelY);
            } else {
                pixelOff(pixelX, pixelY);
            }
        }
    }
}
