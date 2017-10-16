package nullref.dlut.wematch.business;

import nullref.dlut.wematch.layout.userinfo.UserinfoContract;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class UserinfoPresenter implements UserinfoContract.Presenter {

    UserinfoContract.View view;

    @Override
    public void setView(UserinfoContract.View view) {
        this.view = view;
    }

    public UserinfoPresenter(UserinfoContract.View view) {
        this.view = view;
    }


}
