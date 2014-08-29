package com.example.godong.westudy.SideMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.common.CommonUtil;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.dataSet.StudyGroup;

/**
 * Created by baggajin on 14. 8. 4..
 */
public class StudyMakeFragment extends Fragment implements View.OnClickListener {
    private View activityView;
    private RadioGroup subjectRadioGroup;
    private RadioButton selectedSubjectRadioBtn;
    private EditText studyNameEdit;
    private RadioGroup personRadioGroup;
    private RadioButton defaultPersonRadioBtn;
    private RadioButton selectedPersonRadioBtn;
    private EditText locationEdit;
    private Button monBtn, tueBtn, wedBtn, thuBtn, friBtn, satBtn, sunBtn;
    private int [] dayOfWeekArray = {0, 0, 0, 0, 0, 0, 0};
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
        activityView = view;

        return view;
    }

    private void init(View view) {
        //Resource 초기화
        subjectRadioGroup = (RadioGroup) view.findViewById(R.id.studyMake_radio_subject);
        studyNameEdit = (EditText) view.findViewById(R.id.studyMake_edit_title);
        personRadioGroup = (RadioGroup) view.findViewById(R.id.studyMake_radio_person);
        defaultPersonRadioBtn = (RadioButton) view.findViewById(R.id.studyMake_radio_person4);
        defaultPersonRadioBtn.setChecked(true);
//        locationEdit = (EditText) view.findViewById(R.id.studyMake_edit_location);
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
            dayOfWeekArray[0] = (dayOfWeekArray[0] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_tue) {
            tueBtn.setSelected(!tueBtn.isSelected());
            dayOfWeekArray[1] = (dayOfWeekArray[1] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_wed) {
            wedBtn.setSelected(!wedBtn.isSelected());
            dayOfWeekArray[2] = (dayOfWeekArray[2] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_thu) {
            thuBtn.setSelected(!thuBtn.isSelected());
            dayOfWeekArray[3] = (dayOfWeekArray[3] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_fri) {
            friBtn.setSelected(!friBtn.isSelected());
            dayOfWeekArray[4] = (dayOfWeekArray[4] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_sat) {
            satBtn.setSelected(!satBtn.isSelected());
            dayOfWeekArray[5] = (dayOfWeekArray[5] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_btn_sun) {
            sunBtn.setSelected(!sunBtn.isSelected());
            dayOfWeekArray[6] = (dayOfWeekArray[6] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.studyMake_button_makeStudy) {
            sendMakeStudy();
        }
    }

    private void sendMakeStudy() {
        if(!checkParams()) {
            return;
        }
        int selectedSubject = subjectRadioGroup.getCheckedRadioButtonId();
        selectedSubjectRadioBtn = (RadioButton) activityView.findViewById(selectedSubject);
        int selectedPerson = personRadioGroup.getCheckedRadioButtonId();
        selectedPersonRadioBtn = (RadioButton) activityView.findViewById(selectedPerson);

        RequestParams params = new RequestParams();
        params.put("subject",selectedSubjectRadioBtn.getTag());
        params.put("title",studyNameEdit.getText());
        params.put("person",selectedPersonRadioBtn.getTag());
//        params.put("location",locationEdit.getText());
        params.put("day_of_week",dayOfWeekArrayToJSONArray());
        params.put("detail",detailEdit.getText());

        HttpUtil.post("http://godong9.com:3000/study", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Toast toast = Toast.makeText(getActivity(), "스터디를 만들었습니다!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 100);
                toast.show();

//                Intent intentLoginActivity = new Intent(JoinActivity.this, LoginActivity.class);
//                startActivity(intentLoginActivity);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));

                try {
                    String errMsg = errObj.getString("message");
                    Toast toast = Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (Exception je) {
                    Log.e("JSON Error", je.toString());
                }
            }
        });
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

    private JSONArray dayOfWeekArrayToJSONArray() {
        ArrayList dayOfWeekArrayList = new ArrayList();
        for(int i=0; i<dayOfWeekArray.length; i++){
            if(dayOfWeekArray[i] == 1){
                dayOfWeekArrayList.add(i);
            }
        }
        JSONArray dayOfWeekJSONArray = new JSONArray(dayOfWeekArrayList);
        return dayOfWeekJSONArray;
    }
}
