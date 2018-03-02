package nullref.dlut.wematch.business.register;

import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.sessions.RegisterSession;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface RegisterActivityContract {
    interface View {

        void loginSuccess();

        void loginFailed(String error);

        void registerSuccess();

        void registerFailed(String errpr);
    }

    interface Presenter{
        void register(String email, String pwd, String name);

    }
}
