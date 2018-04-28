package nullref.dlut.wematch.layout.info;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.layout.infoset.ModifyInformationActivity;
import nullref.dlut.wematch.layout.infoset.ModifyInformationContract;
import nullref.dlut.wematch.layout.infoset.ModifyInformationPresenter;
import nullref.dlut.wematch.layout.login.LogInActivity;
import nullref.dlut.wematch.layout.login.LogInPresenter;
import nullref.dlut.wematch.layout.teamlist.JoinTeamListPresenter;
import nullref.dlut.wematch.layout.matchlist.SubscribeMatchListPresenter;
import nullref.dlut.wematch.layout.userlist.SubscribeUserListPresenter;
import nullref.dlut.wematch.layout.matchlist.MatchListPage;
import nullref.dlut.wematch.layout.teamlist.TeamListPage;
import nullref.dlut.wematch.layout.userlist.UserListPage;
import nullref.dlut.wematch.utils.NetworkManager;
import nullref.dlut.wematch.utils.database.ConfigDbHelper;
import nullref.dlut.wematch.widgets.AvatarImageTarget;

/**
 * Created by IsakWong on 2017/5/14.
 */

public class SelfSpacePage extends ColorStatusPage<SelfSpacePageContract.Presenter> implements SelfSpacePageContract.View {


    @BindView(R.id.info_avatar_bg)
    ImageView infoAvatarBg;
    @BindView(R.id.info_avatar)
    ImageView infoAvatar;

    Unbinder unbinder;
    @BindView(R.id.overlay)
    ImageView overlay;
    @BindView(R.id.status_bar)
    ImageView statusBar;
    @BindView(R.id.match_info_loc)
    TextView matchInfoLoc;
    @BindView(R.id.my_subscribe_users)
    GridLayout myFollowing;
    @BindView(R.id.match_info_loc2)
    TextView matchInfoLoc2;
    @BindView(R.id.my_subscribe_matches)
    GridLayout myFavourite;
    @BindView(R.id.match_info_loc3)
    TextView matchInfoLoc3;
    @BindView(R.id.my_teams)
    GridLayout myTeam;
    @BindView(R.id.match_info_loc4)
    TextView matchInfoLoc4;
    @BindView(R.id.match_logout)
    GridLayout matchLogout;
    @BindView(R.id.info_update)
    ImageButton infoUpdate;

    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.sex)
    ImageView sex;

    private UserInfo userListInfo;
    private String avatarUrlPath;



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        ConfigDbHelper configDbHelper = ConfigDbHelper.getInstance();
        presenter.getUserInfo();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.info_avatar)
    public void onInfoAvatarClicked() {

    }

    @Override
    public void onGetUserInfo(UserInfo userListInfoInfo) {
        this.userListInfo = userListInfoInfo;
        userName.setText(userListInfo.name);
        /*
        switch (userListInfo.gender) {
            case 0:
                sex.setImageResource(R.drawable.ic_sex_female);
                break;
            case 1:
                sex.setImageResource(R.drawable.ic_sex_male);
                break;
            case 2:
                break;
        }*/
        avatarUrlPath = NetworkManager.avatarPrefix + userListInfo.avatarUrl;
        RequestOptions options2 = new RequestOptions()
                .placeholder(R.drawable.bg_blue)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide
                .with(infoAvatar)
                .asBitmap()
                .load(avatarUrlPath)
                .apply(options2)
                .into(new AvatarImageTarget(infoAvatar, infoAvatarBg));
    }
    @OnClick({R.id.my_subscribe_users, R.id.my_subscribe_matches, R.id.my_teams, R.id.match_logout, R.id.info_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_subscribe_users:
                //// TODO: 2017/9/14 关注的人
                UserListPage userListPage = new UserListPage();
                SubscribeUserListPresenter userPresenter = new SubscribeUserListPresenter();
                userListPage.setPresenter(userPresenter);
                userPresenter.setView(userListPage);
                jumpPage(userListPage);
                break;
            case R.id.my_subscribe_matches:
                MatchListPage matchListPage = new MatchListPage();
                SubscribeMatchListPresenter matchListPresenter = new SubscribeMatchListPresenter();
                matchListPresenter.setView(matchListPage);
                matchListPage.setPresenter(matchListPresenter);
                jumpPage(matchListPage);
                break;
            case R.id.my_teams:
                //// TODO: 2017/9/14 我的小队
                TeamListPage teamListPage = new TeamListPage();
                JoinTeamListPresenter subscribeTeamListPresenter = new JoinTeamListPresenter();
                subscribeTeamListPresenter.setView(teamListPage);
                teamListPage.setPresenter(subscribeTeamListPresenter);
                jumpPage(teamListPage);
                break;
            case R.id.match_logout:
                ConfigDbHelper dbHelper = ConfigDbHelper.getInstance();
                dbHelper.update("auto_login", "false");
                getBaseActivity().jumpTo(LogInActivity.class, false);
                break;
            case R.id.info_update:
                if (userListInfo != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user_info",userListInfo);
                    getBaseActivity().jumpTo(ModifyInformationActivity.class,true,bundle,null);
                }
                break;
        }
    }


}

