package com.example.wuk.question;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */

public class AnswerQuestionFragment extends Fragment implements View.OnClickListener {

    ViewGroup.LayoutParams layoutParams;

    RelativeLayout topLayout;

    View btnDelete, btnClose, btnNext, editDialog;

    ImageButton btnCommit;

    TextView tvQuestion, tvTime, tvDate;

    EditText editText;

    View segmentLine;

    View view;

    Timer timer;

    private static final String TAG = "AnswerQuestionFragment";

    private int width, height;

    public AnswerQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_answer_question, container, false);

        findMyView();

        setMyViewSize();

        tvQuestion.setText(QuestionManager.getQuestionAtPosition(QuestionManager.getCurrentPosition()).getQuestion());

        registerMyReceiver();

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction("updateTime");
                getActivity().sendBroadcast(intent);
            }
        };
        timer.schedule(timerTask, 0, 500);

        return view;
    }

    private void findMyView(){
        btnDelete = view.findViewById(R.id.btnDelete);
        btnClose = view.findViewById(R.id.btnClose);
        btnNext = view.findViewById(R.id.btnNext);
        tvQuestion = view.findViewById(R.id.tvQuestion);
        segmentLine = view.findViewById(R.id.segmentLine);
        topLayout = view.findViewById(R.id.topLayout);
        editDialog = view.findViewById(R.id.editDialog);
        editText = view.findViewById(R.id.evAnswer);
        btnCommit = view.findViewById(R.id.btnCommit);
        tvTime = view.findViewById(R.id.tvTime);
        tvDate = view.findViewById(R.id.tvDate);

        btnNext.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    private void setMyViewSize() {
        height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        layoutParams = topLayout.getLayoutParams();
        layoutParams.height = (int) (height / 2.4);
        topLayout.setLayoutParams(layoutParams);

        layoutParams = tvQuestion.getLayoutParams();
        //为什么这样是0...
        /*layoutParams.height = (int) (view.getLayoutParams().height * 2.0 / 3.0);
        layoutParams.width = (int) (view.getLayoutParams().width * 4.0 / 5.0);*/
        layoutParams.height = (int) (height / 5.4);
        layoutParams.width = (int) (width * 2.0 / 3);
        tvQuestion.setLayoutParams(layoutParams);

        layoutParams = segmentLine.getLayoutParams();
        layoutParams.width = (int) (width * 8.0 / 9);
        segmentLine.setLayoutParams(layoutParams);

        layoutParams = editDialog.getLayoutParams();
        layoutParams.height = (int) (height / 3.8);
        layoutParams.width = (int) (width * 2.0 / 3);
        editDialog.setLayoutParams(layoutParams);

        layoutParams = editText.getLayoutParams();
        layoutParams.height = (int) (height / 4.8) - 32;
        layoutParams.width = (int) (width * 2.0 / 3) - 96;
        editText.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnClose:
                timer.cancel();
                intent = new Intent();
                intent.setAction("changeToAnswerListFragment");
                getActivity().sendBroadcast(intent);
                break;
            case R.id.btnDelete:
                if (QuestionManager.getListSize() == 1) {
                    Toast.makeText(getActivity(), "至少要保留一个问题喔", Toast.LENGTH_SHORT).show();
                } else {
                    /*LitePal.deleteAll(Question.class, "question = ?", QuestionManager.getQuestionAtPosition(QuestionManager.getCurrentPosition()).getQuestion());*/
                    Question updateQuestion = new Question();
                    updateQuestion.setIsDeleted(2);
                    updateQuestion.updateAll("question = ?", QuestionManager.getQuestionAtPosition(QuestionManager.getCurrentPosition()).getQuestion());
                    Answer updateAnswer = new Answer();
                    updateAnswer.setIsDeleted(2);
                    updateAnswer.updateAll("question = ?", QuestionManager.getQuestionAtPosition(QuestionManager.getCurrentPosition()).getQuestion());
                    QuestionManager.remove(QuestionManager.getCurrentPosition());
                    Log.w(TAG, "You delete" + QuestionManager.getCurrentPosition());
                    tvQuestion.setText(QuestionManager.getRandomQuestion().getQuestion());
                    editText.setText("");
                }
                break;
            case R.id.btnNext:
                int lastPosition = QuestionManager.getCurrentPosition();
                tvQuestion.setText(QuestionManager.getRandomQuestion().getQuestion());
                if (lastPosition != QuestionManager.getCurrentPosition())
                    editText.setText("");
                break;
            case R.id.btnCommit:
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "内容不可以为空喔", Toast.LENGTH_SHORT).show();
                    break;
                }
                Answer answer = new Answer();
                answer.setQuestion(tvQuestion.getText().toString());
                answer.setContent(editText.getText().toString());

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                answer.setYear(year);
                answer.setMonth(month + 1);
                answer.setDay(day);
                answer.setHour(hour);
                answer.setMinute(minute);
                answer.save();

                timer.cancel();
                intent = new Intent();
                intent.setAction("changeToAnswerListFragment");
                getActivity().sendBroadcast(intent);
                break;
        }
    }

    private BroadcastReceiver updateUiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals("updateTime")) {
                Calendar c = Calendar.getInstance();
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                tvDate.setText(month+"月" + day + "日" + "/");
                tvTime.setText(" " + hour+ ":" + minute);
            }
        }
    };

    private void registerMyReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateTime");
        getActivity().registerReceiver(updateUiReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(updateUiReceiver);
        super.onDestroy();
    }
}
