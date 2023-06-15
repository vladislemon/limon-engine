package test.rabbitisland;

import limonengine.events.Event;
import limonengine.events.EventListener;
import limonengine.events.EventTimerTick;
import org.lwjgl.opengl.Display;
import test.rabbitisland.animals.Animal;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by slimon
 * on 20.10.2014.
 */
public class World {

    public float frameSize = 0.5F;
    public float cellWidth, cellHeight;
    private float anim;

    private int[][] cells;
    private ArrayList<Animal> animals;

    public World(int width, int height) {
        cells = new int[width][height];
        fillCells(CELL_WATER);
        animals = new ArrayList<Animal>();
        cellWidth = (float)Display.getWidth()/(float)cells.length;
        cellHeight = (float)Display.getHeight()/(float)cells[0].length;
    }

    public void tick() {
        if(isAnimation()) return;
        Animal animal;
        for(int k = 0; k < animals.size(); k++) {
            animal = animals.get(k);
            if(animal != null) {
                animal.tick(this);
            }
        }
    }

    public void moveCalc() {
        if(isAnimation()) return;
        for(Animal animal : animals) {
            animal.moveCalc(this);
        }
    }

    public void moveAnimation() {
        if(anim == 0) {
            anim += 0.01F;
            for(Animal animal : animals) {
                animal.moveAnimation(this, anim);
            }
        }
        anim += 0.01F;
        for(Animal animal : animals) {
            animal.moveAnimation(this, anim);
        }
        if(anim >= 1F) {
            anim = 0;
        }
    }

    public boolean isAnimation() {
        return anim > 0;
    }

    public ArrayList<Animal> getAnimals(int x, int y) {
        ArrayList<Animal> list = new ArrayList<Animal>();
        for(Animal animal : animals) {
            if(x == animal.cellX && y == animal.cellY) {
                list.add(animal);
            }
        }
        return list;
    }

    public ArrayList<Animal> getAnimalsAround(int x, int y) {
        ArrayList<Animal> list = new ArrayList<Animal>();
        for(Animal animal : animals) {
            if(!(x == animal.cellX && y == animal.cellY) && Math.abs(x - animal.cellX) < 2 && Math.abs(y - animal.cellY) < 2) {
                list.add(animal);
            }
        }
        return list;
    }

    public void render() {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glBindTexture(GL_TEXTURE_2D, 0);
        glClearColor(0.8F, 0.8F, 0.8F, 1F);
        for(int i = 0; i < cells.length; i++) {
            for(int k = 0; k < cells[0].length; k++) {
                int cell = cells[i][k];
                glPushMatrix();
                glTranslatef(i * cellWidth, k * cellHeight, 0);
                if(cell == CELL_WATER) {
                    glColor3f(0.05F, 0.15F, 0.8F);
                } else if(cell == CELL_GROUND) {
                    glColor3f(0.05F, 0.6F, 0.1F);
                }
                glBegin(GL_QUADS);
                glTexCoord2f(1, 1); glVertex2f(cellWidth - frameSize, cellHeight - frameSize);
                glTexCoord2f(0, 1); glVertex2f(frameSize, cellHeight - frameSize);
                glTexCoord2f(0, 0); glVertex2f(frameSize, frameSize);
                glTexCoord2f(1, 0); glVertex2f(cellWidth - frameSize, frameSize);
                glEnd();
                glPopMatrix();
            }
        }

        for(Animal animal : animals) {
            glBindTexture(GL_TEXTURE_2D, animal.texture);
            glPushMatrix();
            glTranslatef(animal.x, animal.y, 0);
            glColor4f(1F, 1F, 1F, 1F);
            glBegin(GL_QUADS);
            glVertex2f(cellWidth - frameSize, cellHeight - frameSize);
            glVertex2f(frameSize, cellHeight - frameSize);
            glVertex2f(frameSize, frameSize);
            glVertex2f(cellWidth - frameSize, frameSize);
            glEnd();
            glColor3f(1F, 1F, 1F);
            glBegin(GL_QUADS);
            glTexCoord2f(1, 1); glVertex2f(cellWidth - frameSize, cellHeight - frameSize);
            glTexCoord2f(0, 1); glVertex2f(frameSize, cellHeight - frameSize);
            glTexCoord2f(0, 0); glVertex2f(frameSize, frameSize);
            glTexCoord2f(1, 0); glVertex2f(cellWidth - frameSize, frameSize);
            glEnd();
            glPopMatrix();
        }
    }

    public void addAnimal(Animal animal) {
        animal.x = (float)animal.cellX * (float)Display.getWidth()/(float)cells.length;
        animal.y = (float)animal.cellY * (float)Display.getHeight()/(float)cells[0].length;
        animal.dCellX = animal.cellX;
        animal.dCellY = animal.cellY;
        animals.add(animal);
    }

    /*public Animal getAnimal(int cellX, int cellY) {

    }*/

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void getCellsAround(int x, int y) {

    }

    public void setCell(int x, int y, int cellType) {
        cells[x][y] = cellType;
    }

    public int getCell(int x, int y) {
        if(x >=0 && x < cells.length && y >= 0 && y < cells[0].length) {
            return cells[x][y];
        }
        return 0;
    }

    public void fillCells(int startX, int startY, int endX, int endY, int cellType) {
        for(int i = startX; i < endX; i++) {
            for(int k = startY; k < endY; k++) {
                cells[i][k] = cellType;
            }
        }
    }

    public void fillCells(int cellType) {
        fillCells(0, 0, cells.length, cells[0].length, cellType);
    }

    private EventListener eventListener = new EventListener() {
        private ArrayList<String> requiredEventsName = new ArrayList<String>(Arrays.asList(EventTimerTick.class.getName()));

        @Override
        public void onEvent(Event event) {
            moveAnimation();
        }

        @Override
        public ArrayList<String> getRequiredEventsName() {
            return requiredEventsName;
        }
    };

    public static int CELL_WATER = 0;
    public static int CELL_GROUND = 1;
}
