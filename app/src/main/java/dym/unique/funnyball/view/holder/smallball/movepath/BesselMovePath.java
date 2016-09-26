package dym.unique.funnyball.view.holder.smallball.movepath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dym.unique.funnyball.utils.BesselTools;
import dym.unique.funnyball.view.GameView;

/**
 * Created by daiyiming on 2016/5/3.
 * 贝塞尔曲线移动路径
 */
public class BesselMovePath extends BaseMovePath {

    private final static int MAX_PROGRESS = 100;
    private int mProgress = 0;
    private List<BesselTools.Point> mPoints = new ArrayList<>();
    private Random mRandom = new Random();

    public BesselMovePath(float x, float y, float aimX, float aimY) {
        super(x, y, aimX, aimY);

        // 添加初始点
        mPoints.add(new BesselTools.Point(x, y));
        // 添加控制点
        mPoints.add(new BesselTools.Point(aimX, mRandom.nextBoolean() ? 0 : GameView.height()));
        // 添加结束点
        mPoints.add(new BesselTools.Point(aimX, aimY));
    }

    @Override
    public void move() {
        if (mProgress > MAX_PROGRESS) {
            mProgress = MAX_PROGRESS;
        }
        BesselTools.Point newPoint = BesselTools.calculatePoint(mPoints, (float) mProgress / MAX_PROGRESS);
        mX = newPoint.x;
        mY = newPoint.y;
        mProgress ++;
    }
}
