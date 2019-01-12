package lahtinen.day6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static lahtinen.Utils.fileToStringArray;

public class Part2 {

    public static void main(String... args) throws Exception {
        List<Point> locations = Arrays.stream(fileToStringArray("input.txt")).map(Point::new).collect(toList());
        int xMin = locations.stream().mapToInt(v -> v.x).min().getAsInt();
        int xMax = locations.stream().mapToInt(v -> v.x).max().getAsInt();
        int yMin = locations.stream().mapToInt(v -> v.y).min().getAsInt();
        int yMax = locations.stream().mapToInt(v -> v.y).max().getAsInt();

        Map<Point /*coordinate*/, Boolean /*within range*/> ownedPoints = new HashMap<>();

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                int x2 = x;
                int y2 = y;
                long total = locations.stream().mapToInt(location -> Math.abs(location.x - x2) + Math.abs(location.y - y2)).sum();
                ownedPoints.put(new Point(x, y), total < 10000);
            }
        }

        ownedPoints.entrySet().stream()
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static void printVisual(Map<Point, Boolean> pointsMap) {
        char[][] points = new char[400][400];

        for (Map.Entry<Point, Boolean> e : pointsMap.entrySet()) {
            Point key = e.getKey();
            points[key.x][key.y] = e.getValue() ? 'T' : 'F';
        }

        for (int i = 47; i <= 359; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 40; j <= 349; j++) {
                builder.append(points[j][i]);
            }
            System.out.println(builder.toString());
        }
    }
}
