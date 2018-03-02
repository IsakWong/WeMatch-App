package nullref.dlut.wematch.layout.info;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.UserInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface SelfSpacePageContract {
    interface View{
        void onGetUserInfo(UserInfo userListInfoInfo);
    }

    interface Presenter{
        void getUserInfo();

    }
}
