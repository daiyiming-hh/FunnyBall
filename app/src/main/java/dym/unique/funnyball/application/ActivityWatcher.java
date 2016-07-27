package dym.unique.funnyball.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import dym.unique.funnyball.application.MainApplication;

/**
 * Created by daiyiming on 2016/4/8.
 */
public class ActivityWatcher {

    private static List<Activity> mActivityList = new ArrayList<>();

    public static void onActivityCreated(Activity activity) {
        for (Activity innerActivity : mActivityList) {
            if (innerActivity == activity) {
                return;
            }
        }
        mActivityList.add(activity);
    }

    public static void onActivityDestory(Activity activity) {
        mActivityList.remove(activity);
        if (mActivityList.size() == 0) {
            MainApplication.onDestory();
        }
    }
}
