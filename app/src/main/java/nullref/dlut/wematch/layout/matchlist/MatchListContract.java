package nullref.dlut.wematch.layout.matchlist;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.widgets.adapter.MatchListAdapter;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface MatchListContract {
    interface View   extends BaseView{

        void onRequestMatchError(String cause);

        void onMatchAdded(MatchListInfo[] matches);

        void onRereshMarch(MatchListInfo[] matches);

        void onFollowMatch(int position);

        void onFollowMatchError(String cause);

        void setTitle(String title);

        MatchListAdapter getAdapter();

    }

    interface Presenter {
        void initView();

        void refreshMatches();

        void getMoreMatches();

        void subscribeMatch(int position);


    }
}
