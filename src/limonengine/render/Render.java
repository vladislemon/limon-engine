package limonengine.render;

import limonengine.events.EventFrameTick;
import limonengine.events.EventHandler;
import limonengine.render.model.ModelLoader;
import limonengine.util.ImageUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * slimon
 * 09.08.2014
 */
public class Render {

    private boolean displayCreated;
    private long frame;
    private int maxFps;
    private boolean vSync;

    public Render(int maxFps, boolean vSync) {
        displayCreated = false;
        this.maxFps = maxFps;
        this.vSync = vSync;
    }

    public void createDisplay() {
        createDisplay(Display.getDesktopDisplayMode());
    }

    public void createDisplay(DisplayMode displayMode) {
        try {
            Display.setIcon(new ByteBuffer[] {ImageUtil.getImageData("res/icon/icon.png")});
            Display.setDisplayMode(displayMode);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Display.setVSyncEnabled(vSync);
    }

    public void initOpenGL(int mode) {
        switch (mode) {
            case MODE_PERSPECTIVE:
                initOpenGLPerspective();
                break;
            case MODE_ORTHOGRAPHIC:
                initOpenGLOrthographic();
                break;
        }
    }

    private void initOpenGLPerspective() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(90, (float) Display.getWidth() / (float) Display.getHeight(), 0.1F, 10000F);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void initOpenGLOrthographic() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glPushMatrix();
        EventHandler.handleEvent(new EventFrameTick(frame++));
        //devRender();
        glPopMatrix();

        Display.update();
        Display.sync(maxFps);
    }

    private void devRender() {
        //glPushMatrix();
        ModelLoader.get("tank.obj").draw();
        //glPopMatrix();
    }

    public void setTitle(String title) {
        Display.setTitle(title);
    }

    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public boolean isDisplayCreated() {
        return displayCreated;
    }

    public static void saveScreenShot(File file, String format) {
        glReadBuffer(GL_FRONT);
        int width = Display.getWidth();
        int height= Display.getHeight();
        int bpp = 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        try {
            ImageIO.write(image, format, file);
            System.out.println("Screenshot saved as " + file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int MODE_PERSPECTIVE = 0;
    public static final int MODE_ORTHOGRAPHIC = 1;
}
