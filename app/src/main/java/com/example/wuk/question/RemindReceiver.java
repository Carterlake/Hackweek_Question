package com.example.wuk.question;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;

public class RemindReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action0 = "1";
        action0 = intent.getStringExtra("action0");
        if (action0.equals("test")){
            Toast.makeText(context,"successful add test broadcast !",Toast.LENGTH_SHORT).show();
            setNotification(context);
        }
        Remind remind = new Remind();
        List<Remind> reminds = LitePal.findAll(Remind.class);
        remind = reminds.get(0);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DateTime dateTime = new DateTime(year,month,day,0,0);
        int d = dateTime.getDayOfWeek();
        int[] state = {remind.date_state0,remind.date_state1,remind.date_state2,remind.date_state3,remind.date_state4,remind.date_state5,remind.date_state6};
        if (state[d] == 1){
            Toast.makeText(context,"successful add broadcast !",Toast.LENGTH_SHORT).show();
            setNotification(context);
        }
    }

    public static void setNotification(Context context){

        String id = "notification_channel_01";
        CharSequence name = context.getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id,name,importance);
        mChannel.setDescription(description);
        int notifyID = 1;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
        Notification.Builder builder = new Notification.Builder(context,id);
        Intent  intent1 = new Intent(context.getApplicationContext(),MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,1,intent1,0);

        Notification notification = builder
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("这儿有一个问题等待您来回答呢")
                .setChannelId(id)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(notifyID,notification);

    }
}