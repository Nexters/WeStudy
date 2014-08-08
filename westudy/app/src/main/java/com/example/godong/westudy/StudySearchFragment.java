package com.example.godong.westudy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by baggajin on 14. 8. 8..
 */
public class StudySearchFragment extends Fragment{

    public StudySearchFragment(){

    }

    public static StudySearchFragment newInstance(){

        StudySearchFragment fragment = new StudySearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        /** Inflate the layout for this fragment **/
        return inflater.inflate(R.layout.fragment_study_search, container, false);
    }



}
