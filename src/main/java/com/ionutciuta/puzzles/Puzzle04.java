package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Puzzle04 extends Puzzle<Integer> {

    @Override
    protected Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        int total = 0;
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                final var line = it.nextLine();
                final var sections = line.split(",");
                final var firstSection = sections[0].split("-");
                final var secondSection = sections[1].split("-");

                int firstSectionStart = Integer.parseInt(firstSection[0]);
                int firstSectionEnd = Integer.parseInt(firstSection[1]);
                int secondSectionStart = Integer.parseInt(secondSection[0]);
                int secondSectionEnd = Integer.parseInt(secondSection[1]);

                if (firstSectionStart <= secondSectionStart && firstSectionEnd >= secondSectionEnd
                        || secondSectionStart <= firstSectionStart && secondSectionEnd >= firstSectionEnd) {
                    total++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        final var file = new File(inputFile);
        int total = 0;
        try (var it = FileUtils.lineIterator(file)) {
            while (it.hasNext()) {
                final var line = it.nextLine();
                final var sections = line.split(",");
                final var firstSection = sections[0].split("-");
                final var secondSection = sections[1].split("-");

                int firstSectionStart = Integer.parseInt(firstSection[0]);
                int firstSectionEnd = Integer.parseInt(firstSection[1]);
                int secondSectionStart = Integer.parseInt(secondSection[0]);
                int secondSectionEnd = Integer.parseInt(secondSection[1]);

                if (firstSectionStart <= secondSectionStart && secondSectionStart <= firstSectionEnd
                        || secondSectionStart <= firstSectionStart && firstSectionStart <= secondSectionEnd) {
                    total++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}
