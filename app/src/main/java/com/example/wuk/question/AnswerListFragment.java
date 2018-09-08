package com.example.wuk.question;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerListFragment extends Fragment implements View.OnClickListener {

    View view;

    RecyclerView mRecyclerView;

    AnswerAdapter answerAdapter;

    TextView tvDate;

    List<Answer> answerList = new ArrayList<>();

    int currentYear, currentMonth;

    View btnPre, btnNext, btnSetting;

    public AnswerListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_answer_list, container, false);

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        tvDate = view.findViewById(R.id.tvDate);
        tvDate.setText(currentMonth+"月    "+currentYear);

        btnPre = view.findViewById(R.id.btnPre);
        btnNext = view.findViewById(R.id.btnNext);
        btnSetting = view.findViewById(R.id.btnSetting);
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        answerList = initListData(currentYear, currentMonth);

        answerAdapter = new AnswerAdapter(getContext(), answerList);
        mRecyclerView = view.findViewById(R.id.answerRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(answerAdapter);

        return view;
    }

    private List<Answer> initListData(int currentYear, int currentMonth) {
        return LitePal.where("year = ? and month = ? and isDeleted = ?",
                ""+currentYear,""+currentMonth, "1").find(Answer.class);
    }

    @Override
    public void onResume() {
        answerList = initListData(currentYear, currentMonth);
        tvDate.setText(currentMonth + "月  " + currentYear);
        answerAdapter = new AnswerAdapter(getContext(),answerList);
        mRecyclerView.setAdapter(answerAdapter);
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                currentMonth++;
                if (currentMonth == 13) {
                    currentMonth = 1;
                    currentYear++;
                }
                answerList = initListData(currentYear, currentMonth);
                tvDate.setText(currentMonth + "月  " + currentYear);
                answerAdapter = new AnswerAdapter(getContext(),answerList);
                mRecyclerView.setAdapter(answerAdapter);
                break;
            case R.id.btnPre:
                currentMonth--;
                if (currentMonth == 0) {
                    currentMonth = 12;
                    currentYear--;
                }
                answerList = initListData(currentYear, currentMonth);
                tvDate.setText(currentMonth + "月  " + currentYear);
                answerAdapter = new AnswerAdapter(getContext(),answerList);
                mRecyclerView.setAdapter(answerAdapter);
                break;
            case R.id.btnSetting:
                break;
            default:
                break;
        }
    }
}
