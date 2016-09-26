package dym.unique.funnyball.view.holder.smallball.movepath;

/**
 * Created by daiyiming on 2016/5/1.
 * 直线移动路径
 */
public class LineMovePath extends BaseMovePath {

    protected final static int DISTANCE_MEAN_NUM = 80;

    protected float mXStep = 0, mYStep = 0; // 小球移动的步长

    public LineMovePath(float x, float y, float aimX, float aimY) {
        super(x, y, aimX, aimY);

        this.mXStep = (aimX - x) / DISTANCE_MEAN_NUM;
        this.mYStep = (aimY - y) / DISTANCE_MEAN_NUM;
    }

    @Override
    public void move() {
        this.mX += this.mXStep;
        this.mY += this.mYStep;
    }

}
