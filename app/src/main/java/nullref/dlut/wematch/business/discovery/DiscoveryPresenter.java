package nullref.dlut.wematch.business.discovery;

import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.sessions.GetMatchListSession;
import nullref.dlut.wematch.sessions.GetSubscribeUserSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class DiscoveryPresenter implements DiscoveryContract.Presenter {


    //content
    DiscoveryContract.View view;
    GetMatchListSession getMatchListSession = new GetMatchListSession(new GetMatchListSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches, int matchID) {
            view.onMatchesAdded(matches);
        }

        @Override
        public void onGetMatchListError(String cause, int matchID) {

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

    @Override
    public void setView(DiscoveryContract.View view) {
        this.view = view;
    }

    @Override
    public void getMatches() {
        getMatchListSession.request.matchID = -1;
        getMatchListSession.request.filter = 1;
        getMatchListSession.send();
    }

    @Override
    public void getUsers() {
        getSubscribeUserSession.request.userID = -1;
        getSubscribeUserSession.send();
    }

    @Override
    public void getSubscibedLabels() {


    }
}
