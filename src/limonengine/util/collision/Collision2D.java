package limonengine.util.collision;

import limonengine.util.math.MathUtil;
import limonengine.util.math.vector.Vec2D;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by slimon
 * on 11.01.2015.
 */
public class Collision2D {

    public static final double LIMIT_VALUE = 10000F;
    public static final double ERROR_VALUE = 0.001F;

    public static boolean checkAABBCollision(AABB2D aabb1, AABB2D aabb2) {
        return aabb1.pMin.x <= aabb2.pMax.x &&
                aabb1.pMax.x >= aabb2.pMin.x &&
                aabb1.pMin.y <= aabb2.pMax.y &&
                aabb1.pMax.y >= aabb2.pMin.y;
    }

    public static boolean checkAABBInside(AABB2D aabb1, AABB2D aabb2) {
        return aabb1.pMin.x > aabb2.pMin.x &&
                aabb1.pMax.x < aabb2.pMax.x &&
                aabb1.pMin.y > aabb2.pMin.y &&
                aabb1.pMax.y < aabb2.pMax.y;
    }

    public static boolean checkAABBInside(Vec2D[] points) {
        return points[0].x > points[4].x &&
                points[2].x < points[6].x &&
                points[0].y > points[4].y &&
                points[2].y < points[6].y;
    }

    public static Vec2D[] getAABBCollision(Vec2D[] points) {
        Vec2D p = new Vec2D();
        Vec2D lp1 = new Vec2D();
        Vec2D lp2 = new Vec2D();
        ArrayList<Vec2D> ret = new ArrayList<Vec2D>();
        if(checkAABBInside(points)) {
            ret.add(new Vec2D((points[0].x + points[2].x)/2, (points[0].y + points[2].y)/2));
            ret.add(new Vec2D((points[4].x + points[6].x)/2, (points[4].y + points[6].y)/2));
            return ret.toArray(new Vec2D[ret.size()]);
        }
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 2; k++) {
                int i2 = i + 1;
                if(i == 3) i2 = 0;
                int n, m, p1, p2;
                if(i % 2 == 0) {
                    if(k == 0) {
                        n = i; m = i2 + 4;
                        p1 = m; p2 = m + 1;
                    } else {
                        n = i; m = i + 4;
                        p1 = m; p2 = m - 1;
                    }
                } else {
                    if(k == 0) {
                        n = i2 + 4; m = i;
                        p1 = n; p2 = n + 1;
                    } else {
                        n = i + 4; m = i;
                        p1 = n; p2 = n - 1;
                    }
                }
                if(p2 == -1) p2 = 7;
                p.set(points[n].x, points[m].y);
                lp1.set(points[i].x, points[i].y);
                lp2.set(points[i2].x, points[i2].y);
                if (checkPointAtAABBLineSegment(p, lp1, lp2)) {
                    lp1.set(points[p1].x, points[p1].y);
                    lp2.set(points[p2].x, points[p2].y);
                    if(checkPointAtAABBLineSegment(p, lp1, lp2))
                        ret.add(p.copy());
                }
            }
        }
        return ret.toArray(new Vec2D[ret.size()]);
    }

    public static Vec2D[] getAABBCollision(AABB2D aabb1, AABB2D aabb2) {
        Vec2D[] points = new Vec2D[] {
                aabb1.pMin,
                new Vec2D(aabb1.pMin.x, aabb1.pMax.y),
                aabb1.pMax,
                new Vec2D(aabb1.pMax.x, aabb1.pMin.y),
                aabb2.pMin,
                new Vec2D(aabb2.pMin.x, aabb2.pMax.y),
                aabb2.pMax,
                new Vec2D(aabb2.pMax.x, aabb2.pMin.y)
        };
        return getAABBCollision(points);
    }

    public static Vec2D[] getShapesCollision(Vec2D[] obj1, Vec2D[] obj2) {
        ArrayList<Vec2D> points = new ArrayList<Vec2D>();
        Vec2D point;
        int i1, i2, k1, k2;
        for(int i = 0; i < obj1.length; i++) {
            for(int k = 0; k < obj2.length; k++) {
                i1 = i;
                if(i + 1 >= obj1.length) i2 = 0;
                else i2 = i + 1;
                k1 = k;
                if(k + 1 >= obj2.length) k2 = 0;
                else k2 = k + 1;
                if((point = getLineSegmentsCollisionPoint(obj1[i1], obj1[i2], obj2[k1], obj2[k2])) != null) {
                    points.add(point);
                }
            }
        }
        return points.toArray(new Vec2D[points.size()]);
    }

    public static Vec2D getLineSegmentsCollisionPoint(Vec2D l1p1, Vec2D l1p2, Vec2D l2p1, Vec2D l2p2) {
        double k1 = getLineK(l1p1, l1p2);
        double k2 = getLineK(l2p1, l2p2);
        double b1 = l1p1.y - l1p1.x * k1;
        double b2 = l2p1.y - l2p1.x * k2;
        double x = (b2 - b1) / (k1 - k2);
        double y = k1 * x + b1;

        Vec2D point = new Vec2D(x, y);
        if(checkPointAtLineSegment(point, l1p1, l1p2) && checkPointAtLineSegment(point, l2p1, l2p2)) {
            return point;
        }
        return null;
    }

    public static boolean checkPointAtLineSegment(Vec2D p, Vec2D lp1, Vec2D lp2) {
        double x1 = Math.min(lp1.x, lp2.x);
        double x2 = Math.max(lp1.x, lp2.x);
        double y1 = Math.min(lp1.y, lp2.y);
        double y2 = Math.max(lp1.y, lp2.y);

        return p.x >= x1 - ERROR_VALUE && p.x <= x2 + ERROR_VALUE && p.y >= y1 - ERROR_VALUE && p.y <= y2 + ERROR_VALUE;
    }

    public static boolean checkPointAtAABBLineSegment(Vec2D p, Vec2D lp1, Vec2D lp2) {
        if(lp1.x == lp2.x) {
            double x = lp1.x;
            double y1 = Math.min(lp1.y, lp2.y);
            double y2 = Math.max(lp1.y, lp2.y);
            if(p.x == x && p.y >= y1 && p.y <= y2) {
                return true;
            }
        } else {
            double x1 = Math.min(lp1.x, lp2.x);
            double x2 = Math.max(lp1.x, lp2.x);
            double y = lp1.y;
            if(p.x >= x1 && p.x <= x2 && p.y == y) {
                return true;
            }
        }
        return false;
    }

    public static double getSqDistanceFromLineToPoint(Vec2D lp1, Vec2D lp2, Vec2D p) {
        double k1 = getLineK(lp1, lp2);
        double b1 = lp1.y - lp1.x * k1;
        double k2 = -1/k1;
        double b2 = p.y - p.x * k2;
        double x = (b2 - b1) / (k1 - k2);
        double y = k1 * x + b1;
        return (x - p.x)*(x - p.x) + (y - p.y)*(y - p.y);
    }

    public static double getDistanceFromLineToPoint(Vec2D lp1, Vec2D lp2, Vec2D p) {
        return Math.sqrt(getSqDistanceFromLineToPoint(lp1, lp2, p));
    }

    public static double getSqDistanceFromLineSegmentToPoint(Vec2D lp1, Vec2D lp2, Vec2D p) {
        double k1 = getLineK(lp1, lp2);
        double b1 = lp1.y - lp1.x * k1;
        double k2 = -1/k1;
        double b2 = p.y - p.x * k2;
        double x = (b2 - b1) / (k1 - k2);
        double y = k1 * x + b1;

        if(checkPointAtLineSegment(new Vec2D(x, y), lp1, lp2))
            return (x - p.x)*(x - p.x) + (y - p.y)*(y - p.y);
        else
            return Math.min((lp1.x - p.x)*(lp1.x - p.x) + (lp1.y - p.y)*(lp1.y - p.y),
                    (lp2.x - p.x)*(lp2.x - p.x) + (lp2.y - p.y)*(lp2.y - p.y));
    }

    public static double getDistanceFromLineSegmentToPoint(Vec2D lp1, Vec2D lp2, Vec2D p) {
        return Math.sqrt(getSqDistanceFromLineSegmentToPoint(lp1, lp2, p));
    }

    public static boolean checkCirclesCollision(Vec2D o1, double r1, Vec2D o2, double r2) {
        return (o1.x - o2.x)*(o1.x - o2.x) + (o1.y - o2.y)*(o1.y - o2.y) <= (r1 + r2)*(r1 + r2);
    }


    //not working properly
    public static Vec2D[] getCirclesCollision(Vec2D o1, double r1, Vec2D o2, double r2) {
        double ox = o2.x - o1.x;
        double oy = o2.y - o1.y;
        double A = -2 * ox;
        double B = -2 * oy;
        double C = ox*ox + oy*oy + r1*r1 - r2*r2;

        double lineK = - A / B;
        double lineB = - C / B;
        double a = lineK*lineK + 1;
        double b = 2 * (lineK * (lineB - oy) - ox);
        double c = ox*ox + (lineB - oy)*(lineB - oy) - r2*r2;
        double D = b*b - 4 * a * c;

        if(D < 0) {
            return new Vec2D[] {};
        }
        if(D == 0) {
            double x, y;
            x = - b / (2 * a) + o1.x;
            y = lineK * x + lineB + o1.y;
            Vec2D point = new Vec2D(x, y);
            return new Vec2D[] {point};
        }
        double x1, x2, y1, y2;
        x1 = - (b + Math.sqrt(D)) / (2 * a) + o1.x;
        y1 = lineK * x1 + lineB + o1.y;
        x2 = - (b - Math.sqrt(D)) / (2 * a) + o1.x;
        y2 = lineK * x2 + lineB + o1.y;
        Vec2D point1 = new Vec2D(x1, y1);
        Vec2D point2 = new Vec2D(x2, y2);
        return new Vec2D[] {point1, point2};
    }

    public static boolean checkCircleWithLineSegmentCollision(Vec2D o, double r, Vec2D lp1, Vec2D lp2) {
        return getSqDistanceFromLineSegmentToPoint(lp1, lp2, o) <= r*r;
    }

    public static Vec2D[] getCircleWithLineSegmentCollision(Vec2D o, double r, Vec2D lp1, Vec2D lp2) {
        double lineK = getLineK(lp1, lp2);
        double lineB = lp1.y - lp1.x * lineK;
        double a = lineK*lineK + 1;
        double b = 2 * (lineK * (lineB - o.y) - o.x);
        double c = o.x*o.x + (lineB - o.y)*(lineB - o.y) - r*r;
        double D = b*b - 4 * a * c;

        if(D < 0) {
            return new Vec2D[] {};
        }
        if(D == 0) {
            double x, y;
            x = - b / (2 * a);
            y = lineK * x + lineB;
            Vec2D point = new Vec2D(x, y);
            if(checkPointAtLineSegment(point, lp1, lp2)) {
                return new Vec2D[] {point};
            }
            return new Vec2D[] {};
        }
        double x1, x2, y1, y2;
        double sqrtD = Math.sqrt(D);
        x1 = - (b + sqrtD) / (2 * a);
        y1 = lineK * x1 + lineB;
        x2 = - (b - sqrtD) / (2 * a);
        y2 = lineK * x2 + lineB;
        Vec2D point1 = new Vec2D(x1, y1);
        Vec2D point2 = new Vec2D(x2, y2);
        ArrayList<Vec2D> points = new ArrayList<Vec2D>();
        int i = 0;
        if(checkPointAtLineSegment(point1, lp1, lp2)) {
            points.add(point1);
            i++;
        }
        if(checkPointAtLineSegment(point2, lp1, lp2)) {
            points.add(point2);
            i++;
        }
        return points.toArray(new Vec2D[i]);
    }

    public static double getLineK(Vec2D p1, Vec2D p2) {
        return MathUtil.clamp(-LIMIT_VALUE, (p2.y - p1.y) / (p2.x - p1.x), LIMIT_VALUE);
    }

    public static Vec2D[] getShapeWithCircleCollision(Vec2D[] shape, Vec2D o, double r) {
        ArrayList<Vec2D> points = new ArrayList<Vec2D>();
        Vec2D[] buffer;
        for(int i = 0; i < shape.length; i++) {
            if(i == shape.length - 1) {
                buffer = getCircleWithLineSegmentCollision(o, r, shape[i], shape[0]);
            } else {
                buffer = getCircleWithLineSegmentCollision(o, r, shape[i], shape[i+1]);
            }
            Collections.addAll(points, buffer);
        }
        return points.toArray(new Vec2D[points.size()]);
    }
}
