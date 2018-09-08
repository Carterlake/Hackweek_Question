package com.example.wuk.question;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class DeletedQuestionAdapter extends RecyclerView.Adapter<DeletedQuestionAdapter.ViewHolder> {

    private List<Question> questionList;

    private Context context;

    public DeletedQuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;

        public ViewHolder(View view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tvQuestion);
        }
    }

    @NonNull
    @Override
    public DeletedQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deleted_question_item_layout, parent, false);
        final DeletedQuestionAdapter.ViewHolder holder = new DeletedQuestionAdapter.ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MoreAnswerActivity.class);
                MainActivity.clickedQuestion = holder.tvQuestion.getText().toString();
                context.startActivity(intent);
            }
        });

        holder.itemView.findViewById(R.id.btnUndo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();
                Question question = new Question();
                question.setToDefault("isDeleted");
                question.updateAll("question = ?", holder.tvQuestion.getText().toString());
                Answer answer = new Answer();
                answer.setToDefault("isDeleted");
                answer.updateAll("question = ?", holder.tvQuestion.getText().toString());
                questionList.remove(position);
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeletedQuestionAdapter.ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestion.setText(question.getQuestion());
        holder.itemView.setTag(position);
    }
}
