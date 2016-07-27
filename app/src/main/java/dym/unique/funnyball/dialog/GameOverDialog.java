package dym.unique.funnyball.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dym.unique.funnyball.R;

/**
 * Created by daiyiming on 2016/4/6.
 */
public class GameOverDialog extends Dialog implements View.OnClickListener {

    private Button mBtnSure = null;
    private TextView mTvText = null;

    public GameOverDialog(Context context, int score) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dialog_game_over);

        mTvText = (TextView) findViewById(R.id.tv_text);
        mBtnSure = (Button) findViewById(R.id.btn_sure);

        String text = "游戏结束！你的分数是" + score + "分";
        mTvText.setText(text);

        mBtnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }

}
