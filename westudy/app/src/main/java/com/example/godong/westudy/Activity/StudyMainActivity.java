package com.example.godong.westudy.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.BackPressCloseHandler;
import com.common.NavigationDrawerFragment;
import com.dataSet.User;
import com.example.godong.westudy.R;
import com.example.godong.westudy.SideMenu.InfoFragment;
import com.example.godong.westudy.SideMenu.ProfileFragment;
import com.example.godong.westudy.SideMenu.StudyMakeFragment;
import com.example.godong.westudy.StudyFragment.NewArticleFragment;
import com.example.godong.westudy.StudyFragment.TabFragment;
import com.example.godong.westudy.StudySearchFragment.StudySearchTabFragment;

import java.util.ArrayList;

public class StudyMainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AdapterView.OnItemClickListener {

    /** Fragment managing the behaviors, interactions and presentation of the navigation drawer. **/
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /** BackButton 종료를 위한 핸들러 **/
    private BackPressCloseHandler backPressCloseHandler;

    /** Used to store the last screen title. For use in restoreActionBar(). **/
    private CharSequence mTitle;

    /** fragment들 선언 (DrawerBar에서 선택할 Fragment) **/
    private TabFragment tabFragment;
    private ProfileFragment profileFragment;
    private InfoFragment infoFragment;
    private StudyMakeFragment studyMakeFragment;
    private StudySearchTabFragment studySearchTabFragment;
    private NewArticleFragment newArticleFragment;

    /** Navigation Drawer Side Slide용 **/
    private TextView userName;
    private TextView introduce;
    private ListView myStudy;
    private ArrayList<String> studyList;
    private StudyAdapter listAdapter;

    /** UserInfo Data **/
    Bundle study_id;
    Bundle userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout._custom_title);


        backPressCloseHandler = new BackPressCloseHandler(this);


        
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);

//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);

//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0ea7f7"));
//        actionBar.setBackgroundDrawable(colorDrawable);


        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        /** drawer Setup **/
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));

        /** userInfo LoginActivity에서 받아와서 Profile로 보내기 **/
        Bundle bundle = getIntent().getExtras();
        User userInfo = bundle.getParcelable("LoginData");

        userData = new Bundle();
        userData.putParcelable("userData",userInfo);

        /** 사이드 슬라이드 setting **/
        setupSideSlide(userInfo);

    }

    public void setupSideSlide(User userInfo){

        userName = (TextView) findViewById(R.id.nav_user_name);
        introduce = (TextView) findViewById(R.id.nav_user_introduce);

        userName.setText(userInfo.getName());
        introduce.setText(userInfo.getIntroduce());

        findViewById(R.id.nav_btn_find_study).setOnClickListener(mClickListener);
        findViewById(R.id.nav_btn_make_study).setOnClickListener(mClickListener);
        findViewById(R.id.nav_btn_setting).setOnClickListener(mClickListener);

        setUpStudyList();

    }

    public void setUpStudyList(){

        studyList = new ArrayList<String>();

        //TODO: studyList json 에서 받아와서 list만들기..
//        HttpUtil.get("http://godong9.com:3000/user/getStudyList", null, null, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                article_jarray = CommonUtil.stringToJSONArray(new String(response));
//                setFeedData();
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.e("HttpUtil.get.ERROR", "ERROR");
//            }
//
//        });
        studyList.add("토익 공부 합시당");
        studyList.add("테스트 테스트");
        studyList.add("으아");
        studyList.add("테스트~스터디~");




        listAdapter = new StudyAdapter(this, R.layout._my_study_card, studyList);
        myStudy = (ListView) findViewById(R.id.nav_study_listview);
        myStudy.setAdapter(listAdapter);
        myStudy.setOnItemClickListener(this);


    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_btn_find_study:
                    studySearchTabFragment = StudySearchTabFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container, studySearchTabFragment)
                            .addToBackStack(null)
                            .commit();
                    mNavigationDrawerFragment.closeDrawer();
                    break;
                case R.id.nav_btn_make_study:
                    studyMakeFragment = StudyMakeFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container, studyMakeFragment)
                            .addToBackStack(null)
                            .commit();
                    mNavigationDrawerFragment.closeDrawer();
                    break;
                case R.id.nav_btn_setting:
                    profileFragment = profileFragment.newInstance();
                    profileFragment.setArguments(userData);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container, profileFragment)
                            .addToBackStack(null)
                            .commit();
                    mNavigationDrawerFragment.closeDrawer();
                    break;

            }
        }
    };

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        }
        backPressCloseHandler.onBackPressed();
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

//        mTitle = getString(R.string.title_home);

        /** Fragment 전환 **/
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, PlaceholderFragment.newInstance(position))
                .addToBackStack(null)
                .commit();

    }


//    /**
//     * ActionBar restore
//     * Fragment 전환 전 ActionBar Title 변경
//     */
//    public void restoreActionBar(){
//        ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
////        actionBar.setTitle(mTitle);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        if(!mNavigationDrawerFragment.isDrawerOpen()){
//            /** Only show items in the action bar relevant to this screen
//             if the drawer is not showing. Otherwise, let the drawer
//             decide what to show in the action bar. **/
//            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
//            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

    /**
     * Section Select 됐을 때 실제 Fragment 전환 작업
     * @param number
     */
    public void onSectionAttached(int number){

//
//        switch(number){
//            case 1:
////                mTitle = getString(R.string.title_home);
//                tabFragment = TabFragment.newInstance();
//                tabFragment.setArguments(study_id);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, tabFragment)
//                        .commit();
//                break;
//            case 2:
////                mTitle = getString(R.string.title_profile);
//                profileFragment = profileFragment.newInstance();
//                profileFragment.setArguments(userData);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, profileFragment)
//                        .commit();
//                break;
//            case 3:
////                mTitle = getString(R.string.title_study);
//                tabFragment = TabFragment.newInstance();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, tabFragment)
//                        .commit();
//                break;
//
//            case 4:
////                mTitle = getString(R.string.title_study_search);
//                studySearchTabFragment = StudySearchTabFragment.newInstance();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, studySearchTabFragment)
//                        .commit();
//                break;
//
//            case 5:
////                mTitle = getString(R.string.title_study_search);
//                newArticleFragment = NewArticleFragment.newInstance();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, newArticleFragment)
//                        .commit();
//                break;
//
//            case 6:
////                mTitle = getString(R.string.title_study_make);
//                studyMakeFragment = StudyMakeFragment.newInstance();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, studyMakeFragment)
//                        .commit();
//                break;
//
//            case 7:
////                mTitle = getString(R.string.title_info);
//                infoFragment = InfoFragment.newInstance();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, infoFragment)
//                        .commit();
//                break;
//
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO: side slide list menu 선택시 action

        tabFragment = TabFragment.newInstance();
        tabFragment.setArguments(study_id);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, tabFragment)
                .commit();

    }


    /**
     * Study List Adapter
     * **/
    private class StudyAdapter extends ArrayAdapter<String>{
        private ArrayList<String> items;

        public StudyAdapter(Context context, int textViewResourceId, ArrayList<String> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if(v == null){
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._my_study_card, null);
            }

            String studyName = items.get(position);
            if(studyName !=null) {
                TextView name = (TextView) v.findViewById(R.id.myStudy_textView_name);

                if (name != null) {
                    name.setText(studyName);
                }
            }

            return v;
        }
    }




    /**
     * fragment change 담당
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
