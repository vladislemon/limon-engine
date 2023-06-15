package limonengine.util.math.vector;

/**
 * slimon
 * 09.08.2014
 */
public class Vec2F {

    public float x, y;

    public Vec2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2F() {
        this.x = 0;
        this.y = 0;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vec2F vec) {
        x += vec.x;
        y += vec.y;
    }

    public void subtract(Vec2F vec) {
        x -= vec.x;
        y -= vec.y;
    }

    public void multiply(float f) {
        x *= f;
        y *= f;
    }

    public Vec2F copy() {
        return new Vec2F(x, y);
    }
}
