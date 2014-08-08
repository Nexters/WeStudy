package com.example.godong.westudy.StudySearchFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 8. 9..
 */
public class StudyTestListFragment extends Fragment{

    public StudyTestListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        /** Inflate the layout for this fragment **/
        return inflater.inflate(R.layout.fragment_study_list_test, container, false);
    }

}
