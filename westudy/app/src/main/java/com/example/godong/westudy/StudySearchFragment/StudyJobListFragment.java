package com.example.godong.westudy.StudySearchFragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.CommonUtil;
import com.dataSet.Study;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baggajin on 14. 8. 9..
 */
public class StudyJobListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeLayout;

    /** Data List **/
    private ArrayList<Study> studyJob_data;
    private StudyListAdapter studyJob_adapter;
    private JSONArray studyJob_jarray;

    private ListView StudyJobList;
    private ScrollView StudyJobScroll;

    public StudyJobListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);

        studyJob_data = new ArrayList<Study>();
        studyJob_adapter = new StudyListAdapter(getActivity(), R.layout._study_card, studyJob_data);
        setListAdapter(studyJob_adapter);

        onRefresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_study_list_job, container, false);

        StudyJobList = (ListView) view.findViewById(android.R.id.list);
        StudyJobScroll = (ScrollView) view.findViewById(R.id.studyJob_scrollView);
        StudyJobList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StudyJobScroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.studyJob_swipe_container);
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

                studyJob_jarray = CommonUtil.stringToJSONArray(new String(response));
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

        String _id = "";
        String creator = "";
        String subject = "";
        String title = "";
        String detail = "";
        String create_time="";
        int[] week;
        String[] location;
        String[] members;
        int number_type = 0;

// Article[] articles = new Article[jarray.length()];

        studyJob_data.clear();
        studyJob_adapter.notifyDataSetInvalidated();

        try{
            for(int i=0;i<studyJob_jarray.length();i++){

                JSONObject studyList = studyJob_jarray.getJSONObject(i);

                _id = studyList.getString("_id");
                creator = studyList.getString("creator");
                subject = studyList.getString("subject");
                title = studyList.getString("title");
                number_type = studyList.getInt("number_type");
                detail = studyList.getString("detail");

                /** member 읽어오기 **/
                JSONArray member = studyList.getJSONArray("members");
                members = new String[member.length()];

                for (int j=0; j < member.length(); j++) {
                    members[j] = member.getString(j);
                }

                /** location 읽어오기 **/
                JSONArray locate = studyList.getJSONArray("location");
                location = new String[locate.length()];

                for(int j=0;j<locate.length();j++){
                    location[j] = locate.getString(j);
                }

                /** Week 읽어오기 **/
                JSONArray day = studyList.getJSONArray("day_of_week");
                week = new int[day.length()];

                for(int j=0;j<day.length();j++){
                    week[j] = day.getInt(j);
                }



                Log.d("output:", creator + "/" + subject + "/" + title + "/" + number_type + "/" + detail + "\n"
                        + members + "/" + location + "/" + week + "\n");
                Study study = new Study(_id,creator, subject, title, number_type, detail, "2014-08-08", members, location, week);

                if(subject.equals("job")) {
                    studyJob_data.add(study);
                }
            }

            studyJob_adapter.notifyDataSetChanged();

        }catch(JSONException je){
            Log.e("JSONException:",je.toString());
        }

    }


    private class StudyListAdapter extends ArrayAdapter<Study> implements View.OnClickListener {
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
                TextView title = (TextView) v.findViewById(R.id.studyCard_title);
                TextView memberCount = (TextView) v.findViewById(R.id.studyCard_memberCount);
                TextView maxMember = (TextView) v.findViewById(R.id.studyCard_maxMember);
                TextView day = (TextView) v.findViewById(R.id.studyCard_day);

                ImageView subject = (ImageView) v.findViewById(R.id.studyCard_subject);
                LinearLayout contents = (LinearLayout) v.findViewById(R.id.studyCard_contents);
                contents.setOnClickListener(this);

                if(subject != null){
                    //TODO : subject별로 icon image 변경. BitmapDrawable 불러온 뒤 set.

                    BitmapDrawable icon;

                    if (study.getSubject().equals("language")) {

                        icon = (BitmapDrawable)getResources().getDrawable(R.drawable.study_search_ic_03);
                        subject.setImageDrawable(icon);

                    } else if (study.getSubject().equals("test")) {

                        icon = (BitmapDrawable)getResources().getDrawable(R.drawable.study_search_ic_04);
                        subject.setImageDrawable(icon);

                    } else if (study.getSubject().equals("it")) {

                        icon = (BitmapDrawable)getResources().getDrawable(R.drawable.study_search_ic_05);
                        subject.setImageDrawable(icon);

                    } else if (study.getSubject().equals("job")) {

                        icon = (BitmapDrawable)getResources().getDrawable(R.drawable.study_search_ic_06);
                        subject.setImageDrawable(icon);

                    } else if (study.getSubject().equals("etc")) {

                        icon = (BitmapDrawable)getResources().getDrawable(R.drawable.study_search_ic_07);
                        subject.setImageDrawable(icon);

                    }
                }
                if (create_time != null) {
                    create_time.setText(study.getCreate_time());
                }
                if (location != null){
                    location.setText(study.getLocation());
                }
                if (title != null){
                    title.setText(study.getTitle());
                }
                if (maxMember != null){
                    maxMember.setText(study.getNumber_type()+"명");
                }
                if (memberCount != null){
                    memberCount.setText(Integer.toString(study.getMemberCount())+"명");
                }
                if (day != null){
                    day.setText(study.getWeek());
                }

            }

            return v;
        }


        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.studyCard_contents) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "디테일 화면으로 넘어갑니다!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 100);
                toast.show();
            }
        }
    }

}
