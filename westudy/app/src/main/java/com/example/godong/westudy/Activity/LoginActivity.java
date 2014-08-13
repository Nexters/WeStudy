package com.example.godong.westudy.Activity;

import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class LoginActivity extends FragmentActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    /** UI References **/
    private EditText emailEdit;
    private EditText pwEdit;
    private View mProgressView;
    private View mLoginFormView;

    /** 회원 정보 받아오기 **/
    private User userInfo;

    /** Activity Init **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        emailEdit = (EditText) findViewById(R.id.login_edit_email);
        pwEdit = (EditText) findViewById(R.id.login_edit_pw);

        /** Local ID로 Login 하는 버튼 **/
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Set up the login form. **/
                /** Email 작성란 **/
                /** Password 작성란 **/

                RequestParams params = new RequestParams();
                params.put("email",emailEdit.getText());
                params.put("password",pwEdit.getText());

                HttpUtil.post("http://godong9.com:3000/login", null, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        Toast toast = Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 100);
                        toast.show();

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
                        intentLoginActivity.putExtra("LoginData",userInfo);
                        startActivity(intentLoginActivity);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));

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
        });


        TextView signUpButton = (TextView) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(
            new OnClickListener() {
                public void onClick(View v) {
                    Intent intentJoinActivity = new Intent(LoginActivity.this, JoinActivity.class);
                    startActivity(intentJoinActivity);
                }
            });
    }

}


