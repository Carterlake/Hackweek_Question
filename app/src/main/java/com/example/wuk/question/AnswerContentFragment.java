package com.example.wuk.question;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerContentFragment extends Fragment {

    View view;

    String question;

    TextView tvQuestion, tvAnswer, tvDate, tvTime;

    View btnBack;
    ImageButton btnMoreAnswer;

    public AnswerContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_answer_content, container, false);
        tvQuestion = view.findViewById(R.id.tvQuestion);
        tvAnswer = view.findViewById(R.id.tvAnswer);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        btnMoreAnswer = view.findViewById(R.id.btnMoreAnswer);
        btnBack = view.findViewById(R.id.btnBack);
        tvQuestion.setText(MainActivity.clickedQuestion);
        tvAnswer.setText(MainActivity.clickedAnswer);
        tvDate.setText(MainActivity.clickedDate + "/ ");
        tvTime.setText(MainActivity.clickedTime);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("changeToAnswerListFragment");
                getActivity().sendBroadcast(intent);
            }
        });

        btnMoreAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoreAnswerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
