package nullref.dlut.wematch.business.subscribe;

import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.layout.userlist.UserListContract;
import nullref.dlut.wematch.sessions.GetSubscribeUserSession;


public class SubscribeUserListPresenter implements UserListContract.Presenter{



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

    public void setView(UserListContract.View view) {
        this.view = view;
    }

    public SubscribeUserListPresenter() {
    }

    @Override
    public void getMoreUsers() {

    }

    @Override
    public void refreshUsers() {
        refreshUserListSession.send();

    }

    @Override
    public void subscribeUser(int position) {

    }


}
