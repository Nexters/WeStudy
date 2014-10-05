package com.example.godong.westudy.StudyFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.dataSet.Study;
import com.example.godong.westudy.R;

public class StudyDetailDialog extends DialogFragment {

    private Context context;
    private Study study;

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

        context = getActivity();

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
