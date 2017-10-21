package nullref.dlut.wematch.business.labelinfo;

import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;

/**
 * Created by isakwong on 2017/7/17.
 */

public interface LabelinfoContract {
    interface View {
        void onFollowLabel();

        void onFollowLabelError(String cause);


    }

    interface Presenter extends SubscribeLabelsSession.Listener {
        void followLabel(Label label);
    }
}
