package nullref.dlut.wematch.layout.commit;

import android.graphics.Bitmap;

import nullref.dlut.wematch.bean.CommitMatch;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.CommitMatchSession;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface CommitMatchActivityContract {

    interface View {
        void onCommitMatchImage();

        void onLabelReceived(Label[] labels);

        void onCommitMatchInfo();
    }

    interface Presenter{
        void setImage(Bitmap bitmap);
        void commitMatch();
        CommitMatch getCommitMatch();
        void getAllLabelsFromServer();
    }
}
