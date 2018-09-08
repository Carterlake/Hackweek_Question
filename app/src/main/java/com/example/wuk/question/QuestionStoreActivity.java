package com.example.wuk.question;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class QuestionStoreActivity extends AppCompatActivity {

    private QuestionAdapter adapter;

    public static PopupWindow deletePopupWindow;

    List<Question> list = new ArrayList<>();

    View btnBack, btnAddQuestion, btnTrashCan;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_store);
        recyclerView = findViewById(R.id.mRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }

            @Override
            public void onScroll(View view, int position) {
                view.scrollTo(400, 0);
            }

            @Override
            public void onCancel(View view, int position) {
                view.scrollTo(0, 0);
            }
        }));

        btnBack = findViewById(R.id.btnBack);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnTrashCan = findViewById(R.id.btnTrashCan);

        btnTrashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionStoreActivity.this, QuestionTrashCanActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionStoreActivity.this, "开始添加", Toast.LENGTH_SHORT).show();
                final LinearLayout dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.add_question_dialog, null);

                final AlertDialog.Builder builder = new AlertDialog.Builder(QuestionStoreActivity.this);
                final AlertDialog dialog = builder.create();

                dialog.setIcon(R.drawable.addquestion);
                dialog.setTitle("添加问题");
                dialog.setView(dialogView);
                dialog.setCancelable(true);

                TextView commit = (TextView) dialogView.findViewById(R.id.commit);
                commit.setOnClickListener(new android.view.View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        EditText text = (EditText) dialogView.findViewById(R.id.evQuestion);
                        String content = text.getText().toString();
                        for (int i = 0; i < QuestionManager.getListSize(); i++) {
                            if (QuestionManager.getQuestionAtPosition(i).getQuestion().equals(content)) {
                                Toast.makeText(QuestionStoreActivity.this, "问题已存在!", Toast.LENGTH_SHORT).show();
                                content = "";
                            }
                        } if (!content.equals("")) {
                            Question question = new Question();
                            question.setQuestion(content);
                            question.save();
                            list.add(question);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });

                TextView cancel = (TextView) dialogView.findViewById(R.id.cancel);
                cancel.setOnClickListener(new android.view.View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = initListData();
        adapter = new QuestionAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private List<Question> initListData() {
        return LitePal.where("isDeleted = ?", "1").find(Question.class);
    }
}
