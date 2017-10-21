package nullref.dlut.wematch.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;

import java.util.ArrayList;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BasePage;

/**
 * Created by IsakWong on 2017/5/28.
 * <p>
 * 用于管理MainPage界面的各个Page（Fragment）的跳转，以及Activity的跳转
 */
public class PageManager {

    /**
     * The M fragments.
     */
    ArrayList<BasePage> fragments = new ArrayList<>();
    private BasePage curFragment;
    private FragmentManager mFragmentManager;


    /**
     * Instantiates a new Page manager.
     *
     * @param manager the manager
     */
    public PageManager(FragmentManager manager) {
        mFragmentManager = manager;
    }

    /**
     * Gets cur fragment.
     *
     * @return the cur fragment
     */
    public BasePage getCurFragment() {
        return curFragment;
    }

    /**
     * Sets cur fragment.
     *
     * @param fragment the fragment
     */
    public void setCurFragment(BasePage fragment) {
        curFragment = fragment;
    }


    /**
     * 显示某个Fragment1，隐藏其他Fragment，不可返回。
     *
     * @param fragment 跳转到的Fragment
     */
    public void showPage(BasePage fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (fragments.contains(fragment)) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.mainpage_content, fragment);
        }
        if (curFragment != null) {
            curFragment.setExitTransition(new Fade());
            transaction
                    .hide(curFragment);
        }
        fragment.setEnterTransition(new Fade());
        transaction.commit();
        setCurFragment(fragment);
        fragments.add(fragment);
    }


    /**
     * 从当前Fragment1跳转至另外一个Fragment，可以返回至Fragment1
     *
     * @param fragment 跳转到的Fragment
     */
    public void jumpPage(BasePage fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        fragment.setSharedElementEnterTransition(new DetailTransition());
        fragment.setSharedElementReturnTransition(new DetailTransition());
        fragments.add(fragment);
        curFragment.setExitTransition(new Fade());
        fragment.setEnterTransition(new Fade());
        transaction
                .hide(curFragment)
                .add(R.id.mainpage_content, fragment)
                .commit();
        curFragment.nextFragment = fragment;
        fragment.preFragment = curFragment;
        setCurFragment(fragment);
    }


    /**
     * 返回至上一个Page，如果已经是第一个Page了，则无反应
     */
    public void backPage() {
        BasePage preFragment;
        if (curFragment != null) {
            fragments.remove(curFragment);
            if (curFragment.preFragment != null) {
                preFragment = curFragment.preFragment;
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                curFragment.setExitTransition(new Fade());
                curFragment.preFragment.setEnterTransition(new Fade());
                curFragment.setSharedElementReturnTransition(new DetailTransition());
                curFragment.preFragment.onSaveInstanceState(new Bundle());
                transaction
                        .remove(curFragment)
                        .show(preFragment)
                        .commit();
                preFragment.nextFragment = null;
                curFragment = preFragment;
            }
        }

    }

    public void removeAllPage() {


        FragmentTransaction transcaction = mFragmentManager.beginTransaction();
        for (Fragment fragment : fragments
                ) {

            transcaction.remove(curFragment);
        }
        transcaction.commit();
    }

}
