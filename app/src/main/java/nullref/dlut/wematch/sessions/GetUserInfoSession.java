package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.UserInfo;

/**
 * 获取用户信息的会话
 */

public class GetUserInfoSession extends Session<GetUserInfoSession.Request, GetUserInfoSession.Response> {

    Listener listener;


    public GetUserInfoSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onGetUserInfo(response.userInfo);
        if (response.result == false)
            listener.onGetUserInfoError(response.description);
    }

    public interface Listener {
        void onGetUserInfo(UserInfo user);

        void onGetUserInfoError(String cause);
    }

    public class Response extends Session.Response {
        public UserInfo userInfo;
    }

    public class Request extends Session.Request {
        public String type = "getUserInfo";
        public int userID;
    }
}
