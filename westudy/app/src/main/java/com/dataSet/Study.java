package com.dataSet;

/**
 * Created by baggajin on 14. 8. 8..
 */
public class Study {

    private String creator;
    private String subject;
    private String title;
    private int recruit_number;
    private int memberCount;
    private String detail;
    private String create_time;
    private String _id;
    private String[] members;
    private String[] appliers;
    private String[] location;
    private int[] week;
    private String[] day = {"월","화","수","목","금","토","일"};


    public Study(){
        this.creator = "";
        this.subject = "";
        this.title = "";
        this.recruit_number = 0;
        this.memberCount = 0;
        this.detail = "";
        this.create_time = "";
        this._id = "";
    }

    public Study(String _id, String creator, String subject, String title, int recruit_number, String detail, String create_time,
                 String[] members, String[] location, int[] week){

        this._id = _id;
        this.creator = creator;
        this.subject = subject;
        this.title = title;
        this.recruit_number = recruit_number;
        this.detail = detail;
        this.create_time = create_time;
        this.members = members;
        this.location = location;
        this.week = week;

    }

    public Study(String creator, String subject, String title, int recruit_number, String detail,
                 String[] members, String[] location, int[] week){

        this.creator = creator;
        this.subject = subject;
        this.title = title;
        this.recruit_number = recruit_number;
        this.detail = detail;
        this.members = members;
        this.location = location;
        this.week = week;

    }

    public String getId() { return this._id; }

    public void setId(String id) { this._id = id; }

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

    public int getRecruit_number() {
        return recruit_number;
    }

    public void setRecruit_number(int recruit_number) {
        this.recruit_number = recruit_number;
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

    public String[] getMembers() {
        return this.members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String[] getAppliers() {
        return this.appliers;
    }

    public void setAppliers(String[] appliers) {
        this.appliers = appliers;
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
        String result = "";

        for(int i=0;i<week.length;i++){
            if(result.equals("")){
//                result = day[week[i]];
                result = day[week[i]];
//                result = Integer.toString(week[i]);
            }else{
                result = result + " " + day[week[i]];
//                result = result + " " + Integer.toString(week[i]);

//                result = result+" "+day[week[i]];
            }
        }

        return result;
    }

    public void setWeek(int[] week) {
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
