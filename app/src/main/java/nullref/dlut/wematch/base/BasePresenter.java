package nullref.dlut.wematch.base;

/**
 * Created by isakwong on 2017/10/3.
 */

public interface BasePresenter<T extends BaseView> {
    void setView(T view);
}
