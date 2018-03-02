package nullref.dlut.wematch.utils;

import android.app.Application;

import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * Created by IsakWong on 2017/5/16.
 */

public class WeMatchApplication extends Application {

    private static WeMatchApplication instance;

    public static WeMatchApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //getAllLabelsFromServer database
        LogToFile.init(this);
        ConfigDbHelper.getInstance();
        UserDbHelper.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ConfigDbHelper.getInstance().close();
        UserDbHelper.getInstance().close();
    }
}
