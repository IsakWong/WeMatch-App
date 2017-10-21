package nullref.dlut.wematch.layout.userlist;


import android.os.Bundle;
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
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.layout.userinfo.UserInfoPage;
import nullref.dlut.wematch.layout.userinfo.UserInfoPresenter;
import nullref.dlut.wematch.widgets.adapter.UserListAdapter;

/**
 * Created by IsakWong on 2017/5/14.
 */


public class UserListPage extends ColorStatusPage implements UserListContract.View {

    UserListContract.Presenter presenter;
    UserListAdapter adapter;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.toolbar_refresh)
    ImageButton toolbarRefresh;
    @BindView(R.id.toolbar_search)
    ImageButton toolbarSearch;
    @BindView(R.id.user_list)
    RecyclerView userList;
    Unbinder unbinder;
    int matchID;

    public UserListPage() {

    }

    public void setPresenter(UserListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle saveedInstance) {
        super.onCreate(saveedInstance);
        adapter = new UserListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_user_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        userList.setItemAnimator(new DefaultItemAnimator());
        userList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        userList.setAdapter(adapter);
        adapter.setListener(new UserListAdapter.CardListener() {
            @Override
            public void onViewClicked(View view, int position) {
                UserInfoPage page = new UserInfoPage();
                UserListInfo userListInfo = adapter.getCardData(position);
                UserInfoPresenter presenter = new UserInfoPresenter();
                presenter.setView(page);
                presenter.setUserId(userListInfo.ID);
                page.setPresenter(presenter);
                jumpPage(page);
            }

            @Override
            public void onViewLongPressed(View view, int postion) {

            }
        });

        if (adapter.getDatas().isEmpty()) {
            presenter.refreshUsers();
        }
        return view;
    }


    @Override
    public void onFollowUserError(String cause) {

    }

    @Override
    public void onFollowUser(int position) {

    }


    @Override
    public void onGetUserListError(String cause) {

    }

    @Override
    public void onGetUserList(UserListInfo[] userListInfos) {
        adapter.clearCard();
        for (UserListInfo match : userListInfos
                ) {
            adapter.addCard(match);
        }
        //ptrFrame.refreshComplete();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.toolbar_refresh)
    public void onToolbarRefreshClicked() {
        presenter.refreshUsers();
    }

    @OnClick(R.id.navigation_icon)
    public void onViewClicked() {
        backPage();
    }
}

