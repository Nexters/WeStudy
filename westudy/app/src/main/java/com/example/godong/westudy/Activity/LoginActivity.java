package com.example.godong.westudy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import com.common.ActivityReference;
import com.common.CommonUtil;
import com.dataSet.User;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    /** UI References **/
    private EditText emailEdit;
    private EditText pwEdit;
    private Button loginBtn;
    private LinearLayout kakaoBtn;
    private TextView findPwBtn;
    private TextView joinBtn;
    private SharedPreferences prefs;
    private ProgressDialog login_dialog;

    private String email_text;
    private String pw_text;

    /** 회원 정보 받아오기 **/
    private User userInfo;

    /** Activity Init **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        init();

        String email_text = prefs.getString("email", "");
        String pw_text = prefs.getString("pw", "");

        if(!email_text.equals("") && !pw_text.equals("")) {
            login(email_text, pw_text);
        }
    }

    private void init() {
        //앱 저장공간 초기화
        prefs = getSharedPreferences("login", MODE_PRIVATE);

        //리소스 초기화
        emailEdit = (EditText) findViewById(R.id.login_edit_email);
        pwEdit = (EditText) findViewById(R.id.login_edit_pw);
        loginBtn = (Button) findViewById(R.id.email_sign_in_button);
        kakaoBtn = (LinearLayout) findViewById(R.id.kakao_sign_up_button);
        findPwBtn = (TextView) findViewById(R.id.find_pw_button);
        joinBtn = (TextView) findViewById(R.id.sign_up_button);

        //test
        emailEdit.setText("aa");
        pwEdit.setText("aa");

        //Event 초기화
        loginBtn.setOnClickListener(this);
        kakaoBtn.setOnClickListener(this);
        findPwBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.email_sign_in_button) {
            email_text = emailEdit.getText().toString();
            pw_text = pwEdit.getText().toString();
            login(email_text, pw_text);
        }
        else if (v.getId() == R.id.kakao_sign_up_button) {
            //TODO: 카카오 로그인 붙여야 함

        }
        else if (v.getId() == R.id.find_pw_button) {
            //TODO: 비밀번호 찾기 붙여야 함

        }
        else if (v.getId() == R.id.sign_up_button) {
            Intent intentJoinActivity = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intentJoinActivity);
        }

    }

    private void login(String email, String password) {
        login_dialog = ProgressDialog.show(LoginActivity.this, "",
                "로그인 중입니다. 잠시 기다려주세요", true);

        RequestParams params = new RequestParams();
        params.put("email",email);
        params.put("password",password);

        HttpUtil.post("http://godong9.com:3000/login", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Toast toast = Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 100);
                toast.show();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email",email_text);
                editor.putString("pw",pw_text);
                editor.commit();

                JSONObject inputObj = CommonUtil.stringToJSONObject(new String(response));
                try {
                    JSONObject user = inputObj.getJSONObject("user");

                    Log.d("user info : ", String.valueOf(user));

                    userInfo = new User();
                    userInfo.set_id(user.getString("_id"));
                    userInfo.setProfile_url(user.getString("profile_url"));
                    userInfo.setName(user.getString("name"));
                    userInfo.setEmail(user.getString("email"));
                    userInfo.setGender(user.getString("gender"));
                    userInfo.setCreate_time(user.getString("create_time"));

                    /** interest 읽어오기 **/
                    JSONArray interest = user.getJSONArray("interest");
                    userInfo.initInterest(interest.length());

                    for (int j=0; j < interest.length(); j++) {
                        userInfo.setInterest(interest.getInt(j),j);
                    }

                    /** study 읽어오기 **/
                    JSONArray study = user.getJSONArray("study");
                    userInfo.initStudy(study.length());

                    for (int j=0; j < study.length(); j++) {
                        userInfo.setStudy(study.getString(j),j);
                    }

                } catch (Exception je) {
                    Log.e("JSON Error", je.toString());
                }

                Intent intentLoginActivity = new Intent(LoginActivity.this, StudyMainActivity.class);
                intentLoginActivity.putExtra("LoginData", userInfo);
                startActivity(intentLoginActivity);
                ActivityReference.sActivityReference = LoginActivity.this;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));

                login_dialog.dismiss();
                try {
                    String errMsg = errObj.getString("message");
                    Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (Exception je) {
                    Log.e("JSON Error", je.toString());
                }
            }

        });

    }

}


