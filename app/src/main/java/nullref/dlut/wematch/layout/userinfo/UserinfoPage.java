package nullref.dlut.wematch.layout.userinfo;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.layout.matchlist.SubscribeMatchListPresenter;
import nullref.dlut.wematch.layout.matchlist.MatchListPage;
import nullref.dlut.wematch.widgets.AvatarImageTarget;


/**
 * Created by IsakWong on 2017/5/14.
 */


public class UserInfoPage extends ColorStatusPage<UserInfoContract.Presenter> implements UserInfoContract.View, ObservableScrollViewCallbacks {


    @BindView(R.id.user_info_matches)
    LinearLayout userInfoMatches;
    @BindView(R.id.user_info_subscribers)
    LinearLayout userInfoSubscribers;
    @BindView(R.id.match_info_message)
    LinearLayout matchInfoMessage;
    @BindView(R.id.appCompatImageView2)
    AppCompatImageView appCompatImageView2;
    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;
    UserInfo userInfo;
    Unbinder unbinder;
    UserInfoContract.Presenter presenter;

    @BindView(R.id.user_info_short_info)
    TextView userShortInfo;
    @BindView(R.id.scroll_content)
    LinearLayout scrollContent;
    @BindView(R.id.scroll)
    ObservableScrollView scroll;
    @BindView(R.id.overlay)
    AppCompatImageView overlay;
    @BindView(R.id.fab_like)
    FloatingActionButton fabLike;
    @BindView(R.id.fab_menu)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.status_bar)
    ImageView statusBar;
    @BindView(R.id.navigation_icon)
    ImageButton navigationIcon;
    @BindView(R.id.share_icon)
    ImageButton shareIcon;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.user_info_avatar)
    CircleImageView userInfoAvatar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.user_info_avatar_bg)
    AppCompatImageView userInfoAvatarBg;

    @BindView(R.id.user_info_name)
    TextView userInfoName;
    @BindView(R.id.user_info_school)
    TextView userInfoSchool;
    @BindView(R.id.user_info_qq)
    TextView userInfoQq;
    @BindView(R.id.user_info_major)
    TextView userInfoMajor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.page_user_info_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.topbarSize);
        mActionBarSize = findViewById(R.id.status_bar).getLayoutParams().height + findViewById(R.id.toolbar).getLayoutParams().height;
        scroll.setScrollViewCallbacks(this);
        ScrollUtils.addOnGlobalLayoutListener(scroll, new Runnable() {
            @Override
            public void run() {
                scrollView(scroll.getScrollY());

            }
        });
        presenter.getUserInfo();
        return view;
    }


    @Override
    public void onGetUser(UserInfo userInfo) {
        this.userInfo = userInfo;
        userShortInfo.setText(userInfo.shortInfo);
        userInfoName.setText(userInfo.name);
        userInfoMajor.setText(userInfo.major);
        String avatarUrlPath = "https://wematch.oss-cn-shanghai.aliyuncs.com/" + userInfo.avatarUrl;
        RequestOptions options2 = new RequestOptions()
                .placeholder(R.drawable.bg_blue)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide
                .with(this)
                .asBitmap()
                .load(avatarUrlPath)
                .apply(options2)
                .into(new AvatarImageTarget(userInfoAvatar, userInfoAvatarBg));

    }

    @Override
    public void onError() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onScrollChanged(final int scrollY, boolean firstScroll, boolean dragging) {

        scrollView(scrollY);
    }


    public void scrollView(int scrollY) {

        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - overlay.getHeight();
        ViewHelper.setTranslationY(overlay, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(userInfoAvatarBg, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(userInfoAvatar, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setAlpha(userInfoAvatarBg, 1 - ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
        ViewHelper.setScaleX(userInfoAvatar, 1 - ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
        ViewHelper.setScaleY(userInfoAvatar, 1 - ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
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


    @OnClick(R.id.navigation_icon)
    public void onNavigationIconClicked() {
        backPage();
    }


    @OnClick(R.id.user_info_matches)
    public void onUserInfoMatchesClicked() {
        MatchListPage matchListPage = new MatchListPage();
        SubscribeMatchListPresenter presenter = new SubscribeMatchListPresenter();
        presenter.setView(matchListPage);
        matchListPage.setPresenter(presenter);
        presenter.setUserID(-1);
        jumpPage(matchListPage);
    }

    @OnClick(R.id.user_info_subscribers)
    public void onUserInfoSubscribersClicked() {
        //TODO 关注用户的人
        makeToast("功能正在测试中，即将上线~");
    }


    @OnClick(R.id.share_icon)
    public void onShareIconClicked() {
        //TODO 用户分享
        ClipboardManager cm = (ClipboardManager) getBaseActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", "");
        cm.setPrimaryClip(mClipData);
        makeToast("用户信息已经复制到粘贴板。");
    }

    @OnClick(R.id.match_info_message)
    public void onViewClicked() {
        //TODO 私信功能
        makeToast("功能正在测试中，即将上线~");
    }

    @OnClick(R.id.fab_like)
    public void onFabLikeClicked() {
        makeToast("订阅用户成功！");
        fabMenu.collapse();
    }
}

