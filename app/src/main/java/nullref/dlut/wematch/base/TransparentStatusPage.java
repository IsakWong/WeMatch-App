package nullref.dlut.wematch.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import nullref.dlut.wematch.R;

/**
 * Created by IsakWong on 2017/6/23.
 */

public class TransparentStatusPage extends BasePage {

    public FragmentTransaction preEnterPage(FragmentTransaction transaction, BasePage targetFragment) {
        super.preEnterPage(transaction, targetFragment);
        if (targetFragment.getClass() == ColorStatusPage.class) {
            View statusBar = findViewById(R.id.status_bar);
            if (statusBar != null) {
                transaction.addSharedElement(statusBar, "share_status_bar");
            }
            if (statusBar != null) {
                transaction.addSharedElement(statusBar, "share_tool_bar");
            }
        }
        return transaction;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.status_bar).setBackgroundColor(getResources().getColor(R.color.colorTransparent));
    }
}
