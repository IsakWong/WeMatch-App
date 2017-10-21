package nullref.dlut.wematch.business.login;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.sessions.LoginSession;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface LogInActivityContract {
    interface View extends BaseView {

        void loginSuccess();

        void loginFailed(String cause);

    }

    interface Presenter extends LoginSession.Listener {

    }
}
