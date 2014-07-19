package com.example.godong.westudy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

/**
 * Created by baggajin on 14. 7. 12..
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(){

        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        /** Inflate the layout for this fragment **/
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
