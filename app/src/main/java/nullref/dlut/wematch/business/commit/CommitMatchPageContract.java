package nullref.dlut.wematch.business.commit;

import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.CommitMatchSession;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.UploadImgSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface CommitMatchPageContract {
    interface ViewStep1 {

    }

    interface ViewStep2 {

    }

    interface ViewStep3 {
        void error(String cause);

        void onCommitMatch();

        void onLabelReceived(Label[] labels);

        void onCommitInfo();

    }

    interface View {
    }

    interface Presenter extends CommitMatchSession.Listener, GetAllLabelsSession.Listener, UploadImgSession.Listener {
        void commitMatch();

        void init();
    }
}
