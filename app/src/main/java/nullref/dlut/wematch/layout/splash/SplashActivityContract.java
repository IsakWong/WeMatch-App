package nullref.dlut.wematch.layout.splash;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.sessions.LoginSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface SplashActivityContract {
    interface View{

        void firstRun();

        void notFirstRun();

        void loginSuccess();

        void loginFailed();
    }

    interface Presenter {
        void checkFirstRun();
    }
}
