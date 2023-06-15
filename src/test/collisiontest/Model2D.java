package test.collisiontest;

import limonengine.util.math.MathUtil;
import limonengine.util.math.vector.Vec2D;
import org.lwjgl.opengl.GL11;

/**
 * ===
 * Created by slimon on 30.07.2015.
 */
public class Model2D {

    private Vec2D[] points, rotPoints;
    private double rot;

    public Model2D(Vec2D... points) {
        this.points = points;
        rotPoints = new Vec2D[points.length];
        for(int i = 0; i < points.length; i++) {
            rotPoints[i] = new Vec2D();
        }
    }

    public void setRotation(double rot) {
        this.rot = rot;
        refreshRotPoints();
    }

    public Vec2D[] getRotatedPoints() {
        return rotPoints;
    }

    public void refreshRotPoints() {
        Vec2D p;
        double[] rotP;
        for(int i = 0; i < points.length; i++) {
            p = points[i];
            rotP = MathUtil.getRotatedPos(p.x, p.y, rot);
            rotPoints[i].set(rotP[0], rotP[1]);
        }
    }

    public void render() {
        GL11.glColor3d(0, 1, 0);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for(Vec2D point : rotPoints) {
            GL11.glVertex2d(point.x, point.y);
        }
        GL11.glEnd();
    }
}
