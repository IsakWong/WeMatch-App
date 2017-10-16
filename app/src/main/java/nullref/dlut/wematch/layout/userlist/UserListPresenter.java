package nullref.dlut.wematch.layout.userlist;

import nullref.dlut.wematch.bean.Match;
import nullref.dlut.wematch.bean.User;
import nullref.dlut.wematch.sessions.GetSubscribeUsersOfMatchSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class UserListPresenter implements UserListContract.Presenter{


    public UserListContract.View getView() {
        return view;
    }

    public void setView(UserListContract.View view) {
        this.view = view;
    }

    //content
    UserListContract.View view;

    public Match getData() {
        return data;
    }

    public void setData(Match data) {
        this.data = data;
    }

    Match data;

    GetSubscribeUsersOfMatchSession refreshMatchUserSession = new GetSubscribeUsersOfMatchSession(new GetSubscribeUsersOfMatchSession.Listener() {
        @Override
        public void onGetFollowUsers(User[] user) {
            view.onGetUserList(user);
        }

        @Override
        public void onGetFollowUsersError(String cause) {

        }
    });


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
