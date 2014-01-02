package com.zeejfps.engine2d.core.util.math;

/**
 * Created by Zeejfps on 12/21/13.
 */
public class Vector3f {

    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f v){
        this(v.x, v.y, v.z);
    }

    public Vector3f() {}

    /**
     * This method returns the zero vector of this vector;
     * @return the zero vector
     */
    public Vector3f getZero() {
        return new Vector3f(0, 0, 0);
    }

    /**
     * This method sets all the components of this vector to 0;
     */
    public void setZero() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * This method will return a vector with all of its components negated
     * @return negative copy of this vector
     */
    public Vector3f getNegative() {
        return new Vector3f(-x, -y, -z);
    }

    public void setNegative() {
        x = -x;
        y = -y;
        z = -z;
    }

    /**
     * This method multiples this vector by a scalar value
     * @param scalar value to multiply by
     * @return resulting vector
     */
    public Vector3f mul(float scalar) {
        return new Vector3f(x*scalar, y*scalar, z*scalar);
    }

    /**
     * This method divides this vector by a scalar value
     * @param scalar value to divide by
     * @return resulting vector
     */
    public Vector3f div(float scalar) {
        return new Vector3f(x/scalar, y/scalar, z/scalar);
    }

    /**
     * This method returns a normalized version of this vector
     * @return normalized copy of this vector
     */
    public Vector3f getNormalized() {
        final float length = getMagnitude(this);
        return new Vector3f(x/length, y/length, z/length);
    }

    /**
     * This method normalizes this vector
     */
    public void normalize() {
        final float length = getMagnitude(this);
        x /= length;
        y /= length;
        z /= length;
    }

    /**
     * This method adds two vectors
     * @param v vector to add
     * @return resulting vector
     */
    public Vector3f add(Vector3f v) {
        return new Vector3f(x+v.x, y+v.y, z+v.z);
    }

    /**
     * This method subtracts two vectors
     * @param v vector to subtract
     * @return resulting vector
     */
    public Vector3f sub(Vector3f v) {
        return new Vector3f(x-v.x, y-v.y, z-v.z);
    }

    /**
     * This method takes the dot product of this vector with another
     * @param v vector
     * @return resulting vector
     */
    public float dot(Vector3f v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public Vector3f cross(Vector3f v) {
        return new Vector3f(y*v.z - z*v.y,
                            z*v.x - x*v.z,
                            x*v.y - y*v.x);
    }

    /**
     * This method compares two vectors and returns true if their components are identical
     * @param v vector to compare
     * @return true if identical
     */
    public boolean equals(Vector3f v) {
        return x==v.x && y==v.y && z==v.z;
    }

    /**
     * This method returns the magnitude of a vector
     * @param v vector whose magnitude to calculate
     * @return magnitude
     */
    public static float getMagnitude(Vector3f v) {
        return (float)Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
    }

    public static float findDistance(Vector3f start, Vector3f end) {
        return (float)Math.sqrt((end.x - start.x) * (end.x - start.x) +
                                (end.y - start.y) * (end.y - start.y) +
                                (end.z - start.z) * (end.z - start.z));
    }

    /**
     * This method finds the angle between to vectors
     * @param v1 fist vector
     * @param v2 second vector
     * @return angle in radiance
     */
    public static float findAngle(Vector3f v1, Vector3f v2) {
        return (float)Math.acos(v1.dot(v2) / getMagnitude(v1) * getMagnitude(v2));
    }

}
