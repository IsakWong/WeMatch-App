package nullref.dlut.wematch.sessions;

/**
 * Created by isakwong on 2017/7/16.
 */

public class SubscribeUserSession extends Session<SubscribeUserSession.Request, SubscribeUserSession.Response> {

    Listener listener;


    public SubscribeUserSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onFollowUser();
        if (response.result == false) {
            if (response.description.equals("userListInfo already subscribed"))
                listener.onFollowUserError("您已订阅该用户。");
        }
    }

    public interface Listener {
        void onFollowUser();

        void onFollowUserError(String cause);
    }

    public class Response extends Session.Response {

    }

    public class Request extends Session.Request {
        public String type = "followUsers";
        public int[] userID;

    }
}
