package com.dataSet;

import java.util.ArrayList;

/**
 * Created by baggajin on 14. 11. 20..
 */
public class Content {

    private int count;
    private StringBuilder text;
    private ArrayList<String> checkList;
    private ArrayList<Boolean> checked;

    public Content(){
        count = 0;
        text = new StringBuilder();
        checkList = new ArrayList<String>();
        checked = new ArrayList<Boolean>();

    }

    public Content(int count, StringBuilder text, ArrayList<String> checkList, ArrayList<Boolean> checked){
        this.count = count;
        this.text = text;
        this.checkList = checkList;
        this.checked = checked;
    }

    public Content(int count){
        this.count = count;
        this.text = new StringBuilder();
        this.checkList = new ArrayList<String>();
        this.checked = new ArrayList<Boolean>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String text) { this.text.append(text); }

    public void setText(StringBuilder text) {
        this.text = text;
    }

    public ArrayList<String> getCheckList() {
        return checkList;
    }

    public String getCheckList(int i){
        return checkList.get(i);
    }

    public void setCheckList(String checkList) { this.checkList.add(checkList); }

    public void setCheckList(ArrayList<String> checkList) {
        this.checkList = checkList;
    }

    public ArrayList<Boolean> getChecked() {
        return checked;
    }

    public Boolean getChecked(int i){
        return checked.get(i);
    }

    public void setChecked(ArrayList<Boolean> checked) {
        this.checked = checked;
    }

}
