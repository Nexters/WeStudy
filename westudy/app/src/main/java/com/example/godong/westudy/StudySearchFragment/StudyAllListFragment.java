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
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;

import com.common.CommonUtil;
import com.dataSet.Study;
import com.example.godong.westudy.R;
import com.example.godong.westudy.StudyFragment.StudyDetailDialog;
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
public class StudyAllListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeLayout;

    /** Data List **/
    private ArrayList<Study> studySearch_data;
    private StudyListAdapter studySearch_adapter;
    private JSONArray studySearch_jarray;

    private ListView StudySearchList;
    private ScrollView StudySearchScroll;

    private StudyDetailDialog detailDialog;


    public StudyAllListFragment(){

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

        View view = inflater.inflate(R.layout.fragment_study_list_all, container, false);

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

        studySearch_data.clear();
        studySearch_adapter.notifyDataSetInvalidated();

        try{
            for(int i=0;i<studySearch_jarray.length();i++){

                JSONObject studyList = studySearch_jarray.getJSONObject(i);

                _id = studyList.getString("_id");
                creator = studyList.getString("creator");
                subject = studyList.getString("subject");
                title = studyList.getString("title");
                number_type = studyList.getInt("person");
                detail = studyList.getString("detail");
//                create_time = studyList.getString("create_time");

//                int size=0;

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


                Log.d("output:",creator+"/"+subject+"/"+title+"/"+number_type+"/"+detail+"\n"
                               +members+"/"+location+"/"+week+"\n");
                Study study = new Study(_id,creator,subject,title,number_type,detail,"2014-08-08",members,location,week);

                studySearch_data.add(study);
                Log.d("Arraylist output", studySearch_data.get(i).toString());

            }

            studySearch_adapter.notifyDataSetChanged();

        }catch(JSONException je){
            Log.e("JSONException Occured:",je.toString());
        }

    }



    private class StudyListAdapter extends ArrayAdapter<Study> {
        private ArrayList<Study> items;
        private Context context;
        public StudyListAdapter(Context context, int textViewResourceId, ArrayList<Study> items){
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final int pos = position;
            View v = convertView;

            if(v == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._study_card, null);
            }

            Study study = items.get(position);
            if(study !=null) {
                v.setTag(position);
                TextView create_time = (TextView) v.findViewById(R.id.studyCard_createTime);
                TextView location = (TextView) v.findViewById(R.id.studyCard_location);
                TextView title = (TextView) v.findViewById(R.id.studyCard_title);
                TextView memberCount = (TextView) v.findViewById(R.id.studyCard_memberCount);
                TextView maxMember = (TextView) v.findViewById(R.id.studyCard_maxMember);
                TextView day = (TextView) v.findViewById(R.id.studyCard_day);

                ImageView subject = (ImageView) v.findViewById(R.id.studyCard_subject);
                LinearLayout contents = (LinearLayout) v.findViewById(R.id.studyCard_contents);

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


            // 리스트 아이템을 터치 했을 때 이벤트 발생
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Study study = items.get(pos);
//                    Toast.makeText(context, "리스트 클릭 : "+study.getId(), Toast.LENGTH_SHORT).show();
                    detailDialog = new StudyDetailDialog(study);
                    detailDialog.show(((FragmentActivity)getContext()).getFragmentManager(), "User1 Popup");
                }
            });

            return v;
        }
    }


}
