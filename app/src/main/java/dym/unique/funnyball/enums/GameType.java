package dym.unique.funnyball.enums;

import dym.unique.funnyball.sharedperference.GameSharedPreference;
import dym.unique.funnyball.view.holder.smallball.movepath.BaseMovePath;
import dym.unique.funnyball.view.holder.smallball.movepath.MovePathFactory;

/**
 * Created by daiyiming on 2016/5/5.
 * 游戏类型
 */
public enum GameType {
    LINE {
        @Override
        public BaseMovePath getMovePath() {
            return MovePathFactory.createLineMovePath();
        }

        @Override
        public void saveTopScore(int score) {
            GameSharedPreference.saveTopScore(GameSharedPreference.SP_KEY_TOP_SCORE_LINE, score);
        }

        @Override
        public int getTopScore() {
            return GameSharedPreference.getTopScore(GameSharedPreference.SP_KEY_TOP_SCORE_LINE);
        }
    },
    BESSEL {
        @Override
        public BaseMovePath getMovePath() {
            return MovePathFactory.createBesselMovePath();
        }

        @Override
        public void saveTopScore(int score) {
            GameSharedPreference.saveTopScore(GameSharedPreference.SP_KEY_TOP_SCORE_BESSEL, score);
        }

        @Override
        public int getTopScore() {
            return GameSharedPreference.getTopScore(GameSharedPreference.SP_KEY_TOP_SCORE_BESSEL);
        }
    },
    FLUSH {
        @Override
        public BaseMovePath getMovePath() {
            return MovePathFactory.createFlushMovePath();
        }

        @Override
        public void saveTopScore(int score) {
            GameSharedPreference.saveTopScore(GameSharedPreference.SP_KEY_TOP_SCORE_FLUSH, score);
        }

        @Override
        public int getTopScore() {
            return GameSharedPreference.getTopScore(GameSharedPreference.SP_KEY_TOP_SCORE_FLUSH);
        }
    };

    /**
     * 获取移动路径
     * @return 移动路径
     */
    public abstract BaseMovePath getMovePath();

    /**
     * 保存最高游戏分数
     * @param score 游戏分数
     */
    public abstract void saveTopScore(int score);

    /**
     * 获取最高游戏分数
     * @return 最高游戏分数
     */
    public abstract int getTopScore();
}
