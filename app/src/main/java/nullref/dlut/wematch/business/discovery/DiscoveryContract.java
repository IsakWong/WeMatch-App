package nullref.dlut.wematch.business.discovery;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.bean.UserListInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface DiscoveryContract {
    interface View extends BaseView<Presenter> {
        void onMatchesAdded(MatchListInfo[] matches);
        void onUsersAdded(UserListInfo[] userListInfos);
        void onSubscribedLabelsAdded(Label[] labels);
        void onError(String cause);


    }

    interface Presenter extends BasePresenter<View>{
        void getMatches();
        void getSubscibedLabels();
        void getUsers();

    }
}
