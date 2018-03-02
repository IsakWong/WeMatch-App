package nullref.dlut.wematch.base;


import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import nullref.dlut.wematch.R;

/**
 * Created by IsakWong on 2017/5/17.
 */


/**
 * 所有的页面都需要继承自这个BaseFragmen的子类如
 * <p>
 * ColorStatusPage 带颜色状态栏
 * <p>
 * TransparentPage 透明状态栏
 */
public abstract class BasePage<T> extends Fragment {

    public T presenter;

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public BasePage nextFragment = null;
    public BasePage preFragment = null;
    public View view;
    long time1;
    long time2;

    public BasePage() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time1 = SystemClock.elapsedRealtime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int statusBarHeight = 0;
        View statusBar = findViewById(R.id.status_bar);
        if (statusBar != null) {
            ViewGroup.LayoutParams lp = statusBar.getLayoutParams();
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
            lp.height = statusBarHeight;
            statusBar.setLayoutParams(lp);
        }
        time2 = SystemClock.elapsedRealtime();
        Log.e(this.getClass().toString(), "CreateView:" + Long.toString(time2 - time1));
    }

    @Override
    public void onAttach(Context context) {
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (preFragment != null) {
            preFragment.nextFragment = nextFragment;
        }
        if (nextFragment != null) {
            nextFragment.preFragment = preFragment;
        }
        nextFragment = null;
        preFragment = null;
        Log.i(getClass().toString() + "  ", new Exception().getStackTrace()[0].getMethodName());
    }

    public View findViewById(int id) {
        return view.findViewById(id);
    }


    @CallSuper
    public FragmentTransaction preEnterPage(FragmentTransaction transaction, BasePage targetFragment) {

        getView();
        return transaction;

    }


    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void jumpPage(BasePage fragment) {
        getBaseActivity().pageManager.jumpPage(fragment);
    }

    public void backPage() {
        getBaseActivity().onBackPressed();
    }

    public PopupWindow showPopupWindow(View view, int resource) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.menu_match_list, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setElevation(getResources().getDimension(R.dimen.textSubhead));
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorWhiteBackgournd));
        popupWindow.showAsDropDown(view);
        return popupWindow;
    }

    public void onBackPressed() {
        if (preFragment == null)
            getBaseActivity().finishAfterTransition();
        else {
            getBaseActivity().pageManager.backPage();
        }
    }

    public void authError() {
    }

    public Context getInstance() {
        return this.getContext();
    }

    public void makeToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void makeLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
