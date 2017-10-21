package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.Team;

/**
 * Created by isakwong on 2017/7/16.
 */

public class GetTeamsOfMatchtSession extends Session<GetTeamsOfMatchtSession.Request, GetTeamsOfMatchtSession.Response> {

    Listener listener;


    public GetTeamsOfMatchtSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onGetTeamList(response.teams);
        if (response.result == false)
            listener.onGetTeamListError(response.description);
    }

    public interface Listener {
        void onGetTeamList(Team[] teams);

        void onGetTeamListError(String cause);
    }

    public class Response extends Session.Response {

        public Team[] teams;


    }

    public class Request extends Session.Request {
        public String type = "getTeamList";
        public int matchID;
        public int teamID;
    }
}
