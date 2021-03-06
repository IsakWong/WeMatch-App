package nullref.dlut.wematch.layout.info;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.layout.info.SelfSpacePageContract;
import nullref.dlut.wematch.sessions.GetUserInfoSession;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class SelfSpacePagePresenter extends BasePresenter<SelfSpacePageContract.View> implements SelfSpacePageContract.Presenter {

    GetUserInfoSession getUserInfoSession = new GetUserInfoSession(new GetUserInfoSession.Listener() {
        @Override
        public void onGetUserInfo(UserInfo userListInfo) {
            view.onGetUserInfo(userListInfo);
            ConfigDbHelper config = ConfigDbHelper.getInstance();
            config.update("avatar_url", userListInfo.avatarUrl);
            config.update("need_update", "false");
        }

        @Override
        public void onGetUserInfoError(String cause) {

        }
    });

    public SelfSpacePagePresenter() {

    }


    @Override
    public void getUserInfo() {

        getUserInfoSession.request.userID = -1;
        getUserInfoSession.send();

    }


}
