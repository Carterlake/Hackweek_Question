package com.example.wuk.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionManager {

    private static int currentPosition;

    private static List<Question> questionList = new ArrayList<>();

    public static void setQuestionList(List<Question> list) {
        questionList = list;
    }

    public static Question getRandomQuestion() {
        currentPosition = new Random().nextInt(questionList.size());
        return questionList.get(currentPosition);
    }

    public static Question getQuestionAtPosition(int position) {
        return questionList.get(position);
    }

    public static void remove(int i) {
        questionList.remove(i);
    }

    public static int getCurrentPosition() {
        return currentPosition;
    }

    public static int getListSize() {
        return questionList.size();
    }
}
