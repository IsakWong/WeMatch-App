package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.Match;

/**
 * Created by isakwong on 2017/7/16.
 */

public class GetMatchInfoSession extends Session<GetMatchInfoSession.Request, GetMatchInfoSession.Response> {

    Listener listener;


    public interface Listener {
        void onGetMatch(Match match);
        void onGetMatchError(String cause);
    }

    public GetMatchInfoSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onGetMatch(response.match);
        if (response.result == false)
            listener.onGetMatchError(response.description);
    }

    public class Response extends Session.Response{

        public Match match;


    }

    public class Request extends Session.Request{
        public String type = "getMatchInfo";
        public int matchID;
    }
}
