package dym.unique.funnyball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by daiyiming on 2016/4/5.
 */
public class TopScoreTextView extends View {

    private final static float TEXT_SIZE_SCALE = 0.9f;

    private final static String TEXT_END = "分";

    private Paint mTagTextPaint = null; // 标记文字画笔
    private Rect mTagEndRect = new Rect();
    private Paint mScoreTextPaint = null; // 游戏记录画笔
    private Rect mTextScoreRect = new Rect();
    private float mMargin = 0;
    private int mTopScore = 0;

    public TopScoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTagTextPaint = new Paint();
        mTagTextPaint.setStyle(Paint.Style.FILL);
        mTagTextPaint.setDither(true);
        mTagTextPaint.setAntiAlias(true);
        mTagTextPaint.setColor(0xff666666);
        mTagTextPaint.setTextAlign(Paint.Align.CENTER);
        mTagTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));

        mScoreTextPaint = new Paint();
        mScoreTextPaint.setStyle(Paint.Style.FILL);
        mScoreTextPaint.setDither(true);
        mScoreTextPaint.setAntiAlias(true);
        mScoreTextPaint.setColor(0xffff0000);
        mScoreTextPaint.setTextAlign(Paint.Align.CENTER);
        mScoreTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 32, context.getResources().getDisplayMetrics()));

        mTagTextPaint.getTextBounds(TEXT_END, 0, TEXT_END.length(), mTagEndRect);

        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, context.getResources().getDisplayMetrics());
    }

    public void setScore(int score) {
        mTopScore = score;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 将分数绘制到中间
        String score = String.valueOf(mTopScore);
        mScoreTextPaint.getTextBounds(score, 0, score.length(), mTextScoreRect);
        // 检测文字大小，如果左侧
        while ((getWidth() / 2 + mMargin + mTextScoreRect.width() / 2 + mTagEndRect.width()) > getWidth() * TEXT_SIZE_SCALE) {
            // 重设分数尺寸
            mScoreTextPaint.setTextSize(mScoreTextPaint.getTextSize() * TEXT_SIZE_SCALE);
            mScoreTextPaint.getTextBounds(score, 0, score.length(), mTextScoreRect);
            // 重设标志尺寸
            mTagTextPaint.setTextSize(mTagTextPaint.getTextSize() * TEXT_SIZE_SCALE);
            mTagTextPaint.getTextBounds(TEXT_END, 0, TEXT_END.length(), mTagEndRect);
            // 重设边距
            mMargin *= TEXT_SIZE_SCALE;
        }

        Paint.FontMetricsInt fontMetricsInt = mScoreTextPaint.getFontMetricsInt();
        int baseline = (getHeight() - fontMetricsInt.top - fontMetricsInt.bottom) / 2;
        canvas.drawText(score, getWidth() / 2, baseline, mScoreTextPaint);
        // 绘制标志文字
        canvas.drawText(TEXT_END, getWidth() / 2 + mMargin + (mTextScoreRect.width() + mTagEndRect.width()) / 2,
                baseline, mTagTextPaint);
    }

}
