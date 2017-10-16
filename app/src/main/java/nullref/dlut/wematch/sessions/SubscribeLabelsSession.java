package nullref.dlut.wematch.sessions;

import java.util.ArrayList;

/**
 * Created by isakwong on 2017/7/16.
 */

public class SubscribeLabelsSession extends Session<SubscribeLabelsSession.Request, SubscribeLabelsSession.Response> {

    Listener listener;


    public interface Listener {
        void onFollowLabels();
        void onFollowLabelsError(String cause);
    }

    public SubscribeLabelsSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onFollowLabels();
        if (response.result == false) {
            if (response.description.equals("labels already subscribed"))
                listener.onFollowLabelsError("您已订阅该标签");

        }
    }

    public class Response extends Session.Response{

    }

    public class Request extends Session.Request{
        public String type = "followLabels";
        public ArrayList<Integer> labelsID = new ArrayList<>();

    }
}
