package nullref.dlut.wematch.layout.matchlist;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.ColorStatusPage;
import nullref.dlut.wematch.bean.MatchListInfo;
import nullref.dlut.wematch.layout.matchinfo.MatchInfoPresenter;
import nullref.dlut.wematch.layout.commit.CommitMatchActivity;
import nullref.dlut.wematch.layout.labelinfo.LabelPage;
import nullref.dlut.wematch.layout.matchinfo.MatchInfoPage;
import nullref.dlut.wematch.widgets.adapter.MatchListAdapter;

import static nullref.dlut.wematch.layout.matchlist.MatchListPage.NavigationType.NAVIGATION_BACK;
import static nullref.dlut.wematch.layout.matchlist.MatchListPage.NavigationType.NAVIGATION_MENU;


/**
 * Created by IsakWong on 2017/5/14.
 */


public class MatchListPage extends ColorStatusPage<MatchListContract.Presenter> implements MatchListContract.View {


    MatchListAdapter mAdapter;

    @BindView(R.id.status_bar)
    ImageView statusBar;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    Unbinder unbinder;
    @BindView(R.id.menu_icon)
    ImageButton menuIcon;
    @BindView(R.id.toolbar_refresh)
    ImageButton toolbarRefresh;
    @BindView(R.id.toolbar_search)
    ImageButton toolbarSearch;
    @BindView(R.id.toolbar_more)
    ImageButton toolbarMore;
    @BindView(R.id.match_list)
    RecyclerView matchList;
    @BindView(R.id.ptrFrame)
    PtrFrameLayout ptrFrame;
    @BindView(R.id.match_list_title)
    TextView matchListTitle;

    AppCompatImageView shareImg;
    LinearLayoutManager verticalLinearLayout;
    DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
    MatchListAdapter.CardListener matchCardListener = new MatchListAdapter.CardListener() {
        @Override
        public void onViewClicked(View view, int position) {
            switch (view.getId()) {
                case R.id.match_card:
                    shareImg = (AppCompatImageView) view.findViewById(R.id.match_card_pic);
                    MatchInfoPage matchInfoPage = new MatchInfoPage();
                    MatchInfoPresenter matchInfoPresenter = new MatchInfoPresenter();
                    matchInfoPresenter.setView(matchInfoPage);
                    matchInfoPresenter.setMatchListInfo(mAdapter.getCardData(position));
                    matchInfoPage.setPresenter(matchInfoPresenter);
                    jumpPage(matchInfoPage);
                    break;
                case R.id.match_card_like:
                    presenter.subscribeMatch(position);
                    break;
                case R.id.match_card_label1:
                case R.id.match_card_label2:
                case R.id.match_card_label3:
                    Integer index = (Integer) view.getTag();
                    LabelPage labelInfoPage = new LabelPage();
                    Bundle args = new Bundle();
                    args.putSerializable("label", mAdapter.getCardData(position).labels[index]);
                    labelInfoPage.setArguments(args);
                    jumpPage(labelInfoPage);
                    break;
            }
        }

        @Override
        public void onViewLongPressed(View view, int postion) {

        }
    };
    PtrDefaultHandler2 pullHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            presenter.getMoreMatches();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            presenter.refreshMatches();
        }
    };
    private NavigationType navigationType;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle saveedInstance) {
        super.onCreate(saveedInstance);
        mAdapter = new MatchListAdapter();
        verticalLinearLayout = new LinearLayoutManager(getInstance(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        pageContent = inflater.inflate(R.layout.page_match_list, container, false);
        unbinder = ButterKnife.bind(this, pageContent);
        matchList.setItemAnimator(defaultItemAnimator);
        matchList.setLayoutManager(verticalLinearLayout);
        matchList.setAdapter(mAdapter);
        mAdapter.setListener(matchCardListener);
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(1000);
        ptrFrame.setPullToRefresh(false);
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        ptrFrame.setPtrHandler(pullHandler);
        if (NAVIGATION_BACK == navigationType) {
            menuIcon.setImageResource(R.drawable.ic_back);
        }
        if (NAVIGATION_MENU == navigationType) {

        }
        if (mAdapter.getDatas().isEmpty()) {
            presenter.refreshMatches();
        }
        presenter.initView();
        return pageContent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onFollowMatchError(String cause) {

    }

    @Override
    public void onFollowMatch(int position) {
        makeToast("关注比赛成功");

    }

    @Override
    public void onRequestMatchError(String cause) {

    }

    @Override
    public void setTitle(String title) {
        matchListTitle.setText(title);
    }

    @Override
    public void onRefreshMatch(MatchListInfo[] matches) {

        mAdapter.clearCard();
        for (MatchListInfo match : matches) {
            mAdapter.addCard(match);
        }
        ptrFrame.refreshComplete();

    }

    @Override
    public void onMatchAdded(MatchListInfo[] matches) {
        for (MatchListInfo match : matches
                ) {
            mAdapter.addCard(match);
        }
        ptrFrame.refreshComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.setListener(null);
        matchList.setAdapter(null);
        matchList.setLayoutManager(null);
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.menu_icon)
    public void onMenuIconClicked() {
        if (NAVIGATION_MENU == navigationType) {
            final PopupWindow popupWindow = showPopupWindow(menuIcon, R.layout.menu_match_list);
            View contentView = popupWindow.getContentView();
            // 设置按钮的点击事件
            Button button = (Button) contentView.findViewById(R.id.menu_add_match);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getBaseActivity().jumpTo(CommitMatchActivity.class, true);
                    popupWindow.dismiss();
                }
            });
        }
        if (NAVIGATION_BACK == navigationType) {
            onBackPressed();
        }
    }

    @OnClick(R.id.toolbar_refresh)
    public void onToolbarRefreshClicked() {
        presenter.refreshMatches();
    }

    @OnClick(R.id.toolbar_search)
    public void onToolbarSearchClicked() {
        //// TODO: 2017/9/14  搜索功能
        makeToast(getString(R.string.to_do));
    }

    @OnClick(R.id.toolbar_more)
    public void onToolbarMoreClicked() {
        //// TODO: 2017/9/14  更多操作功能
        makeToast(getString(R.string.to_do));
    }


    @Override
    public MatchListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setNavigationType(NavigationType navigationType) {
        this.navigationType = navigationType;
    }

    public enum NavigationType {
        NAVIGATION_MENU,
        NAVIGATION_BACK
    }
}

