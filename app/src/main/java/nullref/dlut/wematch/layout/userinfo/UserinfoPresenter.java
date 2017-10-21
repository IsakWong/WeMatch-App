package nullref.dlut.wematch.layout.userinfo;

import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.sessions.GetUserInfoSession;

/**
 * Created by isakwong on 2017/10/16.
 */

public class UserInfoPresenter implements UserInfoContract.Presenter {


    UserInfoContract.View view;
    int userId = -1;

    GetUserInfoSession getUserInfoSession = new GetUserInfoSession(new GetUserInfoSession.Listener() {
        @Override
        public void onGetUserInfo(UserInfo user) {
            view.onGetUser(user);
        }

        @Override
        public void onGetUserInfoError(String cause) {

        }
    });

    @Override
    public void setView(UserInfoContract.View view) {
        this.view = view;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void getUserInfo() {
        getUserInfoSession.request.userID = this.userId;
        getUserInfoSession.send();
    }
}
