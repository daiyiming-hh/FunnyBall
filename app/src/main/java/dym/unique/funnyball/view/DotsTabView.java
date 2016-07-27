package dym.unique.funnyball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by daiyiming on 2016/4/6.
 */
public class DotsTabView extends View {

    private final static int COLOR_SELECTED = 0xffffffff;
    private final static int COLOR_UNSELECTED = 0xffbcbcbc;

    private final int[] mOffsetMargins;
    private final int mDotRadius;
    private Paint mDotPaint = null; // 绘制圆点的画笔
    private int mIndex = 0;

    public DotsTabView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDotRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        mOffsetMargins = new int[] {
                -margin - 2 * mDotRadius, 0, margin + 2 * mDotRadius
        };

        mDotPaint = new Paint();
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setDither(true);
        mDotPaint.setAntiAlias(true);
    }

    public void setIndex(int index) {
        mIndex = index;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 3; i ++) {
            if (i == mIndex) {
                mDotPaint.setColor(COLOR_SELECTED);
            } else {
                mDotPaint.setColor(COLOR_UNSELECTED);
            }
            canvas.drawCircle(getWidth() / 2 + mOffsetMargins[i], getHeight() / 2, mDotRadius, mDotPaint);
        }
    }
}




