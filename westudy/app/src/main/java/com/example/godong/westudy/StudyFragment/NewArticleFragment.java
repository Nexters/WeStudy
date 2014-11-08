package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.common.CommonUtil;
import com.dataSet.Article;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by baggajin on 14. 8. 16..
 */
public class NewArticleFragment extends Fragment implements OnClickListener {

    private Article newArticle;

    /** 글쓰기 화면 리소스 선언 **/
    private EditText text;
    private ImageButton writeBtn;
    private TextView toStudyName;
    private JSONObject jsonObj;

    private TabFragment tabFragment;

    private String study_id;
    private String photo_url="";

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
        study_id = "53da400fe5bd41dd256c495e";
        toStudyName = (TextView) v.findViewById(R.id.article_new_toStudyName);
        writeBtn = (ImageButton) v.findViewById(R.id.articleNew_btn_send);
        text = (EditText) v.findViewById(R.id.article_new_contents);

        /** Event 초기화 **/
        writeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.articleNew_btn_send){
            //TODO: article 작성 내용 post 붙여야 함.
            sendArticleData();
        }

    }



    private void sendArticleData() {
        if(!checkParams()) {
            return;
        }

//        int selectedId = genderGroup.getCheckedRadioButtonId();
//        genderBtn = (RadioButton) findViewById(selectedId);

        JSONObject contents = new JSONObject();
        jsonObj = new JSONObject();

        try {
            contents.put("text",text.getText());
            contents.put("photo_url",photo_url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("test",jsonObj.toString());

        RequestParams params = new RequestParams();
        params.put("study_id",study_id);
        params.put("contents",contents);

        HttpUtil.post("http://godong9.com:3000/article", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"


                JSONObject jArticle = CommonUtil.stringToJSONObject(new String(response));
                try {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "작성되었습니다!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 100);
                    toast.show();
                    Bundle study_id = new Bundle();
                    study_id.putString("study_id", jArticle.getString("study_id"));

                    tabFragment = TabFragment.newInstance();
                    tabFragment.setArguments(study_id);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container, tabFragment)
                            .commit();

                }catch(Exception e){
                    Log.e("JSONException Occured:",e.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));

                try {
                    String errMsg = errObj.getString("message");
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), errMsg, Toast.LENGTH_LONG);
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
        if(text.getText().toString().equals("")){
            toastText = "내용을 입력해주세요!";
        }else if(study_id.equals("")){
            toastText = "Study Id가 비어있습니다.";
        }

        if(toastText.equals("")){
            return true;
        }
        else{
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
    }

}
