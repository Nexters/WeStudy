package com.example.godong.westudy.StudyFragment;

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
import com.dataSet.Article;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baggajin on 14. 7. 13..
 */
public class TimelineFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeLayout;

    /** Data List **/
    private ArrayList<Article> timeline_data;
    private FeedAdapter timeline_adapter;
    private JSONArray timeline_jarray;

    private ListView TimelineList;
    private ScrollView TimelineScroll;

    public TimelineFragment(){

    }

    public static TimelineFragment newInstance(){

        TimelineFragment fragment = new TimelineFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);

        timeline_data = new ArrayList<Article>();
        timeline_adapter = new FeedAdapter(getActivity(), R.layout._feed_card, timeline_data);
        setListAdapter(timeline_adapter);

        onRefresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        TimelineList = (ListView) view.findViewById(android.R.id.list);
        TimelineScroll = (ScrollView) view.findViewById(R.id.timeline_scrollView);
        TimelineList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TimelineScroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.timeline_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onRefresh() {
//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    String response = HttpRequest.get("https://api.dailymotion.com/videos/").body();
//                    Gson gson = new Gson();
//                    JSONObject a = new JSONObject(response);
//                    System.out.println(a.get("page"));
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        });
//        thread.start();
//        // TODO Auto-generated method stub

        HttpUtil.get("http://godong9.com:3000/article/all", null, null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.i("HttpUtil.get.Start", "START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                timeline_jarray = CommonUtil.stringToJSONArray(new String(response));
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

        String create_time="";
        String author = "";
        String study_id = "";
        String text = "";
        String photo_url = "";

// Article[] articles = new Article[jarray.length()];

        timeline_data.clear();
        timeline_adapter.notifyDataSetInvalidated();

        try{
            for(int i=0;i<timeline_jarray.length();i++){

                JSONObject feed = timeline_jarray.getJSONObject(i);

                author = feed.getString("author");
                study_id = feed.getString("study_id");

                create_time = feed.getString("create_time");
                String[] timeTemp;
                timeTemp = create_time.split("T");
                create_time = timeTemp[0];

                /** contents 읽어오기 **/
                JSONObject contents = feed.getJSONObject("contents");
                int size = contents.length();

                for(int j=0;j<size;j++){
                    text = contents.getString("text");
                    photo_url = contents.getString("photo_url");
                }

//                Log.d("output",author+"/"+study_id+"/"+text+"/"+photo_url);
                Article article = new Article(create_time, text, photo_url, author, study_id);

                timeline_data.add(article);
                Log.d("Arraylist output", timeline_data.get(i).toString());

            }

            timeline_adapter.notifyDataSetChanged();

        }catch(JSONException je){
            Log.e("JSONException:",je.toString());
        }

    }


    private class FeedAdapter extends ArrayAdapter<Article>{
        private ArrayList<Article> items;

        public FeedAdapter(Context context, int textViewResourceId, ArrayList<Article> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if(v == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._feed_card, null);
            }

            Article article = items.get(position);
            if(article !=null) {
                TextView create_time = (TextView) v.findViewById(R.id.feedCard_createTime);
                TextView contents = (TextView) v.findViewById(R.id.feedCard_contents);
                TextView author = (TextView) v.findViewById(R.id.feedCard_author);
                TextView study_id = (TextView) v.findViewById(R.id.feedCard_studyId);

                if (create_time != null) {
                    create_time.setText(article.getCreate_time());
                }
                if (contents != null) {
                    contents.setText(article.getContents());
                }if(author!=null){
                    author.setText(article.getAuthor());
                }if(study_id!=null){
                    study_id.setText(article.getStudy_id());
                }
            }

            return v;
        }
    }


}
