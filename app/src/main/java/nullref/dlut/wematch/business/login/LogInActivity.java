package nullref.dlut.wematch.business.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.business.MainpageActivity;
import nullref.dlut.wematch.base.BaseActivity;


/**
 * LogInActivity super BaseActivity
 * <p>
 * 登陆界面
 * <p>
 * Model:LogInModel 处理网络传输数据，数据库读写数据
 * <p>
 * Presenter:MatchInfoPresenter 处理业务逻辑
 */

public class LogInActivity extends BaseActivity implements  LogInActivityContract.View {


    @BindView(R.id.user_edit)
    EditText userEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.btn_log_in)
    Button loginBtn;
    @BindView(R.id.btn_forget_pwd)
    Button forgetPwdBtn;

    private LogInPresenter viewModel = new LogInPresenter(this);

    private EditText username;
    private EditText password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        username = (EditText) findViewById(R.id.user_edit);
        password = (EditText) findViewById(R.id.password_edit);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onViewClicked(loginBtn);
                }
                return false;
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void loginSuccess() {
        jumpTo(MainpageActivity.class, false);
    }

    @Override
    public void loginFailed(String cause) {
        makeToast(cause);
    }


    @OnClick({R.id.btn_log_in, R.id.btn_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_log_in:
                viewModel.logIn(username.getText().toString(), password.getText().toString());
                break;
            case R.id.btn_forget_pwd:
                break;
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }
}
