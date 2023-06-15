package test.collisiontest;

import limonengine.Engine;
import limonengine.events.Event;
import limonengine.events.EventFrameTick;
import limonengine.events.EventHandler;
import limonengine.events.EventListener;
import limonengine.render.Render;
import limonengine.util.collision.Collision2D;
import limonengine.util.math.vector.Vec2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.ArrayList;

/**
 * ===
 * Created by slimon on 30.07.2015.
 */
public class CollisionGame {

    static Player player;
    static Vec2D[] shape, col, CCol;
    static Vec2D o;
    static double r;

    public static void main(String[] args) {
        init();
        Engine.run();
    }

    private static void init() {
        Engine.initialize(100, 100, Render.MODE_ORTHOGRAPHIC, 1280, 720, true);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluOrtho2D(-Display.getWidth() / 2, Display.getWidth() / 2, -Display.getHeight() / 2, Display.getHeight() / 2);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();


        Vec2D[] playerModel = new Vec2D[] {
                new Vec2D(30, 0),
                new Vec2D(-30, -20),
                new Vec2D(-20, 0),
                new Vec2D(-30, 20)
        };

        player = new Player(new Vec2D(0, 0), 0, playerModel);

        shape = new Vec2D[] {
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500),
                new Vec2D(Math.random()*500, Math.random()*500)
        };

        o = new Vec2D(-50, -50);
        r = 25;

        EventListener eventListener = new EventListener() {

            long count = 0;
            long timeSum = 0;
            @Override
            public void onEvent(Event event) {
                Vec2D[] points = player.getPoints();
                long time = System.nanoTime();
                col = Collision2D.getShapesCollision(points, shape);
                CCol = Collision2D.getShapeWithCircleCollision(points, o, r);
                count++;
                timeSum += System.nanoTime() - time;
                System.out.println(timeSum / count);
                render();
            }

            @Override
            public ArrayList<String> getRequiredEventsName() {
                ArrayList<String> requiredEventsNames = new ArrayList<String>();
                requiredEventsNames.add(EventFrameTick.class.getName());
                return requiredEventsNames;
            }
        };
        EventHandler.register(eventListener);
    }

    private static void render() {
        GL11.glColor3d(1, 0, 0);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        //GL11.glLineWidth(120);
        for(Vec2D point : shape) {
            GL11.glVertex2d(point.x, point.y);
        }
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for(float t = 0; t <= 2*Math.PI; t += 0.05F) {
            GL11.glVertex2d(o.x + r * Math.cos(t), o.y + r * Math.sin(t));
        }
        GL11.glEnd();

        //INTERSECTION POINTS
        GL11.glColor3d(1, 1, 1);
        GL11.glPointSize(4);
        GL11.glBegin(GL11.GL_POINTS);
        for(Vec2D point : col) {
            GL11.glVertex2d(point.x, point.y);
        }
        for(Vec2D point : CCol) {
            GL11.glVertex2d(point.x, point.y);
        }
        GL11.glEnd();
    }
}
