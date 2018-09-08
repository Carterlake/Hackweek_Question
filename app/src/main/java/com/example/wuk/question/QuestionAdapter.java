package com.example.wuk.question;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    private List<Question> questionList;

    private Context context;

    public QuestionAdapter(Context context, List<Question> questionList) {
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
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        final QuestionAdapter.ViewHolder holder = new QuestionAdapter.ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MoreAnswerActivity.class);
                MainActivity.clickedQuestion = holder.tvQuestion.getText().toString();
                context.startActivity(intent);
            }
        });

        holder.itemView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();

                //显示popupwindow
               /* View contentView = LayoutInflater.from(context).inflate(R.layout.delete_popupwindow,null);
                QuestionStoreActivity.deletePopupWindow = new PopupWindow(contentView,
                        WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
                QuestionStoreActivity.deletePopupWindow.setContentView(contentView);

                TextView tvConfirm = contentView.findViewById(R.id.confirm);
                TextView tvCancel = contentView.findViewById(R.id.cancel);

                tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Question question = questionList.get(position);
                        Question updateQuestion = new Question();
                        updateQuestion.setIsDeleted(2);
                        updateQuestion.updateAll("question = ?", question.getQuestion());
                        Answer updateAnswer = new Answer();
                        updateAnswer.setIsDeleted(2);
                        updateAnswer.updateAll("question = ?", question.getQuestion());
                        questionList.remove(position);
                        notifyDataSetChanged();
                        if (questionList.get(position) != null) {
                            holder.itemView.scrollTo(0, 0);
                            RecyclerItemClickListener.isDeleteShown = false;
                        }
                        QuestionStoreActivity.deletePopupWindow.dismiss();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QuestionStoreActivity.deletePopupWindow.dismiss();
                        if (questionList.get(position) != null) {
                            holder.itemView.scrollTo(0, 0);
                            RecyclerItemClickListener.isDeleteShown = false;
                        }
                    }
                });
                View rootView = LayoutInflater.from(context).inflate(R.layout.activity_question_store,null);
                QuestionStoreActivity.deletePopupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
*/
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.delete)
                        .setTitle("一定要删嘛？")
                        .setPositiveButton("对",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int j) {
                                        Question question = questionList.get(position);
                                        Question updateQuestion = new Question();
                                        updateQuestion.setIsDeleted(2);
                                        updateQuestion.updateAll("question = ?", question.getQuestion());
                                        Answer updateAnswer = new Answer();
                                        updateAnswer.setIsDeleted(2);
                                        updateAnswer.updateAll("question = ?", question.getQuestion());
                                        questionList.remove(position);
                                        notifyDataSetChanged();
                                        if (questionList.get(position) != null) {
                                            holder.itemView.scrollTo(0, 0);
                                            RecyclerItemClickListener.isDeleteShown = false;
                                        }
                                    }
                                }).setNegativeButton("不对", null).create()
                        .show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestion.setText(question.getQuestion());
        holder.itemView.setTag(position);
    }
}
