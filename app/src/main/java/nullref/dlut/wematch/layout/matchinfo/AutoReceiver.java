package nullref.dlut.wematch.layout.matchinfo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import nullref.dlut.wematch.R;
import nullref.dlut.wematch.business.MainpageActivity;

/**
 * Created by isakwong on 2017/9/20.
 */

public class AutoReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_FLAG = 1;

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("OPEN_PAGE")) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainpageActivity.class), 0);
            // 通过Notification.Builder来创建通知，注意API Level
            // API16之后才支持
            Notification notify = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_app)
                    .setTicker("您有关注的比赛提醒")
                    .setContentTitle("请关注您的比赛！")
                    .setContentText("您有一个关注的比赛进入了您的时间表安排中，请注意！")
                    .setContentIntent(pendingIntent).setNumber(1).build(); // 需要注意build()是在API
            // level16及之后增加的，API11可以使用getNotificatin()来替代
            notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
            // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_FLAG, notify);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
        }
    }
}