package nullref.dlut.wematch.layout.discovery;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.layout.commit.CommitMatchActivity;
import nullref.dlut.wematch.layout.commit.CommitMatchPresenter;
import nullref.dlut.wematch.layout.matchinfo.MatchInfoPage;
import nullref.dlut.wematch.layout.matchinfo.MatchInfoPresenter;
import nullref.dlut.wematch.layout.matchlist.MatchListPage;
import nullref.dlut.wematch.layout.matchlist.SubscribeMatchListPresenter;
import nullref.dlut.wematch.layout.teamlist.JoinTeamListPresenter;
import nullref.dlut.wematch.layout.teamlist.TeamListPage;
import nullref.dlut.wematch.layout.userinfo.UserInfoPage;
import nullref.dlut.wematch.layout.userinfo.UserInfoPresenter;
import nullref.dlut.wematch.utils.NetworkManager;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.widgets.UserCardSmall;


/**
 * Created by IsakWong on 2017/5/14.
 */


public class DiscoveryPage extends ColorStatusPage<DiscoveryContract.Presenter> implements DiscoveryContract.View {


    Unbinder unbinder;

    @BindView(R.id.community_web)
    WebView communityWeb;
    @BindView(R.id.discovery_date)
    TextView discoveryDate;
    @BindView(R.id.discovery_match)
    LinearLayout discoveryMatch;
    @BindView(R.id.discovery_team)
    LinearLayout discoveryTeam;
    @BindView(R.id.discovery_labels)
    LinearLayout discoveryLabels;
    @BindView(R.id.discovery_user_scroll)
    HorizontalScrollView discoveryUserScroll;
    @BindView(R.id.discvoeru_user_layout)
    LinearLayout discvoeruUserLayout;


    View.OnClickListener userCardClickListener = v -> {
        UserCardSmall item = (UserCardSmall) v;

    };
    View.OnClickListener matchCardListener = v -> {

    };
    @BindView(R.id.menu_icon)
    ImageButton menuIcon;
    @BindView(R.id.match_grid)
    GridLayout matchGrid;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        pageContent = inflater.inflate(R.layout.page_discovery, container, false);
        unbinder = ButterKnife.bind(this, pageContent);


        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日");
        String date = sDateFormat.format(new Date());
        discoveryDate.setText(date);

        presenter.getMatches();
        presenter.getUsers();

        String url = "http://wematchcommunity.applinzi.com/api/label-list.php?labelname=" + Utils.toURLEncoded("人工智能") + "&labelname2=" + Utils.toURLEncoded("互联网");
        communityWeb.loadUrl(url);
        return pageContent;
    }

    @Override
    public void onError(String cause) {

    }

    @Override
    public void onSubscribedLabelsAdded(Label[] labels) {
    }

    @Override
    public void onUsersAdded(UserListInfo[] userListInfos) {

        for (UserListInfo item : userListInfos) {

            final UserListInfo userListInfo = item;
            UserCardSmall userCard = new UserCardSmall(getContext());
            userCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserInfoPage userInfoPage = new UserInfoPage();
                    UserInfoPresenter userInfoPresenter = new UserInfoPresenter();
                    userInfoPage.setPresenter(userInfoPresenter);
                    userInfoPresenter.setView(userInfoPage);
                    userInfoPresenter.setUserId(userListInfo.ID);
                    jumpPage(userInfoPage);
                }
            });
            CircleImageView profileImage = (CircleImageView) userCard.findViewById(R.id.profile_image);
            TextView title = (TextView) userCard.findViewById(R.id.profile_name);
            NetworkManager.LoadAvatar(profileImage, item.avatarUrl);
            title.setText(item.name);
            discvoeruUserLayout.addView(userCard);
        }


    }

    @Override
    public void onMatchesAdded(MatchListInfo[] matches) {
        int size = matches.length;
        for (int i = 0; i < size; i++) {
            final MatchListInfo match = matches[i];
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            View matchCard = inflater.inflate(R.layout.card_match_grid, null);
            GridLayout.Spec rowSpec = GridLayout.spec(i / 2, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 2, 1f);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.width = 0;
            matchCard.setOnClickListener(view -> {
                MatchInfoPage matchInfoPage = new MatchInfoPage();
                MatchInfoPresenter matchInfoPresenter = new MatchInfoPresenter();
                matchInfoPage.setPresenter(matchInfoPresenter);
                matchInfoPresenter.setView(matchInfoPage);
                matchInfoPresenter.setMatchListInfo(match);
                jumpPage(matchInfoPage);
            });
            matchGrid.addView(matchCard, params);
            ImageView pic = (ImageView) matchCard.findViewById(R.id.match_pic);
            TextView title = (TextView) matchCard.findViewById(R.id.match_title);
            title.setText(matches[i].name);
            NetworkManager.LoadPic(pic, match.imgUrl);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.discovery_match)
    public void onDiscoveryMatchClicked() {
        MatchListPage matchListPage = new MatchListPage();
        SubscribeMatchListPresenter matchListPresenter = new SubscribeMatchListPresenter();
        matchListPresenter.setView(matchListPage);
        matchListPage.setPresenter(matchListPresenter);
        matchListPage.setNavigationType(MatchListPage.NavigationType.NAVIGATION_BACK);
        jumpPage(matchListPage);
    }

    @OnClick(R.id.discovery_team)
    public void onDiscoveryTeamClicked() {
        TeamListPage teamListPage = new TeamListPage();
        JoinTeamListPresenter subscribeTeamListPresenter = new JoinTeamListPresenter();
        subscribeTeamListPresenter.setView(teamListPage);
        teamListPage.setPresenter(subscribeTeamListPresenter);
        jumpPage(teamListPage);
    }

    @OnClick(R.id.discovery_labels)
    public void onDiscoveryLabelsClicked() {
        makeToast(getResources().getString(R.string.to_do));
    }

    static BaseActivity.PresenterSetter commitMatchActivityCallback = (BaseActivity.PresenterSetter) baseActivity -> {
        CommitMatchPresenter commitMatchPresenter = new CommitMatchPresenter();
        commitMatchPresenter.setView((CommitMatchActivity) baseActivity);
        baseActivity.setPresenter(commitMatchPresenter);
    };


    View.OnClickListener commitMatchButtonOnClick = view -> {
        getBaseActivity().jumpTo(CommitMatchActivity.class, true, commitMatchActivityCallback);
    };

    @OnClick(R.id.menu_icon)
    public void onViewClicked() {

        final PopupWindow popupWindow = showPopupWindow(menuIcon, R.layout.menu_match_list);
        View contentView = popupWindow.getContentView();
        // 设置按钮的点击事件
        Button button = (Button) contentView.findViewById(R.id.menu_add_match);
        button.setOnClickListener(commitMatchButtonOnClick);

    }
}

