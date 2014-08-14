package com.example.godong.westudy.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.common.NavigationDrawerFragment;
import com.example.godong.westudy.R;
import com.example.godong.westudy.SideMenu.InfoFragment;
import com.example.godong.westudy.SideMenu.ProfileFragment;
import com.example.godong.westudy.SideMenu.StudyMakeFragment;
import com.example.godong.westudy.StudyFragment.TabFragment;
import com.example.godong.westudy.StudySearchFragment.StudySearchTabFragment;
import com.dataSet.User;

public class StudyMainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    /** Fragment managing the behaviors, interactions and presentation of the navigation drawer. **/
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /** Used to store the last screen title. For use in restoreActionBar(). **/
    private CharSequence mTitle;

    /** fragment들 선언 (DrawerBar에서 선택할 Fragment) **/
    private TabFragment tabFragment;
    private ProfileFragment profileFragment;
    private InfoFragment infoFragment;
    private StudyMakeFragment studyMakeFragment;
    private StudySearchTabFragment studySearchTabFragment;

    /** UserInfo Data **/
    Bundle userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_title);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        /** drawer Setup **/
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));

        /** userInfo LoginActivity에서 받아와서 Profile로 보내기 **/
        Bundle bundle = getIntent().getExtras();
        User userInfo = bundle.getParcelable("LoginData");

        userData = new Bundle();
        userData.putParcelable("userData",userInfo);

    }


    /**
     * Navigation Drawer item 선택 되었을 때 작업
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position){
        /** fragement로 main content update **/
        Toast toast;
        position = position+1;

        mTitle = getString(R.string.title_home);

        /** Fragment 전환 **/
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, PlaceholderFragment.newInstance(position))
                .commit();

    }


    /**
     * ActionBar restore
     * Fragment 전환 전 ActionBar Title 변경
     */
    public void restoreActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(!mNavigationDrawerFragment.isDrawerOpen()){
            /** Only show items in the action bar relevant to this screen
             if the drawer is not showing. Otherwise, let the drawer
             decide what to show in the action bar. **/
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Section Select 됐을 때 실제 Fragment 전환 작업
     * @param number
     */
    public void onSectionAttached(int number){
        switch(number){
            case 1:
                mTitle = getString(R.string.title_home);
                tabFragment = TabFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, tabFragment)
                        .commit();
                break;
            case 2:
                mTitle = getString(R.string.title_profile);
                profileFragment = profileFragment.newInstance();
                profileFragment.setArguments(userData);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, profileFragment)
                        .commit();
                break;
            case 3:
                mTitle = getString(R.string.title_study);
                tabFragment = TabFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, tabFragment)
                        .commit();
                break;

            case 4:
                mTitle = getString(R.string.title_study_search);
                studySearchTabFragment = StudySearchTabFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, studySearchTabFragment)
                        .commit();
                break;

            case 5:
                mTitle = getString(R.string.title_study_make);
                studyMakeFragment = StudyMakeFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, studyMakeFragment)
                        .commit();
                break;

            case 6:
                mTitle = getString(R.string.title_info);
                infoFragment = InfoFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, infoFragment)
                        .commit();
                break;

        }
    }


    /**
     *
     */
    public static class PlaceholderFragment extends Fragment {

        /** The fragment argument representing the section number for this fragment. **/
        private static final String ARG_SECTION_NUMBER = "section_number";


        /** Returns a new instance of this fragment for the given section number. **/
        public static PlaceholderFragment newInstance(int sectionNumber){
            PlaceholderFragment fragement = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragement.setArguments(args);
            return fragement;
        }

        public PlaceholderFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_study, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity){
            super.onAttach(activity);
            ((StudyMainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
