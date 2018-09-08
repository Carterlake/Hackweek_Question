package com.example.wuk.question;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MoreAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;

    List<Answer> answerList = new ArrayList<>();

    SpecificAnswerAdapter answerAdapter;

    TextView tvQuestion;

    View btnPre, btnNext, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_answer);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuestion.setText(MainActivity.clickedQuestion);

        btnNext = findViewById(R.id.btnNext);
        btnPre = findViewById(R.id.btnPre);
        btnBack = findViewById(R.id.btnBack);
        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        answerList = initListData(MainActivity.clickedQuestion);
        answerAdapter = new SpecificAnswerAdapter(this, answerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(answerAdapter);
    }

    private List<Answer> initListData(String question){
        return LitePal.where("question = ?", question).find(Answer.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                tvQuestion.setText(QuestionManager.getRandomQuestion().getQuestion());
                answerList = initListData(tvQuestion.getText().toString());
                answerAdapter = new SpecificAnswerAdapter(this,answerList);
                mRecyclerView.setAdapter(answerAdapter);
                break;
            case R.id.btnPre:
                tvQuestion.setText(QuestionManager.getRandomQuestion().getQuestion());
                answerList = initListData(tvQuestion.getText().toString());
                answerAdapter = new SpecificAnswerAdapter(this,answerList);
                mRecyclerView.setAdapter(answerAdapter);
                break;
            case R.id.btnBack:
                finish();
            default:
                break;
        }
    }
}
