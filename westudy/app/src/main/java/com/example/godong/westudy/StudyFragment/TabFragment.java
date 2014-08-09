package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 7. 18..
 */
public class TabFragment extends Fragment {

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
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabHost);

        mTabHost.addTab(mTabHost.newTabSpec("timeline").setIndicator("홈"),
                TimelineFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("calendar").setIndicator("캘 린 더"),
                CalendarFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("plan").setIndicator("계 획 표"),
                PlanFragment.class, null);
        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }


}