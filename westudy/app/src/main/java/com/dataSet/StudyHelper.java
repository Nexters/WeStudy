package com.dataSet;

/**
 * Created by KimYongSeong on 14. 11. 8..
 */
public class StudyHelper {
    private static Study _study;

    public static void setStudy(Study selectedStudy) {
        _study = new Study();
        _study.setId(selectedStudy.getId());
        _study.setCreator(selectedStudy.getCreator());
        _study.setSubject(selectedStudy.getSubject());
        _study.setTitle(selectedStudy.getTitle());
        _study.setRecruit_number(selectedStudy.getRecruit_number());
        _study.setDetail(selectedStudy.getDetail());
        _study.setCreate_time(selectedStudy.getCreate_time());
        _study.setMembers(selectedStudy.getMembers());
        _study.setAppliers(selectedStudy.getAppliers());

    }

    public static Study getStudy() {
        return _study;
    }
}
