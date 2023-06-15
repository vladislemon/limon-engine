package test.rabbitisland;

import org.lwjgl.opengl.Display;
import test.rabbitisland.animals.Animal;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

/**
 * Created by slimon
 * on 23.10.2014.
 */
public class World2 {
    public static final float rabbitK = 9F/8F;
    float frameSize = .5F;
    int[][] map;

    public World2() {
        map = new int[20][20];
        setWolfMale(10, 10, 255);
        printInt(map[10][10]);
    }

    public void update() {
        
        for(int x=0; x<map.length; x++) {
            for(int y=0; y<map[0].length; y++) {
                if(isRabbit(x, y)) {
                    if(isWolfMale(x, y)) {
                        setRabbit(x, y, false);
                        setWolfMale(x, y, getWolfMale(x, y) + 10);
                    }
                    if(isWolfFemale(x, y)) {
                        setRabbit(x, y, false);
                        setWolfFemale(x, y, getWolfFemale(x, y) + 10);
                    }
                    if(Math.random() < rabbitK) {
                        int dx = (int)Math.round(Math.random() * 2 - 1);
                        int dy = (int)Math.round(Math.random() * 2 - 1);

                        if(!isRabbit(x+dx, y+dy) || isLand(x+dx, y+dy)) {
                            setRabbit(x+dx, y+dy, true);
                            setRabbit(x, y, false);
                        }
                    }
                }
                



            }
        }
    }
    
    public void render() {
        update();
        float cellWidth, cellHeight;
        cellWidth = (float) Display.getWidth()/(float)map.length;
        cellHeight = (float)Display.getHeight()/(float)map[0].length;
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glBindTexture(GL_TEXTURE_2D, 0);
        //glColor3f(0.8F, 0.8F, 0.8F);
        glClearColor(0.8F, 0.8F, 0.8F, 1F);
        for(int i = 0; i < map.length; i++) {
            for(int k = 0; k < map[0].length; k++) {
                int cell = map[i][k];
                glPushMatrix();
                glTranslatef(i * cellWidth, k * cellHeight, 0);
                if(!isLand(i, k)) {
                    glColor3f(0.05F, 0.15F, 0.8F);
                } else {
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
        for(int x=0; x<map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (isRabbit(x, y)) {
                    glBindTexture(GL_TEXTURE_2D, RabbitIsland.TEXTURE_RABBIT);
                } else if (isWolfMale(x, y) || isWolfFemale(x, y)){
                    glBindTexture(GL_TEXTURE_2D, RabbitIsland.TEXTURE_HE_WOLF);
                }
                glPushMatrix();
                glTranslatef(x*cellWidth, y*cellHeight, 0);
                glColor3f(1F, 1F, 1F);
                glBegin(GL_QUADS);
                glTexCoord2f(1, 1);
                glVertex2f(cellWidth - frameSize, cellHeight - frameSize);
                glTexCoord2f(0, 1);
                glVertex2f(frameSize, cellHeight - frameSize);
                glTexCoord2f(0, 0);
                glVertex2f(frameSize, frameSize);
                glTexCoord2f(1, 0);
                glVertex2f(cellWidth - frameSize, frameSize);
                glEnd();
                glPopMatrix();
            }
        }
    }


    public boolean isLand(int x, int y) {
        return getBit(map[x][y], 0);
    }

    public boolean isRabbit(int x, int y) {
        return getBit(map[x][y], 1);
    }

    public void setRabbit(int x, int y, boolean rabbit) {
        map[x][y] = setBit(map[x][y], 1, rabbit);
    }

    public int getWolfMale(int x, int y) {
        return (map[x][y] >> 8) & 0xFF;
    }

    public void setWolfMale(int x, int y, int wolf) {
        map[x][y] = (map[x][y] & (0xFF << 8)) | wolf&0xFF << 8;
    }


    public int getWolfFemale(int x, int y) {
        return (map[x][y] >> 16) & 0xFF;
    }

    public void setWolfFemale(int x, int y, int wolf) {
        map[x][y] = (map[x][y] & (0xFF << 16)) | wolf&0xFF << 16;
    }

    public boolean isWolfMale(int x, int y) {
        return getWolfMale(x, y) > 0;
    }

    public boolean isWolfFemale(int x, int y) {
        return getWolfMale(x, y) > 0;
    }


    public static int setBit(int a, int n, boolean bit) {
        if(bit) {
            return a | (1 << n);
        } else {
            return a & ~(1 << n);
        }
    }

    public static boolean getBit(int a, int n) {
        return (a & (1 << n)) > 0;
    }

    public static void printInt(int integer) {
        for (int i=0; i<32; i++) {
            System.out.print(getBit(integer, i) ? 1:0);
        }
        System.out.println();
    }


}
