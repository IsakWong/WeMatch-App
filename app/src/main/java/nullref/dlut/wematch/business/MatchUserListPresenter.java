package nullref.dlut.wematch.business;

import nullref.dlut.wematch.bean.Match;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.layout.userlist.UserListContract;
import nullref.dlut.wematch.sessions.GetSubscribeUsersOfMatchSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class MatchUserListPresenter implements UserListContract.Presenter {


    //content
    UserListContract.View view;
    Match data;
    GetSubscribeUsersOfMatchSession refreshMatchUserSession = new GetSubscribeUsersOfMatchSession(new GetSubscribeUsersOfMatchSession.Listener() {
        @Override
        public void onGetFollowUsers(UserListInfo[] userListInfo) {
            view.onGetUserList(userListInfo);
        }

        @Override
        public void onGetFollowUsersError(String cause) {

        }
    });

    public UserListContract.View getView() {
        return view;
    }

    public void setView(UserListContract.View view) {
        this.view = view;
    }

    public Match getData() {
        return data;
    }

    public void setData(Match data) {
        this.data = data;
    }

    @Override
    public void refreshUsers() {
        refreshMatchUserSession.request.matchID = data.ID;
        refreshMatchUserSession.request.userID = -1;
        refreshMatchUserSession.send();

    }

    @Override
    public void getMoreUsers() {

    }

    @Override
    public void subscribeUser(int position) {

    }
}
