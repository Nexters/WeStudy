package com.example.godong.westudy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.os.StrictMode;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by baggajin on 14. 7. 13..
 */
public class TimelineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeLayout;

    public TimelineFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);
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
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    String response = HttpRequest.get("https://api.dailymotion.com/videos/").body();
                    Gson gson = new Gson();
                    JSONObject a = new JSONObject(response);
                    System.out.println(a.get("page"));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        thread.start();
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                System.out.println("END");
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }

}
