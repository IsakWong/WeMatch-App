package nullref.dlut.wematch.business.commit;

import android.graphics.Bitmap;

import java.io.Serializable;

import nullref.dlut.wematch.bean.CommitMatch;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.CommitMatchSession;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.UploadImgSession;
import nullref.dlut.wematch.utils.Utils;

/**
 * Created by IsakWong on 2017/7/10.
 */

public class CommitMatchPresenter implements CommitMatchPageContract.Presenter, Serializable {


    public static CommitMatch commitMatch;
    public static String base64;
    public CommitMatchPageContract.ViewStep1 viewStep1;
    public CommitMatchPageContract.ViewStep2 viewStep2;
    public CommitMatchPageContract.ViewStep3 viewStep3;
    CommitMatchSession session = new CommitMatchSession(this);


    public CommitMatchPresenter() {
        commitMatch = new CommitMatch();
        base64 = new String();
    }

    public CommitMatch getCommitMatch() {
        return commitMatch;
    }

    @Override
    public void onUploadImgError(String cause) {

    }

    @Override
    public void onUploadImg() {

    }

    @Override
    public void onCommitMatch(String key) {
        viewStep3.onCommitInfo();
        UploadImgSession session = new UploadImgSession(this);
        session.request.imgBase64 = base64;
        session.request.key = key;
        session.send();
    }

    @Override
    public void onCommitMatchError(String cause) {

    }

    @Override
    public void onGetLabels(Label[] labels) {

        viewStep3.onLabelReceived(labels);
    }

    @Override
    public void onGetLabelsError(String cause) {
        viewStep3.error(cause);
    }

    @Override
    public void init() {
        GetAllLabelsSession session = new GetAllLabelsSession(this);
        session.send();
    }

    public void selectImage(Bitmap bitmap) {
        base64 = Utils.bitmapToBase64(bitmap);
    }

    @Override
    public void commitMatch() {
        session.request.commitMatch = commitMatch;
        session.send();

    }
}
