package com.dataSet;

/**
 * Created by baggajin on 14. 7. 26..
 */
public class Article {

    private String create_time;
    private String author;
    private String text;
    private String photo_url;
    private String study_id;

    public Article() {
        this.create_time = "";
        this.author = "";
        this.text = "";
        this.photo_url = "";
        this.study_id = "";
    }

    public Article(String create_time, String text, String photo_url, String author) {
        this.create_time = create_time;
        this.author = author;
        this.text = text;
        this.photo_url = photo_url;
    }

    public Article(String create_time, String text, String photo_url, String author, String study_id) {
        this.create_time = create_time;
        this.author = author;
        this.text = text;
        this.photo_url = photo_url;
        this.study_id = study_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContents() {

        String contents = "";

        if(photo_url.equals("") || photo_url.equals("\"\"")){
            contents = text;
        }else {
            contents = text + "\n" + "("+photo_url+")";
        }

        return contents;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPhoto(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getStudy_id() {
        return "from. " + study_id;
    }

    public void setStudy_id(String study_id) {
        this.study_id = study_id;
    }

    public String toString(){
        return "[ "+author+", "+text+"/"+photo_url+", "+study_id+", "+create_time+"\n";
    }
}