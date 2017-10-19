package nullref.dlut.wematch.business.info;

import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.sessions.GetUserInfoSession;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class InfoPagePresenter implements InfoPageContract.Presenter{

    InfoPageContract.View view;

    @Override
    public void setView(InfoPageContract.View view) {
        this.view = view;
    }

    GetUserInfoSession getUserInfoSession = new GetUserInfoSession(new GetUserInfoSession.Listener() {
        @Override
        public void onGetUserInfo(UserListInfo userListInfo) {
            view.onGetUserInfo(userListInfo);
            ConfigDbHelper config = ConfigDbHelper.getInstance();
            config.update("avatar_url", userListInfo.avatarUrl);
            config.update("need_update","false");
        }

        @Override
        public void onGetUserInfoError(String cause) {

        }
    });
    @Override
    public void getUserInfo() {

        getUserInfoSession.request.userID = -1;
        getUserInfoSession.send();

    }

    public InfoPagePresenter(){

    }



}
