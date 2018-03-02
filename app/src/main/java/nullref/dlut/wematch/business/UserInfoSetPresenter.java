package nullref.dlut.wematch.business;

import nullref.dlut.wematch.layout.infoset.ModifyInformationContract;
import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by ICCD on 2017/9/7.
 */

public class UserInfoSetPresenter implements ModifyInformationContract.Presenter {

    public static Boolean AvaChanges = false;

    public ModifyInformationContract.View view;

    private String imgBase64;

    private UpdateUserInfoSession updateUserInfoSession = new UpdateUserInfoSession(new UpdateUserInfoSession.Listener() {
        @Override
        public void onUpdateUserInfo(UpdateUserInfoSession.Response response) {
            uploadImgSession.request.key = response.key;
            uploadImgSession.request.imgBase64 = imgBase64;
            uploadImgSession.send();
        }

        @Override
        public void onUpdateUserInfoError(String cause) {
            view.onUpdateUserInfoError(cause);
        }
    });

    private UploadImgSession uploadImgSession = new UploadImgSession(new UploadImgSession.Listener() {
        @Override
        public void onUploadImg() {

            view.onUploadImg();
        }

        @Override
        public void onUploadImgError(String cause) {
            view.onUploadImgError(cause);

        }
    });




    public void modifyInfo(String key, String value) {
        boolean hasKey = false;
        for (UpdateUserInfoSession.Pair pair : updateUserInfoSession.request.pairs
                ) {
            if (pair.key.equals(key)) {
                pair.value = value;
                hasKey = true;
            }
        }
        if (hasKey == false) {
            updateUserInfoSession.request.pairs.add(new UpdateUserInfoSession.Pair(key, value));
        }

    }

    public void UpdateInfo() {
        updateUserInfoSession.send();
    }


    public void UpdateImg(String imgBase64) {
        this.imgBase64 = imgBase64;
        modifyInfo("avatar", "true");
    }


}
