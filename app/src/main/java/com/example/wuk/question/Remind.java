package com.example.wuk.question;


import org.litepal.crud.LitePalSupport;

public class Remind extends LitePalSupport {
    public  int date_state0 = 2;
    public  int date_state1 = 2;
    public  int date_state2 = 2;
    public  int date_state3 = 2;
    public  int date_state4 = 2;
    public  int date_state5 = 2;
    public  int date_state6 = 2;
    final public static String date[] = {"天","一","二","三","四","五","六"};
    private int hour;
    private int min;
    private int isSet;
    public  boolean isCreate = false;
    private int mmyId = 1;
    private int fuck = 0;

    public void setFuck(int fuck) {
        this.fuck = fuck;
    }

    public int getFuck() {
        return fuck;
    }

    public void setId(int id) {
        this.mmyId = id;
    }

    public int getId() {
        return this.mmyId;
    }

    public int getIsSet() {
        return isSet;
    }

    public void setIsSet(int isSet) {
        this.isSet = isSet;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }
}
