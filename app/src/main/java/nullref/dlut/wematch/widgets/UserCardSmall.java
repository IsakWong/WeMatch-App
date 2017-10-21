package nullref.dlut.wematch.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.bean.UserListInfo;

/**
 * Created by isakwong on 2017/10/19.
 */

public class UserCardSmall extends LinearLayout {

    public UserListInfo userListInfo;

    public UserCardSmall(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.discovery_user_card, this);
    }

    public UserCardSmall(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.discovery_user_card, this);
    }


}
