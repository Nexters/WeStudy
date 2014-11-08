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
import android.widget.Toast;

import com.example.godong.westudy.R;

public class StudyMakeDialog extends DialogFragment {

    private Context context;
    private ImageButton dialog_exit_btn;
    private Button dialog_home_btn;
    private String study_id;


    public StudyMakeDialog(String study_id) {
        this.study_id = study_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStadte) {
        //Theme_Holo_Light_Panel 이용해서 테두리 없게 만들어줌
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Panel);
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();

        View view;

        view = mLayoutInflater.inflate(R.layout.study_make_dialog, null);
        mBuilder.setView(view);

        context = getActivity();

        dialog_exit_btn = (ImageButton)view.findViewById(R.id.studyMake_btn_exit);
        dialog_home_btn = (Button)view.findViewById(R.id.studyMake_btn_home);

        //공통 다이얼로그 종료 버튼 이벤트 리스너
        dialog_exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        dialog_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //TODO: 스터디 홈 페이지로 이동 구현
                dismiss();

            }
        });

        return mBuilder.create();
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
