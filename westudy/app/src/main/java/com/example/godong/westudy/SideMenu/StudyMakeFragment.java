package com.example.godong.westudy.SideMenu;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.dataSet.StudyGroup;
import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 8. 4..
 */
public class StudyMakeFragment extends Fragment implements View.OnClickListener {
    private RadioGroup subjectRadioGroup;
    private RadioButton defaultSubjectRadioBtn;
    private RadioButton selectedSubjectRadioBtn;
    private EditText studyNameEdit;
    private RadioGroup personRadioGroup;
    private RadioButton defaultPersonRadioBtn;
    private RadioButton selectedPersonRadioBtn;
    private EditText locationEdit;
    private Button monBtn, tueBtn, wedBtn, thuBtn, friBtn, satBtn, sunBtn;
    private int [] weekOfDayArray = {0, 0, 0, 0, 0, 0, 0};
    private EditText detailEdit;
    private Button makeStudyBtn;

    public static StudyMakeFragment newInstance(){
        StudyMakeFragment fragment = new StudyMakeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        /** Inflate the layout for this fragment **/
        View view = inflater.inflate(R.layout.fragment_study_make, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        //Resource 초기화
        subjectRadioGroup = (RadioGroup) view.findViewById(R.id.studyMake_radio_subject);
        studyNameEdit = (EditText) view.findViewById(R.id.studyMake_edit_name);
        personRadioGroup = (RadioGroup) view.findViewById(R.id.studyMake_radio_person);
        defaultPersonRadioBtn = (RadioButton) view.findViewById(R.id.studyMake_radio_person3);
        defaultPersonRadioBtn.setChecked(true);
        locationEdit = (EditText) view.findViewById(R.id.studyMake_edit_location);
        monBtn = (Button) view.findViewById(R.id.studyMake_btn_mon);
        tueBtn = (Button) view.findViewById(R.id.studyMake_btn_tue);
        wedBtn = (Button) view.findViewById(R.id.studyMake_btn_wed);
        thuBtn = (Button) view.findViewById(R.id.studyMake_btn_thu);
        friBtn = (Button) view.findViewById(R.id.studyMake_btn_fri);
        satBtn = (Button) view.findViewById(R.id.studyMake_btn_sat);
        sunBtn = (Button) view.findViewById(R.id.studyMake_btn_sun);
        detailEdit = (EditText) view.findViewById(R.id.studyMake_edit_detail);
        makeStudyBtn = (Button) view.findViewById(R.id.studyMake_button_makeStudy);

        //Event 초기화
        monBtn.setOnClickListener(this);
        tueBtn.setOnClickListener(this);
        wedBtn.setOnClickListener(this);
        thuBtn.setOnClickListener(this);
        friBtn.setOnClickListener(this);
        satBtn.setOnClickListener(this);
        sunBtn.setOnClickListener(this);
        makeStudyBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.studyMake_btn_mon) {
            monBtn.setSelected(!monBtn.isSelected());
            weekOfDayArray[0] = (weekOfDayArray[0] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_tue) {
            tueBtn.setSelected(!tueBtn.isSelected());
            weekOfDayArray[1] = (weekOfDayArray[1] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_wed) {
            wedBtn.setSelected(!wedBtn.isSelected());
            weekOfDayArray[2] = (weekOfDayArray[2] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_thu) {
            thuBtn.setSelected(!thuBtn.isSelected());
            weekOfDayArray[3] = (weekOfDayArray[3] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_fri) {
            friBtn.setSelected(!friBtn.isSelected());
            weekOfDayArray[4] = (weekOfDayArray[4] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_sat) {
            satBtn.setSelected(!satBtn.isSelected());
            weekOfDayArray[5] = (weekOfDayArray[5] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_sun) {
            sunBtn.setSelected(!sunBtn.isSelected());
            weekOfDayArray[6] = (weekOfDayArray[6] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_button_makeStudy) {
            sendMakeStudy();
        }
    }

    private void sendMakeStudy() {
        if(!checkParams()) {
            return;
        }

    }

    private boolean checkParams() {
        String toastText = "";
        if(subjectRadioGroup.getCheckedRadioButtonId() == -1) {
            toastText = "스터디 주제를 선택해주세요!";
        }
        else if(studyNameEdit.getText().toString().equals("")){
            toastText = "스터디 이름을 입력해주세요!";
        }

        if(toastText.equals("")){
            return true;
        }
        else{
            Toast toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

    }
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch(checkedId){
//            case R.id.studyMake_radio_language:
//                subject = "언어";
//                break;
//            case R.id.studyMake_radio_job:
//                subject = "취업";
//                break;
//            case R.id.studyMake_radio_it:
//                subject = "IT";
//                break;
//            case R.id.studyMake_radio_seoul:
//                location = "서울";
//                break;
//            case R.id.studyMake_radio_kyungki:
//                location = "경기";
//                break;
//            case R.id.studyMake_radio_etc:
//                location = "기타";
//                break;
//        }
//    }

//    private void checkDay(View v) {
//
//        day = new String[7];
//
//        if(mon.isChecked()){
//            day[0]="mon";
//        }else {
//            day[0] = "";
//        }
//
//        if(tue.isChecked()){
//            day[1]="tue";
//        }else {
//            day[1]="";
//        }
//
//        if(wed.isChecked()){
//            day[2]="wed";
//        }else{
//            day[2]="";
//        }
//
//        if(thu.isChecked()){
//            day[3]="thu";
//        }else{
//            day[3]="";
//        }
//
//        if(fri.isChecked()){
//            day[4]="fri";
//        }else{
//            day[4]="";
//        }
//
//        if(sat.isChecked()){
//            day[5]="sat";
//        }else{
//            day[5]="";
//        }
//
//        if(sun.isChecked()){
//            day[6]="sun";
//        }else{
//            day[6]="";
//        }
//    }

//    @Override
//    public void onClick(View v) {
//
//        title = titleInput.getText().toString();
//        memberCount = Integer.parseInt("" + memberCountInput.getText());
//        detail = detailInput.getText().toString();
//
//        StudyGroup newGroup = new StudyGroup(subject, title, memberCount, detail, day, location);
//        save(newGroup);
//
//        String date ="";
////        for(int i=1;i<=7;i++) {
////            if (day[i].equals("")) {
////
////            } else {
////                date = date + "'" + day[i] + "',";
////            }
////        }
//
//        /** 지금은 임시로 Toast 띄우고 데이터 들어오는지만 확인 **/
////        Toast.makeText(getActivity(), subject+"/"+title+"/"+memberCount+"/"+detail+"/"+location+"/"+date, Toast.LENGTH_LONG).show();
//
//
//    }

    public void save(StudyGroup newGroup){
        /** Data DB Save **/
    }

}
