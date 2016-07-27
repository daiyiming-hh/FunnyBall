package dym.unique.funnyball.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import dym.unique.funnyball.R;

/**
 * Created by daiyiming on 2016/4/6.
 */
public class ResetTopScoreDialog extends Dialog implements View.OnClickListener {

    private OnSureButtonClickListener mSureClickListener = null;

    public interface OnSureButtonClickListener {
        void OnSureClick();
    }

    public ResetTopScoreDialog(Context context, OnSureButtonClickListener listener) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dialog_reset_top_score);

        mSureClickListener = listener;

        ((TextView) findViewById(R.id.tv_text)).setText("隐藏功能：重置此项游戏记录\n是否需要重置？");

        findViewById(R.id.btn_sure).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure: {
                mSureClickListener.OnSureClick();
                dismiss();
            } break;
            case R.id.btn_cancel: {
                dismiss();
            } break;
        }
    }

}
