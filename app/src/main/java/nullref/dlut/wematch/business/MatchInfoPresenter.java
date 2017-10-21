package nullref.dlut.wematch.business;

import nullref.dlut.wematch.bean.Match;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.layout.matchinfo.MatchInfoContract;
import nullref.dlut.wematch.sessions.CreateTeamSession;
import nullref.dlut.wematch.sessions.GetMatchInfoSession;
import nullref.dlut.wematch.sessions.SubscribeMatchSession;

public class MatchInfoPresenter implements MatchInfoContract.Presenter {


    MatchListInfo matchListInfo;
    Match match;
    MatchInfoContract.View view;
    CreateTeamSession createTeamSession = new CreateTeamSession(new CreateTeamSession.Listener() {
        @Override
        public void onCreateTeam() {

        }

        @Override
        public void onCreateTeamError(String cause) {

        }
    });
    GetMatchInfoSession getMatchInfoSession = new GetMatchInfoSession(new GetMatchInfoSession.Listener() {
        @Override
        public void onGetMatch(Match match) {
            view.onMatchInfo(match);
        }

        @Override
        public void onGetMatchError(String cause) {

        }
    });
    SubscribeMatchSession subscribeMatchSession = new SubscribeMatchSession(new SubscribeMatchSession.Listener() {
        @Override
        public void onFollowMatch() {

        }

        @Override
        public void onFollowMatchError(String cause) {

        }
    });

    public MatchInfoPresenter() {

    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public MatchListInfo getMatchListInfo() {
        return matchListInfo;
    }

    public void setMatchListInfo(MatchListInfo matchListInfo) {
        this.matchListInfo = matchListInfo;
    }

    public MatchInfoContract.View getView() {
        return view;
    }

    public void setView(MatchInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void getMatchInfo() {
        getMatchInfoSession.request.matchID = matchListInfo.matchID;
        getMatchInfoSession.send();
    }

    @Override
    public void createTeam(String name, String info, int person) {
        createTeamSession.request.matchID = match.ID;
        createTeamSession.request.teamName = name;
        createTeamSession.request.teamInfo = info;
        createTeamSession.request.number = person;
        createTeamSession.send();
    }

    @Override
    public void followMatch() {
        subscribeMatchSession.request.matchID.clear();
        subscribeMatchSession.request.matchID.add(match.ID);
        subscribeMatchSession.send();

    }


}
