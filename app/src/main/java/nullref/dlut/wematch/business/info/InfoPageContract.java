package nullref.dlut.wematch.business.info;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.UserListInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface InfoPageContract {
    interface View extends BaseView<Presenter>{
        void onGetUserInfo(UserListInfo userListInfoInfo);
    }

    interface Presenter extends BasePresenter<View>{
        void getUserInfo();

    }
}
