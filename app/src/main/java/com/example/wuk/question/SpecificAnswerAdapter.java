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

public class SpecificAnswerAdapter extends RecyclerView.Adapter<SpecificAnswerAdapter.ViewHolder> {
    private List<Answer> answerList;

    private Context context;

    public SpecificAnswerAdapter(Context context, List<Answer> answerList) {
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

        TextView tvAnswer, tvDate;

        public ViewHolder(View view) {
            super(view);
            tvAnswer = view.findViewById(R.id.tvAnswer);
            tvDate = view.findViewById(R.id.tvDate);
        }
    }

    @NonNull
    @Override
    public SpecificAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_specific_item_layout, parent, false);
        final SpecificAnswerAdapter.ViewHolder holder = new SpecificAnswerAdapter.ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificAnswerAdapter.ViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.tvDate.setText((answer.getYear()) + "/"+answer.getMonth() + "/" + answer.getDay());
        holder.tvAnswer.setText(answer.getContent());
        holder.itemView.setTag(position);
    }
}
