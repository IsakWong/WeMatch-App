package nullref.dlut.wematch.business.register;

import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.sessions.RegisterSession;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * Created by IsakWong on 2017/7/1.
 */

public class RegisterPresenter implements RegisterActivityContract.Presenter {


    public int gender;//0女1男2保密
    RegisterActivityContract.View view;
    String email;
    String pwd;
    LoginSession loginSession = new LoginSession(this);
    RegisterSession session = new RegisterSession(this);
    private boolean registering = false;


    public RegisterPresenter(RegisterActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void onRegisterError(String cause) {
        view.registerFailed(cause);
    }

    @Override
    public void onRegister(RegisterSession.Response response) {
        view.registerSuccess();
        loginSession.request.email = email;
        loginSession.request.pwd = pwd;
        loginSession.send();

    }

    @Override
    public void onLoginError(String cause) {
        view.loginFailed(cause);
    }

    @Override
    public void onLogin(LoginSession.Response response) {
        view.loginSuccess();
        UserDbHelper userDb = UserDbHelper.getInstance();
        userDb.saveUserPwd(email, pwd);
        userDb.close();
    }

    @Override
    public void register(String email, String pwd, String name) {
        this.email = email;
        this.pwd = Utils.md5(pwd);
        session.request.email = this.email;
        session.request.pwd = this.pwd;
        session.request.name = name;
        session.send();
    }


}
