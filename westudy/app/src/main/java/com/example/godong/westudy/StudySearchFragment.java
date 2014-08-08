package com.example.godong.westudy;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.common.CommonUtil;
import com.dataSet.Study;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baggajin on 14. 8. 8..
 */
public class StudySearchFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeLayout;

    /** Data List **/
    private ArrayList<Study> studySearch_data;
    private StudyListAdapter studySearch_adapter;
    private JSONArray studySearch_jarray;

    private ListView StudySearchList;
    private ScrollView StudySearchScroll;

    public StudySearchFragment(){

    }

    public static StudySearchFragment newInstance(){

        StudySearchFragment fragment = new StudySearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);

        studySearch_data = new ArrayList<Study>();
        studySearch_adapter = new StudyListAdapter(getActivity(), R.layout._study_card, studySearch_data);
        setListAdapter(studySearch_adapter);

        onRefresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_study_search, container, false);

        StudySearchList = (ListView) view.findViewById(android.R.id.list);
        StudySearchScroll = (ScrollView) view.findViewById(R.id.studySearch_scrollView);
        StudySearchList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StudySearchScroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.studySearch_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onRefresh() {

        HttpUtil.get("http://godong9.com:3000/study/all", null, null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.i("HttpUtil.get.Start", "START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"

                studySearch_jarray = CommonUtil.stringToJSONArray(new String(response));
                setFeedData();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("HttpUtil.get.ERROR", "ERROR");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Log.i("Handler","END");
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }


    private void setFeedData(){

        String creator = "";
        String subject = "";
        String title = "";
        String detail = "";
        String create_time="";
        String[] week;
        String[] location;
        String[] members;
        int number_type = 0;

// Article[] articles = new Article[jarray.length()];

        studySearch_data.clear();
        studySearch_adapter.notifyDataSetInvalidated();

        try{
            for(int i=0;i<studySearch_jarray.length();i++){

                JSONObject studyList = studySearch_jarray.getJSONObject(i);

                creator = studyList.getString("creator");
                subject = studyList.getString("subject");
                title = studyList.getString("title");
                number_type = studyList.getInt("number_type");
                detail = studyList.getString("detail");
//                create_time = studyList.getString("create_time");

//                int size=0;

                /** member 읽어오기 **/
                JSONArray member = studyList.getJSONArray("members");
                members = new String[member.length()];

                for (int j=0; j < member.length(); j++) {
                    members[j] = member.getString(j);
                }

//                JSONArray member = studyList.getJSONArray("members");
//                members = new String[member.length()];
//
//                for(int j=0;j<members.length;j++){
//                    members[j] = member.getJSONObject(j).toString();
//                }

                /** location 읽어오기 **/
                JSONArray locate = studyList.getJSONArray("location");
                location = new String[locate.length()];

                for(int j=0;j<locate.length();j++){
                    location[j] = locate.getString(j);
                }

                /** Week 읽어오기 **/
                JSONArray day = studyList.getJSONArray("day_of_week");
                week = new String[day.length()];

                for(int j=0;j<day.length();j++){
                    week[j] = day.getString(j);
                }



                Log.d("output:",creator+"/"+subject+"/"+title+"/"+number_type+"/"+detail+"\n"
                               +members+"/"+location+"/"+week+"\n");
                Study study = new Study(creator,subject,title,number_type,detail,members,location,week);

                studySearch_data.add(study);
                Log.d("Arraylist output", studySearch_data.get(i).toString());

            }

            studySearch_adapter.notifyDataSetChanged();

        }catch(JSONException je){
            Log.e("JSONException:",je.toString());
        }

    }



    private class StudyListAdapter extends ArrayAdapter<Study> {
        private ArrayList<Study> items;

        public StudyListAdapter(Context context, int textViewResourceId, ArrayList<Study> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if(v == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._study_card, null);
            }

            Study study = items.get(position);
            if(study !=null) {
                TextView create_time = (TextView) v.findViewById(R.id.studyCard_createTime);
                TextView location = (TextView) v.findViewById(R.id.studyCard_location);
                TextView creator = (TextView) v.findViewById(R.id.studyCard_creator);
                TextView title = (TextView) v.findViewById(R.id.studyCard_title);
                TextView detail = (TextView) v.findViewById(R.id.studyCard_detail);
                TextView memberCount = (TextView) v.findViewById(R.id.studyCard_memberCount);
                TextView maxMember = (TextView) v.findViewById(R.id.studyCard_maxMember);
                TextView week = (TextView) v.findViewById(R.id.studyCard_week);

                if (create_time != null) {
                    create_time.setText(study.getCreate_time());
                }
                if (location != null){
                    location.setText(study.getLocation());
                }
                if (creator != null){
                    creator.setText(study.getCreator());
                }
                if (title != null){
                    title.setText(study.getTitle());
                }
                if (detail != null) {
                    detail.setText(study.getDetail());
                }
//                if (memberCount != null){
//
//                }
                if (maxMember != null){
                    maxMember.setText(study.getMembers().toString()+"명");
                }
                if (memberCount != null){
                    memberCount.setText(Integer.toString(study.getMemberCount()));
                }
                if (week != null){
                    week.setText(study.getWeek());
                }

            }

            return v;
        }
    }


}
