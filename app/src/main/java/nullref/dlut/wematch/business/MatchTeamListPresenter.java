package nullref.dlut.wematch.business;

import nullref.dlut.wematch.bean.Match;
import nullref.dlut.wematch.bean.Team;
import nullref.dlut.wematch.layout.teamlist.TeamListContract;
import nullref.dlut.wematch.sessions.GetTeamsOfMatchtSession;
import nullref.dlut.wematch.sessions.JoinTeamSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class MatchTeamListPresenter implements TeamListContract.Presenter {


    //content
    TeamListContract.View view;
    Match match;
    JoinTeamSession joinTeamSession = new JoinTeamSession(new JoinTeamSession.Listener() {
        @Override
        public void onJoinTeam() {

        }

        @Override
        public void onJoinTeamError(String cause) {

        }
    });
    GetTeamsOfMatchtSession refreshTeamListSession = new GetTeamsOfMatchtSession(new GetTeamsOfMatchtSession.Listener() {
        @Override
        public void onGetTeamList(Team[] teams) {
            view.onGetTeam(teams);
        }

        @Override
        public void onGetTeamListError(String cause) {

        }
    });

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setView(TeamListContract.View view) {
        this.view = view;
    }

    @Override
    public void refreshTeam() {

        refreshTeamListSession.request.matchID = match.ID;
        refreshTeamListSession.request.teamID = -1;
        refreshTeamListSession.send();
    }

    @Override
    public void getMoreTeam() {

    }

    @Override
    public void joinTeam(int teamID) {
        joinTeamSession.request.teamID = teamID;
        joinTeamSession.send();
    }

}
