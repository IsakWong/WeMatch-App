package nullref.dlut.wematch.sessions;

/**
 * 通过小队ID加入某个小队
 */

public class JoinTeamSession extends Session<JoinTeamSession.Request, JoinTeamSession.Response> {

    Listener listener;


    public JoinTeamSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onJoinTeam();
        if (response.result == false)
            listener.onJoinTeamError(response.description);
    }

    public interface Listener {
        void onJoinTeam();

        void onJoinTeamError(String cause);
    }

    public class Response extends Session.Response {


    }

    public class Request extends Session.Request {
        public String type = "joinTeam";
        public int teamID;
    }
}
