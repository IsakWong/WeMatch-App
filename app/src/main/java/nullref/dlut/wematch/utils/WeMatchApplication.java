package nullref.dlut.wematch.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.business.login.LogInPresenter;
import nullref.dlut.wematch.business.login.LogInActivityContract;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * Created by IsakWong on 2017/5/16.
 */

public class WeMatchApplication extends Application {

    private static WeMatchApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //init database
        ConfigDbHelper.getInstance();
        UserDbHelper.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ConfigDbHelper.getInstance().close();
        UserDbHelper.getInstance().close();
    }



    public static WeMatchApplication getInstance() {
        return instance;
    }
}
