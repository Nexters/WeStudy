package com.example.godong.westudy.StudyFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.common.CommonUtil;
import com.dataSet.Study;
import com.dataSet.User;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class StudyDetailDialog extends DialogFragment {

    private Context context;
    private Study study;
    private User user;
    private TextView title;
    private TextView recruit_number;
    private TextView detail;
    private ImageButton dialog_exit_btn;
    private Button study_apply_btn;

    public StudyDetailDialog(Study study) {
        this.study = study;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStadte) {
        //Theme_Holo_Light_Panel 이용해서 테두리 없게 만들어줌
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Panel);
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();

        View view;

        view = mLayoutInflater.inflate(R.layout.study_detail_dialog, null);
        mBuilder.setView(view);

        title = (TextView)view.findViewById(R.id.studyDetail_text_title);
        title.setText(this.study.getTitle());

        recruit_number = (TextView)view.findViewById(R.id.studyDetail_text_recruit_number);
        recruit_number.setText(this.study.getMemberCount()+" / "+this.study.getRecruit_number()+"명");

        detail = (TextView)view.findViewById(R.id.studyDetail_text_detail);
        detail.setText(this.study.getDetail());

        context = getActivity();

        dialog_exit_btn = (ImageButton)view.findViewById(R.id.studyDetail_btn_exit);

        study_apply_btn = (Button)view.findViewById(R.id.studyApply_btn_makeStudy);

        //공통 다이얼로그 종료 버튼 이벤트 리스너
        dialog_exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        study_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendApplyData();
                dismiss();
            }
        });

        return mBuilder.create();
    }

    private void sendApplyData() {
        RequestParams params = new RequestParams();
        params.put("study_id", this.study.getId());

        HttpUtil.post("http://godong9.com:3000/study/apply", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Toast toast = Toast.makeText(context, "스터디 지원 완료!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 100);
                toast.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                JSONObject errObj = CommonUtil.stringToJSONObject(new String(errorResponse));

                try {
                    String errMsg = errObj.getString("message");
                    Toast toast = Toast.makeText(context, errMsg, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (Exception je) {
                    Log.e("JSON Error", je.toString());
                }
            }
        });
    }

    @Override
    public void onStart() {
        //다이얼로그 외부 클릭시 다이얼로그 사라지도록 구현
        if (getDialog() != null) {
            //다이얼로그 생성시 배경 어두워지게 설정
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
            lp.dimAmount=0.8f;

            getDialog().setCanceledOnTouchOutside(true);
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getDialog().getWindow().setAttributes(lp);
        }

        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
