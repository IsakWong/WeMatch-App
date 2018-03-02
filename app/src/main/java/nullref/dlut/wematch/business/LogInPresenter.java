package nullref.dlut.wematch.business;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.layout.login.LogInActivityContract;
import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * Created by IsakWong on 2017/5/24.
 */

public class LogInPresenter extends BasePresenter<LogInActivityContract.View> implements LogInActivityContract.Presenter {

    String email;
    String pwd;
    LoginSession loginSession = new LoginSession(new LoginSession.Listener() {
        @Override
        public void onLogin(LoginSession.Response response) {

            view.loginSuccess();
            UserDbHelper userDb = UserDbHelper.getInstance();
            userDb.saveUserPwd(email, pwd);
        }

        @Override
        public void onLoginError(String cause) {
            view.loginFailed(cause);
        }
    });


    public LogInPresenter() {

    }


    public void logIn(final String email, final String pwd) {
        this.email = email;
        this.pwd = Utils.md5(pwd);
        loginSession.request.email = this.email;
        loginSession.request.pwd = this.pwd;
        loginSession.send();
    }


}
