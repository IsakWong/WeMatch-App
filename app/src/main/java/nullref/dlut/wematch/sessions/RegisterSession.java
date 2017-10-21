package nullref.dlut.wematch.sessions;

/**
 * 注册会话
 */

public class RegisterSession extends Session<RegisterSession.Request, RegisterSession.Response> {

    Listener listener;


    public RegisterSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        if (response.result == true)
            listener.onRegister(response);
        if (response.result == false) {
            if (response.description.equals("usr existed")) {
                listener.onRegisterError("注册失败，您的邮箱已经被注册了。");
                return;
            }
            listener.onRegisterError("未知错误");

        }
    }

    //回调方法接口
    public interface Listener {
        void onRegister(Response response);

        void onRegisterError(String cause);
    }

    public class Response extends Session.Response {

    }

    public class Request extends Session.Request {
        public String type = "register";
        public String email;
        public String name;
        public String pwd;


    }
}
