package nullref.dlut.wematch.business.splash;

import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * Created by IsakWong on 2017/7/9.
 */

public class SplashPresenter implements SplashActivityContract.Presenter {


    SplashActivityContract.View view;
    LoginSession loginSession = new LoginSession(this);

    public SplashPresenter(SplashActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void onLogin(LoginSession.Response response) {

        view.loginSuccess();
    }

    @Override
    public void onLoginError(String cause) {

        view.loginFailed();
    }

    public void logInFromLocalDB() {
        ConfigDbHelper configDb = ConfigDbHelper.getInstance();
        UserDbHelper db = UserDbHelper.getInstance();
        String userEmail = configDb.query("default_user");
        if (userEmail == null) {
            view.loginFailed();
            return;
        }
        UserDbHelper.UserPwd user = db.getUserPwd(userEmail);
        if (user == null) {
            view.loginFailed();
        } else {
            loginSession.request.email = user.email;
            loginSession.request.pwd = user.md5pwd;
            loginSession.send();
        }
    }

    public void checkFirstRun() {
        ConfigDbHelper config = ConfigDbHelper.getInstance();
        String value = config.query("first_run");
        if (value.equals("true")) {
            view.firstRun();
        } else {
            String autoLog = config.query("auto_login");
            if (autoLog.equals("true")) {
                logInFromLocalDB();
            }
            view.notFirstRun();
        }
        config.update("first_run", "false");
    }

}
