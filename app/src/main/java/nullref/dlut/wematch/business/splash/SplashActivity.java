package nullref.dlut.wematch.business.splash;

import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.base.BasePresenter;
import nullref.dlut.wematch.business.MainpageActivity;
import nullref.dlut.wematch.business.login.LogInActivity;
import nullref.dlut.wematch.business.register.RegisterActivity;

/**
 * Created by IsakWong on 2017/6/26.
 */

public class SplashActivity extends BaseActivity implements SplashActivityContract.View {


    SplashPresenter presenter = new SplashPresenter(this);

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.regiser_btn)
    Button regiserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.checkFirstRun();
    }


    @Override
    public void loginFailed() {
    }

    @Override
    public void loginSuccess() {
        jumpTo(MainpageActivity.class, false);
    }

    /**
     * 第一次运行App的操作
     */
    public void firstRun() {

    }

    /**
     * App不是第一次运行时的操作
     */
    public void notFirstRun() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.login_btn)
    public void onLoginBtnClicked()
    {
        jumpTo(LogInActivity.class,false);
    }

    @OnClick(R.id.regiser_btn)
    public void onRegiserBtnClicked() {
        jumpTo(RegisterActivity.class,true);
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }
}
