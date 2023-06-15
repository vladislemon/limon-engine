package limonengine.util.math.vector;

/**
 * slimon
 * 09.08.2014
 */
public class Vec3F {

    public float x, y, z;

    public Vec3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3F() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public void add(Vec3F vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public void subtract(Vec3F vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
    }

    public void multiply(float f) {
        x *= f;
        y *= f;
        z *= f;
    }

    public Vec3F copy() {
        return new Vec3F(x, y, z);
    }
}
