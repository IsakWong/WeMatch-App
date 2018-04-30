package nullref.dlut.wematch.layout.matchinfo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.TransparentStatusPage;
import nullref.dlut.wematch.bean.Match;
import nullref.dlut.wematch.layout.teamlist.MatchTeamListPresenter;
import nullref.dlut.wematch.layout.userlist.MatchUserListPresenter;
import nullref.dlut.wematch.layout.teamlist.TeamListPage;
import nullref.dlut.wematch.layout.userlist.UserListPage;
import nullref.dlut.wematch.utils.NetworkManager;
import nullref.dlut.wematch.utils.Utils;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by IsakWong on 2017/5/28.
 */

public class MatchInfoPage extends TransparentStatusPage<MatchInfoContract.Presenter> implements ObservableScrollViewCallbacks, MatchInfoContract.View {


    public int mShareImgId;

    Match match;
    @BindView(R.id.match_info_short_info)
    TextView matchInfoShortInfo;
    @BindView(R.id.match_info_community)
    LinearLayout matchInfoCommunity;
    @BindView(R.id.match_info_website)
    LinearLayout matchInfoWebsite;
    @BindView(R.id.match_info_concern)
    LinearLayout matchInfoConcern;
    @BindView(R.id.match_info_loc)
    TextView matchInfoLoc;
    @BindView(R.id.match_info_time)
    TextView matchInfoTime;
    @BindView(R.id.match_info_host)
    TextView matchInfoHost;
    @BindView(R.id.match_info_people)
    TextView matchInfoPeople;
    @BindView(R.id.match_info_content)
    TextView matchInfoContent;
    @BindView(R.id.match_info_content_more)
    Button matchInfoContentMore;
    @BindView(R.id.scroll_content)
    LinearLayout scrollContent;
    @BindView(R.id.scroll)
    ObservableScrollView scroll;
    @BindView(R.id.overlay)
    AppCompatImageView overlay;
    @BindView(R.id.match_card_pic)
    AppCompatImageView matchCardPic;

    @BindView(R.id.match_info_title)
    TextView matchInfoTitle;

    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    Unbinder unbinder;

    @BindView(R.id.status_bar)
    ImageView statusBar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.share_icon)
    ImageButton shareIcon;
    @BindView(R.id.fab_like)
    FloatingActionButton fabLike;
    @BindView(R.id.fab_create_team)
    FloatingActionButton fabCreateTeam;
    @BindView(R.id.web_content)
    WebView webContent;
    @BindView(R.id.fab_menu)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.fab_alarm)
    FloatingActionButton fabAlarm;
    boolean autoScrolling = false;
    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    public void setPresenter(MatchInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle saveedInstance) {
        super.onCreate(saveedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        pageContent = inflater.inflate(R.layout.page_match_info, container, false);
        unbinder = ButterKnife.bind(this, pageContent);
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.topbarSize);
        mActionBarSize = findViewById(R.id.status_bar).getLayoutParams().height + findViewById(R.id.toolbar).getLayoutParams().height;
        if (match == null) {
            presenter.getMatchInfo();

        }

        scroll.setScrollViewCallbacks(this);


        ScrollUtils.addOnGlobalLayoutListener(scroll, new Runnable() {
            @Override
            public void run() {
                scrollView(scroll.getScrollY());

            }
        });
        return pageContent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onMatchInfo(Match match) {
        this.match = match;
        NetworkManager.LoadPic(matchCardPic,match.imgUrl);
        matchInfoTitle.setText(match.name);
        matchInfoShortInfo.setText(match.shortInfo);
        matchInfoLoc.setText(match.loc);
        matchInfoTime.setText(match.startTime + " - " + match.endTime);
        matchInfoHost.setText(match.host);
        matchInfoPeople.setText(Integer.toString(match.minPersons) + " - " + Integer.toString(match.maxPersons) + "人");
        matchInfoContent.setText(match.content);
        try {
            String encodeurl = "http://wematchcommunity.applinzi.com/main-label.php?labelname=" + Utils.toURLEncoded(match.labels[0].getName())
                    + "&labelname2=" + Utils.toURLEncoded(match.labels[1].getName())
                    + "&labelname3=" + Utils.toURLEncoded(match.labels[2].getName());
            webContent.loadUrl(encodeurl);
        } catch (Exception ex) {

        }

    }

    @Override
    public void onScrollChanged(final int scrollY, boolean firstScroll, boolean dragging) {

        scrollView(scrollY);
    }


    public void scrollView(int scrollY) {

        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - overlay.getHeight();
        ViewHelper.setTranslationY(overlay, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(matchCardPic, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setAlpha(matchCardPic, 1 - ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
        ViewHelper.setAlpha(matchInfoTitle, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }


    @Override
    public void onDestroyView() {
        scroll.setScrollViewCallbacks(null);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.match_info_community)
    public void onMatchInfoCommunityClicked() {

        if (match != null) {
            TeamListPage teamListPage = new TeamListPage();
            MatchTeamListPresenter matchTeamListPresenter = new MatchTeamListPresenter();
            matchTeamListPresenter.setView(teamListPage);
            matchTeamListPresenter.setMatch(match);
            teamListPage.setPresenter(matchTeamListPresenter);

            jumpPage(teamListPage);
        }
    }

    @OnClick(R.id.match_info_website)
    public void onMatchInfoWebsiteClicked() {
        if (match != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(match.website);
            intent.setData(content_url);
            startActivity(intent);
        }
    }

    @OnClick(R.id.match_info_concern)
    public void onMatchInfoConcernClicked() {
        if (match != null) {
            Bundle args = new Bundle();
            UserListPage userListPage = new UserListPage();
            MatchUserListPresenter matchUserListPresenter = new MatchUserListPresenter();
            userListPage.setPresenter(matchUserListPresenter);
            matchUserListPresenter.setView(userListPage);
            matchUserListPresenter.setData(match);
            jumpPage(userListPage);
        }

    }

    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        backPage();
    }


    public void scrollToPosition() {

        ValueAnimator intAnimator = ValueAnimator.ofInt(0, scroll.getHeight());
        final int i = scroll.getHeight();
        intAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                          @Override
                                          public void onAnimationUpdate(ValueAnimator animation) {
                                              Integer animatedValue = (Integer) ((ValueAnimator) animation).getAnimatedValue();
                                              if (scroll != null) {

                                                  scroll.scrollTo(0, animatedValue);
                                                  if (animatedValue >= i) {
                                                      autoScrolling = false;
                                                      scroll.setOnTouchListener(new View.OnTouchListener() {
                                                          @Override
                                                          public boolean onTouch(View view, MotionEvent motionEvent) {
                                                              return false;
                                                          }
                                                      });
                                                  }

                                              }
                                          }
                                      }
        );
        intAnimator.setDuration(1500);
        intAnimator.start();
    }

    @Override
    public void onFollowMatch() {
        Toast.makeText(getContext(), "关注比赛成功", Toast.LENGTH_SHORT);
    }

    @Override
    public void onFollowMatchError(String cause) {
        Toast.makeText(getContext(), cause, Toast.LENGTH_SHORT);
    }

    @Override
    public void onCreateTeam() {

        fabMenu.collapse();
    }

    @Override
    public void onCreateTeamError(String cause) {
        makeToast("创建队伍失败，请重新尝试");
    }

    @OnClick(R.id.share_icon)
    public void onShareIconClicked() {
        //TODO 比赛分享
        ClipboardManager cm = (ClipboardManager) getBaseActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", "");
        cm.setPrimaryClip(mClipData);
        makeToast("用户信息已经复制到粘贴板。");
    }

    @OnClick({R.id.fab_like, R.id.fab_create_team, R.id.fab_alarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_like:
                if (match != null) {
                    presenter.followMatch();
                }
                fabMenu.collapse();

                break;
            case R.id.fab_create_team:
                final AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(getContext());
                final View dialogView = getBaseActivity().getLayoutInflater().inflate(R.layout.dialog_create_team, null);
                dialogBuilder.setView(dialogView);
                final EditText name = (EditText) dialogView.findViewById(R.id.team_name_edit);
                final EditText info = (EditText) dialogView.findViewById(R.id.team_info_edit);
                final EditText person = (EditText) dialogView.findViewById(R.id.team_person_edit);
                dialogView.findViewById(R.id.team_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (match != null) {
                            String strName = name.getText().toString();
                            String strInfo = info.getText().toString();
                            try {
                                Integer persons = Integer.valueOf(person.getText().toString());
                                presenter.createTeam(strName, strInfo, persons);
                            } catch (NumberFormatException exception) {
                                makeToast("人数格式出错");
                            }
                        }

                    }
                });
                dialogBuilder.show();
                fabMenu.collapse();
                break;
            case R.id.fab_alarm:
                Intent intent = new Intent(getBaseActivity(), MatchNotificationReceiver.class);
                intent.setAction("OPEN_PAGE");
                // PendingIntent这个类用于处理即将发生的事情
                PendingIntent sender = PendingIntent.getBroadcast(getBaseActivity(), 0, intent, 0);
                AlarmManager am = (AlarmManager) getBaseActivity().getSystemService(ALARM_SERVICE);
                // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
                // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
                am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + 6000, sender);
                fabMenu.collapse();
                makeToast("成功设置比赛提醒，当比赛进入日程时会进行提醒!");
        }
    }


}
