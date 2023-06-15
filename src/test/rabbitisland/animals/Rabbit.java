package test.rabbitisland.animals;

import test.rabbitisland.RabbitIsland;
import test.rabbitisland.World;

/**
 * Created by slimon
 * on 21.10.2014.
 */
public class Rabbit extends Animal {

    public Rabbit(int cellX, int cellY, int maxHp, float multiplicationFactor) {
        super(cellX, cellY, maxHp, multiplicationFactor);
        id = ID_RABBIT;
        texture = RabbitIsland.TEXTURE_RABBIT;
    }

    public void tick(World world) {
        super.tick(world);
        if(random(multiplicationFactor)) {
            world.addAnimal(new Rabbit(cellX, cellY, maxHp, multiplicationFactor));
        }
    }

    public void moveCalc(World world) {
        int direction = (int)Math.round(Math.random() * 8);
        int dx = 1 - direction / 3;
        int dy = direction % 3 - 1;
        dCellX = cellX + dx;
        dCellY = cellY + dy;
        super.moveCalc(world);
    }
}
