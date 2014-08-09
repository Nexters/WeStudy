
/**
 * Created by baggajin on 14. 8. 9..
 */

package com.example.godong.westudy.StudySearchFragment;

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
public class StudySearchTabFragment extends Fragment {

    /** TabHost 선언 **/
    private FragmentTabHost studySearch_TabHost;


    /** MainActivity 에서 호출할 수 있게 Instance 생성 **/
    public static StudySearchTabFragment newInstance(){

        StudySearchTabFragment fragment = new StudySearchTabFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        studySearch_TabHost = new FragmentTabHost(getActivity());
        studySearch_TabHost.setup(getActivity(), getChildFragmentManager(), R.id.studySearch_tabHost);

        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("all").setIndicator("전체"),
                StudyAllListFragment.class, null);
        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("recommand").setIndicator("추천"),
                StudyRecommandListFragment.class, null);
        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("language").setIndicator("어학"),
                StudyLanguageListFragment.class, null);
        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("job").setIndicator("취업"),
                StudyJobListFragment.class, null);
        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("it").setIndicator("IT"),
                StudyITListFragment.class, null);
        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("test").setIndicator("자격증,시험"),
                StudyTestListFragment.class, null);
        studySearch_TabHost.addTab(studySearch_TabHost.newTabSpec("etc").setIndicator("기타"),
                StudyETCListFragment.class, null);


        return studySearch_TabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        studySearch_TabHost = null;
    }


}