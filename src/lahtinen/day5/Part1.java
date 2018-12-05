package lahtinen.day5;

import lahtinen.Utils;

import static java.util.stream.Collectors.toList;

public class Part1 {
    public static void main(String... args) throws Exception {
        var units = Utils.fileToString("src/lahtinen/day5/input.txt")
                .chars().mapToObj(c -> (byte) c).collect(toList());
        int index = 0;
        while (index < units.size() - 1) {
            byte thisUnit = units.get(index);
            byte nextUnit = units.get(index + 1);
            if (thisUnit == nextUnit) {
                index++;
            } else if ((thisUnit & 0b11111) == (nextUnit & 0b11111)) {
                units.remove(index);
                units.remove(index);
                if (index != 0) {
                    index--;
                }
            } else {
                index++;
            }
        }
        System.out.println("Size after reaction: " + units.size());
    }
}
