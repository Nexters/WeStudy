package com.example.godong.westudy.StudyFragment;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import com.example.godong.westudy.R;

import java.util.GregorianCalendar;


public class CalendarFragment extends Fragment {
    private CalendarView cal;


    public static CalendarFragment newInstance(){

        CalendarFragment fragment = new CalendarFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        cal = (CalendarView) view.findViewById(R.id.calendarView);
        cal.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                Toast.makeText(getActivity(),"Selected Date is\n\n"
                                +dayOfMonth+" : "+month+" : "+year ,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

//    private void setCalendarEvent() {
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType("vnd.android.cursor.item/event");
//        intent.putExtra(CalendarContract.Events.TITLE, "Learn Android");
//        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home suit home");
//        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Download Examples");
//
//// Setting dates
//        GregorianCalendar calDate = new GregorianCalendar(2014, 8, 20);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
//                calDate.getTimeInMillis());
//        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
//                calDate.getTimeInMillis());
//
//// make it a full day event
//        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//
//// make it a recurring Event
//        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");
//
//// Making it private and shown as busy
//        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
//        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//    }
}
