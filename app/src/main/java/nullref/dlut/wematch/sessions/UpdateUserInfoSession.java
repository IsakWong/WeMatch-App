package nullref.dlut.wematch.sessions;


import java.util.ArrayList;

import nullref.dlut.wematch.utils.database.ConfigDbHelper;

/**
 * 更新用户数据会话
 */

public class UpdateUserInfoSession extends Session<UpdateUserInfoSession.Request, UpdateUserInfoSession.Response> {

    Listener listener;


    public interface Listener {
        void onUpdateUserInfo(Response response);
        void onUpdateUserInfoError(String cause);
    }

    public UpdateUserInfoSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onUpdateUserInfo(response);
        if (response.result == false)
            listener.onUpdateUserInfoError(response.description);

    }

    public class Response extends Session.Response{

        public String key;
    }

    public static class Pair{
        public String key;
        public String value;
        public Pair(String key,String value){
            this.key = key;
            this.value = value;
        }
        public Pair(){

        }
    }

    public class Request extends Session.Request{

        public String type = "updateUserInfo";
        public ArrayList<Pair> pairs = new ArrayList<>();
        //如果值为空，则不更新
    }
}
