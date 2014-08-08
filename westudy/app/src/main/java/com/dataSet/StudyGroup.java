package com.dataSet;

/**
 * Created by baggajin on 14. 8. 4..
 */
public class StudyGroup {
    private String creater;
    private String subject;
    private String title;
    private int number_type;
    private String detail;
    private String create_time;
    private String[] day_of_week;
    private String location;

    public StudyGroup() {
        this.creater = "";
        this.subject = "";
        this.title = "";
        this.number_type = 0;
        this.detail = "";
        this.create_time = "";
        this.day_of_week = null;
        this.location = null;
    }


    public StudyGroup(String subject, String title, int number_type, String detail,
                      String[] day_of_week, String location) {
        this.subject = subject;
        this.title = title;
        this.number_type = number_type;
        this.detail = detail;
        this.day_of_week = day_of_week;
        this.location = location;
    }


    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber_type() {
        return number_type;
    }

    public void setNumber_type(int number_type) {
        this.number_type = number_type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String[] getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String[] day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
