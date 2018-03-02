package nullref.dlut.wematch.layout.matchinfo;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.Match;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface MatchInfoContract {
    interface View {

        void onError(String cause);

        void onMatchInfo(Match match);

        void onCreateTeam();

        void onCreateTeamError(String cause);

        void onFollowMatch();

        void onFollowMatchError(String cause);

    }

    interface Presenter {
        void getMatchInfo();

        void followMatch();

        void createTeam(String name, String info, int person);

    }
}
