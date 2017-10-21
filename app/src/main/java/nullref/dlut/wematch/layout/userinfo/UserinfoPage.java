package nullref.dlut.wematch.layout.userinfo;


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
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.UserInfo;
import nullref.dlut.wematch.widgets.AvatarImageTarget;
import nullref.dlut.wematch.widgets.RoundImageView;


/**
 * Created by IsakWong on 2017/5/14.
 */


public class UserInfoPage extends ColorStatusPage implements UserInfoContract.View, ObservableScrollViewCallbacks {

    UserInfoContract.Presenter presenter;
    @BindView(R.id.match_info_short_info)
    TextView matchInfoShortInfo;
    @BindView(R.id.match_info_community)
    LinearLayout matchInfoCommunity;
    @BindView(R.id.match_info_website)
    LinearLayout matchInfoWebsite;
    @BindView(R.id.match_info_concern)
    LinearLayout matchInfoConcern;
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
    RoundImageView userInfoAvatar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.user_info_avatar_bg)
    AppCompatImageView userInfoAvatarBg;

    UserInfo userInfo;
    Unbinder unbinder;

    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    public UserInfoPage() {

    }

    public void setPresenter(UserInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

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
    public void onGetUser(UserInfo user) {
        userInfo = user;
        String avatarUrlPath = "https://wematch.oss-cn-shanghai.aliyuncs.com/" + user.avatarUrl;
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


    @OnClick(R.id.user_info_avatar_bg)
    public void onViewClicked() {
    }
}

