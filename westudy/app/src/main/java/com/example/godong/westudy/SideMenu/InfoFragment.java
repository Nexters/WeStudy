package com.example.godong.westudy.SideMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 7. 26..
 */
public class InfoFragment extends Fragment {

    public InfoFragment(){

    }

    public static InfoFragment newInstance(){

        InfoFragment fragment = new InfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        /** Inflate the layout for this fragment **/
        return inflater.inflate(R.layout.fragment_monday_arivo_info, container, false);
    }

}
