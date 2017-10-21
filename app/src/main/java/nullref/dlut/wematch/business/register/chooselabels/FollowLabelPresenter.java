package nullref.dlut.wematch.business.register.chooselabels;

import java.util.ArrayList;

import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;

/**
 * Created by IsakWong on 2017/7/10.
 */

public class FollowLabelPresenter implements FollowLabelActivityContract.Presenter {


    FollowLabelActivityContract.View view;
    SubscribeLabelsSession subscribeLabelsSession = new SubscribeLabelsSession(this);
    private boolean gettingLabels = false;

    public FollowLabelPresenter(FollowLabelActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void onFollowLabels() {
        view.onFollowLabel();

    }

    @Override
    public void onFollowLabelsError(String cause) {
        view.onFollowLabelError(cause);
    }

    @Override
    public void onGetLabels(Label[] labels) {

        for (Label label : labels
                ) {
            view.addLabel(label);
        }

    }

    @Override
    public void onGetLabelsError(String cause) {

    }

    @Override
    public void followLabels(ArrayList<Integer> labels) {
        subscribeLabelsSession.request.labelsID = labels;
        subscribeLabelsSession.send();
    }

    public void getLabelsFromInternet() {
        if (gettingLabels == false) {
            GetAllLabelsSession session = new GetAllLabelsSession(this);
            session.send();
        }
    }
}
