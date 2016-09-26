package dym.unique.funnyball.view.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.util.TypedValue;

/**
 * Created by daiyiming on 2016/4/4.
 */
public abstract class IHolder {
    protected final static int[] GAME_COLOR = new int[]{ // 渐变颜色
            0xff668feb,
            0xff7f88d4,
            0xffa27eb6,
            0xffcd7290,
            0xffe86a78
    };
    protected static int sLifeCount = 5; // 剩余生命数
    protected final static int SCALE_NUMBER = 6; // 屏幕平分的块数

    protected Context mContext = null;

    protected final int SCALE_SIZE;

    public IHolder(Context context) {
        mContext = context;
        SCALE_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取Scale数目
     *
     * @return Scale数目
     */
    protected abstract float getScaleNum();

    protected int getColor() {
        if (GAME_COLOR.length - sLifeCount >= 0 && GAME_COLOR.length - sLifeCount < GAME_COLOR.length) {
            return GAME_COLOR[GAME_COLOR.length - sLifeCount];
        }
        return GAME_COLOR[0];
    }

    public float getRadius() {
        return (float) SCALE_SIZE * getScaleNum() / 2;
    }

    /**
     * 用于绘制的函数
     *
     * @param canvas 画布
     */
    public abstract void draw(Canvas canvas);

    /**
     * 用于子类继承，刷新数据使用
     */
    public void flush() {
    }

    public static void reduceLifeCount() {
        if (sLifeCount > 1) {
            sLifeCount--;
        }
    }

    public static void flushLifeCount() {
        sLifeCount = 5;
    }

    public static int getLifeCount() {
        return sLifeCount;
    }
}
