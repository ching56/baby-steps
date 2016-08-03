package com.example.lulu.babystep;


/**
 * Created by Ching on 2016/8/1.
 */
public class Data {
    private long id;
    private String event;
    private String date;
    private String time;
    private String mark;

    public Data(String event, String date, String time, String mark) {
        this.event = event;
        this.date = date;
        this.time = time;
        this.mark = mark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
