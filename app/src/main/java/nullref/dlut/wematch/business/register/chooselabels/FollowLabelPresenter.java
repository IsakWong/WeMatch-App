package nullref.dlut.wematch.business.register.chooselabels;

import java.util.ArrayList;

import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;
import nullref.dlut.wematch.sessions.GetAllLabelsSession;

/**
 * Created by IsakWong on 2017/7/10.
 */

public class FollowLabelPresenter implements FollowLabelActivityContract.Presenter {


    FollowLabelActivityContract.View view;

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

    SubscribeLabelsSession subscribeLabelsSession = new SubscribeLabelsSession(this);
    @Override
    public void followLabels(ArrayList<Integer> labels) {
        subscribeLabelsSession.request.labelsID = labels;
        subscribeLabelsSession.send();
    }

    private boolean gettingLabels = false;

    public void getLabelsFromInternet() {
        if (gettingLabels == false) {
            GetAllLabelsSession session = new GetAllLabelsSession(this);
            session.send();
        }
    }
}
