package lahtinen.day11;

public class Part1 {
    private static final int GRID_SERIAL_NUMBER = 7511;
    private static final int GRID_SIZE = 300;

    public static void main(String... args) {
        int max = 0;
        int x = 0;
        int y = 0;
        for (int i = 1; i <= GRID_SIZE - 2; i++) {
            for (int j = 1; j <= GRID_SIZE - 2; j++) {
                int result = getSquarePowerLevel(i, j);
                if (max < result) {
                    max = result;
                    x = i;
                    y = j;
                }
            }
        }
        System.out.println("power level " + max + " at " + x + "," + y);
    }

    private static int getSquarePowerLevel(int x, int y) {
        var result = 0;
        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                result += getPowerLevel(i, j);
            }
        }
        return result;
    }

    private static int getPowerLevel(int x, int y) {
        var rackId = x + 10;
        var powerLevel = rackId * y;
        powerLevel += GRID_SERIAL_NUMBER;
        powerLevel = powerLevel * rackId;
        var powerLevelAsString = String.valueOf(powerLevel);
        if (powerLevelAsString.length() <= 2) {
            powerLevel = 0;
        } else {
            powerLevel = Integer.parseInt(Character.toString(powerLevelAsString.charAt(powerLevelAsString.length() - 3)));
        }
        powerLevel -= 5;
        return powerLevel;
    }

}
