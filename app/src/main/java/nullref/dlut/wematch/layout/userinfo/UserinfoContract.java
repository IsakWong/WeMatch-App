package nullref.dlut.wematch.layout.userinfo;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.bean.UserListInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface UserinfoContract {
    interface View extends BaseView<Presenter> {
        void onGetUser(UserInfo userListInfo);
        void onError();
    }

    interface Presenter extends BasePresenter<View> {
        void getUserInfo();
    }
}
