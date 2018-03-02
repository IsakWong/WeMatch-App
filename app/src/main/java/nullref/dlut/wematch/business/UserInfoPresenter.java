package nullref.dlut.wematch.business;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.layout.userinfo.UserInfoContract;
import nullref.dlut.wematch.sessions.GetUserInfoSession;

/**
 * Created by isakwong on 2017/10/16.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {


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


    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void getUserInfo() {
        getUserInfoSession.request.userID = this.userId;
        getUserInfoSession.send();
    }
}
