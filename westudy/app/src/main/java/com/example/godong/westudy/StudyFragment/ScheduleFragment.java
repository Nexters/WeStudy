package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.common.CustomScrollView;
import com.dataSet.Article;
import com.example.godong.westudy.R;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by baggajin on 14. 7. 13..
 */
public class ScheduleFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeLayout;

    /** Data List **/
    private ArrayList<Article> schedule_data;
 //   private FeedAdapter schedule_adapter;
    private JSONArray schedule_jarray;

    private ListView ScheduleList;
    private ScrollView ScheduleScroll;

    public ScheduleFragment(){

    }

    public static ScheduleFragment newInstance(){

        ScheduleFragment fragment = new ScheduleFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);

        schedule_data = new ArrayList<Article>();
//        schedule_adapter = new FeedAdapter(getActivity(), R.layout._feed_card, schedule_data);
//        setListAdapter(schedule_adapter);


        onRefresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_article, container, false);

        init(view);

        return view;
    }

    public void init(View v){

        /** 리소스 초기화 **/
        ScheduleList = (ListView) v.findViewById(android.R.id.list);
        ScheduleScroll = (CustomScrollView) v.findViewById(R.id.article_scrollView);

//        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.article_swipe_container);
//        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

//        study_id = getArguments().getString("study_id");


        /** Event 초기화 **/
////        swipeLayout.setOnRefreshListener(this);
//        ScheduleScroll.setOnEdgeTouchListener(new CustomScrollView.OnEdgeTouchListener(){
//            @Override
//            public void onEdgeTouch(CustomScrollView.DIRECTION direction) {
//                if(direction == CustomScrollView.DIRECTION.TOP){
//                    //TODO : Scroll Top 일 때 action
//                    if(scrollFlag == true) {
//                        onRefresh();
//                    }
//
//                }else if(direction == CustomScrollView.DIRECTION.BOTTOM){
//                    //TODO : Scroll Bottom 일 때 action
//
//
//                }else if(direction == CustomScrollView.DIRECTION.NONE){
//                    //TODO : Top도 Bottom 도 아닐 때 action
//
//                }else{
//                    throw new IllegalArgumentException("Invalid direction..");
//                }
//            }
//        });

//        WriteArticle = (LinearLayout)v.findViewById(R.id.article_floating_button);
//        WriteArticle.setOnClickListener(new LinearLayout.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                newArticleFragment = NewArticleFragment.newInstance();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fl_container, newArticleFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
    }

    @Override
    public void onRefresh() {

    }
}
