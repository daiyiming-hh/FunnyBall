package dym.unique.funnyball.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import dym.unique.funnyball.application.ActivityWatcher;

/**
 * Created by daiyiming on 2016/4/8.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWatcher.onActivityCreated(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityWatcher.onActivityCreated(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityWatcher.onActivityDestroy(this);
    }
}
