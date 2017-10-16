package nullref.dlut.wematch.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import nullref.dlut.wematch.R;

/**
 * Created by isakwong on 2017/9/22.
 */

public class ScrollUtils {

    public static View curentView;
    public static int keyboardHeight;

    static boolean isVisiableForLast = false;

    public static void addOnSoftKeyBoardVisibleListener(Activity activity, final ScrollView scrollView) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                //获得键盘高度
                keyboardHeight  = hight-displayHight;
                boolean visible = (double) displayHight / hight < 0.8;
                if(visible != isVisiableForLast){
                    int height = scrollView.getHeight() - keyboardHeight;
                    int Top = curentView.getTop();
                    if(visible){
                        Space space =(Space)scrollView.findViewById(R.id.fill_space);
                        if(space !=null){
                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) space.getLayoutParams(); //取控件textView当前的布局参数
                            linearParams.height = scrollView.getHeight();
                            space.setLayoutParams(linearParams); //使设置好的布局参数应用到控件</pre>
                        }
                        Utils.smoothScroll(scrollView,Top- height,500);
                    }

                }
                isVisiableForLast = visible;
            }
        });
    }
}
