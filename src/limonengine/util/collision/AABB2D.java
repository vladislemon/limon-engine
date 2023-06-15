package limonengine.util.collision;

import limonengine.util.math.vector.Vec2D;
import org.lwjgl.opengl.GL11;

/**
 * Created by slimon
 * on 11.01.2015.
 */
public class AABB2D {

    public Vec2D pMin, pMax;

    public AABB2D(Vec2D pMin, Vec2D pMax) {
        update(pMin, pMax);
    }

    public AABB2D(Vec2D[] points) {
        update(points);
    }

    public void update(Vec2D pMin, Vec2D pMax) {
        this.pMin = pMin;
        this.pMax = pMax;
    }

    public void update(Vec2D[] points) {
        double minX = points[0].x;
        double maxX = points[0].x;
        double minY = points[0].y;
        double maxY = points[0].y;
        for(Vec2D point : points) {
            minX = Math.min(point.x, minX);
            maxX = Math.max(point.x, maxX);
            minY = Math.min(point.y, minY);
            maxY = Math.max(point.y, maxY);
        }
        update(new Vec2D(minX, minY), new Vec2D(maxX, maxY));
    }

    public void draw() {
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2d(pMax.x, pMax.y);
        GL11.glVertex2d(pMin.x, pMax.y);
        GL11.glVertex2d(pMin.x, pMin.y);
        GL11.glVertex2d(pMax.x, pMin.y);
        GL11.glEnd();
    }
}
