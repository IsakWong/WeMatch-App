package nullref.dlut.wematch.business.register.updateinfo;

import android.graphics.Bitmap;

import nullref.dlut.wematch.sessions.UpdateUserInfoSession;
import nullref.dlut.wematch.sessions.UploadImgSession;
import nullref.dlut.wematch.utils.Utils;

/**
 * Created by IsakWong on 2017/7/1.
 */

public class UpdateUserInfoPresenter implements UpdateUserInfoActivityContract.Presenter {


    UpdateUserInfoActivityContract.View view;
    String imagBase64;

    public UpdateUserInfoPresenter(UpdateUserInfoActivityContract.View view){
        this.view = view;
    }

    public UpdateUserInfoSession updateUserInfoSession = new UpdateUserInfoSession(this);

    @Override
    public void updateInfo(String shortInfo, String major, String school, int gender) {
        updateUserInfoSession.request.pairs.add(new UpdateUserInfoSession.Pair("shortInfo",shortInfo));
        updateUserInfoSession.request.pairs.add(new UpdateUserInfoSession.Pair("major",major));
        updateUserInfoSession.request.pairs.add(new UpdateUserInfoSession.Pair("school",school));
        updateUserInfoSession.request.pairs.add(new UpdateUserInfoSession.Pair("gender",Integer.toString(gender)));
        updateUserInfoSession.send();

    }

    @Override
    public void onUploadImg() {
    }

    @Override
    public void onUploadImgError(String cause) {

    }

    UploadImgSession uploadImgSession = new UploadImgSession(this);
    @Override
    public void onUpdateUserInfo(UpdateUserInfoSession.Response response) {

        view.onUploadImg();
        uploadImgSession.request.key = response.key;
        uploadImgSession.request.imgBase64 = imagBase64;
        //uploadImgSession.send();

    }

    @Override
    public void selectAvatar(Bitmap bitmap) {
        imagBase64 = Utils.bitmapToBase64(bitmap);
    }

    @Override
    public void onUpdateUserInfoError(String cause) {
        view.onUpdateUserInfoError(cause);
    }
}
