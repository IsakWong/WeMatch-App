package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.CommitMatch;

/**
 * Created by isakwong on 2017/7/16.
 */

public class CommitMatchSession extends Session<CommitMatchSession.Request, CommitMatchSession.Response> {

    Listener listener;


    public CommitMatchSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onCommitMatch(response.key);
        if (response.result == false)
            listener.onCommitMatchError("提交失败");
    }

    public interface Listener {
        void onCommitMatch(String key);

        void onCommitMatchError(String cause);
    }

    public class Response extends Session.Response {
        public String key;

    }

    public class Request extends Session.Request {
        public String type = "userCommitMatch";
        public CommitMatch commitMatch;//提交的比赛信息


    }
}
