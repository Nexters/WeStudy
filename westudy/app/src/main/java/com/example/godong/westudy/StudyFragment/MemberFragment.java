package com.example.godong.westudy.StudyFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.CommonUtil;
import com.dataSet.StudyHelper;
import com.dataSet.User;
import com.example.godong.westudy.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.network.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

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

    private ArrayList<User> applier_data;
    private ApplierAdapter applier_adapter;
    private JSONArray applier_jarray;
    private ListView ApplierList;


    public static MemberFragment newInstance () {
        MemberFragment fragment = new MemberFragment();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        init(view);
        return view;
    }

    public void init (View v) {
        MemberList = (ListView) v.findViewById(R.id.member_list);
        ApplierList = (ListView) v.findViewById(R.id.applier_list);

        member_data = new ArrayList<User>();
        member_adapter = new MemberAdapter(getActivity(), R.layout._member_card, member_data);
        MemberList.setAdapter(member_adapter);

        applier_data = new ArrayList<User>();
        applier_adapter = new ApplierAdapter(getActivity(), R.layout._member_card, applier_data);
        ApplierList.setAdapter(applier_adapter);

        onRefresh();
    }

    public void onRefresh () {
        RequestParams params = new RequestParams();
        params.put("study_id", StudyHelper.getStudy().getId());

        HttpUtil.get("http://godong9.com:3000/study/getMembers", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.i("HttpUtil.getMember.Start", "START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                member_jarray = CommonUtil.stringToJSONArray(new String(response));
                JSONObject member = null;
                for(int i = 0; i < member_jarray.length(); i++) {
                   try {
                       member = (JSONObject) member_jarray.get(i);
                       Log.d("HTTP GET MEMBER", member.getString("name"));
                   } catch (Exception e) {

                   }
                }
                setMemberData(member_jarray);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("HttpUtil.getMember.ERROR", "ERROR");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        HttpUtil.get("http://godong9.com:3000/study/getAppliers", null, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.i("HttpUtil.getApplier.Start", "START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                applier_jarray = CommonUtil.stringToJSONArray(new String(response));
                JSONObject applier = null;
                for(int i = 0; i < applier_jarray.length(); i++) {
                    try {
                        applier = (JSONObject) applier_jarray.get(i);
                        Log.d("HTTP GET APPLIER NAME", applier.getString("name"));
                    } catch (Exception e) {

                    }
                }
                setApplierData(applier_jarray);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("HttpUtil.getApplier.ERROR", "ERROR");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void setMemberData (JSONArray members) {
        member_data.clear();
        member_adapter.notifyDataSetInvalidated();
        for (int i = 0; i < members.length(); i++) {
            try {
                JSONObject memberObj = (JSONObject) members.get(i);
                User member = new User();
                member.set_id(memberObj.getString("_id"));
                member.setName(memberObj.getString("name"));
                member.setEmail(memberObj.getString("email"));
                member.setProfile_url(memberObj.getString("profile_url"));
                member_data.add(member);

                member_adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d("setMemberData JSONException", e.toString());
            }

        }

    }

    public void setApplierData (JSONArray appliers) {
        applier_data.clear();
        applier_adapter.notifyDataSetInvalidated();
        for (int i = 0; i < appliers.length(); i++) {
            try {
                JSONObject applierObj = (JSONObject) appliers.get(i);
                User applier = new User();
                applier.set_id(applierObj.getString("_id"));
                applier.setName(applierObj.getString("name"));
                applier.setEmail(applierObj.getString("email"));
                applier.setProfile_url(applierObj.getString("profile_url"));
                applier_data.add(applier);

                applier_adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d("setApplierData JSONException", e.toString());
            }
        }
    }

    private class MemberAdapter extends ArrayAdapter<User> {
        private ArrayList<User> _members;

        public MemberAdapter(Context context, int textViewResourceId, ArrayList<User> members){
            super(context, textViewResourceId, members);
            this._members = members;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if(v == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._member_card, null);
            }

            User user = _members.get(position);
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

    private class ApplierAdapter extends ArrayAdapter<User> {
        private ArrayList<User> _appliers;

        public ApplierAdapter(Context context, int textViewResourceId, ArrayList<User> appliers) {
            super(context, textViewResourceId, appliers);
            this._appliers = appliers;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if(v == null){
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout._member_card, null);
            }

            User user = _appliers.get(position);
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
