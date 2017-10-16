package nullref.dlut.wematch.business.splash;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.sessions.RegisterSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface SplashActivityContract {
    interface View extends BaseView{

        void firstRun();
        void notFirstRun();
        void loginSuccess();
        void loginFailed();
    }
    interface Presenter extends LoginSession.Listener{
    }
}
