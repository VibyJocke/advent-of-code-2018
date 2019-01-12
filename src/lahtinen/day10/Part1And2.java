package lahtinen.day10;

import lahtinen.Utils;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Part1And2 {
    public static void main(String... args) throws Exception {
        var originalLights = Stream.of(Utils.fileToStringArray("src/lahtinen/day10/input.txt"))
                .map(Light::new)
                .collect(toList());
        var lights = Stream.of(Utils.fileToStringArray("src/lahtinen/day10/input.txt"))
                .map(Light::new)
                .collect(toList());

        System.out.println("Found " + lights.size() + " lights");

        int iteration = findConverganceIteration(lights);
        System.out.println("Seconds: " + iteration);
        printMessage(originalLights, iteration);
        // GFANEHKJ
    }

    private static void printMessage(List<Light> lights, int numIterations) {
        for (int i = 0; i < numIterations; i++) {
            lights.forEach(Light::move);
        }
        printLights(lights);
    }

    //TODO: this could be removed if we stop at the last iteration!
    private static void printLights(List<Light> lights) {
        int xMin = lights.stream().mapToInt(Light::x).min().getAsInt();
        int xMax = lights.stream().mapToInt(Light::x).max().getAsInt();
        int yMin = lights.stream().mapToInt(Light::y).min().getAsInt();
        int yMax = lights.stream().mapToInt(Light::y).max().getAsInt();

        int x = Math.abs(xMin - xMax);
        int y = Math.abs(yMin - yMax);

        boolean[][] lightMap = new boolean[x + 1][y + 1];

        for (Light light : lights) {
            lightMap[light.positionX - xMin][light.positionY - yMin] = true;
        }

        for (int i = 0; i <= xMax - xMin; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j <= yMax - yMin; j++) {
                if (lightMap[i][j]) {
                    builder.append('#');
                } else {
                    builder.append('.');
                }
            }
            System.out.println(builder.toString());
        }
    }

    private static int findConverganceIteration(List<Light> lights) {
        int smallestXBoundary = Integer.MAX_VALUE;
        int smallestYBoundary = Integer.MAX_VALUE;
        int iteration = 0;
        int smallestIteration = 0;
        while (true) {
            iteration++;
            lights.forEach(Light::move);
            int xMin = lights.stream().mapToInt(Light::x).min().getAsInt();
            int xMax = lights.stream().mapToInt(Light::x).max().getAsInt();
            int yMin = lights.stream().mapToInt(Light::y).min().getAsInt();
            int yMax = lights.stream().mapToInt(Light::y).max().getAsInt();
            int xBound = Math.abs(xMin - xMax);
            int yBound = Math.abs(yMin - yMax);
            if (xBound < smallestXBoundary || yBound < smallestYBoundary) {
                smallestXBoundary = xBound;
                smallestYBoundary = yBound;
                smallestIteration = iteration;
            } else {
                break;
            }
            //TODO: replace this with a simple area measure
        }
        return smallestIteration;
    }

    private static class Light {
        private final int velocityX, velocityY;
        private int positionX, positionY;

        Light(String input) {
            String[] split = input.replace("position=<", "").replace("velocity=<", "")
                    .replace(">", "").replace(",", "").split(" ");
            List<Integer> collect = Stream.of(split)
                    .filter(str -> !str.isEmpty())
                    .map(Integer::parseInt).collect(toList());
            this.positionX = collect.get(0);
            this.positionY = collect.get(1);
            this.velocityX = collect.get(2);
            this.velocityY = collect.get(3);
        }

        int x() {
            return positionX;
        }

        int y() {
            return positionY;
        }

        void move() {
            positionX += velocityX;
            positionY += velocityY;
        }
    }
}
