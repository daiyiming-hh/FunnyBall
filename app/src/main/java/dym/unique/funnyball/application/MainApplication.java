package dym.unique.funnyball.application;

import android.app.Application;
import dym.unique.funnyball.meida.SoundsPlayer;
import dym.unique.funnyball.sharedperference.GameSharedPreference;

/**
 * Created by daiyiming on 2016/4/7.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GameSharedPreference.init(this);
        SoundsPlayer.init(this);
    }

    public static void onDestroy() {
        SoundsPlayer.release();
    }

}
