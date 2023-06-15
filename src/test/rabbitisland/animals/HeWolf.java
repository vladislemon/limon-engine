package test.rabbitisland.animals;

import test.rabbitisland.RabbitIsland;
import test.rabbitisland.World;

import java.util.ArrayList;

/**
 * Created by slimon
 * on 21.10.2014.
 */
public class HeWolf extends Animal {

    public HeWolf(int cellX, int cellY, int maxHp, float multiplicationFactor) {
        super(cellX, cellY, maxHp, multiplicationFactor);
        id = ID_HE_WOLF;
        texture = RabbitIsland.TEXTURE_HE_WOLF;
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

        for(Animal animal : animals) {
            if(animal instanceof SheWolf) {
                if(random(multiplicationFactor)) {
                    if (random(0.5F)) {
                        world.addAnimal(new SheWolf(cellX, cellY, maxHp, multiplicationFactor));
                    } else {
                        world.addAnimal(new HeWolf(cellX, cellY, maxHp, multiplicationFactor));
                    }
                }
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

        for(Animal animal : animals) {
            if(animal instanceof SheWolf) {
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
        System.out.println(dCellX + " " + dCellY);
        super.moveCalc(world);
    }
}
