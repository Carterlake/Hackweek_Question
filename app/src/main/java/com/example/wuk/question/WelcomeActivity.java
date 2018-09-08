package com.example.wuk.question;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    Animation appearAnimation, disappearAnimation;

    TextView tvQuestion;

    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = View.inflate(WelcomeActivity.this, R.layout.activity_welcome, null);
        setContentView(view);

        LitePal.getDatabase();

        questionList = LitePal.where("isDeleted = ?", "1").find(Question.class);

        if (questionList.size() == 0) {
            firstInitListData();
        } else {
            QuestionManager.setQuestionList(questionList);
        }


        tvQuestion = view.findViewById(R.id.tvQuestion);

        tvQuestion.setText(QuestionManager.getRandomQuestion().getQuestion());

        appearAnimation = new AlphaAnimation(0.1f, 1.0f);
        appearAnimation.setDuration(2000);
        view.startAnimation(appearAnimation);
        appearAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                disappearAnimation = new AlphaAnimation(1.0f, 0f);
                disappearAnimation.setDuration(1000);
                disappearAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        tvQuestion.setVisibility(View.INVISIBLE);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(disappearAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void firstInitListData() {
        Question question = new Question();
        question.setQuestion("今天过得开心吗？");
        question.save();
        question = new Question();
        question.setQuestion("多久没跟爸妈联系了？");
        question.save();
        question = new Question();
        question.setQuestion("上一次静下心看书是什么时候？");
        question.save();
        question = new Question();
        question.setQuestion("最近有在运动吗？");
        question.save();
        question = new Question();
        question.setQuestion("上次在背后议论别人是什么时候？");
        question.save();
        question = new Question();
        question.setQuestion("如果时间暂停，第一件想做的事情是什么？");
        question.save();
        question = new Question();
        question.setQuestion("下一顿吃什么？");
        question.save();
        question = new Question();
        question.setQuestion("感觉自己压力大吗？");
        question.save();
        question = new Question();
        question.setQuestion("你渴望爱情吗？");
        question.save();
        question = new Question();
        question.setQuestion("最近有没有失眠？");
        question.save();
        question = new Question();
        question.setQuestion("如果给你一个回到过去的机会，想去到什么时候？");
        question.save();
        question = new Question();
        question.setQuestion("上次痛快地哭是什么时候？");
        question.save();
        question = new Question();
        question.setQuestion("放空脑袋随意地想，第一个出现的人是谁？");
        question.save();
        question = new Question();
        question.setQuestion("在坚持学习英语吗？");
        question.save();
        question = new Question();
        question.setQuestion("昨晚的梦还记得吗？");
        question.save();
        question = new Question();
        question.setQuestion("下一顿打算吃什么？");
        question.save();
        question = new Question();
        question.setQuestion("有没有给自己定长期目标？");
        question.save();
        question = new Question();
        question.setQuestion("喜欢自己现在的状态吗？");
        question.save();
        question = new Question();
        question.setQuestion("平常用的最多的三个app是什么？");
        question.save();
        question = new Question();
        question.setQuestion("和室友关系怎么样？");
        question.save();
        question = new Question();
        question.setQuestion("一天大约会喝几杯水？");
        question.save();
        question = new Question();
        question.setQuestion("上次出远门是去哪了？");
        question.save();
        questionList = LitePal.findAll(Question.class);
        QuestionManager.setQuestionList(questionList);
    }
}
