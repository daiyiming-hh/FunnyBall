package dym.unique.funnyball.view.holder.smallball.movepath;

import java.util.Random;

import dym.unique.funnyball.view.GameView;

/**
 * Created by daiyiming on 2016/5/12.
 * 闪现移动路径，继承自直线移动路径
 */
public class FlushMovePath extends LineMovePath {
    
    private static final int FLUSH_MAX_DISTANCE_MEAN = DISTANCE_MEAN_NUM / 4;

    private int mFlushMeanNum = 0; // 要闪现的位置点
    private int mCurrentMeanNum = 0; // 当前的位置点
    private Random mRandom = new Random();

    public FlushMovePath(float x, float y, float aimX, float aimY) {
        super(x, y, aimX, aimY);

        mFlushMeanNum = mRandom.nextInt(FLUSH_MAX_DISTANCE_MEAN) + FLUSH_MAX_DISTANCE_MEAN;
    }

    @Override
    public void move() {
        super.move();
        // 当移动到闪现点的时候
        if (mCurrentMeanNum == mFlushMeanNum) {
            mX = GameView.width() - mX;
            mXStep = -mXStep;
            if (mRandom.nextBoolean()) {
                mY = GameView.height() - mY;
                mYStep = -mYStep;
            }
        }
        mCurrentMeanNum ++;
    }
}
