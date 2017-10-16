package nullref.dlut.wematch.business.infoset;

import nullref.dlut.wematch.bean.User;
import nullref.dlut.wematch.sessions.GetUserInfoSession;
import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by ICCD on 2017/9/7.
 */

public class UserInfoSetPresenter implements UserInfoSetContract.Presenter {

    public static Boolean AvaChanges= false;

    public UserInfoSetContract.View view;
    private String imgBase64;
    private UpdateUserInfoSession updateUserInfoSession = new UpdateUserInfoSession(this);
    private UploadImgSession uploadImgSession = new UploadImgSession(this);

    public UserInfoSetPresenter(UserInfoSetContract.View view) {
        this.view = view;
    }

    @Override
    public void onUpdateUserInfo(UpdateUserInfoSession.Response response) {
        uploadImgSession.request.key = response.key;
        uploadImgSession.request.imgBase64 = imgBase64;
        uploadImgSession.send();
    }

    @Override
    public void onUploadImgError(String cause) {
        view.onUploadImgError(cause);

    }

    @Override
    public void onUploadImg() {
        view.onUploadImg();

    }

    @Override
    public void onUpdateUserInfoError(String cause) {
        view.onUpdateUserInfoError(cause);
    }


    public void modifyInfo(String key,String value){
        boolean hasKey = false;
        for (UpdateUserInfoSession.Pair pair:updateUserInfoSession.request.pairs
             ) {
            if(pair.key.equals(key) ){
                pair.value = value;
                hasKey = true;
            }
        }
        if(hasKey == false){
            updateUserInfoSession.request.pairs.add(new UpdateUserInfoSession.Pair(key,value));
        }

    }
    public void UpdateInfo() {
        updateUserInfoSession.send();
    }


    public void UpdateImg(String imgBase64) {
        this.imgBase64 = imgBase64;
        modifyInfo("avatar","true");
    }


}
