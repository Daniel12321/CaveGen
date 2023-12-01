public class CellularAutomata {

    public static boolean[][] doSimulationStep(boolean[][] oldMap) {
        boolean[][] newMap = new boolean[oldMap.length][oldMap[0].length];

        for (int x = 0; x < oldMap.length; x++) {
            for (int y = 0; y < oldMap[x].length; y++) {
                int nbs = countAliveNeighbours(oldMap, x, y);

                newMap[x][y] = nbs > 2 || oldMap[x][y];
            }
        }
        return newMap;
    }

    private static int countAliveNeighbours(boolean[][] map, int x, int y) {
        int count = 0;
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                int neighbour_x = x + i;
                int neighbour_y = y + j;

                if (i == 0 && j == 0) {
                    // Do nothing
                } else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length) {
//                    count = count + 1;
                } else if (map[neighbour_x][neighbour_y]) {
                    count = count + 1;
                }
            }
        }
        return count;
    }
}

/*
                if (oldMap[x][y]) {
                    if (nbs < 4) newMap[x][y] = false;
                    else newMap[x][y] = true;
                } else {
                    if (nbs > 5) newMap[x][y] = true;
                }

                if (oldMap[x][y]) {
                    newMap[x][y] = nbs < 2;
                } else {
                    newMap[x][y] = nbs > 2;
                }

                if (!oldMap[x][y]) {
                    newMap[x][y] = nbs > 2;
                } else {
                    newMap[x][y] = true;
                }

                if (oldMap[x][y]) {
                    newMap[x][y] = nbs < DEATH_LIMIT;
                } else {
                    newMap[x][y] = nbs > BIRTH_LIMIT;
                }
 */
