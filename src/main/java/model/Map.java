package model;

import java.util.Arrays;

public class Map {
    private final int NUMBER_OF_TILES = 10;
    private final int TILE_SIZE = 100;

    private final boolean[][] walls;

    public Map() {
        boolean[][] tempWalls = new boolean[NUMBER_OF_TILES][NUMBER_OF_TILES];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tempWalls[i][j] = ((i == 0 || i == 9) || (j == 0 || j == 9) || Math.random() < 0.2);
            }
            tempWalls[4][4] = false;
            tempWalls[4][5] = false;
            tempWalls[5][4] = false;
            tempWalls[5][5] = false;
        }
        walls = tempWalls;
    }

    public int getNUMBER_OF_TILES() {
        return NUMBER_OF_TILES;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public boolean[][] getWalls() {
        return walls;
    }
}
