package lahtinen.day11;

public class Part2 {
    private static final int GRID_SERIAL_NUMBER = 7511;
    private static final int GRID_SIZE = 300;

    //TODO: can speed up using thread striping or caching of already calculated blocks.
    public static void main(String... args) {
        int max = 0;
        int x = 0;
        int y = 0;
        for (int size = 1; size <= 300; size++) {
            for (int i = 1; i <= GRID_SIZE - (size - 1); i++) {
                for (int j = 1; j <= GRID_SIZE - (size - 1); j++) {
                    int result = getSquarePowerLevel(i, j, size);
                    if (max < result) {
                        max = result;
                        x = i;
                        y = j;
                    }
                }
            }
            System.out.println("current max power level " + max + " at " + x + "," + y + " size " + size);
        }
        System.out.println("max power level " + max + " at " + x + "," + y);
    }

    // 235,288 size 13

    private static int getSquarePowerLevel(int x, int y, int size) {
        var result = 0;
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
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
