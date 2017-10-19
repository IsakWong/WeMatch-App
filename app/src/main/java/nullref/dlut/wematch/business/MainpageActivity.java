package nullref.dlut.wematch.business;


import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

import nullref.dlut.wematch.base.BasePage;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.business.discovery.DiscoveryPage;
import nullref.dlut.wematch.business.discovery.DiscoveryPresenter;
import nullref.dlut.wematch.business.info.InfoPage;
import nullref.dlut.wematch.business.info.InfoPagePresenter;
import nullref.dlut.wematch.layout.matchlist.MatchListPage;
import nullref.dlut.wematch.layout.website.WebPage;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with UserListInfo interaction.
 */
public class MainpageActivity extends BaseActivity {


    AHBottomNavigation bottomNavigation;
    ArrayList<BasePage> fragments = new ArrayList<BasePage>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem matchListBtn = new AHBottomNavigationItem(R.string.bottom_navigation_match_list, R.drawable.ic_match_white, R.color.colorAccent5);
        AHBottomNavigationItem communityBtn = new AHBottomNavigationItem(R.string.bottom_navigation_community, R.drawable.community, R.color.colorAccent5);
        AHBottomNavigationItem infoBtn = new AHBottomNavigationItem(R.string.bottom_navigation_info, R.drawable.ic_info, R.color.colorAccent5);
        AHBottomNavigationItem discoveryBtn = new AHBottomNavigationItem(R.string.bottom_navigation_discovery, R.drawable.ic_discovery_white, R.color.colorAccent5);

        // Add items
        bottomNavigation.addItem(discoveryBtn);
        bottomNavigation.addItem(communityBtn);
        bottomNavigation.addItem(matchListBtn);
        bottomNavigation.addItem(infoBtn);
        bottomNavigation.setTranslucentNavigationEnabled(true);


        // Set background color
        bottomNavigation.setAccentColor(Color.argb(255, 3, 169, 244));
        bottomNavigation.setInactiveColor(Color.argb(255, 175, 175, 175));
        bottomNavigation.setDefaultBackgroundColor(Color.argb(255, 255, 255, 255));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    BasePage toShowFragment = fragments.get(position);
                    while (toShowFragment.nextFragment != null) {
                        toShowFragment = toShowFragment.nextFragment;
                    }
                    getPageManager().showPage(toShowFragment);
                }
                return true;
            }
        });


        MatchListPage matchListPage = new MatchListPage();
        matchListPage.setNavigationType(MatchListPage.NavigationType.NAVIGATION_MENU);
        MatchListPresenter matchListPresenter = new MatchListPresenter();
        matchListPage.setPresenter(matchListPresenter);
        matchListPresenter.setView(matchListPage);


        WebPage communityPage = new WebPage();


        DiscoveryPage discoveryPage = new DiscoveryPage();
        DiscoveryPresenter discoveryPresenter = new DiscoveryPresenter();
        discoveryPage.setPresenter(discoveryPresenter);
        discoveryPresenter.setView(discoveryPage);


        InfoPage infoPage = new InfoPage();
        InfoPagePresenter infoPagePresenter = new InfoPagePresenter();
        infoPage.setPresenter(infoPagePresenter);
        infoPagePresenter.setView(infoPage);


        int index = 0;
        fragments.add(discoveryPage);
        fragments.add(communityPage);
        fragments.add(matchListPage);
        fragments.add(infoPage);
        pageManager.showPage(fragments.get(index));
        bottomNavigation.setCurrentItem(index);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bottomNavigation.removeOnTabSelectedListener();
        bottomNavigation = null;
        fragments.clear();
        fragments = null;
    }

    @Override
    public void onBackPressed() {
        pageManager.getCurFragment().onBackPressed();
    }
}
