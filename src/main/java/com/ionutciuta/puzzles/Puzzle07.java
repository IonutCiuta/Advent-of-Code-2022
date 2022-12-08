package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Puzzle07 extends Puzzle<Long> {

    @Override
    public Long solvePart1(String inputFile) {
        final var file = new File(inputFile);
        var result = 0L;

        try (var it = FileUtils.lineIterator(file)) {
            final var rootDir = parseFromFile(it);
            rootDir.computeSize();

            var set = findDirectoriesSmallerThanThreshold(rootDir);
            result = set.stream()
                    .map(folder -> folder.size)
                    .reduce(Long::sum)
                    .orElse(0L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Set<MyFolder> findDirectoriesSmallerThanThreshold(MyFolder root) {
        final var result = new HashSet<MyFolder>();
        findDirectoriesSmallerThanThreshold(root, 100000, result);
        return result;
    }

    private void findDirectoriesSmallerThanThreshold(MyFolder root, long threshold, Set<MyFolder> result) {
        for (FsObject fsObject : root.nodes.values()) {
            if (fsObject instanceof MyFolder folder) {
                if (folder.size <= threshold) {
                    result.add(folder);
                }
                findDirectoriesSmallerThanThreshold(folder, threshold, result);
            }
        }
    }

    @Override
    protected Long solvePart2(String inputFile) {
        final var file = new File(inputFile);
        var result = 0L;

        final var totalMemory = 70000000;
        final var updateSize = 30000000;

        try (var it = FileUtils.lineIterator(file)) {
            final var rootDir = parseFromFile(it);
            rootDir.computeSize();

            final var availableMemory = totalMemory - rootDir.size;
            final var requiredSpace = updateSize - availableMemory;

            final var set = findDirectoriesGreaterThanThreshold(rootDir, requiredSpace);
            result = set.stream()
                    .map(folder -> folder.size)
                    .min(Long::compare)
                    .orElse(0L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Set<MyFolder> findDirectoriesGreaterThanThreshold(MyFolder root, long threshold) {
        final var result = new HashSet<MyFolder>();
        findDirectoriesGreaterThanThreshold(root, threshold, result);
        return result;
    }

    private void findDirectoriesGreaterThanThreshold(MyFolder root, long threshold, Set<MyFolder> result) {
        for (FsObject fsObject : root.nodes.values()) {
            if (fsObject instanceof MyFolder folder) {
                if (folder.size >= threshold) {
                    result.add(folder);
                }
                findDirectoriesGreaterThanThreshold(folder, threshold, result);
            }
        }
    }

    protected MyFolder parseFromFile(LineIterator it) {
        final var rootDir = new MyFolder("/");
        var currentDir = rootDir;
        while (it.hasNext()) {
            var line = it.nextLine();

            if (line.startsWith("$ cd /")) {
                currentDir = rootDir;
                continue;
            }

            if (line.startsWith("$ cd ..")) {
                currentDir = currentDir.parent;
                continue;
            }

            if (line.startsWith("$ cd ")) {
                var dirName = line.split("\\$ cd ")[1];
                currentDir = (MyFolder) currentDir.nodes.get(dirName);
                continue;
            }

            if (line.startsWith("$ ls")) {
                continue;
            }

            if (line.startsWith("dir")) {
                var dirName = line.split(" ")[1];
                var dir = new MyFolder(dirName, currentDir);
                currentDir.addNode(dir);
            } else {
                var fileLineParts = line.split(" ");
                var size = Long.parseLong(fileLineParts[0]);
                var name = fileLineParts[1];
                var newFile = new MyFile(name, size, currentDir);
                currentDir.addNode(newFile);
            }
        }
        return rootDir;
    }

    abstract class FsObject {
        String name;
        Long size;
        Map<String, FsObject> nodes;
        MyFolder parent;

        public FsObject(String name, Long size, MyFolder parent, Map<String, FsObject> nodes) {
            this.name = name;
            this.size = size;
            this.nodes = nodes;
            this.parent = parent;
        }

        boolean isFolder() {
            return nodes != null;
        }

        void addNode(FsObject fsObject) {
            if (isFolder()) {
                nodes.put(fsObject.name, fsObject);
            } else {
                throw new UnsupportedOperationException(name + " is file!");
            }
        }

        long computeSize() {
            if (isFolder()) {
                this.size = nodes
                        .values()
                        .stream()
                        .map(FsObject::computeSize)
                        .reduce((Long::sum))
                        .orElse(0L);
            }
            return this.size;
        }
    }

    class MyFolder extends FsObject {
        MyFolder(String name) {
            super(name, 0L, null, new HashMap<>());
        }

        MyFolder(String name, MyFolder parent) {
            super(name, 0L, parent, new HashMap<>());
        }
    }

    class MyFile extends FsObject {
        MyFile(String name, Long size, MyFolder parent) {
            super(name, size, parent, null);
        }
    }
}
