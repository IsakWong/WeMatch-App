package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.Label;

/**
 * Created by isakwong on 2017/7/16.
 */

public class GetAllLabelsSession extends Session<GetAllLabelsSession.Request, GetAllLabelsSession.Response> {

    Listener listener;


    public GetAllLabelsSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onGetLabels(response.labels);
        if (response.result == false)
            listener.onGetLabelsError(response.description);
    }

    public interface Listener {
        void onGetLabels(Label[] labels);

        void onGetLabelsError(String cause);
    }

    public class Response extends Session.Response {
        public Label[] labels;

    }

    public class Request extends Session.Request {
        public String type = "getLabels";

    }
}

