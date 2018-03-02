package nullref.dlut.wematch.business;

import android.graphics.Bitmap;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.bean.CommitMatch;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.layout.commit.CommitMatchActivityContract;
import nullref.dlut.wematch.sessions.CommitMatchSession;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.UploadImgSession;
import nullref.dlut.wematch.utils.Utils;

/**
 * Created by IsakWong on 2017/7/10.
 */

public class CommitMatchPresenter  extends BasePresenter<CommitMatchActivityContract.View> implements CommitMatchActivityContract.Presenter{


    public CommitMatch commitMatch = new CommitMatch();
    public String base64 = new String();

    GetAllLabelsSession getAllLabelsSession = new GetAllLabelsSession(new GetAllLabelsSession.Listener() {
        @Override
        public void onGetLabels(Label[] labels) {

            view.onLabelReceived(labels);
        }

        @Override
        public void onGetLabelsError(String cause) {

        }
    });

    UploadImgSession uploadImgSession = new UploadImgSession(new UploadImgSession.Listener() {
        @Override
        public void onUploadImg() {

        }

        @Override
        public void onUploadImgError(String cause) {

        }
    });



    CommitMatchSession session = new CommitMatchSession(new CommitMatchSession.Listener() {
        @Override
        public void onCommitMatch(String key) {
            view.onCommitMatchInfo();
            uploadImgSession.request.imgBase64 = base64;
            uploadImgSession.request.key = key;
            uploadImgSession.send();
        }

        @Override
        public void onCommitMatchError(String cause) {

        }
    });

    @Override
    public void setImage(Bitmap bitmap) {
        base64 = Utils.bitmapToBase64(bitmap);

    }


    public  CommitMatch getCommitMatch() {
        return commitMatch;
    }

    public void getAllLabelsFromServer() {
        getAllLabelsSession.send();
    }


    public void commitMatch() {
        session.request.commitMatch = commitMatch;
        session.send();

    }
}
