package nullref.dlut.wematch.business.subscribe;

import java.util.ArrayList;

import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.layout.matchlist.MatchListContract;
import nullref.dlut.wematch.sessions.GetMatchListSession;
import nullref.dlut.wematch.widgets.adapter.MatchListAdapter;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class SubscribeMatchListPresenter implements MatchListContract.Presenter {

    MatchListContract.View view;
    GetMatchListSession getSubscribeMatchList = new GetMatchListSession(new GetMatchListSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches, int matchID) {
            view.onRereshMarch(matches);
        }

        @Override
        public void onGetMatchListError(String cause, int matchID) {

        }
    });
    GetMatchListSession getMoreSubscribeMatchList = new GetMatchListSession(new GetMatchListSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches, int matchID) {
            view.onMatchAdded(matches);
        }

        @Override
        public void onGetMatchListError(String cause, int matchID) {

        }
    });

    public SubscribeMatchListPresenter() {

    }

    @Override
    public void initView() {
        view.setTitle("关注的比赛");
    }

    @Override
    public void refreshMatches() {
        getSubscribeMatchList.request.filter = 2;
        getSubscribeMatchList.request.matchID = -1;
        getSubscribeMatchList.send();
    }

    @Override
    public void getMoreMatches() {
        getMoreSubscribeMatchList.request.filter = 2;
        MatchListAdapter adapter = view.getAdapter();
        ArrayList<MatchListInfo> datas = adapter.getDatas();
        MatchListInfo last = null;
        if (datas.size() > 0) {
            last = datas.get(datas.size() - 1);
        }
        if (null == last) {
            getMoreSubscribeMatchList.request.matchID = -1;
        } else {
            getMoreSubscribeMatchList.request.matchID = last.matchID;
        }
        getMoreSubscribeMatchList.send();

    }

    @Override
    public void subscribeMatch(int position) {

    }

    @Override
    public void setView(MatchListContract.View view) {
        this.view = view;
    }

}
