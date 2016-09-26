package dym.unique.funnyball.view.holder.smallball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

import dym.unique.funnyball.view.holder.IHolder;
import dym.unique.funnyball.view.holder.smallball.movepath.BaseMovePath;

public class SmallBallHolder extends IHolder {
    private Paint mSmallBallPaint = null; // 用于绘制小球的画笔
    private int mDistanceOffset = 0; // 用于限制点击时的距离
    private final BaseMovePath mMovePath; // 小球移动的路径

    public SmallBallHolder(Context context, BaseMovePath movePath) {
        super(context);

        mMovePath = movePath;

        mDistanceOffset = -(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());

        mSmallBallPaint = new Paint();
        mSmallBallPaint.setStyle(Paint.Style.FILL);
        mSmallBallPaint.setAntiAlias(true);
        mSmallBallPaint.setDither(true);
    }

    public float getY() {
        return mMovePath.getY();
    }

    public float getX() {
        return mMovePath.getX();
    }

    @Override
    protected float getScaleNum() {
        return 0.5f;
    }

    @Override
    public void draw(Canvas canvas) {
        mMovePath.move();
        mSmallBallPaint.setColor(getColor());
        canvas.drawCircle(getX(), getY(), getRadius(), mSmallBallPaint);
    }

    public boolean isInner(float x, float y) {
        float distance = (float) Math.sqrt(Math.pow(Math.abs(x - getX()), 2) + Math.pow(Math.abs(y - getY()), 2));
        return getRadius() >= distance + mDistanceOffset;
    }

}






















