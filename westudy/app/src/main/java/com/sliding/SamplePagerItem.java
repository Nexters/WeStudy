package com.sliding;

import android.support.v4.app.Fragment;
import com.example.godong.westudy.StudyFragment.ArticleFragment;
import com.example.godong.westudy.StudyFragment.CalendarFragment;
import com.example.godong.westudy.StudyFragment.ScheduleFragment;
import com.example.godong.westudy.StudyFragment.MemberFragment;


public class SamplePagerItem {

    private final CharSequence mTitle;
    private final int mIndicatorColor;
    private final int mDividerColor;
    private final int position;

    private Fragment[] listFragments = null;

    public SamplePagerItem(int position, CharSequence title, int indicatorColor, int dividerColor) {
        this.mTitle = title;
        this.position = position;
        this.mIndicatorColor = indicatorColor;
        this.mDividerColor = dividerColor;

        this.listFragments = new Fragment[] {ArticleFragment.newInstance(), CalendarFragment.newInstance(),
                                        ScheduleFragment.newInstance(), MemberFragment.newInstance()};
    }

    public Fragment createFragment() {
        return listFragments[position];
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

}
