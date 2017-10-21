package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.utils.NetworkManager;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import nullref.dlut.wematch.utils.database.UserDbHelper;

/**
 * 登陆会话，其中密码为md5加密后的密码
 */

public class LoginSession extends Session<LoginSession.Request, LoginSession.Response> {

    Listener listener;

    public LoginSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        if (response.result == true) {

            ConfigDbHelper db = ConfigDbHelper.getInstance();
            UserDbHelper userDb = UserDbHelper.getInstance();
            db.update("auto_login", "true");
            db.update("default_user", request.email);
            userDb.saveUserPwd(request.email, request.pwd);
            NetworkManager.setUserAuth(response.auth);
            listener.onLogin(response);
        }
        if (response.result == false) {
            if (response.description.equals("pwd err")) {
                listener.onLoginError("密码有误，请重新输入");
                return;
            }
            listener.onLoginError("未知错误");
        }

    }

    public interface Listener {
        void onLogin(Response response);

        void onLoginError(String cause);
    }

    public class Response extends Session.Response {
        public String auth;
    }

    public class Request extends Session.Request {
        public String type = "login";
        public String email;
        public String pwd;

    }
}
