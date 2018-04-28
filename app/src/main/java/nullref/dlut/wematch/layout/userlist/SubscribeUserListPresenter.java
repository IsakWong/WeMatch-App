package nullref.dlut.wematch.layout.userlist;

import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.layout.userlist.UserListContract;
import nullref.dlut.wematch.sessions.GetSubscribeUserSession;


public class SubscribeUserListPresenter implements UserListContract.Presenter {


    //content
    UserListContract.View view;

    GetSubscribeUserSession refreshUserListSession = new GetSubscribeUserSession(new GetSubscribeUserSession.Listener() {
        @Override
        public void onGetFollowUsers(UserListInfo[] userListInfo) {
            view.onGetUserList(userListInfo);
        }

        @Override
        public void onGetFollowUsersError(String cause) {

        }
    });

    public SubscribeUserListPresenter() {
    }

    public void setView(UserListContract.View view) {
        this.view = view;
    }

    @Override
    public void getMoreUsers() {

    }

    @Override
    public void refreshUsers() {
        refreshUserListSession.request.userID = -1;
        refreshUserListSession.send();

    }

    @Override
    public void subscribeUser(int position) {

    }


}
