package com.dataSet;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by baggajin on 14. 8. 9..
 */
public class User implements Parcelable{

    private String _id;
    private String email;
    private String name;
    private String profile_url;
    private String gender;
    private String create_time;
    private String[] interest = {"어학","취업","IT","자격증&시험"};
    private int[] interest_in;
    private String[] study;

    private int size;

    public User(Parcel in){
        readFromParcel(in);
    }

    public User(){
        _id = "";
        email = "";
        name = "";
        profile_url = "";
        gender = "";
        create_time = "";
        interest_in = new int[1];
        study = new String[1];
    }

    public User(String _id, String email, String name, String profile_url, String gender, String create_time,
                int[] interest, String[] study){

        this._id = _id;
        this.email = email;
        this.name = name;
        this.profile_url = profile_url;
        this. gender = gender;
        this.create_time = create_time;
        this.interest_in = interest;
        this.study = study;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getGender() {
        String result;

        if(this.gender.equals("0")){
            result = "남";
        }else
            result = "여";

        return result;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {

        String[] timeTemp;
        timeTemp = create_time.split("T");
        this.create_time = timeTemp[0];

    }

    public String getInterest() {

        String result="";

        for(int i=0;i<size;i++){
            if(result.equals("")){
                result = interest[interest_in[i]];
            }else{
                result += interest[interest_in[i]];
            }

        }

        return result;
    }

    public void initInterest(int i){
        this.interest_in = new int[i];
        this.size = i;
    }

    public void setInterest(int input, int i) {
        this.interest_in[i] = input;
    }

    public String getStudy() {
        String result = "";

        if(this.study==null){
            result = "가입한 스터디가 없습니다.";
        }else{
            result = "결과존재";
        }

        return result;
    }

    public void initStudy(int i){
        this.study = new String[i];
    }

    public void setStudy(String[] study) {
        this.study = study;
    }

    public void setStudy(String input, int i) {
        this.study[i] = input;
    }

    public String toString(){
        return _id+"/"+name+"/"+email+"/"+interest+"/"+create_time+"/"+study;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(profile_url);
        dest.writeString(gender);
        dest.writeString(create_time);
        dest.writeStringArray(interest);
        dest.writeStringArray(study);
    }

    public void readFromParcel(Parcel in){
        this._id = in.readString();
        this.email = in.readString();
        this.name = in.readString();
        this.profile_url = in.readString();
        this.gender = in.readString();
        this.create_time = in.readString();
//        in.readStringArray(this.interest);
//        in.readStringArray(this.study);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
