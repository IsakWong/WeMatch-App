package nullref.dlut.wematch.business.infoset;

import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by ICCD on 2017/9/7.
 */

public interface UserInfoSetContract {
    interface View {
        void onUploadImg();

        void onUploadImgError(String cause);

        void onUpdateUserInfo();

        void onUpdateUserInfoError(String cause);
    }

    interface Presenter extends UpdateUserInfoSession.Listener, UploadImgSession.Listener {
        void UpdateInfo();

        void modifyInfo(String key, String value);
    }

}
