package nullref.dlut.wematch.layout.userinfo;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.UserInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface UserInfoContract {
    interface View  extends BaseView {
        void onGetUser(UserInfo userListInfo);

        void onError();
    }

    interface Presenter{
        void getUserInfo();
    }
}
