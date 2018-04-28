package nullref.dlut.wematch.layout.infoset;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.layout.infoset.ModifyInformationContract;
import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by ICCD on 2017/9/7.
 */

public class ModifyInformationPresenter extends BasePresenter<ModifyInformationContract.View>implements ModifyInformationContract.Presenter {

    public static Boolean AvaChanges = false;



    private String imgBase64;

    private UpdateUserInfoSession updateUserInfoSession = new UpdateUserInfoSession(new UpdateUserInfoSession.Listener() {
        @Override
        public void onUpdateUserInfo(UpdateUserInfoSession.Response response) {
            if(imgBase64!=null){
                uploadImgSession.request.key = response.key;
                uploadImgSession.request.imgBase64 = imgBase64;
                uploadImgSession.send();
                view.onMessage("您的信息已经成功修改，现在正在上传头像");
            }
            view.onMessage("您的信息已经成功修改！");
        }

        @Override
        public void onUpdateUserInfoError(String cause) {
            view.onMessage("更新用户信息失败，错误代码:cause");
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
