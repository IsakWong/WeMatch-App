package nullref.dlut.wematch.layout.subscribe_labels;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.bean.Label;
import nullref.dlut.wematch.layout.MainpageActivity;

/**
 * Created by IsakWong on 2017/7/8.
 */

public class SubscribeLabelsActivity extends BaseActivity<SubscribeLabelsActivityContract.Presenter>implements SubscribeLabelsActivityContract.View {




    ArrayList<Label> labels = new ArrayList<>();

    @BindView(R.id.labels_group)
    LinearLayout labelsGroup;
    @BindView(R.id.reg_btn)
    Button regBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_choose);
        ButterKnife.bind(this);
        labelsGroup = (LinearLayout) findViewById(R.id.labels_group);
        presenter.getLabelsFromInternet();
    }

    public void addLabel(Label label) {
        CheckBox labelBtn = new CheckBox(this);
        labelBtn.setText(label.getName());
        labelBtn.setTextColor(getResources().getColor(R.color.textGray));
        labelBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textBody1));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.LEFT;
        labelBtn.setLayoutParams(lp);
        labels.add(label);
        labelsGroup.addView(labelBtn, lp);
    }

    @Override
    public void onFollowLabel() {

        jumpTo(MainpageActivity.class, false);
    }

    @Override
    public void onFollowLabelError(String cause) {
        makeToast(cause);
    }

    @OnClick(R.id.reg_btn)
    public void onViewClicked() {
        ArrayList<Integer> chooseLabels = new ArrayList<>();
        for (int i = 0; i < labelsGroup.getChildCount(); i++) {
            CheckBox labelBox = (CheckBox) labelsGroup.getChildAt(i);
            if (labelBox.isChecked()) {
                chooseLabels.add(labels.get(i).getId());
            }
        }
        if (chooseLabels.size() < 4) {
            makeToast("至少选择四个以上的标签");
        } else {
            presenter.followLabels(chooseLabels);
        }
    }
}
