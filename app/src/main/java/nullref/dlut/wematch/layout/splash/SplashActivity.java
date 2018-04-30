package nullref.dlut.wematch.layout.splash;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nullref.dlut.wematch.R;
import nullref.dlut.wematch.base.BaseActivity;
import nullref.dlut.wematch.layout.MainpageActivity;
import nullref.dlut.wematch.layout.login.LogInPresenter;
import nullref.dlut.wematch.layout.register.RegisterActivity;
import nullref.dlut.wematch.layout.login.LogInActivity;

/**
 * Created by IsakWong on 2017/6/26.
 */

public class SplashActivity extends BaseActivity<SplashActivityContract.Presenter> implements SplashActivityContract.View {



    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        SplashPresenter splashPresenter = new SplashPresenter();
        splashPresenter.setView(this);
        setPresenter(splashPresenter);
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

    static PresenterSetter presenterSetter = new PresenterSetter() {
        @Override
        public void Run(BaseActivity baseActivity) {

            final LogInPresenter logInPresenter = new LogInPresenter();
            logInPresenter.setView((LogInActivity)baseActivity);
            baseActivity.setPresenter(logInPresenter);
        }
    };
    @OnClick(R.id.login_btn)
    public void onLoginBtnClicked() {

        jumpTo(LogInActivity.class, false, presenterSetter);
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
        jumpTo(RegisterActivity.class, true);
    }

}
