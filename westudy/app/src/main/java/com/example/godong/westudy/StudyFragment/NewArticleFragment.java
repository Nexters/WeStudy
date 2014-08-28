package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dataSet.Article;
import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 8. 16..
 */
public class NewArticleFragment extends Fragment implements OnClickListener {

    private Article newArticle;

    /** 글쓰기 화면 리소스 선언 **/
    private EditText contents;
    private Button writeBtn;
    private EditText toStudyName;

    /** MainActivity 에서 호출할 수 있게 Instance 생성 **/
    public static NewArticleFragment newInstance(){

        NewArticleFragment fragment = new NewArticleFragment();

        return fragment;
    }

    public NewArticleFragment(){
        this.newArticle = new Article();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_article_new, container, false);

        /** 초기화 **/
        init(view);

        return view;

    }

    private void init(View v) {

        /** 리소스 초기화 **/
        toStudyName = (EditText) v.findViewById(R.id.article_new_toStudyName);
        writeBtn = (Button) v.findViewById(R.id.article_new_writeButton);
        contents = (EditText) v.findViewById(R.id.article_new_contents);

        /** Event 초기화 **/
        writeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.article_new_writeButton){
            //TODO: article 작성 내용 post 붙여야 함.

        }

    }
//
//
//
//    private void sendArticleData() {
//        if(!checkParams()) {
//            return;
//        }
//
////        int selectedId = genderGroup.getCheckedRadioButtonId();
////        genderBtn = (RadioButton) findViewById(selectedId);
//        RequestParams params = new RequestParams();
//        params.put("email",toStudyName.getText());
//        params.put("text",contents.getText());
//        params.put("name",nameEdit.getText());
//        params.put("gender",genderBtn.getTag());
//        params.put("interest",interestArrayToJSONArray());
//        params.put("introduce",introduceEdit.getText());
//
//        HttpUtil.post("http://godong9.com:3000/signup", null, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                Toast toast = Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 100);
//                toast.show();
//
//                Intent intentLoginActivity = new Intent(JoinActivity.this, LoginActivity.class);
//                startActivity(intentLoginActivity);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));
//
//                try {
//                    String errMsg = errObj.getString("message");
//                    Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } catch (Exception je) {
//                    Log.e("JSON Error", je.toString());
//                }
//            }
//        });
//    }
//
//    private boolean checkParams() {
//        String toastText = "";
//        if(emailEdit.getText().toString().equals("")){
//            toastText = "Email을 입력해주세요!";
//        }
//        else if(pwEdit.getText().toString().equals("")){
//            toastText = "비밀번호를 입력해주세요!";
//        }
//        else if(nameEdit.getText().toString().equals("")){
//            toastText = "이름을 입력해주세요!";
//        }
//
//        if(toastText.equals("")){
//            return true;
//        }
//        else{
//            Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//            return false;
//        }
//    }

}
