package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.function.Function;

public class Puzzle11 extends Puzzle<Integer> {

    @Override
    public Integer solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var monkeys = parseInput(file);
        System.out.println(monkeys);

        for (int i = 0; i < 20; i++) {
            for (var monkey : monkeys) {
                if (monkey.items.isEmpty()) {
                    continue;
                }
                for (int j = 0; j < monkey.items.size(); j++) {
                    monkey.rounds += 1;
                    int currentItem = monkey.items.get(j);
                    int newItem = monkey.operation.apply(currentItem);
                    int worry = newItem / 3;
                    if (worry % monkey.divBy == 0) {
                        monkeys.get(monkey.trueNextMonkeyId).items.addLast(worry);
                    } else {
                        monkeys.get(monkey.falseNextMoneyId).items.addLast(worry);
                    }
                }
                monkey.items.clear();
            }
        }

        monkeys.sort(Comparator.comparingInt(m -> m.rounds));
        return monkeys.get(monkeys.size() - 1).rounds * monkeys.get(monkeys.size() - 2).rounds;
    }

    @Override
    protected Integer solvePart2(String inputFile) {
        final var file = new File(inputFile);
        var result = 0;

        return result;
    }

    class Monkey {
        int id;
        int divBy;
        LinkedList<Integer> items = new LinkedList<>();
        Function<Integer, Integer> operation;
        int trueNextMonkeyId;
        int falseNextMoneyId;
        int rounds = 0;

        public Monkey(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Monkey.class.getSimpleName() + "[", "]")
                    .add("id=" + id)
                    .add("divBy=" + divBy)
                    .add("items=" + items)
                    .add("operation=" + operation)
                    .add("trueNextMonkeyId=" + trueNextMonkeyId)
                    .add("falseNextMoneyId=" + falseNextMoneyId)
                    .add("rounds=" + rounds)
                    .toString();
        }
    }

    public List<Monkey> parseInput(File file) {
        final var monkeys = new ArrayList<Monkey>();

        try (var it = FileUtils.lineIterator(file)) {
            Monkey monkey = null;
            int id = 0;
            while (it.hasNext()) {
                final var line = it.nextLine();
                System.out.println("> " + line);

                if (line.isEmpty()) {
                    monkeys.add(monkey);
                    id++;
                    continue;
                }

                if (line.startsWith("Monkey")) {
                    monkey = new Monkey(id);
                    continue;
                }

                if (line.startsWith("Start")) {
                    var items = line.substring(16).split(", ");
                    for (var item : items) {
                        monkey.items.add(Integer.parseInt(item));
                    }
                    continue;
                }

                if (line.startsWith("Operation")) {
                    var op = line.substring(17);
                    if (op.contains("+")) {
                        var toAdd = op.split(" \\+ ");
                        if (toAdd[1].equals("old")) {
                            monkey.operation = old -> old + old;
                        } else {
                            monkey.operation = old -> old + Integer.parseInt(toAdd[1]);
                        }
                        continue;
                    }

                    if (op.contains("*")) {
                        var toAdd = op.split(" \\* ");
                        if (toAdd[1].trim().equals("old")) {
                            monkey.operation = (Integer old) -> old * old;
                        } else {
                            monkey.operation = (Integer old) -> old * Integer.parseInt(toAdd[1]);
                        }
                        continue;
                    }
                }

                if (line.startsWith("Test")) {
                    var divBy = Integer.parseInt(line.substring(19));
                    monkey.divBy = divBy;
                    continue;
                }

                if (line.startsWith("If true")) {
                    var nextMonkey = Integer.parseInt(line.substring(25));
                    monkey.trueNextMonkeyId = nextMonkey;
                    continue;
                }

                if (line.startsWith("If false")) {
                    var nextMonkey = Integer.parseInt(line.substring(26));
                    monkey.falseNextMoneyId = nextMonkey;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return monkeys;
    }
}
