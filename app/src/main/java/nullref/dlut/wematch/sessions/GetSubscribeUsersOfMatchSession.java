package nullref.dlut.wematch.sessions;

import nullref.dlut.wematch.bean.UserListInfo;

/**
 * Created by isakwong on 2017/7/16.
 */

public class GetSubscribeUsersOfMatchSession extends Session<GetSubscribeUsersOfMatchSession.Request, GetSubscribeUsersOfMatchSession.Response> {

    Listener listener;


    public interface Listener {
        void onGetFollowUsers(UserListInfo[] userListInfo);
        void onGetFollowUsersError(String cause);
    }

    public GetSubscribeUsersOfMatchSession(Listener listener) {
        this.listener = listener;
        request = new Request();
        response = new Response();
    }

    @Override
    public void success(Response response) {
        super.success(response);
        if (response.result == true)
            listener.onGetFollowUsers(response.userListInfo);
        if (response.result == false)
            listener.onGetFollowUsersError(response.description);
    }

    public class Response extends Session.Response {
        public UserListInfo[] userListInfo;
    }

    public class Request extends Session.Request{
        public String type = "getMatchFollowUser";
        public int matchID;//获取该ID比赛的关注者列表
        public int userID;
    }
}
