package nullref.dlut.wematch.business.login;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.utils.WeMatchApplication;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * Created by IsakWong on 2017/5/24.
 */

public class LogInPresenter implements LogInActivityContract.Presenter {

    LogInActivityContract.View view;
    String email;
    String pwd;

    public LogInPresenter(LogInActivityContract.View view) {

        this.view = view;
    }


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


    LoginSession loginSession = new LoginSession(this);
    public void logIn(final String email, final String pwd) {
        this.email = email;
        this.pwd = Utils.md5(pwd);
        loginSession.request.email = this.email;
        loginSession.request.pwd = this.pwd;
        loginSession.send();
    }


}
