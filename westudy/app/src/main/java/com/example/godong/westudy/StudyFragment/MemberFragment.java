package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 8. 30..
 */
public class MemberFragment extends Fragment {


    public static MemberFragment newInstance(){

        MemberFragment fragment = new MemberFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_member, container, false);

        init(view);

        return view;
    }

    public void init(View v){

    }

}
