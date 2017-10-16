package nullref.dlut.wematch.business.labelinfo;

import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.sessions.SubscribeLabelsSession;

/**
 * Created by IsakWong on 2017/5/25.
 */

public class LabelinfoPresenter implements LabelinfoContract.Presenter{

    LabelinfoContract.View view;


    public LabelinfoPresenter(LabelinfoContract.View view) {
        this.view = view;
    }

    SubscribeLabelsSession session = new SubscribeLabelsSession(this);
    @Override
    public void followLabel(Label label) {
        session.request.labelsID.clear();
        session.request.labelsID.add(label.getId());
        session.send();
    }

    @Override
    public void onFollowLabels() {
        view.onFollowLabel();
    }

    @Override
    public void onFollowLabelsError(String cause) {
        view.onFollowLabelError(cause);
    }
}
