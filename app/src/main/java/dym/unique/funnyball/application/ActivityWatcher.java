package dym.unique.funnyball.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

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

    public static void onActivityDestroy(Activity activity) {
        mActivityList.remove(activity);
        if (mActivityList.size() == 0) {
            MainApplication.onDestroy();
        }
    }
}
