package nullref.dlut.wematch.business.register.chooselabels;

import java.util.ArrayList;

import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface FollowLabelActivityContract {
    interface View {
        void addLabel(Label label);

        void onFollowLabel();

        void onFollowLabelError(String cause);
    }

    interface Presenter extends GetAllLabelsSession.Listener, SubscribeLabelsSession.Listener {

        void getLabelsFromInternet();

        void followLabels(ArrayList<Integer> labels);
    }
}
