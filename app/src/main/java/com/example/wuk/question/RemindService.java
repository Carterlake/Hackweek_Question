package com.example.wuk.question;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class RemindService extends Service {
    private AlarmManager am;
    private static final String ACTION = "com.example.alarmClock.TIMER_ACTION";

    private IntentFilter intentFilter;
    private RemindReceiver remindReceiver;
    public RemindService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        remindReceiver = new RemindReceiver();
        // Toast.makeText(getApplicationContext(),"successful Add service !",Toast.LENGTH_SHORT).show();
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(remindReceiver,intentFilter);
        Remind remind = new Remind();
        List<Remind> reminds = LitePal.findAll(Remind.class);
        remind = reminds.get(0);
        setRepeatingAlarmTimer(getApplicationContext(),getRemindTime(remind.getHour(),remind.getMin()),action);
        return START_REDELIVER_INTENT;


    }
    public static void setRepeatingAlarmTimer(Context context, long firstTime,
                                              String action) {
        Intent myIntent = new Intent("com.example.alarmClock.TIMER_ACTION");
        myIntent.putExtra("name","com.example.alarmClock.TIMER_ACTION");

        PendingIntent sender = PendingIntent.getBroadcast(context, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (action.equals("set")){
            myIntent.putExtra("action0","t");
            sender = PendingIntent.getBroadcast(context, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Toast.makeText(context,"succsessful set remind !",Toast.LENGTH_SHORT).show();
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstTime+2000,60*60*1000*24, sender);}
        if (action.equals("cancel")){
            myIntent.putExtra("action0","e");
            sender = PendingIntent.getBroadcast(context, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Toast.makeText(context,"succsessful cancel remind !",Toast.LENGTH_SHORT).show();
            alarm.cancel(sender);
        }
        if (action.equals("test")){
            Toast.makeText(context,"succsessful set test remind !",Toast.LENGTH_SHORT).show();
            myIntent.putExtra("action0","test");
            sender = PendingIntent.getBroadcast(context, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+2000,60000, sender);
        }
        if (action.equals("test_cancel")){
            Toast.makeText(context,"succsessful cancel test remind !",Toast.LENGTH_SHORT).show();
            myIntent.putExtra("action0","test");
            sender = PendingIntent.getBroadcast(context, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarm.cancel(sender);
        }
    }


    public static long getRemindTime(int hour,int min){
        Calendar calendar = Calendar.getInstance();
        long systemTime = System.currentTimeMillis();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long selectTime = calendar.getTimeInMillis();
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        selectTime = calendar.getTimeInMillis();
        return selectTime;

    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}