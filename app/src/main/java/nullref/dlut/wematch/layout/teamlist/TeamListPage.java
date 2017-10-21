package nullref.dlut.wematch.layout.teamlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.Team;
import nullref.dlut.wematch.widgets.adapter.TeamListAdapter;

/**
 * Created by IsakWong on 2017/5/14.
 */


public class TeamListPage extends ColorStatusPage implements TeamListContract.View {

    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.toolbar_refresh)
    ImageButton toolbarRefresh;
    @BindView(R.id.match_list)
    RecyclerView userList;
    @BindView(R.id.ptrFrame)
    PtrClassicFrameLayout ptrFrame;


    TeamListContract.Presenter presenter;

    TeamListAdapter adapter;

    Unbinder unbinder;

    public void setPresenter(TeamListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public TeamListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle saveedInstance) {
        super.onCreate(saveedInstance);
        adapter = new TeamListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_team_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        userList.setItemAnimator(new DefaultItemAnimator());
        userList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        userList.setAdapter(adapter);
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(1000);
        ptrFrame.setPullToRefresh(false);
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                presenter.getMoreTeam();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refreshTeam();
            }
        });
        if (adapter.getDatas().isEmpty()) {
            presenter.refreshTeam();
        }
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onJoinTeamError(String cause) {
        makeToast(cause);
    }

    @Override
    public void onJoinTeam(int position) {

    }


    @Override
    public void onGetTeamListError(String cause) {
        makeToast(cause);
    }

    @Override
    public void onGetTeam(Team[] teams) {
        adapter.clearCard();
        for (Team team : teams
                ) {
            adapter.addCard(team);
        }
        ptrFrame.refreshComplete();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.navigation_icon)
    public void onNavigationClicked() {
        onBackPressed();
    }

    @OnClick(R.id.toolbar_refresh)
    public void onToolbarRefreshClicked() {
        presenter.refreshTeam();
    }

}

