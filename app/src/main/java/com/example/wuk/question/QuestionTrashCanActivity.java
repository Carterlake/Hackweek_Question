package com.example.wuk.question;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class QuestionTrashCanActivity extends AppCompatActivity {

    View btnBack;

    RecyclerView recyclerView;

    DeletedQuestionAdapter adapter;

    List<Question> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_trash_can);

        recyclerView = findViewById(R.id.mRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        initListData();
        adapter = new DeletedQuestionAdapter(this, list);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }

    private void initListData() {
        list = LitePal.where("isDeleted = ?", "2").find(Question.class);
    }
}
