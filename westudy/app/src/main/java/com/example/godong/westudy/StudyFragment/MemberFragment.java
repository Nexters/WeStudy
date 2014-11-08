package com.example.godong.westudy.StudyFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.CustomScrollView;
import com.dataSet.Article;
import com.dataSet.User;
import com.example.godong.westudy.R;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by baggajin on 14. 8. 30..
 */
public class MemberFragment extends Fragment {

    /** item List **/
    private ArrayList<User> member_data;
    private MemberAdapter member_adapter;
    private JSONArray member_jarray;
    private ListView MemberList;

    public static MemberFragment newInstance(){

        MemberFragment fragment = new MemberFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_member, container, false);

        init(view);

        return view;
    }

    public void init(View v){
        /** 리소스 초기화 **/
        MemberList = (ListView) v.findViewById(android.R.id.list);

    }

    private class MemberAdapter extends ArrayAdapter<User>{
        private ArrayList<User> items;

        public MemberAdapter(Context context, int textViewResourceId, ArrayList<User> items){
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

            User user = items.get(position);
            if(user !=null) {
                TextView name = (TextView) v.findViewById(R.id.memberCard_textView_name);
                ImageView user_image = (ImageView) v.findViewById(R.id.memberCard_imageView_photo);

                if (name != null) {
                    name.setText(user.getName());
                }
            }

            return v;
        }

    }
}
