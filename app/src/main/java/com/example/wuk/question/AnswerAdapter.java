package com.example.wuk.question;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder>{

    private List<Answer> answerList;

    private Context context;

    public AnswerAdapter(Context context, List<Answer> answerList) {
        this.context = context;
        this.answerList = answerList;
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion, tvAnswer, tvDate, tvTime;

        public ViewHolder(View view) {
            super(view);
            tvAnswer = view.findViewById(R.id.tvAnswer);
            tvDate = view.findViewById(R.id.tvDate);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvTime = view.findViewById(R.id.tvTime);
        }
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout, parent, false);
        final AnswerAdapter.ViewHolder holder = new AnswerAdapter.ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("changeToAnswerContentFragment");
                MainActivity.clickedQuestion = holder.tvQuestion.getText().toString().substring(3);
                MainActivity.clickedAnswer = holder.tvAnswer.getText().toString().substring(3);
                MainActivity.clickedDate = holder.tvDate.getText().toString();
                MainActivity.clickedTime = holder.tvTime.getText().toString();
                context.sendBroadcast(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.tvTime.setText(answer.getHour() + ":" + answer.getMinute());
        holder.tvDate.setText(answer.getMonth() + "月" + answer.getDay() + "日");
        holder.tvQuestion.setText("-- " + answer.getQuestion());
        holder.tvAnswer.setText("-- " + answer.getContent());
        holder.itemView.setTag(position);
    }
}
