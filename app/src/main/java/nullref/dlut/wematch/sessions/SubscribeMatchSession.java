package nullref.dlut.wematch.sessions;

import java.util.ArrayList;

/**
 * Created by isakwong on 2017/7/16.
 */

public class SubscribeMatchSession extends Session<SubscribeMatchSession.Request, SubscribeMatchSession.Response> {

    Listener listener;


    public SubscribeMatchSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onFollowMatch();
        if (response.result == false) {
            if (response.description.equals("match already subscribed"))
                listener.onFollowMatchError("您已订阅该比赛。");
        }
    }

    public interface Listener {
        void onFollowMatch();

        void onFollowMatchError(String cause);
    }

    public class Response extends Session.Response {

    }

    public class Request extends Session.Request {
        public String type = "followMatches";
        public ArrayList<Integer> matchID = new ArrayList<>();

    }
}
