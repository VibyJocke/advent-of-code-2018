package lahtinen.day6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static lahtinen.Utils.fileToStringArray;

public class Part1 {

    public static void main(String... args) throws Exception {
        List<Point> locations = Arrays.stream(fileToStringArray("input.txt")).map(Point::new).collect(toList());
        int xMin = locations.stream().mapToInt(v -> v.x).min().getAsInt();
        int xMax = locations.stream().mapToInt(v -> v.x).max().getAsInt();
        int yMin = locations.stream().mapToInt(v -> v.y).min().getAsInt();
        int yMax = locations.stream().mapToInt(v -> v.y).max().getAsInt();

        Map<Point /*coordinate*/, Point /*closest location*/> ownedPoints = new HashMap<>();

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                int closestDistance = Integer.MAX_VALUE;
                Point closestLocation = null;
                for (Point location : locations) {
                    int manhattanDistance = Math.abs(location.x - x) + Math.abs(location.y - y);
                    if (manhattanDistance < closestDistance) {
                        closestLocation = location;
                        closestDistance = manhattanDistance;
                    } else if (manhattanDistance == closestDistance) {
                        closestLocation = null;
                    }
                }
                ownedPoints.put(new Point(x, y), closestLocation);
            }
        }

        //Find all owners of infinite areas i.e. those that own a point on the boundary border
        Set<Point> ownersToDisregard = ownedPoints.entrySet().stream()
                .filter(e -> {
                    Point coordinate = e.getKey();
                    return coordinate.x == xMin || coordinate.x == xMax || coordinate.y == yMin || coordinate.y == yMax;
                })
                .map(Map.Entry::getValue)
                .collect(toSet());

        ownedPoints.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .filter(e -> !ownersToDisregard.contains(e.getValue()))
                .collect(groupingBy(Map.Entry::getValue))
                .entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue().size()))
                .ifPresent(e -> System.out.println("Largest area: " + e.getKey() + " " + e.getValue().size()));
    }

    static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(String coordinates) {
            String[] parsed = coordinates.split(", ");
            this.x = Integer.parseInt(parsed[0]);
            this.y = Integer.parseInt(parsed[1]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static void printVisual(Map<Point, Point> pointsMap) {
        char[][] points = new char[400][400];
        char currentSymbol = 0x41;
        HashMap<Point, Character> characterMapping = new HashMap<>();
        for (Point location : pointsMap.values()) {
            if (!characterMapping.containsKey(location)) {
                characterMapping.put(location, currentSymbol++);
            }
        }

        for (Map.Entry<Point, Point> e : pointsMap.entrySet()) {
            Point key = e.getKey();
            points[key.x][key.y] = characterMapping.get(e.getValue());
        }

        for (int i = 1; i <= 9; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 1; j <= 8; j++) {
                char c = points[j][i];
                if (c == 0) {
                    builder.append(' ');
                } else {
                    builder.append(c);
                }
            }
            System.out.println(builder.toString());
        }
    }
}
