package nullref.dlut.wematch.layout.teamlist;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.Team;
import nullref.dlut.wematch.widgets.adapter.TeamListAdapter;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface TeamListContract {
    interface View extends BaseView<Presenter>{

        void onGetTeamListError(String cause);
        void onGetTeam(Team[] teams);
        void onJoinTeam(int position);
        void onJoinTeamError(String cause);
        TeamListAdapter getAdapter();

    }

    interface Presenter extends BasePresenter<View> {
        void refreshTeam();
        void getMoreTeam();
        void joinTeam(int position);
    }
}
