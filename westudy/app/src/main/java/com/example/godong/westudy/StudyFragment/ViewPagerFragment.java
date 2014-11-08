package com.example.godong.westudy.StudyFragment;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.common.ViewPagerAdapter;
import com.sliding.SamplePagerItem;
import com.sliding.SlidingTabLayout;

import com.example.godong.westudy.R;

public class ViewPagerFragment extends Fragment{

    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();
    private String study_id="";


    /** MainActivity 에서 호출할 수 있게 Instance 생성 **/
    public static ViewPagerFragment newInstance(){

        ViewPagerFragment fragment = new ViewPagerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        study_id = getArguments().getString("study_id");

        Bundle Bundle_studyId = new Bundle();
        Bundle_studyId.putString("study_id", study_id);

        mTabs.add(new SamplePagerItem(0, getString(R.string.tab_home), Color.rgb(14,167,247), Color.WHITE));
        mTabs.add(new SamplePagerItem(1, getString(R.string.tab_calendar), Color.rgb(14,167,247), Color.WHITE));
        mTabs.add(new SamplePagerItem(2, getString(R.string.tab_schedule), Color.rgb(14,167,247), Color.WHITE));
        mTabs.add(new SamplePagerItem(3, getString(R.string.tab_member), Color.rgb(14, 167, 247), Color.WHITE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.mPager);

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mTabs));

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.mTabs);
        mSlidingTabLayout.setBackgroundResource(R.color.color_white);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }
        });
    }
}