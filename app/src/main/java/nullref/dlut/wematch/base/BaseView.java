package nullref.dlut.wematch.base;

import android.content.Context;

/**
 * Created by isakwong on 2017/7/16.
 */

public interface BaseView<T extends BasePresenter>{
    void setPresenter(T presenter);
    void authError();
}
