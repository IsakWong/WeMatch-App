package nullref.dlut.wematch.business.register.updateinfo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.layout.subscribe_labels.SubscribeLabelsActivity;

/**
 * Created by IsakWong on 2017/5/18.
 */


public class UpdateUserInfoActivity extends BaseActivity implements UpdateUserInfoActivityContract.View {

    UpdateUserInfoPresenter presenter = new UpdateUserInfoPresenter(this);

    @BindView(R.id.school_spinner)
    Spinner schoolSpinner;
    @BindView(R.id.gender_spinner)
    Spinner genderSpinner;
    @BindView(R.id.reg_short_info)
    EditText regShortInfo;
    @BindView(R.id.reg_major)
    EditText regMajor;
    @BindView(R.id.reg_btn)
    Button regBtn;

    ArrayList<String> dataSpinner = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_info);
        ButterKnife.bind(this);

        dataSpinner.clear();
        dataSpinner.add("大连理工大学");
        dataSpinner.add("大连海事大学");
        dataSpinner.add("大连东软信息学院");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dataSpinner);
        schoolSpinner.setAdapter(adapter);
        schoolSpinner.setSelection(0, true);
        genderSpinner.setSelection(2, true);

    }

    @Override
    public void onUploadImg() {
        jumpTo(SubscribeLabelsActivity.class, false);

    }

    @Override
    public void onUploadImgError(String cause) {

    }

    @Override
    public void onUpdateUserInfoError(String cause) {
        Toast.makeText(getBaseContext(), cause, Toast.LENGTH_SHORT);
    }

    @Override
    public void onUpdateUserInfo() {
        jumpTo(SubscribeLabelsActivity.class,false);

    }

    @OnClick(R.id.reg_btn)
    public void onViewClicked() {

        presenter.updateInfo(regShortInfo.getText().toString(), regMajor.getText().toString(), dataSpinner.get(schoolSpinner.getSelectedItemPosition()), genderSpinner.getSelectedItemPosition());

    }
}
