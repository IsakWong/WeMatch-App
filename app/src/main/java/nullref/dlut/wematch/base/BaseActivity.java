package nullref.dlut.wematch.base;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.utils.PageManager;
/* Created by IsakWong on 2017/5/15.
 */

public class BaseActivity extends AppCompatActivity {


    protected PageManager pageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageManager = new PageManager(getSupportFragmentManager());
    }

    @Override
    protected void onDestroy() {
        pageManager = null;
        super.onDestroy();
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    public void popDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void jumpTo(Class<?> type, boolean canBack) {
        Intent intent = new Intent();
        intent.setClass(this, type);
        startActivity(intent);
        overridePendingTransition(R.animator.in_to_left, R.animator.out_to_left);
        if (!canBack) {
            pageManager.removeAllPage();
            finishAfterTransition();
        }
    }

    public void jumpTo(Class<?> type, boolean canBack, Bundle args) {
        Intent intent = new Intent();
        intent.setClass(this, type);
        intent.putExtras(args);
        startActivity(intent);
        overridePendingTransition(R.animator.in_to_left, R.animator.out_to_left);

        if (!canBack) {
            pageManager.removeAllPage();
            finishAfterTransition();
        }
    }

    public Context getInstance() {

        return this;
    }

    public void authError() {

    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void makeLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
