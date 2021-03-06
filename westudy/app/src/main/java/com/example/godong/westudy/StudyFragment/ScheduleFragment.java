package com.example.godong.westudy.StudyFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.common.CommonUtil;
import com.dataSet.Schedule;
import com.dataSet.Content;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

/**
 * Created by baggajin on 14. 7. 13..
 */
public class ScheduleFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{


    /** Data List **/
    private ArrayList<Schedule> schedule_data;
    private ScheduleAdapter schedule_adapter;
    private JSONArray schedule_jarray;

    private TextView ScheduleTabStudyTitle;

    private ListView ScheduleList;
    private ScrollView ScheduleScroll;

//    private String study_id="Test";

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

        schedule_data = new ArrayList<Schedule>();
        schedule_adapter = new ScheduleAdapter(getActivity(), R.layout._schedule_card, schedule_data);
        setListAdapter(schedule_adapter);
        onRefresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        init(view);

        return view;
    }

    public void init(View v){

        /** 리소스 초기화 **/

        ScheduleList = (ListView) v.findViewById(android.R.id.list);
        ScheduleScroll = (ScrollView) v.findViewById(R.id.schedule_scrollView);

//        study_id = getArguments().getString("study_id");

    }

    @Override
    public void onRefresh() {

        HttpUtil.get("http://godong9.com:3000/schedule/all", null, null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.i("HttpUtil.get.Start", "START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                schedule_jarray = CommonUtil.stringToJSONArray(new String(response));
                setScheduleData();


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

        //TODO: refrash 구현 부분..
//        new Handler().postDelayed(new Runnable() {
//            @Override public void run() {
//                Log.i("Handler","END");
//                swipeLayout.setRefreshing(false);
//            }
//        }, 2000);
    }

    private void setScheduleData(){

        String order ="";
        String create_time="";
        String start_time="";
        String end_time="";
        String title = "";
        String study_id="";
        ArrayList<Content> contents = new ArrayList<Content>();

        schedule_data.clear();
        schedule_adapter.notifyDataSetInvalidated();

        try{
            for(int i=0;i<schedule_jarray.length();i++){

                JSONObject Jschedule = schedule_jarray.getJSONObject(i);

                String[] timeTemp;

                title = Jschedule.getString("title");
                order = Jschedule.getString("order");
                study_id = Jschedule.getString("study_id");


                create_time = Jschedule.getString("create_time");
                timeTemp = create_time.split("T");
                create_time = timeTemp[0];

                start_time = Jschedule.getString("start_time");
                timeTemp = start_time.split("T");
                start_time = timeTemp[0];

                end_time = Jschedule.getString("end_time");
                timeTemp = end_time.split("T");
                end_time = timeTemp[0];


                /** contents 읽어오기 **/
                JSONArray jsonContents = Jschedule.getJSONArray("contents");
                Content input = new Content();

                for (int j=0; j < jsonContents.length(); j++) {
                    JSONObject con = jsonContents.getJSONObject(j);
                    if(con.getString("type").equals("text")){
                        input.setText(con.getString("text"));
                    }else if(con.getString("type").equals("check")){
                        input.setCheckList(con.getString("text"));
                    }

                }
                contents.add(input);

                Log.d("output",contents.toString());

                Schedule schedule = new Schedule(study_id, order, start_time, end_time,
                                                     title, create_time, contents);

                schedule_data.add(schedule);
                Log.d("Arraylist output", schedule_data.get(i).toString());

            }

            schedule_adapter.notifyDataSetChanged();

        }catch(JSONException je){
            Log.e("JSONException:",je.toString());
        }

    }

    private class ScheduleAdapter extends ArrayAdapter<Schedule> {
        private ArrayList<Schedule> items;

        public ScheduleAdapter(Context context, int textViewResourceId, ArrayList<Schedule> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if(v == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._schedule_card, null);
            }

            Schedule schedule = items.get(position);
            if(schedule !=null) {
                TextView create_time = (TextView) v.findViewById(R.id.scheduleCard_textView_createTime);
                TextView contents = (TextView) v.findViewById(R.id.scheduleCard_textView_contents);
                TextView title = (TextView) v.findViewById(R.id.scheduleCard_textView_title);
                TextView order = (TextView) v.findViewById(R.id.scheduleCard_textView_order);
                TextView end_time = (TextView) v.findViewById(R.id.scheduleCard_textView_endTime);
                ListView checkList = (ListView) v.findViewById(R.id.scheduleCard_listView_checkList);
                ArrayList<String> list;
                ArrayAdapter<String> adapter;

                if (create_time != null) {
                    create_time.setText(schedule.getCreate_time());
                }
                if (contents != null) {
                    if(schedule.getContentsText(position)!="") {
                        contents.setText(schedule.getContentsText(position));
                    }
                }if(checkList != null){
                    //TODO: 체크리스트 구현 부분...
                    if(schedule.getContentCheckList(position)!=null) {

//                        list = schedule.getContentCheckList(position);
//                        adapter = new ContentsAdapter(list, this);
//                        checkList.setAdapter(adapter);
//                        checkList.setItemsCanFocus(false);
//                        checkList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//                    }
//                        adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_schedule, list);
//                        checkList.setAdapter(adapter);
                    }


                }if(title!=null){
                    title.setText(schedule.getTitle());
                }if(order!=null){
                    order.setText(schedule.getOrder());
                }if(end_time!=null){
                    end_time.setText(schedule.getEnd_time());
                }
            }

            return v;
        }
    }


}

