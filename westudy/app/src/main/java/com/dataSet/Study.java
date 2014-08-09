package com.dataSet;

/**
 * Created by baggajin on 14. 8. 8..
 */
public class Study {

    private String creator;
    private String subject;
    private String title;
    private int number_type;
    private int memberCount;
    private String detail;
    private String create_time;
    private String[] members;
    private String[] location;
    private String[] week;

    public Study(){
        this.creator = "";
        this.subject = "";
        this.title = "";
        this.number_type = 0;
        this.memberCount = 0;
        this.detail = "";
        this.create_time = "";
    }

    public Study(String creator, String subject, String title, int number_type, String detail, String create_time,
                 String[] members, String[] location, String[] week){

        this.creator = creator;
        this.subject = subject;
        this.title = title;
        this.number_type = number_type;
        this.detail = detail;
        this.create_time = create_time;
        this.members = members;
        this.location = location;
        this.week = week;

    }

    public Study(String creator, String subject, String title, int number_type, String detail,
                 String[] members, String[] location, String[] week){

        this.creator = creator;
        this.subject = subject;
        this.title = title;
        this.number_type = number_type;
        this.detail = detail;
        this.members = members;
        this.location = location;
        this.week = week;

    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getMembers() {
        String member="";

        for(int i=0;i<members.length;i++){
            member += members[i];
        }

        return member;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getLocation() {
        String location ="";

        for(int i=0;i<this.location.length;i++){
            location += this.location[i];
        }
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }

    public String getWeek() {
        String day = "";

        for(int i=0;i<week.length;i++){
            if(day.equals("")){
                day = week[i];
            }else{
                day = day+", "+week[i];
            }
        }

        return day;
    }

    public void setWeek(String[] week) {
        this.week = week;
    }

    public int getMemberCount(){
        int count=0;

        for(int i=0;i<members.length;i++){
            count++;
        }

        return count;
    }
}