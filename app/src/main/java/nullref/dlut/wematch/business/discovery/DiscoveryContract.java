package nullref.dlut.wematch.business.discovery;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.bean.Match;
import nullref.dlut.wematch.bean.MatchListInfo;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface DiscoveryContract {
    interface View extends BaseView<Presenter> {
        void onMatchesAdded(MatchListInfo[] matches);
        void onSubscribedLabelsAdded(Label[] labels);
        void onError(String cause);


    }

    interface Presenter extends BasePresenter<View>{
        void getMatches();
        void getSubscibedLabels();

    }
}
