package dym.unique.funnyball.view.holder.smallball.movepath;

import java.util.Random;

import dym.unique.funnyball.view.GameView;

/**
 * Created by daiyiming on 2016/5/4.
 */
public class MovePathFactory {
    private static Random sRandom = new Random();

    private static int[] getParams() {
        switch (sRandom.nextInt(2)) {
            default:
            case 0: {
                return new int[] {0, sRandom.nextInt(GameView.height()), GameView.width() / 2, GameView.height() / 2};
            }
            case 1: {
                return new int[] {GameView.width(), sRandom.nextInt(GameView.height()), GameView.width() / 2, GameView.height() / 2};
            }
        }
    }

    public static BaseMovePath createFlushMovePath() {
        int[] params = getParams();
        return new FlushMovePath(params[0], params[1], params[2], params[3]);
    }

    public static BaseMovePath createLineMovePath() {
        int[] params = getParams();
        return new LineMovePath(params[0], params[1], params[2], params[3]);
    }

    public static BaseMovePath createBesselMovePath() {
        int[] params = getParams();
        return new BesselMovePath(params[0], params[1], params[2], params[3]);
    }

}
