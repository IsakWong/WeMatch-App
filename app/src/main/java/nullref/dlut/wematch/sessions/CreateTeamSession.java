package nullref.dlut.wematch.sessions;

/**
 * Created by isakwong on 2017/7/16.
 */

public class CreateTeamSession extends Session<CreateTeamSession.Request, CreateTeamSession.Response> {

    Listener listener;


    public CreateTeamSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onCreateTeam();
        if (response.result == false)
            listener.onCreateTeamError(response.description);
    }

    public interface Listener {
        void onCreateTeam();

        void onCreateTeamError(String cause);
    }

    public class Response extends Session.Response {


    }

    public class Request extends Session.Request {
        public String type = "createTeam";
        public int matchID;
        public int number;
        public String teamName;
        public String teamInfo;
    }
}
