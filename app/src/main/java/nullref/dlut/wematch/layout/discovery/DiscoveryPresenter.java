package nullref.dlut.wematch.layout.discovery;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.layout.discovery.DiscoveryContract;
import nullref.dlut.wematch.sessions.GetRecommendedMatchesSession;
import nullref.dlut.wematch.sessions.GetSubscribeUserSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class DiscoveryPresenter extends BasePresenter<DiscoveryContract.View>implements DiscoveryContract.Presenter {


    GetRecommendedMatchesSession getMatchListSession = new GetRecommendedMatchesSession(new GetRecommendedMatchesSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches) {
            view.onMatchesAdded(matches);
        }

        @Override
        public void onGetMatchListError(String cause) {

        }
    });

    GetSubscribeUserSession getSubscribeUserSession = new GetSubscribeUserSession(new GetSubscribeUserSession.Listener() {
        @Override
        public void onGetFollowUsers(UserListInfo[] userListInfo) {
            view.onUsersAdded(userListInfo);
        }

        @Override
        public void onGetFollowUsersError(String cause) {

        }
    });


    public void setView(DiscoveryContract.View view) {
        this.view = view;
    }

    @Override
    public void getMatches() {
        getMatchListSession.send();
    }

    @Override
    public void getUsers() {
        getSubscribeUserSession.request.userID = -1;
        getSubscribeUserSession.send();
    }

    @Override
    public void getSubscribedLabels() {


    }
}
