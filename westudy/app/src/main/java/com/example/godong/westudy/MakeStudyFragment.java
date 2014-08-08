package com.example.godong.westudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.dataSet.StudyGroup;

/**
 * Created by baggajin on 14. 8. 4..
 */
public class MakeStudyFragment extends Fragment implements OnClickListener, OnCheckedChangeListener{

    private String subject;
    private String title;
    private int memberCount;
    private String detail;
    private String[] day;
    private String location;

    private EditText titleInput;
    private EditText memberCountInput;
    private EditText detailInput;
    private RadioGroup subjectInput;
    private RadioGroup locationInput;

    private CheckBox mon, tue, wed, thu, fri, sat, sun;

    public static MakeStudyFragment newInstance(){

        MakeStudyFragment fragment = new MakeStudyFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        /** Inflate the layout for this fragment **/
        View view = inflater.inflate(R.layout.fragment_make_study, container, false);

        titleInput = (EditText) view.findViewById(R.id.study_name);
        memberCountInput = (EditText) view.findViewById(R.id.member_count);
        detailInput = (EditText) view.findViewById(R.id.detail);

        subjectInput = (RadioGroup) view.findViewById(R.id.subject);
        subjectInput.setOnCheckedChangeListener(this);

        locationInput = (RadioGroup) view.findViewById(R.id.location);
        locationInput.setOnCheckedChangeListener(this);

        /** 여기부터 Day Check Box init**/
        mon = (CheckBox) view.findViewById(R.id.Meet_mon);
        tue = (CheckBox) view.findViewById(R.id.Meet_tue);
        wed = (CheckBox) view.findViewById(R.id.Meet_wed);
        thu = (CheckBox) view.findViewById(R.id.Meet_thu);
        fri = (CheckBox) view.findViewById(R.id.Meet_fri);
        sat = (CheckBox) view.findViewById(R.id.Meet_sat);
        sun = (CheckBox) view.findViewById(R.id.Meet_sun);
        /** 여기까지 Day Check Box init**/

        Button MakeStudyButton = (Button) view.findViewById(R.id.makeStudybutton);
        MakeStudyButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.language:
                subject = "언어";
                break;
            case R.id.job:
                subject = "취업";
                break;
            case R.id.it:
                subject = "IT";
                break;
            case R.id.seoul:
                location = "서울";
                break;
            case R.id.kyungki:
                location = "경기";
                break;
            case R.id.etc:
                location = "기타";
                break;
        }
    }

    private void checkDay(View v) {

        day = new String[7];

        if(mon.isChecked()){
            day[0]="mon";
        }else {
            day[0] = "";
        }

        if(tue.isChecked()){
            day[1]="tue";
        }else {
            day[1]="";
        }

        if(wed.isChecked()){
            day[2]="wed";
        }else{
            day[2]="";
        }

        if(thu.isChecked()){
            day[3]="thu";
        }else{
            day[3]="";
        }

        if(fri.isChecked()){
            day[4]="fri";
        }else{
            day[4]="";
        }

        if(sat.isChecked()){
            day[5]="sat";
        }else{
            day[5]="";
        }

        if(sun.isChecked()){
            day[6]="sun";
        }else{
            day[6]="";
        }
    }

    @Override
    public void onClick(View v) {

        title = titleInput.getText().toString();
        memberCount = Integer.parseInt("" + memberCountInput.getText());
        detail = detailInput.getText().toString();

        StudyGroup newGroup = new StudyGroup(subject, title, memberCount, detail, day, location);
        save(newGroup);

        String date ="";
//        for(int i=1;i<=7;i++) {
//            if (day[i].equals("")) {
//
//            } else {
//                date = date + "'" + day[i] + "',";
//            }
//        }

        /** 지금은 임시로 Toast 띄우고 데이터 들어오는지만 확인 **/
        Toast.makeText(getActivity(), subject+"/"+title+"/"+memberCount+"/"+detail+"/"+location+"/"+date, Toast.LENGTH_LONG).show();


    }

    public void save(StudyGroup newGroup){
        /** Data DB Save **/
    }

}
