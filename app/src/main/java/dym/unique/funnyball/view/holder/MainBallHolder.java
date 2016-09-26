package dym.unique.funnyball.view.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

/**
 * Created by daiyiming on 2016/4/4.
 */
public class MainBallHolder extends IHolder {

    private final int RADIUS_RAISE_NUM;
    private final float MAX_TEXT_SCALE = 0.8f; // 文字最大所占的尺寸

    private float mX = 0, mY = 0;
    private Paint mMainBallPaint = null; // 中央主球绘制的画笔
    private float mGameScoreTextSize = 0; // 文字的尺寸
    private Paint mGameScorePaint = null; // 绘制剩余生命数的画笔
    private Rect mGameScoreRect = new Rect(); // 生命数的框
    private int mGameScore = 0; // 游戏得分
    private float mPrevRadius = 0; // 上一次的半径，用于动画绘制

    public MainBallHolder(Context context) {
        super(context);
        // 创建画球的画笔
        mMainBallPaint = new Paint();
        mMainBallPaint.setStyle(Paint.Style.FILL);
        mMainBallPaint.setAntiAlias(true);
        mMainBallPaint.setDither(true);
        //创建画文字的画笔
        mGameScoreTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources()
                .getDisplayMetrics());
        mGameScorePaint = new Paint();
        mGameScorePaint.setColor(Color.WHITE);
        mGameScorePaint.setStyle(Paint.Style.FILL);
        mGameScorePaint.setAntiAlias(true);
        mGameScorePaint.setDither(true);
        mGameScorePaint.setTextSize(mGameScoreTextSize);
        mGameScorePaint.setTextAlign(Paint.Align.CENTER);

        //设置半径增长值
        RADIUS_RAISE_NUM = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources()
                .getDisplayMetrics());
        //获取之前的半径
        mPrevRadius = getRadius();
    }

    @Override
    protected float getScaleNum() {
        return GAME_COLOR.length - sLifeCount + 1;
    }

    @Override
    public void draw(Canvas canvas) {
        // 绘制球体
        mMainBallPaint.setColor(getColor());
        if (mPrevRadius < getRadius()) {
            mPrevRadius += RADIUS_RAISE_NUM;
        } else {
            mPrevRadius = getRadius();
        }
        int width = canvas.getWidth(), height = canvas.getHeight();
        canvas.drawCircle(mX = width / 2, mY = height / 2, mPrevRadius, mMainBallPaint);
        // 绘制文字,并要求文字宽度小于最小圆的半径
        String text = String.valueOf(mGameScore);
        mGameScorePaint.getTextBounds(text, 0, text.length(), mGameScoreRect);
        float textSize = mGameScoreTextSize;
        mGameScorePaint.setTextSize(textSize);
        while (mGameScoreRect.width() > MAX_TEXT_SCALE * SCALE_SIZE) {
            mGameScorePaint.setTextSize(textSize *= MAX_TEXT_SCALE);
            mGameScorePaint.getTextBounds(text, 0, text.length(), mGameScoreRect);
        }
        Paint.FontMetricsInt fontMetrics = mGameScorePaint.getFontMetricsInt();
        int baseline = (height - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(text, width / 2, baseline, mGameScorePaint);
    }

    @Override
    public void flush() {
        mGameScoreTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources()
                .getDisplayMetrics());
        mGameScore = 0; // 游戏得分
        mPrevRadius = getRadius(); // 上一次的半径，用于动画绘制
    }

    public void setGameScore(int score) {
        mGameScore = score;
    }

    /**
     * 判断小球是否完全进入大球
     * @param x 小球中心x
     * @param y 小球中心y
     * @return 是否进入
     */
    public boolean isInner(float x, float y, float radius) {
        float distance = (float) Math.sqrt(Math.pow(Math.abs(x - mX), 2) + Math.pow(Math.abs(y - mY), 2));
        return getRadius() >= distance + radius;
    }

}
