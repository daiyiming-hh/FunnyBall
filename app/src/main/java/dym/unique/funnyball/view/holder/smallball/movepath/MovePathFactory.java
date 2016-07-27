package dym.unique.funnyball.view.holder.smallball.movepath;

import java.lang.reflect.Constructor;
import java.util.Random;

import android.util.Log;
import dym.unique.funnyball.view.GameView;

/**
 * Created by daiyiming on 2016/5/4.
 */
public class MovePathFactory {
    private static Random sRandom = new Random();
    private static Class[] sConstructParamTypes = new Class[] {
            float.class, float.class, float.class, float.class
    };

    private static BaseMovePath createMovePath(Class<? extends BaseMovePath> className) {
        try {
            Constructor<? extends BaseMovePath> constructor = className.getConstructor(sConstructParamTypes);
            switch (sRandom.nextInt(2)) {
                default:
                case 0: {
                    Object[] params = new Object[] {0, sRandom.nextInt(GameView.height()), GameView.width() / 2, GameView.height() / 2};
                    return constructor.newInstance(params);
                }
                case 1: {
                    Object[] params = new Object[] {GameView.width(), sRandom.nextInt(GameView.height()), GameView.width() / 2, GameView.height() / 2};
                    return constructor.newInstance(params);
                }
            }
        } catch (Exception e) {
            Log.e("tag", "createMovePath: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    public static BaseMovePath createFlushMovePath() {
        return createMovePath(FlushMovePath.class);
    }

    public static BaseMovePath createLineMovePath() {
        return createMovePath(LineMovePath.class);
    }

    public static BaseMovePath createBesselMovePath() {
        return createMovePath(BesselMovePath.class);
    }

}
