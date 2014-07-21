package com.example.godong.westudy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

/**
 * Created by baggajin on 14. 7. 13..
 */
public class CalendarFragment extends Fragment{

    public CalendarFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView cal = (CalendarView) view.findViewById(R.id.calendarView);
        cal.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(),"Selected Date is\n\n"
                                +dayOfMonth+" : "+month+" : "+year ,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
