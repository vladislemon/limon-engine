package test.rabbitisland.animals;

import test.rabbitisland.RabbitIsland;
import test.rabbitisland.World;

import java.util.ArrayList;

/**
 * Created by slimon
 * on 21.10.2014.
 */
public class SheWolf extends Animal {

    public SheWolf(int cellX, int cellY, int maxHp, float multiplicationFactor) {
        super(cellX, cellY, maxHp, multiplicationFactor);
        id = ID_SHE_WOLF;
        texture = RabbitIsland.TEXTURE_SHE_WOLF;
        hp = 10;
    }

    public void tick(World world) {
        super.tick(world);
        ArrayList<Animal> animals = world.getAnimals(cellX, cellY);
        for(Animal animal : animals) {
            if(animal instanceof Rabbit) {
                world.removeAnimal(animal);
                hp += (10 - Math.min(maxHp - hp, 10));
                return;
            }
        }
        hp--;
        if(hp <= 0) {
            world.removeAnimal(this);
        }
    }

    public void moveCalc(World world) {
        ArrayList<Animal> animals = world.getAnimalsAround(cellX, cellY);
        for(Animal animal : animals) {
            if(animal instanceof Rabbit) {
                dCellX = animal.cellX;
                dCellY = animal.cellY;
                return;
            }
        }

        int direction = (int)Math.round(Math.random() * 7);
        int dx, dy;
        if(direction < 4) {
            dx = 1 - direction / 3;
            dy = direction % 3 - 1;
        } else {
            direction++;
            dx = 1 - direction / 3;
            dy = direction % 3 - 1;
        }
        dCellX = cellX + dx;
        dCellY = cellY + dy;
        super.moveCalc(world);
    }
}
