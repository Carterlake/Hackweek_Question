package com.example.wuk.question;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout main_linearLayout;
    private float progress1;
    private MyThread thread;
    private Button Go_to_question_button;
    private int color1;
    private int color2 = 0x00CAFFCF;
    private int color3;
    private  int j = 0;
    private Animation startAnimation,endAnimation;
    private Animation appearAnimation,appearAnimation2;
    private ToggleButton If_notified_button;
    private Button drawer_notify_button,draw_notify_test,draw_notify_test_cancel;
    public static Boolean isToggleChecked = false;
    private Remind remind;
    private List<Remind> reminds;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    public static String clickedQuestion;
    public static String clickedAnswer;
    public static String clickedDate;
    public static String clickedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new AnswerQuestionFragment());

        registerMyReceiver();

        init();
        findViewById();
        setView();
        setOnclickListener();
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mFragment, fragment);
        transaction.commit();
    }

    private BroadcastReceiver changeFragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals("changeToAnswerListFragment")) {
                replaceFragment(new AnswerListFragment());
            } else if (action.equals("changeToAnswerContentFragment")) {
                replaceFragment(new AnswerContentFragment());
            }
        }
    };

    private void registerMyReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeToAnswerListFragment");
        intentFilter.addAction("changeToAnswerContentFragment");
        registerReceiver(changeFragmentReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(changeFragmentReceiver);
        super.onDestroy();
    }

    private void setView(){
        If_notified_button.setTextOff("");
        If_notified_button.setTextOn("");
        If_notified_button.setChecked(isToggleChecked);
        If_notified_button.setBackgroundResource(If_notified_button.isChecked()?R.drawable.togglebuttonon:R.drawable.togglebuttonoff);
    }

    private void findViewById(){
        If_notified_button = findViewById(R.id.toggle_button);
        draw_notify_test = findViewById(R.id.drawer_notify_test);
        main_linearLayout = findViewById(R.id.main_linear);
        Go_to_question_button= findViewById(R.id.Go_to_question);
        drawer_notify_button =findViewById(R.id.drawer_notify_button);
        draw_notify_test_cancel = findViewById(R.id.drawer_notify_test_cancel);
    }

    private void init(){
        reminds = LitePal.findAll(Remind.class);
        if (reminds.size()==0){
            remind = new Remind();
            remind.isCreate = true;
            remind.setHour(8);
            remind.setMin(45);
            remind.setId(1);
            remind.setFuck(0);
            remind.save();
        } else {
            remind =reminds.get(0);
            if (remind.getFuck() == 1){
                isToggleChecked = true;
                Intent intent = new Intent(getApplicationContext(),RemindService.class);
                intent.putExtra("action","set");
                startService(intent);

            }else {
                isToggleChecked =false;
            }
        }
    }

    private void setOnclickListener(){
        If_notified_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Remind mRemind = new Remind();
                if (If_notified_button.isChecked()){
                    mRemind.setFuck(1);
                    Intent intent = new Intent(getApplicationContext(),RemindService.class);
                    intent.putExtra("action","set");
                    startService(intent);
                }else {
                    mRemind.setFuck(2);
                    Intent intent = new Intent(getApplicationContext(),RemindService.class);
                    intent.putExtra("action","cancel");
                    startService(intent);
                }
                mRemind.updateAll();

                If_notified_button.setBackgroundResource(If_notified_button.isChecked()?
                        R.drawable.togglebuttonon:R.drawable.togglebuttonoff);
                isToggleChecked =  If_notified_button.isChecked();

            }
        });

        Go_to_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuestionStoreActivity.class);
                startActivity(intent);
            }
        });


        draw_notify_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RemindService.class);
                intent.putExtra("action","test");
                startService(intent);

            }
        });
        drawer_notify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, notifiyActivity.class);
                startActivity(intent);
            }
        });
        draw_notify_test_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RemindService.class);
                intent.putExtra("action","test_cancel");
                startService(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        If_notified_button.setBackgroundResource(isToggleChecked?R.drawable.togglebuttonon:R.drawable.togglebuttonoff);
        If_notified_button.setChecked(isToggleChecked);
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
            color1 = 0xffA1FFCE;
            color2 =0x00CAFFCF;
            color3 = 0xffFAFFD1;
            j = 0;
            for (int i=0;i<40;i++){
                j++;
                //int k1 =(0xffACB6E5 -  0xffA1FFCE)/100;
                //int k2 =(0xff86FDE8 - 0xffa1ffce)/100;
                final int k1 = 0x00ffffff;
                final int k2 = 0x00ffffff;
                try {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            color1 = color1-k1;
                            color2 =color2-k1;
                            color3 = color3-k2;
                            setColor(color1,color2,color3);
                        }
                    });
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i=0;i<3;i++){
                final int k1 = 0x00000002;
                final int k2 = 0x00000002;
                try {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            color1 = color1+k1;
                            color2 = color2+k2;
                            color3 = color3+k2;
                            setColor(color1,color2,color3);
                        }
                    });
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void setColor(int Color1, int Color2 ,int Color3){
        //  int colors[] = { 0xffACB6E5 ,0xff00000, 0xff86FDE8};
        int colors2[] = {Color1,Color2,Color3};
        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors2);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            main_linearLayout.setBackgroundDrawable(bg);
        } else {
            main_linearLayout.setBackground(bg);
        }
    }

    private void setColor2(){
      /*  CountDownTimer timer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                main_linearLayout.setBackgroundResource(R.drawable.colorful_backgound_layout2);
            }
        };
        timer.start();*/
        appearAnimation = new AlphaAnimation(1.0f,0.3f);
        appearAnimation.setDuration(1000);
        main_linearLayout.startAnimation(appearAnimation);
        appearAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                main_linearLayout.setBackgroundResource(R.drawable.colorful_backgound_layout2);
                appearAnimation2 = new AlphaAnimation(0.3f,1.0f);
                appearAnimation2.setDuration(1000);
                main_linearLayout.startAnimation(appearAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    private void setColor3(){
        ValueAnimator colorAnim = ObjectAnimator.ofInt(main_linearLayout,"backgroundColor",R.drawable.colorful_backgound_layout,0xff8080ff);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }
}
