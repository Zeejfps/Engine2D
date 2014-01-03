package com.zeejfps.engine2d.util.math;

/**
 * Created by Zeejfps on 12/21/13.
 */
public class Vector2f {

    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f v) {
        this(v.x, v.y);
    }

    public Vector2f() {}

    /**
     * This method returns the zero vector of this vector;
     * @return the zero vector
     */
    public Vector2f getZero() {
        return new Vector2f(0, 0);
    }

    /**
     * This method sets all the components of this vector to 0;
     */
    public void setZero() {
        x = 0;
        y = 0;
    }

    /**
     * This method will return a vector with all of its components negated
     * @return negative copy of this vector
     */
    public Vector2f getNegative() {
        return new Vector2f(-x, -y);
    }

    public void setNegative() {
        x = -x;
        y = -y;
    }

    /**
     * This method multiples this vector by a scalar value
     * @param scalar value to multiply by
     * @return resulting vector
     */
    public Vector2f mul(float scalar) {
        return new Vector2f(x*scalar, y*scalar);
    }

    /**
     * This method divides this vector by a scalar value
     * @param scalar value to divide by
     * @return resulting vector
     */
    public Vector2f div(float scalar) {
        return new Vector2f(x/scalar, y/scalar);
    }

    /**
     * This method returns a normalized version of this vector
     * @return normalized copy of this vector
     */
    public Vector2f getNormalized() {
        final float length = getMagnitude(this);
        return new Vector2f(x/length, y/length);
    }

    /**
     * This method normalizes this vector
     */
    public void normalize() {
        final float length = getMagnitude(this);
        x /= length;
        y /= length;
    }

    /**
     * This method adds two vectors
     * @param v vector to add
     * @return resulting vector
     */
    public Vector2f add(Vector2f v) {
        return new Vector2f(x+v.x, y+v.y);
    }

    /**
     * This method subtracts two vectors
     * @param v vector to subtract
     * @return resulting vector
     */
    public Vector2f sub(Vector2f v) {
        return new Vector2f(x-v.x, y-v.y);
    }

    /**
     * This method takes the dot product of this vector with another
     * @param v vector
     * @return resulting vector
     */
    public float dot(Vector2f v) {
        return x*v.x + y*v.y;
    }

    /**
     * This method compares two vectors and returns true if their components are identical
     * @param v vector to compare
     * @return true if identical
     */
    public boolean equals(Vector2f v) {
        return x==v.x && y==v.y;
    }

    /**
     * This method returns the magnitude of a vector
     * @param v vector whose magnitude to calculate
     * @return magnitude
     */
    public static float getMagnitude(Vector2f v) {
        return (float)Math.sqrt(v.x*v.x + v.y*v.y);
    }

    public static float findDistance(Vector2f start, Vector2f end) {
        return (float)Math.sqrt((end.x - start.x) * (end.x - start.x) +
                                (end.y - start.y) * (end.y - start.y));
    }

    /**
     * This method finds the angle between to vectors
     * @param v1 fist vector
     * @param v2 second vector
     * @return angle in radiance
     */
    public static float findAngle(Vector2f v1, Vector2f v2) {
        return (float)Math.acos(v1.dot(v2) / getMagnitude(v1) * getMagnitude(v2));
    }

}
