package test.collisiontest;

import limonengine.Engine;
import limonengine.events.Event;
import limonengine.events.EventFrameTick;
import limonengine.events.EventHandler;
import limonengine.events.EventListener;
import limonengine.render.Render;
import limonengine.util.collision.AABB2D;
import limonengine.util.collision.Collision2D;
import limonengine.util.math.vector.Vec2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by slimon
 * on 23.01.2015.
 */
public class CollisionTest {

    public static void main(String[] args) {
        Engine.initialize(100, 100, Render.MODE_ORTHOGRAPHIC, 800, 600, true);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluOrtho2D(-Display.getWidth()/2, Display.getWidth()/2, -Display.getHeight()/2, Display.getHeight()/2);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        //GL11.glTranslatef(250F, 250F, 0);

        final Vec2D[] obj1 = new Vec2D[] {
                new Vec2D(2, 2),
                new Vec2D(2, 4),
                new Vec2D(4.5, 4.5),
                new Vec2D(4, 2)
        };
        final Vec2D[] obj2 = new Vec2D[] {
                new Vec2D(1, 1),
                new Vec2D(3, 5),
                new Vec2D(3, 3),
                new Vec2D(4, 3)
        };
        final AABB2D aabb1 = new AABB2D(obj1);
        final AABB2D aabb2 = new AABB2D(obj2);

        final Vec2D l1p1 = new Vec2D(5, 5);
        final Vec2D l1p2 = new Vec2D(5, 7);
        final Vec2D l2p1 = new Vec2D(4, 6);
        final Vec2D l2p2 = new Vec2D(6, 6.5);
        final Vec2D l3p1 = new Vec2D(4, 5.5);
        final Vec2D l3p2 = new Vec2D(6, 5.5);

        System.out.println(Collision2D.checkAABBCollision(aabb1, aabb2));
        final Vec2D[] aabbCol = Collision2D.getAABBCollision(aabb1, aabb2);
        final Vec2D[] col = Collision2D.getShapesCollision(obj1, obj2);

        final Vec2D lineCol1 = Collision2D.getLineSegmentsCollisionPoint(l1p1, l1p2, l2p1, l2p2);
        final Vec2D lineCol2 = Collision2D.getLineSegmentsCollisionPoint(l1p1, l1p2, l3p1, l3p2);
        final Vec2D lineCol3 = Collision2D.getLineSegmentsCollisionPoint(l3p1, l3p2, l2p1, l2p2);

        final Vec2D lineP1 = new Vec2D(-7, -5);
        final Vec2D lineP2 = new Vec2D(-1, -5);
        final Vec2D pO = new Vec2D(-3, -4);
        final double r = 1;
        final Vec2D[] collideCL = Collision2D.getCircleWithLineSegmentCollision(pO, r, lineP1, lineP2);
        for(Vec2D p : collideCL) {
            System.out.println("CL:  " + p.x + "   " + p.y);
        }
        final Vec2D pO2 = new Vec2D(-4, -4);
        final double r2 = 1;
        final Vec2D[] collideCC = Collision2D.getCirclesCollision(pO, r, pO2, r2);
        for(Vec2D p : collideCC) {
            System.out.println("CC:  " + p.x + "   " + p.y);
        }

        final float zoom = 40F;

        EventListener eventListener = new EventListener() {

            private ArrayList<String> requiredEventsName = new ArrayList<String>(Collections.singletonList(EventFrameTick.class.getName()));

            @Override
            public void onEvent(Event event) {
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor3f(1F, 1F, 1F);
                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex2f(-Display.getWidth() / 2, 0);
                GL11.glVertex2f(Display.getWidth() / 2, 0);
                GL11.glVertex2f(0, -Display.getHeight()/2);
                GL11.glVertex2f(0, Display.getHeight()/2);
                GL11.glEnd();

                GL11.glScalef(zoom, zoom, 0);
                aabb1.draw();
                aabb2.draw();
                GL11.glColor3f(1F, 0F, 0F);
                GL11.glBegin(GL11.GL_LINE_LOOP);
                for(Vec2D p : obj1) {
                    GL11.glVertex2d(p.x, p.y);
                }
                GL11.glEnd();
                GL11.glColor3f(0F, 1F, 0F);
                GL11.glBegin(GL11.GL_LINE_LOOP);
                for(Vec2D p : obj2) {
                    GL11.glVertex2d(p.x, p.y);
                }
                GL11.glEnd();

                GL11.glColor3f(0F, 1F, 1F);
                GL11.glBegin(GL11.GL_LINE_LOOP);
                for(float t = 0; t < 2*Math.PI; t += 0.05F) {
                    GL11.glVertex2d(pO.x + r * Math.cos(t), pO.y + r * Math.sin(t));
                }
                GL11.glEnd();

                GL11.glBegin(GL11.GL_LINE_LOOP);
                for(float t = 0; t < 2*Math.PI; t += 0.05F) {
                    GL11.glVertex2d(pO2.x + r2 * Math.cos(t), pO2.y + r2 * Math.sin(t));
                }
                GL11.glEnd();

                GL11.glColor3f(1F, 1F, 1F);
                GL11.glPointSize(5F);
                GL11.glBegin(GL11.GL_POINTS);
                for(Vec2D p : col) {
                    GL11.glVertex2d(p.x, p.y);
                }
                for(Vec2D p : collideCL) {
                    GL11.glVertex2d(p.x, p.y);
                }
                for(Vec2D p : collideCC) {
                    GL11.glVertex2d(p.x, p.y);
                }
                GL11.glColor3f(1, 0, 0);
                for(Vec2D p : aabbCol) {
                    GL11.glVertex2d(p.x, p.y);
                }
                GL11.glEnd();
                GL11.glColor3f(1F, 1F, 0F);
                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex2d(l1p1.x, l1p1.y);
                GL11.glVertex2d(l1p2.x, l1p2.y);
                GL11.glVertex2d(l2p1.x, l2p1.y);
                GL11.glVertex2d(l2p2.x, l2p2.y);
                GL11.glVertex2d(l3p1.x, l3p1.y);
                GL11.glVertex2d(l3p2.x, l3p2.y);
                GL11.glVertex2d(lineP1.x, lineP1.y);
                GL11.glVertex2d(lineP2.x, lineP2.y);
                GL11.glEnd();
                GL11.glColor3f(0F, 0F, 1F);
                GL11.glBegin(GL11.GL_POINTS);
                if(lineCol1 != null) GL11.glVertex2d(lineCol1.x, lineCol1.y);
                if(lineCol2 != null) GL11.glVertex2d(lineCol2.x, lineCol2.y);
                if(lineCol3 != null) GL11.glVertex2d(lineCol3.x, lineCol3.y);
                GL11.glEnd();
            }

            @Override
            public ArrayList<String> getRequiredEventsName() {
                return requiredEventsName;
            }
        };
        EventHandler.register(eventListener);

        Engine.run();
    }
}
