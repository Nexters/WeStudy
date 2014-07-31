package com.example.godong.westudy;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dataSet.Article;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import com.loopj.android.http.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * Created by baggajin on 14. 7. 13..
 */
public class TimelineFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeLayout;

    /** Data List **/
    private ArrayList<Article> m_data;
    private FeedAdapter m_adapter;


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


        m_data = new ArrayList<Article>();

        Article f1 = new Article("14-07-26", "테스트 중이예요", "박가진", "Monday Arivo");
        Article f2 = new Article("14-07-26", "22테스트 중이예요22", "박가진", "Monday Arivo");
        Article f3 = new Article("14-07-26", "장문의 텍스트는 어떻게 출력 될까 궁금해서 테스트 하는 문장임.", "박가진", "Monday Arivo");

        m_data.add(f1);
        m_data.add(f2);
        m_data.add(f3);

        m_adapter = new FeedAdapter(getActivity(), R.layout._feed_card, m_data);
        setListAdapter(m_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
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
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://godong9.com:3000/test/add/user", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                System.out.println("START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                System.out.println("ABC");
                System.out.println(new String(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.println("ERRROR");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                System.out.println("END");
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
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
                TextView create_time = (TextView) v.findViewById(R.id.create_time);
                TextView contents = (TextView) v.findViewById(R.id.contents);
                TextView author = (TextView) v.findViewById(R.id.author);
                TextView study_id = (TextView) v.findViewById(R.id.study_id);

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
