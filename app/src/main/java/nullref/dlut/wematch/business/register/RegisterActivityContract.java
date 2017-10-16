package nullref.dlut.wematch.business.register;

import nullref.dlut.wematch.sessions.LoginSession;
import nullref.dlut.wematch.sessions.RegisterSession;
import nullref.dlut.wematch.sessions.UpdateUserInfoSession;

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

    interface Presenter extends RegisterSession.Listener, LoginSession.Listener{
        void register(String email,String pwd,String name);

    }
}
