package nullref.dlut.wematch.business.discovery;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.bean.UserListInfo;
import nullref.dlut.wematch.business.subscribe.JoinTeamListPresenter;
import nullref.dlut.wematch.business.subscribe.SubscribeMatchListPresenter;
import nullref.dlut.wematch.layout.matchlist.MatchListPage;
import nullref.dlut.wematch.layout.teamlist.TeamListPage;
import nullref.dlut.wematch.layout.userinfo.UserinfoPage;
import nullref.dlut.wematch.layout.userinfo.UserinfoPresenter;
import nullref.dlut.wematch.utils.Utils;
import nullref.dlut.wematch.widgets.UserCardSmall;


/**
 * Created by IsakWong on 2017/5/14.
 */


public class DiscoveryPage extends ColorStatusPage implements DiscoveryContract.View {

    DiscoveryContract.Presenter presenter;


    Unbinder unbinder;

    LinearLayout matchesLayout[] = new LinearLayout[4];
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

    MatchListInfo[] matches;
    @Override
    public void setPresenter(DiscoveryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_discovery, container, false);
        unbinder = ButterKnife.bind(this, view);


        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日");
        String date = sDateFormat.format(new Date());
        discoveryDate.setText(date);

        matchesLayout[0] = (LinearLayout) view.findViewById(R.id.match_1);
        matchesLayout[1] = (LinearLayout) view.findViewById(R.id.match_2);
        matchesLayout[2] = (LinearLayout) view.findViewById(R.id.match_3);
        matchesLayout[3] = (LinearLayout) view.findViewById(R.id.match_4);
        for (LinearLayout item : matchesLayout) {
            item.setVisibility(View.INVISIBLE);
        }
        presenter.getMatches();
        presenter.getUsers();

        String url = "http://wematchcommunity.applinzi.com/api/label-list.php?labelname=" + Utils.toURLEncoded("人工智能") + "&labelname2=" + Utils.toURLEncoded("互联网");
        communityWeb.loadUrl(url);
        return view;
    }

    @Override
    public void onError(String cause) {

    }

    @Override
    public void onSubscribedLabelsAdded(Label[] labels) {
    }

    View.OnClickListener userCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UserCardSmall item = (UserCardSmall)v;
            UserinfoPage userInfoPage= new UserinfoPage();
            UserinfoPresenter userInfoPresenter = new UserinfoPresenter();
            userInfoPage.setPresenter(userInfoPresenter);
            userInfoPresenter.setView(userInfoPage);
            userInfoPresenter.setUserId(item.userListInfo.ID);
        }
    };

    @Override
    public void onUsersAdded(UserListInfo[] userListInfos) {

        for (UserListInfo item: userListInfos
             ) {
            UserCardSmall small = new UserCardSmall(getContext());
            small.userListInfo = item;
            String avatarUrlPath= "https://wematch.oss-cn-shanghai.aliyuncs.com/"+ item.avatarUrl;
            CircleImageView profileImage = (CircleImageView)small.findViewById(R.id.profile_image);
            TextView title = (TextView)small.findViewById(R.id.profile_name);
            Glide
                    .with(profileImage)
                    .load(avatarUrlPath)
                    .into(profileImage);
            title.setText(item.name);
            discvoeruUserLayout.addView(small);
        }


    }

    View.OnClickListener matchCardListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    @Override
    public void onMatchesAdded(MatchListInfo[] matches) {
        this.matches = matches;
        int size = matches.length > 4 ? 4 : matches.length;
        for (int i = 0; i < size; i++) {
            matchesLayout[i].setOnClickListener(matchCardListener);
            ImageView pic = (ImageView) matchesLayout[i].findViewById(R.id.match_pic);
            TextView title = (TextView) matchesLayout[i].findViewById(R.id.match_title);
            title.setText(matches[i].name);
            matchesLayout[i].setVisibility(View.VISIBLE);
            Glide
                    .with(this)
                    .load("http://60.205.218.75:8980/image/" + matches[i].imgUrl)
                    .into(pic);
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
    }
}

