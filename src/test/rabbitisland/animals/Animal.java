package test.rabbitisland.animals;

import test.rabbitisland.RabbitIsland;
import test.rabbitisland.World;

/**
 * Created by slimon
 * on 21.10.2014.
 */
public class Animal {

    public float x, y;
    public int cellX, cellY, dCellX, dCellY;
    public int id, texture;
    public int hp, maxHp;
    public float multiplicationFactor;

    public Animal(int cellX, int cellY, int maxHp, float multiplicationFactor) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.maxHp = hp = maxHp;
        this.multiplicationFactor = multiplicationFactor;
    }

    public void tick(World world) {
        cellX = dCellX;
        cellY = dCellY;
    }

    public void moveCalc(World world) {
        if(world.getCell(dCellX, dCellY) != World.CELL_GROUND) {
            moveCalc(world);
        }
    }

    public void moveAnimation(World world, float f) {
        x = cellX * world.cellWidth + (dCellX - cellX) * world.cellWidth * f;
        y = cellY * world.cellHeight + (dCellY - cellY) * world.cellHeight * f;
    }

    protected boolean random(float chance) {
        return Math.random() <= chance;
    }

    public static int ID_RABBIT = 0;
    public static int ID_SHE_WOLF = 1;
    public static int ID_HE_WOLF = 2;
}
