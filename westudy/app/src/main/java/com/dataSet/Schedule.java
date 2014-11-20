package com.dataSet;

import java.util.ArrayList;
/**
 * Created by baggajin on 14. 10. 9..
 */
public class Schedule {
    private String create_time;
    private String title;
    private ArrayList<String> contents;
    private ArrayList<String> checkList;
    private String order;
    private String start_time;
    private String end_time;
    private String study_id;

    public Schedule(){
        this.study_id="";
        this.order="";
        this.start_time="";
        this.end_time="";
        this.title="";
        this.create_time="";
        this.contents=null;
        this.checkList = null;
    }


    public Schedule(String study_id, String order, String start_time,
                    String end_time, String title, String create_time, ArrayList<String> contents){
        this.study_id = study_id;
        this.order = order;
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
        this.create_time = create_time;
        this.contents = contents;

    }

    public Schedule(String study_id, String order, String start_time,
                    String end_time, String title, String create_time, ArrayList<String> contents, ArrayList<String> checkList){
        this.study_id = study_id;
        this.order = order;
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
        this.create_time = create_time;
        this.contents = contents;
        this.checkList = checkList;

    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStudy_id() {
        return study_id;
    }

    public void setStudy_id(String study_id) {
        this.study_id = study_id;
    }

    public String getContents() {
        StringBuilder output = new StringBuilder();

        for(String str : contents){
            output.append(str+"\n");
        }

        return output.toString();
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public ArrayList<String> getCheckList() {
        return checkList;
    }

    public void setCheckList(ArrayList<String> checkList) {
        this.checkList = checkList;
    }
}



