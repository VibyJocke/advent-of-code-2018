package lahtinen.day5;

import lahtinen.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Character.toLowerCase;
import static java.util.stream.Collectors.toList;

public class Part2 {
    private static final List<Character> CHARS = Arrays.asList('a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'i', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    public static void main(String... args) throws Exception {
        var originalUnits = Utils.fileToString("src/lahtinen/day5/input.txt")
                .chars().mapToObj(c -> (char) c).collect(toList());
        react(originalUnits);
        var times = new ArrayList<Integer>();
        for (var unitToRemove : CHARS) {
            var units = originalUnits.stream()
                    .filter(c -> !(toLowerCase(c) == unitToRemove))
                    .collect(toList());
            react(units);
            times.add(units.size());
        }
        Collections.sort(times);
        System.out.println("Smallest polymer: " + times.get(0));
    }

    private static void react(List<Character> units) {
        int index = 0;
        while (index < units.size() - 1) {
            char thisUnit = units.get(index);
            char nextUnit = units.get(index + 1);
            if (thisUnit == nextUnit) {
                index++;
            } else if (toLowerCase(thisUnit) == toLowerCase(nextUnit)) {
                units.remove(index);
                units.remove(index);
                if (index != 0) {
                    index--;
                }
            } else {
                index++;
            }
        }
    }
}
