package com.example.wuk.question;

import org.litepal.crud.LitePalSupport;

public class Question extends LitePalSupport{

    private String question;

    private int hasAnswer;

    private int isDeleted = 1;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getHasAnswer() {
        return hasAnswer;
    }

    public void setHasAnswer(int hasAnswer) {
        this.hasAnswer = hasAnswer;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
