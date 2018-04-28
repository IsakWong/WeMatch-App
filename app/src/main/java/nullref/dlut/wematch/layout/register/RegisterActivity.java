package nullref.dlut.wematch.layout.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.layout.login.LogInActivity;
import nullref.dlut.wematch.layout.updateinfo.UpdateUserInfoActivity;

/**
 * Created by IsakWong on 2017/5/18.
 */


public class RegisterActivity extends BaseActivity<RegisterActivityContract.Presenter> implements RegisterActivityContract.View {


    @BindView(R.id.email_edit)
    EditText emailEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.reg_btn)
    Button regBtn;
    @BindView(R.id.back_btn)
    Button backBtn;
    @BindView(R.id.name_edit)
    EditText nameEdit;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        RegisterPresenter registerPresenter = new RegisterPresenter();
        setPresenter(registerPresenter);
        registerPresenter.setView(this);
    }


    @Override
    public void loginSuccess() {
        jumpTo(UpdateUserInfoActivity.class, false);
    }

    @Override

    public void loginFailed(String error) {
        jumpTo(LogInActivity.class, false);
        makeToast(error);
    }

    @Override
    public void registerSuccess() {
        makeToast("注册成功，正在登陆中...");

    }

    @Override

    public void registerFailed(String error) {
        makeToast(error);

    }


    @OnClick(R.id.reg_btn)
    public void onRegBtnClicked() {
        presenter.register(emailEdit.getText().toString(), passwordEdit.getText().toString(), nameEdit.getText().toString());
    }

    @OnClick(R.id.back_btn)
    public void onBackBtnClicked() {
    }
}
