package nullref.dlut.wematch.layout.subscribe_labels;

import java.util.ArrayList;

import nullref.dlut.wematch.base.BaseView;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface SubscribeLabelsActivityContract {
    interface View  extends BaseView {
        void addLabel(Label label);

        void onFollowLabel();

        void onFollowLabelError(String cause);
    }

    interface Presenter  {

        void getLabelsFromInternet();

        void followLabels(ArrayList<Integer> labels);
    }
}
