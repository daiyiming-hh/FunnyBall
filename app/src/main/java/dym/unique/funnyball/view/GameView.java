package dym.unique.funnyball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dym.unique.funnyball.enums.GameType;
import dym.unique.funnyball.meida.SoundsPlayer;
import dym.unique.funnyball.view.holder.IHolder;
import dym.unique.funnyball.view.holder.MainBallHolder;
import dym.unique.funnyball.view.holder.MainBorderHolder;
import dym.unique.funnyball.view.holder.smallball.SmallBallHolder;

/**
 * Created by daiyiming on 2016/4/3.
 */
public class GameView extends View {

    private static int HEIGHT = 0; // 高度
    private static int WIDTH = 0; // 宽度

    private final static int PLAY_DURATION = 10; // 播放的时间间隔

    private MainBallHolder mMainBallHolder = null; // 主球
    private MainBorderHolder mMainBorderHolder = null; // 边界
    private List<SmallBallHolder> mSmallBallHolders = new ArrayList<>(); // 放置小球的容器

    private Random mRandom = new Random();
    private boolean mIsStart = false; // 是否开始
    private long mGameTime = 0; // 游戏时间
    private int mGameScore = 0; // 分数
    private long mCreateSmallBallTime = 0; // 用于限制产生小球的时间
    private GameType mGameType = null; // 游戏类型

    private OnGameMessageChangeListener mGameMessageListener = null; // 游戏信息改变的Listener

    private Handler handler = new Handler();
    private Runnable playRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsStart) {
                // 判断小球与大球的相对位置
                Iterator<SmallBallHolder> iterator = mSmallBallHolders.iterator();
                while (iterator.hasNext()) {
                    // 如果小球进入了大球内部则清除该小球并减少生命
                    SmallBallHolder holder = iterator.next();
                    if (mMainBallHolder.isInner(holder.getX(), holder.getY(), holder.getRadius())) {
                        iterator.remove();
                        if (IHolder.getLifeCount() == 1) { // 游戏结束
                            // 通知游戏结束
                            if (mGameMessageListener != null) {
                                mGameMessageListener.OnGameOver();
                            }
                            finish();
                            SoundsPlayer.playGameOverSound();
                            return;
                        } else { // 游戏未结束，继续减少生命
                            IHolder.reduceLifeCount();
                            SoundsPlayer.playLostLifeSound();
                        }
                    }
                }
                // 更新游戏时间
                mGameTime += PLAY_DURATION;
                // 控制每秒产生一次小球
                if (mCreateSmallBallTime <= 1000) {
                    mCreateSmallBallTime += PLAY_DURATION;
                } else {
                    int seconds = (int) (mGameTime / 1000);
                    int createCount;
                    if (seconds < 60) {
                        createCount = seconds / 10 + 1;
                    } else {
                        createCount = mRandom.nextInt(2) + 6;
                    }
                    createSmallBall(createCount);
                    mCreateSmallBallTime = 0;
                }
                invalidate();
                handler.postDelayed(playRunnable, PLAY_DURATION);
            }
        }
    };

    public interface OnGameMessageChangeListener {
        void OnGameOver();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mMainBallHolder = new MainBallHolder(getContext());
        mMainBorderHolder = new MainBorderHolder(getContext(),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 配置布局宽高
        HEIGHT = canvas.getHeight();
        WIDTH = canvas.getWidth();
        // 绘制背景
        canvas.drawColor(Color.WHITE);
        // 绘制小球
        for (IHolder holder : mSmallBallHolders) {
            holder.draw(canvas);
        }
        // 绘制中央主球
        mMainBallHolder.draw(canvas);
        // 绘制边界
        mMainBorderHolder.draw(canvas);
    }

    /**
     * 游戏开始
     */
    public void start(GameType gameType) {
        mIsStart = true;
        mGameType = gameType;
        handler.postDelayed(playRunnable, PLAY_DURATION);
    }

    /**
     * 游戏结束
     */
    public void finish() {
        mIsStart = false;
        mGameTime = 0;
        mGameScore = 0;
        mMainBallHolder.flush();
        mMainBorderHolder.flush();
        mSmallBallHolders.clear();
        IHolder.flushLifeCount();
        invalidate();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 返回游戏类型
     *
     * @return 游戏类型
     */
    public GameType getGameType() {
        return mGameType;
    }

    private void createSmallBall(int count) {
        for (int i = 0; i < count; i++) {
            mSmallBallHolders.add(new SmallBallHolder(getContext(), mGameType.getMovePath()));
        }
    }

    /**
     * 获取游戏界面高度
     *
     * @return 高度
     */
    public static int height() {
        return HEIGHT;
    }

    /**
     * 获取游戏界面宽度
     *
     * @return 宽度
     */
    public static int width() {
        return WIDTH;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                // 当手指点击时检测是否有小球被点击
                Iterator<SmallBallHolder> iterator = mSmallBallHolders.iterator();
                while (iterator.hasNext()) {
                    SmallBallHolder holder = iterator.next();
                    if (holder.isInner(event.getX(), event.getY())) {
                        iterator.remove();
                        // 更新游戏分数
                        mGameScore++;
                        mMainBallHolder.setGameScore(mGameScore);
                        // 播放音乐
                        SoundsPlayer.playTouchSound();
                        break;
                    }
                }
            }
            break;
        }
        return true;
    }

    public int getGameScore() {
        return mGameScore;
    }

    public void setOnGameMessageChangeListener(OnGameMessageChangeListener listener) {
        mGameMessageListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }
}
