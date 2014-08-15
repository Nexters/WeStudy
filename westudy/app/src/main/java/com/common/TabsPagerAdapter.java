package com.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.godong.westudy.StudyFragment.CalendarFragment;
import com.example.godong.westudy.StudyFragment.ScheduleFragment;
import com.example.godong.westudy.StudyFragment.TimelineFragment;

/**
 * Created by baggajin on 14. 8. 14..
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {


    public TabsPagerAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new TimelineFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new ScheduleFragment();
        }

        return null;
    }


    @Override
    public int getCount() {

        // get item count - equal to number of tabs
        return 3;

    }

}