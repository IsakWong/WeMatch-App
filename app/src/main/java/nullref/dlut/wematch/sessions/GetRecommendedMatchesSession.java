package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.MatchListInfo;


/**
 * 获取推荐的比赛列表，默认为获取前4个
 */
public class GetRecommendedMatchesSession extends Session<GetRecommendedMatchesSession.Request, GetRecommendedMatchesSession.Response> {

    Listener listener;


    public GetRecommendedMatchesSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true) {
            listener.onGetMatchList(response.matches);
        } else {
            listener.onGetMatchListError(response.description);
        }

    }

    public interface Listener {
        void onGetMatchList(MatchListInfo[] matches);

        void onGetMatchListError(String cause);
    }


    public class Response extends Session.Response {

        public MatchListInfo[] matches;


    }

    public class Request extends Session.Request {
        public String type = "getRecommendedMatchList";
    }
}


