package nullref.dlut.wematch.layout.userlist;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.UserListInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface UserListContract {
    interface View extends BaseView<Presenter> {

        void onGetUserListError(String cause);

        void onGetUserList(UserListInfo[] userListInfo);

        void onFollowUser(int position);

        void onFollowUserError(String cause);

    }

    interface Presenter extends BasePresenter<View> {
        void refreshUsers();

        void getMoreUsers();

        void subscribeUser(int position);
    }
}
