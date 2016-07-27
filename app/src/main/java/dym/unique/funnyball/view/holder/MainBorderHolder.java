package dym.unique.funnyball.view.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by daiyiming on 2016/4/4.
 * 用于边框的绘制的类
 */
public class MainBorderHolder extends IHolder {

    private Paint mMainBorderPaint = null; // 画笔
    private int mMainBorderAlpha = 120; // 边界透明度
    private boolean isAlphaReduce = false;

    public MainBorderHolder(Context context, int borderSize) {
        super(context);
        mMainBorderPaint = new Paint();
        mMainBorderPaint.setStyle(Paint.Style.STROKE);
        mMainBorderPaint.setStrokeWidth(borderSize);
        mMainBorderPaint.setAntiAlias(true);
        mMainBorderPaint.setDither(true);
    }

    @Override
    protected float getScaleNum() {
        return SCALE_NUMBER - 1;
    }

    @Override
    public void draw(Canvas canvas, int width, int height) {
        mMainBorderPaint.setColor(getColor());
        if (mMainBorderAlpha < 10) {
            isAlphaReduce = false;
        } else if (mMainBorderAlpha > 240) {
            isAlphaReduce = true;
        }
        mMainBorderAlpha = isAlphaReduce ? mMainBorderAlpha - 2 : mMainBorderAlpha + 2;
        mMainBorderPaint.setAlpha(mMainBorderAlpha);
        canvas.drawCircle(width / 2, height / 2, getRadius(), mMainBorderPaint);
    }

    @Override
    public void flush() {
        mMainBorderAlpha = 120;
        isAlphaReduce = false;
    }

}
