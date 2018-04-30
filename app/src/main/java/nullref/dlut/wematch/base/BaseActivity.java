package nullref.dlut.wematch.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.Serializable;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.utils.WeMatchApplication;
/* Created by IsakWong on 2017/5/15.
 */

public class BaseActivity<T> extends AppCompatActivity implements BaseView{


    public T presenter;

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    protected PageManager pageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageManager = new PageManager(getSupportFragmentManager());
        PresenterSetter presenterSetter = (PresenterSetter) getIntent().getSerializableExtra("presenter_setter_callback");
        if(presenterSetter !=null){
            presenterSetter.Run(this);
        }

    }

    @Override
    protected void onDestroy() {
        pageManager = null;
        super.onDestroy();
    }

    public interface PresenterSetter extends Serializable{
        void Run(BaseActivity baseActivity);
    }
    public PageManager getPageManager() {
        return pageManager;
    }


    public void jumpTo(Class<?> activityType, boolean canGoBack) {
        Intent intent = new Intent();
        intent.setClass(this, activityType);
        startActivity(intent);
        overridePendingTransition(R.animator.in_to_left, R.animator.out_to_left);
        if (!canGoBack) {
            pageManager.removeAllPage();
            finishAfterTransition();
        }
    }
    public void jumpTo(Class<?> type, boolean canGoBack,PresenterSetter presenterSetter) {
        Intent intent = new Intent();
        if(null!= presenterSetter)
            intent.putExtra("presenter_setter_callback", presenterSetter);
        intent.setClass(this, type);
        startActivity(intent);
        overridePendingTransition(R.animator.in_to_left, R.animator.out_to_left);
        if (!canGoBack) {
            pageManager.removeAllPage();
            finishAfterTransition();
        }
    }
    public void jumpTo(Class<?> type, boolean canBack, Bundle args,PresenterSetter presenterSetter) {
        Intent intent = new Intent();
        if(presenterSetter !=null)
            intent.putExtra("presenter_setter_callback", presenterSetter);
        intent.setClass(this, type);
        intent.putExtras(args);
        startActivity(intent);
        overridePendingTransition(R.animator.in_to_left, R.animator.out_to_left);

        if (!canBack) {
            pageManager.removeAllPage();
            finishAfterTransition();
        }
    }

    public void onMessage(String message){
        makeToast(message);
    }

    @Override
    public void authError(String errorMessage) {
        makeToast(errorMessage);
    }

    public void makeToast(String message) {
        Toast.makeText(WeMatchApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public void makeLongToast(String message) {
        Toast.makeText(WeMatchApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }

}
