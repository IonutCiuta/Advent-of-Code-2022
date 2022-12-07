package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Puzzle07 extends Puzzle<Integer> {

    abstract class FsObject {
        String name;
        Long size = 0L;
        List<FsObject> nodes;
        MyFolder parent;


        public FsObject(String name, Long size, MyFolder parent, List<FsObject> nodes) {
            this.name = name;
            this.size = size;
            this.nodes = nodes;
            this.parent = parent;
        }

        boolean isFolder() {
            return nodes != null;
        }

        public void setParent(MyFolder parent) {
            this.parent = parent;
        }

        void addNode(FsObject fsObject) {
            if (isFolder()) {
                nodes.add(fsObject);
            } else {
                throw new UnsupportedOperationException(name + " is file!");
            }
        }

        long computeSize() {
            if (!isFolder()) {
                return size;
            } else {
                var size = nodes.stream().map(FsObject::computeSize).reduce((Long::sum)).get();
                this.size = size;
                return size;
            }
        }
    }

    class MyFolder extends FsObject {
        public MyFolder(String name) {
            super(name, 0L, null, new ArrayList<>());
        }

        public MyFolder(String name, MyFolder parent) {
            super(name, 0L, parent, new ArrayList<>());
        }
    }

    class MyFile extends FsObject {
        public MyFile(String name, Long size, MyFolder parent) {
            super(name, size, parent, null);
        }
    }

    @Override
    public Integer solvePart1(String inputFile) {
        var file = new File(inputFile);
        var result = 0;

        final var deleteThreshold = 100000L;

        var rootDir = new MyFolder("/");
        var currentDir = rootDir;

        try (var it = FileUtils.lineIterator(file)) {
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

            rootDir.computeSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    int searchSmallDirs(int threshold, MyFolder folder) {
        if (folder.nodes.isEmpty()) {
            return 0;
        }
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
