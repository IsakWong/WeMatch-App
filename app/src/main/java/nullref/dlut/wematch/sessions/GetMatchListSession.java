package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.MatchListInfo;

/**
 * Created by isakwong on 2017/7/16.
 */

public class GetMatchListSession extends Session<GetMatchListSession.Request, GetMatchListSession.Response> {

    Listener listener;


    public GetMatchListSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            if (response.matches.length == 0) {
                listener.onGetMatchList(response.matches, request.matchID);
            } else {
                listener.onGetMatchList(response.matches, response.matches[response.matches.length - 1].matchID);
            }
        else ;
        if (response.result == false)
            listener.onGetMatchListError(response.description, request.matchID);
    }

    public interface Listener {
        void onGetMatchList(MatchListInfo[] matches, int matchID);

        void onGetMatchListError(String cause, int matchID);
    }

    public class Response extends Session.Response {

        public MatchListInfo[] matches;


    }

    public class Request extends Session.Request {
        public String type = "getMatchList";
        public int matchID;
        public int filter; //0表示不筛选，1表示获取与订阅标签相同标签的比赛，2表示获取订阅的比赛（follow match），3表示获取订阅标签对应的比赛和订阅的比赛
    }
}
