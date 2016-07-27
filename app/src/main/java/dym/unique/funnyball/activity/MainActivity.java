package dym.unique.funnyball.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import dym.unique.funnyball.R;
import dym.unique.funnyball.dialog.GameOverDialog;
import dym.unique.funnyball.dialog.ResetTopScoreDialog;
import dym.unique.funnyball.enums.GameType;
import dym.unique.funnyball.meida.SoundsPlayer;
import dym.unique.funnyball.view.GameView;
import dym.unique.funnyball.view.TopScoreTextView;

public class MainActivity extends BaseActivity implements GameView.OnGameMessageChangeListener, View.OnClickListener, View.OnLongClickListener {

    private final static int ANIMATOR_DURATION = 300; // 动画进行时间

    private GameView mGameView = null; // 游戏界面
    private FrameLayout mFlLineGame = null; // 直线游戏
    private FrameLayout mFLBesselGame = null; // 曲线游戏
    private FrameLayout mFlFlushGame = null; // 闪现游戏
    private Button mBtnLearnGame = null; // 游戏教程按钮
    private Button mBtnExistGame = null; // 退出游戏按钮
    private ImageView mImgVoice = null; // 声音按钮
    private TopScoreTextView mTvtLineScore = null; // 直线游戏分数
    private TopScoreTextView mTvtBesselScore = null; // 曲线游戏分数
    private TopScoreTextView mTvtFlushScore = null; // 混合游戏分数
    private LinearLayout mLlControlView = null; // 控制界面的容器
    private View mVShield = null; // 遮罩层
    private boolean mIsControlViewShow = true; // 控制界面是否显示
    private boolean mIsAnimationDoing = false; // 动画是否在进行中

    private View.OnTouchListener mShieldTouchListener = new View.OnTouchListener() { // 用于屏蔽点击穿透
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取控件
        mGameView = (GameView) findViewById(R.id.gv_game);
        mFlLineGame = (FrameLayout) findViewById(R.id.fl_lineGame);
        mFLBesselGame = (FrameLayout) findViewById(R.id.fl_besselGame);
        mFlFlushGame = (FrameLayout) findViewById(R.id.fl_flushGame);
        mBtnLearnGame = (Button) findViewById(R.id.btn_learnGame);
        mBtnExistGame = (Button) findViewById(R.id.btn_existGame);
        mLlControlView = (LinearLayout) findViewById(R.id.ll_controlView);
        mVShield = findViewById(R.id.v_shield);
        mImgVoice = (ImageView) findViewById(R.id.img_voice);
        mTvtLineScore = (TopScoreTextView) findViewById(R.id.tst_lineScore);
        mTvtBesselScore = (TopScoreTextView) findViewById(R.id.tst_besselScore);
        mTvtFlushScore = (TopScoreTextView) findViewById(R.id.tst_mixScore);

        // 绑定事件
        mGameView.setOnGameMessageChangeListener(this);
        mFlLineGame.setOnClickListener(this);
        mFLBesselGame.setOnClickListener(this);
        mFlFlushGame.setOnClickListener(this);
        mBtnLearnGame.setOnClickListener(this);
        mBtnExistGame.setOnClickListener(this);
        mVShield.setOnTouchListener(mShieldTouchListener);
        mImgVoice.setOnClickListener(this);
        mFlLineGame.setOnLongClickListener(this);
        mFLBesselGame.setOnLongClickListener(this);
        mFlFlushGame.setOnLongClickListener(this);

        mTvtLineScore.setScore(GameType.LINE.getTopScore());
        mTvtBesselScore.setScore(GameType.BESSEL.getTopScore());
        mTvtFlushScore.setScore(GameType.FLUSH.getTopScore());

        setVoiceImage();
    }

    @Override
    public boolean onLongClick(final View v) {
        new ResetTopScoreDialog(this, new ResetTopScoreDialog.OnSureButtonClickListener() {
            @Override
            public void OnSureClick() {
                switch (v.getId()) {
                    case R.id.fl_lineGame: {
                        resetTopGameScore(GameType.LINE);
                    } break;
                    case R.id.fl_besselGame: {
                        resetTopGameScore(GameType.BESSEL);
                    } break;
                    case R.id.fl_flushGame: {
                        resetTopGameScore(GameType.FLUSH);
                    } break;
                }
            }
        }).show();
        return true;
    }

    private void resetTopGameScore(GameType gameType) {
        gameType.saveTopScore(0);
        switch (gameType) {
            case LINE: {
                mTvtLineScore.setScore(0);
            } break;
            case BESSEL: {
                mTvtBesselScore.setScore(0);
            } break;
            case FLUSH: {
                mTvtFlushScore.setScore(0);
            } break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_lineGame: { // 开始直线游戏
                hideControlView(GameType.LINE);
            } break;
            case R.id.fl_besselGame: { // 开始曲线游戏
                hideControlView(GameType.BESSEL);
            } break;
            case R.id.fl_flushGame: { // 开始混合游戏
                hideControlView(GameType.FLUSH);
            } break;
            case R.id.btn_learnGame: { // 开启教程
                startActivity(new Intent(this, IntroduceActivity.class));
            } break;
            case R.id.btn_existGame: { // 退出游戏
                this.finish();
            }
            case R.id.img_voice: { // 声音控制
                SoundsPlayer.setIsCanPlaySounds(! SoundsPlayer.getIsCanPlaySounds());
                setVoiceImage();
            } break;
        }
    }

    private void flushTopScore(GameType gameType, int score) {
        if (score > gameType.getTopScore()) {
            switch (gameType) {
                case LINE: {
                    mTvtLineScore.setScore(score);
                } break;
                case BESSEL: {
                    mTvtBesselScore.setScore(score);
                } break;
                case FLUSH: {
                    mTvtFlushScore.setScore(score);
                } break;
            }
            gameType.saveTopScore(score);
        }
    }

    private void setVoiceImage() {
        if (SoundsPlayer.getIsCanPlaySounds()) {
            mImgVoice.setImageResource(R.drawable.ic_voice);
        } else {
            mImgVoice.setImageResource(R.drawable.ic_no_voice);
        }
    }

    private void hideControlView(final GameType gameType) {
        if (mIsAnimationDoing) {
            return;
        }

        mIsControlViewShow = false;
        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(mLlControlView, "translationY", 0,
                -mLlControlView.getHeight());
        moveAnimator.setDuration(ANIMATOR_DURATION);
        moveAnimator.setInterpolator(new DecelerateInterpolator());
        moveAnimator.start();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mVShield, "alpha", 1, 0);
        alphaAnimator.setDuration(ANIMATOR_DURATION * 2);
        alphaAnimator.setInterpolator(new DecelerateInterpolator());
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimationDoing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mVShield.setVisibility(View.GONE);
                mIsAnimationDoing = false;
                //开始游戏
                mGameView.start(gameType);
            }
        });
        alphaAnimator.start();
    }

    private void showControlView() {
        if (mIsAnimationDoing) {
            return;
        }

        mIsControlViewShow = true;
        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(mLlControlView, "translationY",
                -mLlControlView.getHeight(), 0);
        moveAnimator.setDuration(ANIMATOR_DURATION);
        moveAnimator.setInterpolator(new DecelerateInterpolator());
        moveAnimator.start();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mVShield, "alpha", 0, 1);
        alphaAnimator.setDuration(ANIMATOR_DURATION);
        alphaAnimator.setInterpolator(new DecelerateInterpolator());
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mVShield.setVisibility(View.VISIBLE);
                mIsAnimationDoing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimationDoing = false;
            }
        });
        alphaAnimator.start();
    }

    @Override
    public void onBackPressed() {
        if (! mIsControlViewShow) {
            finishGame();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (! mIsControlViewShow) {
            finishGame();
        }
    }

    /**
     * 主动调用结束游戏并刷新分数
     */
    private void finishGame() {
        flushTopScore(mGameView.getGameType(), mGameView.getGameScore());
        mGameView.finish();
        showControlView();
    }

    @Override
    public void OnGameOver() {
        flushTopScore(mGameView.getGameType(), mGameView.getGameScore());
        new GameOverDialog(this, mGameView.getGameScore()).show();
        showControlView();
    }

}
