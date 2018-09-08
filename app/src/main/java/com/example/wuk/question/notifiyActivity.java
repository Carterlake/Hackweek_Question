package com.example.wuk.question;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.litepal.LitePal;

import java.util.List;

public class notifiyActivity extends AppCompatActivity {

    private ImageButton exit_button;
    private ToggleButton toggleButton;
    private TextView time_text,timePeriod_text;
    private PopupWindow timePicker_PopUpWindow;
    private TimePicker timePicker;
    private Button timePicker_final_button;
    private int hour = 4, min = 39;
    private String period = "Am";
    private String time;
    private Button date_button0,date_button1,date_button2,date_button3,date_button4,date_button5,date_button6;
    private Remind remind = new Remind();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiy);
        List<Remind> Reminds = LitePal.findAll(Remind.class);
        remind = Reminds.get(0);

        findView();
        setView();
        setOnClickListener();
    }

    private void findView(){
        exit_button =findViewById(R.id.notify_exit_button);
        toggleButton = findViewById(R.id.notify_toggle_button);
        time_text =findViewById(R.id.notify_time_text);
        timePeriod_text = findViewById(R.id.notify_timePeriod_text);
        date_button0 = findViewById(R.id.date_button0);
        date_button1 = findViewById(R.id.date_button1);
        date_button2 = findViewById(R.id.date_button2);
        date_button3 = findViewById(R.id.date_button3);
        date_button4 = findViewById(R.id.date_button4);
        date_button5 = findViewById(R.id.date_button5);
        date_button6 = findViewById(R.id.date_button6);
    }

    private void changeDateButton(Button button,int order){
        switch (order){
            case 0:remind.date_state0=(remind.date_state0==1?2:1);break;
            case 1:remind.date_state1=(remind.date_state1==1?2:1);break;
            case 2:remind.date_state2=(remind.date_state2==1?2:1);break;
            case 3:remind.date_state3=(remind.date_state3==1?2:1);break;
            case 4:remind.date_state4=(remind.date_state4==1?2:1);break;
            case 5:remind.date_state5=(remind.date_state5==1?2:1);break;
            case 6:remind.date_state6=(remind.date_state6==1?2:1);break;
            default:break;
        }
        setView();

        remind.updateAll();
    }

    private void setView(){

        setDateButtonView();
        toggleButton.setBackgroundResource(MainActivity.isToggleChecked?R.drawable.togglebuttonon:R.drawable.togglebuttonoff);
        toggleButton.setChecked(MainActivity.isToggleChecked);
        hour = remind.getHour();
        if (hour >12){
            hour = hour-12;
            period = "Pm";
        }else {
            period = "Am";
        }
        min = remind.getMin();
        if (hour<10){
            time = "0"+hour+": ";
        }else {
            time = ""+hour+": ";
        }
        if (min<10){
            time = time + "0"+min;
        } else{
            time = time +""+min;
        }
        time_text.setText(time);
        timePeriod_text.setText(period );
    }
    private void setDateButtonView(){
        if (remind.date_state0==1){
            date_button0.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button0.setBackgroundResource(R.drawable.circlegray);
        }
        if (remind.date_state1==1){
            date_button1.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button1.setBackgroundResource(R.drawable.circlegray);
        }
        if (remind.date_state2==1){
            date_button2.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button2.setBackgroundResource(R.drawable.circlegray);
        }
        if (remind.date_state3==1){
            date_button3.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button3.setBackgroundResource(R.drawable.circlegray);
        }
        if (remind.date_state4==1){
            date_button4.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button4.setBackgroundResource(R.drawable.circlegray);
        }
        if (remind.date_state5==1){
            date_button5.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button5.setBackgroundResource(R.drawable.circlegray);
        }
        if (remind.date_state6==1){
            date_button6.setBackgroundResource(R.drawable.circlegreen);
        }else {
            date_button6.setBackgroundResource(R.drawable.circlegray);
        }
    }

    private void setOnClickListener(){
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleButton.isChecked()){
                    remind.setFuck(1);
                    Intent intent = new Intent(getApplicationContext(),RemindService.class);
                    intent.putExtra("action","set");
                    startService(intent);
                }else {
                    remind.setFuck(2);
                    Intent intent = new Intent(getApplicationContext(),RemindService.class);
                    intent.putExtra("action","cancel");
                    startService(intent);
                }
                remind.updateAll();
                toggleButton.setBackgroundResource(toggleButton.isChecked()?R.drawable.togglebuttonon:R.drawable.togglebuttonoff);
                MainActivity.isToggleChecked = toggleButton.isChecked();

            }
        });
        date_button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button0,0);
            }
        });
        date_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button1,1);
            }
        });
        date_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button2,2);
            }
        });
        date_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button3,3);
            }
        });
        date_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button4,4);
            }
        });
        date_button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button5,5);
            }
        });
        date_button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateButton(date_button6,6);
            }
        });


        time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerPopUpWindow();
            }
        });
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showTimePickerPopUpWindow(){
        View contentView = LayoutInflater.from(notifiyActivity.this).inflate(R.layout.timepicker_popupwindow_layout,null);
        timePicker_PopUpWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,true);
        timePicker_PopUpWindow.setContentView(contentView);
        timePicker = contentView.findViewById(R.id.timePicker);
        timePicker_final_button = contentView.findViewById(R.id.timePicker_final_button);
        hour = remind.getHour();
        if (hour>12){
            period = "Pm";
        }
        if (period.equals("Pm")){
            hour = remind.getHour()-12;
            timePicker.setHour(hour+12);
        }else {
            timePicker.setHour(hour);
        }
        min = remind.getMin();
        timePicker.setMinute(min);

        timePicker_final_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hour = timePicker.getHour();
                min =timePicker.getMinute();

                remind.setHour(hour);
                remind.setMin(min);

                remind.updateAll();
                Toast.makeText(getApplicationContext(),"succeful change!",Toast.LENGTH_SHORT).show();


                if (hour>12){
                    hour = hour - 12;
                    period = "Pm";
                }else {
                    period = "Am";
                }
                if (hour<10){
                    time = "0"+hour+": ";
                }else {
                    time = ""+hour+": ";
                }
                if (min<10){
                    time = time + "0"+min;
                } else{
                    time = time +""+min;
                }
                time_text.setText(time);
                timePeriod_text.setText(period );
                timePicker_PopUpWindow.dismiss();
            }
        });
        View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_notifiy,null);
        timePicker_PopUpWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
    }
}