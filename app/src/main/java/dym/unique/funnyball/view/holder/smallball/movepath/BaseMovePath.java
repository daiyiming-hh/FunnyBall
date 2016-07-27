package dym.unique.funnyball.view.holder.smallball.movepath;

/**
 * Created by daiyiming on 2016/5/1.
 */
public abstract class BaseMovePath {
    protected float mX = 0, mY = 0;
    protected float mAimX = 0, mAimY = 0;

    public BaseMovePath(float x, float y, float aimX, float aimY) {
        mX = x;
        mY = y;
        mAimX = aimX;
        mAimY = aimY;
    }

    public abstract void move();

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }
}
