package nullref.dlut.wematch.layout.matchlist;

import java.util.ArrayList;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.sessions.GetMatchListSession;
import nullref.dlut.wematch.widgets.adapter.MatchListAdapter;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class MatchListPresenter extends BasePresenter<MatchListContract.View> implements MatchListContract.Presenter {

    GetMatchListSession getNoFilterMatchList = new GetMatchListSession(new GetMatchListSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches, int matchID) {
            view.onMatchAdded(matches);
        }

        @Override
        public void onGetMatchListError(String cause, int matchID) {

        }
    });
    //刷新比赛列表，获取订阅标签相关的比赛列表
    GetMatchListSession getSubsribeLabelMatchList = new GetMatchListSession(new GetMatchListSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches, int matchID) {
            if (0 == matches.length) {
                //订阅标签相关的比赛列表为0，则不筛选的获取列表
                getNoFilterMatchList.request.filter = 0;
                getNoFilterMatchList.send();
            } else {
                view.onRefreshMatch(matches);
            }

        }

        @Override
        public void onGetMatchListError(String cause, int matchID) {

        }
    });
    GetMatchListSession getMoreSubsribeMatchList = new GetMatchListSession(new GetMatchListSession.Listener() {
        @Override
        public void onGetMatchList(MatchListInfo[] matches, int matchID) {
            if (0 == matches.length) {
                getNoFilterMatchList.request.filter = 0;
                getNoFilterMatchList.request.matchID = matchID;
                getNoFilterMatchList.send();
                view.onRequestMatchError("没有更多了，自动为您加载所有比赛。");
            } else {
                view.onMatchAdded(matches);
            }
        }

        @Override
        public void onGetMatchListError(String cause, int matchID) {

        }
    });
    public MatchListPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void refreshMatches() {
        getSubsribeLabelMatchList.request.filter = 1;
        getSubsribeLabelMatchList.request.matchID = -1;
        getSubsribeLabelMatchList.send();
    }

    @Override
    public void getMoreMatches() {
        getMoreSubsribeMatchList.request.filter = 1;
        MatchListAdapter adapter = view.getAdapter();
        ArrayList<MatchListInfo> datas = adapter.getDatas();
        MatchListInfo last = null;
        if (datas.size() > 0) {
            last = datas.get(datas.size() - 1);
        }
        if (null == last) {
            getMoreSubsribeMatchList.request.matchID = -1;
        } else {
            getMoreSubsribeMatchList.request.matchID = last.matchID;
        }
        getMoreSubsribeMatchList.send();

    }

    @Override
    public void subscribeMatch(int position) {

    }


}
