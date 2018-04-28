package nullref.dlut.wematch.layout.updateinfo;

import android.graphics.Bitmap;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface UpdateUserInfoActivityContract {
    interface View extends BaseView {
        void onUpdateUserInfo();

        void onUpdateUserInfoError(String cause);

        void onUploadImg();

        void onUploadImgError(String cause);
    }

    interface Presenter extends UpdateUserInfoSession.Listener, UploadImgSession.Listener {
        void updateInfo(String shortInfo, String major, String school, int gender);

        void selectAvatar(Bitmap bitmap);
    }
}
