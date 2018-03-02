package nullref.dlut.wematch.layout.login;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.sessions.LoginSession;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface LogInActivityContract {
    interface View {

        void loginSuccess();

        void loginFailed(String cause);

    }

    interface Presenter{
        void logIn(String emailName,String md5pwd);
        void setView(View view);

    }
}
