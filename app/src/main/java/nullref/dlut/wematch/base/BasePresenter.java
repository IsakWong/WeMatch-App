package nullref.dlut.wematch.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by isakwong on 2017/10/3.
 */

public class BasePresenter<T> implements Serializable {
    public T view;
    public void setView(T view){
        this.view = view;
    }


}
