package nullref.dlut.wematch.business.register;

import java.util.ArrayList;

import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.layout.subscribe_labels.SubscribeLabelsActivityContract;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;

/**
 * Created by IsakWong on 2017/7/10.
 */

public class SubscribeLabelsPresenter extends BasePresenter<SubscribeLabelsActivityContract.View> implements SubscribeLabelsActivityContract.Presenter {


    SubscribeLabelsSession subscribeLabelsSession = new SubscribeLabelsSession(new SubscribeLabelsSession.Listener() {
        @Override
        public void onFollowLabels() {

            view.onFollowLabel();
        }

        @Override
        public void onFollowLabelsError(String cause) {

        }
    });
    GetAllLabelsSession getAllLabelsSession = new GetAllLabelsSession(new GetAllLabelsSession.Listener() {
        @Override
        public void onGetLabels(Label[] labels) {

            for (Label label : labels
                    ) {
                view.addLabel(label);
            }
        }

        @Override
        public void onGetLabelsError(String cause) {
            view.onFollowLabelError(cause);
        }
    });

    public void followLabels(ArrayList<Integer> labels) {
        subscribeLabelsSession.request.labelsID = labels;
        subscribeLabelsSession.send();
    }

    public void getLabelsFromInternet() {
            getAllLabelsSession.send();
    }
}
