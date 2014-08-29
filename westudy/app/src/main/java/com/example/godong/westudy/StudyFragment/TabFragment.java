package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.TabHost;

import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 7. 18..
 */


public class TabFragment extends Fragment implements TabHost.OnTabChangeListener {

    /** TabHost 선언 **/
    private FragmentTabHost mTabHost;


    /** MainActivity 에서 호출할 수 있게 Instance 생성 **/
    public static TabFragment newInstance(){

        TabFragment fragment = new TabFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabhost);


        mTabHost.setOnTabChangedListener(this);

        mTabHost.addTab(mTabHost.newTabSpec("feed").setIndicator("홈"),
                ArticleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("calendar").setIndicator("캘 린 더"),
                CalendarFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("plan").setIndicator("스 케 줄"),
                ScheduleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("member").setIndicator("멤 버"),
                MemberFragment.class, null);


        //TODO: TabHost color change

        for(int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        mTabHost.getTabWidget().setCurrentTab(0);
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#5bb7e7"));

        return mTabHost;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }


    @Override
    public void onTabChanged(String tabId) {
        // Tab 색 변경
        for(int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#5bb7e7"));
    }

//    /** TabHost 선언 **/
//    private FragmentTabHost mTabHost;
//    private TabsPagerAdapter mAdapter;
//    private ViewPager mViewPager;
//
//    private HorizontalScrollView scroll;
//
//    /** MainActivity 에서 호출할 수 있게 Instance 생성 **/
//    public static TabFragment newInstance(){
//
//        TabFragment fragment = new TabFragment();
//
//        return fragment;
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_study, container, false);
////        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
////
////        /** Tab Initialization **/
////        initialiseTabHost();
////
////
////        mAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager());
////
////        mViewPager.setAdapter(mAdapter);
////        mViewPager.setOnPageChangeListener(this);
////
////        mTabHost.addView(view);
//
//        mTabHost = new FragmentTabHost(getActivity());
//        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabhost);
//
//        mTabHost.addTab(mTabHost.newTabSpec("timeline").setIndicator("홈"),
//                TimelineFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("calendar").setIndicator("캘 린 더"),
//                CalendarFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("plan").setIndicator("계 획 표"),
//                ScheduleFragment.class, null);
//
//        mTabHost.setOnTabChangedListener(this);
//
//
////        scroll = (HorizontalScrollView) view.findViewById(R.id.study_tabs_scrollView);
////        mTabHost.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                scroll.requestDisallowInterceptTouchEvent(true);
////                return false;
////            }
////        });
//
//
//        return mTabHost;
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//
//        mTabHost = null;
//        mAdapter = null;
//        mViewPager = null;
//
//    }
//
//
//    // Tabs Creation
//
//    private void initialiseTabHost() {
//
//        mTabHost = new FragmentTabHost(getActivity());
//        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabhost);
//
//        mTabHost.addTab(mTabHost.newTabSpec("timeline").setIndicator("홈"),
//                TimelineFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("calendar").setIndicator("캘 린 더"),
//                CalendarFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("plan").setIndicator("계 획 표"),
//                ScheduleFragment.class, null);
//
//        mTabHost.setOnTabChangedListener(this);
//
//
////        mTabHost = new FragmentTabHost(getActivity());
////        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabhost);
////
////        // TODO Put here your Tabs
////        AddTab(getActivity(), this.mTabHost, this.mTabHost.newTabSpec("Timeline").setIndicator("홈"));
////        AddTab(getActivity(), this.mTabHost, this.mTabHost.newTabSpec("Calendar").setIndicator("캘 린 더"));
////        AddTab(getActivity(), this.mTabHost, this.mTabHost.newTabSpec("TextTab").setIndicator("스 케 줄"));
//
//        mTabHost.setOnTabChangedListener(this);
//
//    }
//
//    /** Method to add a TabHost **/
//    private void AddTab(FragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
//        tabSpec.setContent(new MyTabFactory(activity));
//        tabHost.addTab(tabSpec);
//    }
//
//    /** Manages the Tab changes, synchronizing it with Pages **/
//    public void onTabChanged(String tag) {
//
//        int pos = this.mTabHost.getCurrentTab();
//        this.mTabHost.setCurrentTab(pos);
//
//    }
//
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        int pos = this.mTabHost.getCurrentTab();
//        this.mTabHost.setCurrentTab(pos);
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//
//    class MyTabFactory implements TabContentFactory {
//
//        private final Context mContext;
//
//        /**
//         * @param context
//         */
//        public MyTabFactory(Context context) {
//            mContext = context;
//        }
//
//        /** (non-Javadoc)
//         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
//         */
//        public View createTabContent(String tag) {
//            View v = new View(mContext);
//            v.setMinimumWidth(0);
//            v.setMinimumHeight(0);
//            return v;
//        }
//
//    }
}

