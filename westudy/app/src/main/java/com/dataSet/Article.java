package com.dataSet;

/**
 * Created by baggajin on 14. 7. 26..
 */
public class Article {

    private String create_time;
    private String author;
    private String contents;
    private String study_id;

    public Article() {
        this.create_time = "";
        this.author = "";
        this.contents = "";
        this.study_id = "";
    }

    public Article(String create_time, String contents, String author) {
        this.create_time = create_time;
        this.author = author;
        this.contents = contents;
    }

    public Article(String create_time, String contents, String author, String study_id) {
        this.create_time = create_time;
        this.author = author;
        this.contents = contents;
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
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStudy_id() {
        return "from. " + study_id;
    }

    public void setStudy_id(String study_id) {
        this.study_id = study_id;
    }
}