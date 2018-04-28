package nullref.dlut.wematch.layout.infoset;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by ICCD on 2017/9/7.
 */

public interface ModifyInformationContract {
    interface View  extends BaseView {
        void onUploadImg();

        void onUploadImgError(String cause);

        void onUpdateUserInfo();

        void onUpdateUserInfoError(String cause);
    }

    interface Presenter {
        void UpdateImg(String base64);
        void UpdateInfo();

        void modifyInfo(String key, String value);
    }

}
