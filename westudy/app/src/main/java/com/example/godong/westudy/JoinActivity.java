package com.example.godong.westudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.common.CommonUtil;
import com.network.HttpUtil;

public class JoinActivity extends Activity implements View.OnClickListener {
    private EditText emailEdit;
    private EditText pwEdit;
    private EditText pw2Edit;
    private EditText nameEdit;
    private RadioGroup genderGroup;
    private RadioButton genderBtn;
    private RadioButton defaultGenderBtn;
    private Button interest1Btn;
    private Button interest2Btn;
    private Button interest3Btn;
    private Button interest4Btn;
    private int [] interestArray = {0, 0, 0, 0};
    private EditText introduceEdit;
    private Button joinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
    }

    private void init() {
        //Resource 초기화
        emailEdit = (EditText) findViewById(R.id.edit_join_email);
        pwEdit = (EditText) findViewById(R.id.edit_join_pw);
        pw2Edit = (EditText) findViewById(R.id.edit_join_pw2);
        nameEdit = (EditText) findViewById(R.id.edit_join_name);
        genderGroup = (RadioGroup) findViewById(R.id.radio_join_gender);
        interest1Btn = (Button) findViewById(R.id.btn_join_interest1);
        interest2Btn = (Button) findViewById(R.id.btn_join_interest2);
        interest3Btn = (Button) findViewById(R.id.btn_join_interest3);
        interest4Btn = (Button) findViewById(R.id.btn_join_interest4);
        introduceEdit = (EditText) findViewById(R.id.edit_join_introduce);
        joinBtn = (Button) findViewById(R.id.btn_join_join);

        //Event 초기화
        interest1Btn.setOnClickListener(this);
        interest2Btn.setOnClickListener(this);
        interest3Btn.setOnClickListener(this);
        interest4Btn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
        defaultGenderBtn = (RadioButton) findViewById(R.id.radio_join_gender_male);
        defaultGenderBtn.setChecked(true);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_join_interest1) {
            interest1Btn.setSelected(!interest1Btn.isSelected());
            interestArray[0] = (interestArray[0] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.btn_join_interest2) {
            interest2Btn.setSelected(interest2Btn.isSelected());
            interestArray[1] = (interestArray[1] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.btn_join_interest3) {
            interest3Btn.setSelected(interest3Btn.isSelected());
            interestArray[2] = (interestArray[2] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.btn_join_interest4) {
            interest4Btn.setSelected(interest4Btn.isSelected());
            interestArray[3] = (interestArray[3] == 0) ? 1 : 0;
        }
        else if (v.getId() == R.id.btn_join_join) {
            sendJoinData();
        }
    }

    private void sendJoinData() {
        if(!checkParams()) {
            return;
        }
        int selectedId = genderGroup.getCheckedRadioButtonId();
        genderBtn = (RadioButton) findViewById(selectedId);
        RequestParams params = new RequestParams();
        params.put("email",emailEdit.getText());
        params.put("password",pwEdit.getText());
        params.put("name",nameEdit.getText());
        params.put("gender",genderBtn.getTag());
        params.put("interest",interestArrayToString());
        params.put("introduce",introduceEdit.getText());

        HttpUtil.post("http://192.168.1.13:3000/signup", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                System.out.println("START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"


                System.out.println("SUCCESS");
                Intent intentLoginActivity = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intentLoginActivity);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.println("ERRROR");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    private boolean checkParams() {
        String toastText = null;
        if(emailEdit.getText().toString().equals("")){
            toastText = "Email을 입력해주세요!";
        }
        else if(pwEdit.getText().toString().equals("") || pw2Edit.getText().toString().equals("")){
            toastText = "비밀번호를 입력해주세요!";
        }
        else if(!pwEdit.getText().toString().equals(pw2Edit.getText().toString())){
            toastText = "비밀번호를 잘못 입력하였습니다.";
        }
        else if(nameEdit.getText().toString().equals("")){
            toastText = "이름을 입력해주세요!";
        }

        if(toastText.equals(null)){
            return true;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }
    }

    private String interestArrayToString() {
        String interestString = interestArray[0] + "," + interestArray[1] + "," + interestArray[2] + "," + interestArray[3];
        return interestString;
    }
}
