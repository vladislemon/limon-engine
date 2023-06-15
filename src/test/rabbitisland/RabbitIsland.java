package test.rabbitisland;

import limonengine.Engine;
import limonengine.events.*;
import limonengine.render.Render;
import limonengine.util.TimeUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import test.rabbitisland.animals.Animal;
import test.rabbitisland.animals.HeWolf;
import test.rabbitisland.animals.SheWolf;
import test.rabbitisland.animals.Rabbit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by slimon
 * on 20.10.2014.
 */
public class RabbitIsland {

    private static World world;

    public static int TEXTURE_RABBIT;
    public static int TEXTURE_SHE_WOLF;
    public static int TEXTURE_HE_WOLF;

    public static void main(String[] args) {
        Engine.setTitle("Rabbit Island");
        Engine.initialize(1, 100, Render.MODE_ORTHOGRAPHIC, 650, 650, true);

        TEXTURE_RABBIT = loadTexture("res/textures/rabbit.png");
        TEXTURE_SHE_WOLF = loadTexture("res/textures/she_wolf.png");
        TEXTURE_HE_WOLF = loadTexture("res/textures/he_wolf.png");

        world = new World(21, 21);
        world.fillCells(1, 1, 20, 20, World.CELL_GROUND);
        world.addAnimal(new Rabbit(getRand(1, 19), getRand(1, 19), 1, 0.2F));
        world.addAnimal(new Rabbit(getRand(1, 19), getRand(1, 19), 1, 0.2F));
        world.addAnimal(new Rabbit(15, 15, 1, 0.2F));
        world.addAnimal(new HeWolf(5, 5, 100, 1F));
        world.addAnimal(new SheWolf(getRand(1, 19), getRand(1, 19), 100, 1F));



        EventHandler.register(eventListener);

        Engine.run();
    }

    private static int getRand(int min, int max) {
        return min + (int)Math.round(Math.random() * (max - min));
    }

    private static EventListener eventListener = new EventListener() {
        private ArrayList<String> requiredEventsName = new ArrayList<String>(Arrays.asList(
                EventFrameTick.class.getName(), EventInputKeyboard.class.getName()));

        @Override
        public void onEvent(Event event) {
            if(event.getName().equals(EventTimerTick.class.getName())) {

            } else if(event.getName().equals(EventFrameTick.class.getName())) {
                world.render();
                world.tick();
                world.moveCalc();
                world.moveAnimation();
            } else if(event.getName().equals(EventInputKeyboard.class.getName())) {
                if(((EventInputKeyboard)event).getEventKey() == Keyboard.KEY_F2 &&
                        ((EventInputKeyboard)event).getEventKeyState()) {
                    File file = new File("screenshots/" + TimeUtil.getTime("dd^MM^YY()hh^mm^ss") + ".png");
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Render.saveScreenShot(file, "PNG");
                }
            }
        }

        @Override
        public ArrayList<String> getRequiredEventsName() {
            return requiredEventsName;
        }
    };

    public static int loadTexture(String path) {
        File file = new File(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();


        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 8);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return textureId;
    }
}
