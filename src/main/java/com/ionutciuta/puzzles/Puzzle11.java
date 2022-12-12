package com.ionutciuta.puzzles;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.function.Function;

public class Puzzle11 extends Puzzle<Long> {

    @Override
    public Long solvePart1(String inputFile) {
        final var file = new File(inputFile);
        final var monkeys = parseInput(file);
        System.out.println(monkeys);

        doMonkeyBusiness(monkeys, 20, true, 3, false, 1);

        monkeys.sort(Comparator.comparingDouble(m -> m.rounds));
        return monkeys.get(monkeys.size() - 1).rounds * monkeys.get(monkeys.size() - 2).rounds;
    }

    @Override
    protected Long solvePart2(String inputFile) {
        final var file = new File(inputFile);
        final var monkeys = parseInput(file);
        System.out.println(monkeys);

        final var worryLevelMod = monkeys.stream().map(m -> m.divBy).reduce((a, b) -> a * b).orElseThrow();
        doMonkeyBusiness(monkeys, 10000, false, 1, true, worryLevelMod);

        monkeys.sort(Comparator.comparingDouble(m -> m.rounds));
        return monkeys.get(monkeys.size() - 1).rounds * monkeys.get(monkeys.size() - 2).rounds;
    }

    private void doMonkeyBusiness(List<Monkey> monkeys,
                                  int rounds,
                                  boolean divideWorryLevel,
                                  int worryLevelDiv,
                                  boolean reduceWorryLevel,
                                  long worryLevelMod) {
        for (int i = 0; i < rounds; i++) {
            for (var monkey : monkeys) {
                if (monkey.items.isEmpty()) {
                    continue;
                }
                for (int j = 0; j < monkey.items.size(); j++) {
                    monkey.rounds += 1;
                    long currentItem = monkey.items.get(j);
                    long newItem = monkey.operation.apply(currentItem);

                    long worry;
                    if (divideWorryLevel) {
                        worry = newItem / worryLevelDiv;
                    } else {
                        worry = newItem;
                    }

                    if (worry % monkey.divBy == 0) {
                        monkeys.get(monkey.trueNextMonkeyId).items.addLast(worry);
                    } else {
                        if (reduceWorryLevel) {
                            monkeys.get(monkey.falseNextMoneyId).items.addLast(worry % worryLevelMod);
                        } else {
                            monkeys.get(monkey.falseNextMoneyId).items.addLast(worry);
                        }
                    }
                }
                monkey.items.clear();
            }
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

                assert monkey != null;

                if (line.startsWith("Start")) {
                    var items = line.substring(16).split(", ");
                    for (var item : items) {
                        monkey.items.add(Long.parseLong(item));
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
                            monkey.operation = old -> old + Long.parseLong(toAdd[1]);
                        }
                        continue;
                    }

                    if (op.contains("*")) {
                        var toAdd = op.split(" \\* ");
                        if (toAdd[1].trim().equals("old")) {
                            monkey.operation = old -> old * old;
                        } else {
                            monkey.operation = old -> old * Long.parseLong(toAdd[1]);
                        }
                        continue;
                    }
                }

                if (line.startsWith("Test")) {
                    monkey.divBy = Long.parseLong(line.substring(19));
                    continue;
                }

                if (line.startsWith("If true")) {
                    monkey.trueNextMonkeyId = Integer.parseInt(line.substring(25));
                    continue;
                }

                if (line.startsWith("If false")) {
                    monkey.falseNextMoneyId = Integer.parseInt(line.substring(26));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return monkeys;
    }

    static class Monkey {
        int id;
        long divBy;
        LinkedList<Long> items = new LinkedList<>();
        Function<Long, Long> operation;
        int trueNextMonkeyId;
        int falseNextMoneyId;
        long rounds = 0L;

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
}
