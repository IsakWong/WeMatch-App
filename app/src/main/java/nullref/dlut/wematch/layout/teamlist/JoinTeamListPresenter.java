package nullref.dlut.wematch.layout.teamlist;

import nullref.dlut.wematch.bean.Team;
import nullref.dlut.wematch.layout.teamlist.TeamListContract;
import nullref.dlut.wematch.sessions.GetUserJoinTeamsSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class JoinTeamListPresenter implements TeamListContract.Presenter {


    //content
    TeamListContract.View view;

    GetUserJoinTeamsSession session = new GetUserJoinTeamsSession(new GetUserJoinTeamsSession.Listener() {
        @Override
        public void onGetTeamList(Team[] teams) {
            view.onGetTeam(teams);
        }

        @Override
        public void onGetTeamListError(String cause) {

        }
    });

    public void setView(TeamListContract.View view) {
        this.view = view;
    }

    @Override
    public void joinTeam(int position) {

    }

    @Override
    public void getMoreTeam() {

    }

    @Override
    public void refreshTeam() {
        session.send();
    }


}
