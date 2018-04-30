package nullref.dlut.wematch.base;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface BaseView{

    void onMessage(String message);
    void authError(String errorMessage);
}
