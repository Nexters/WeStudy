package com.example.godong.westudy.SideMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.godong.westudy.Activity.JoinActivity;
import com.example.godong.westudy.Activity.LoginActivity;
import com.example.godong.westudy.Activity.StudyMainActivity;
import com.example.godong.westudy.R;
import com.dataSet.User;
/**
 * Created by baggajin on 14. 7. 12..
 */
public class ProfileFragment extends Fragment {

    /** Data UI 선언 **/
    private TextView name;
    private TextView _id;
    private TextView email;
    private TextView interest;
    private TextView create_time;
    private TextView gender;
    private TextView study;
    private Button logoutBtn;

    private SharedPreferences prefs;

    private User user;

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(){

        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        prefs = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);

        logoutBtn = (Button) view.findViewById(R.id.profile_logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        user = getArguments().getParcelable("userData");
        Log.d("userINFO :: ",user.toString());

        name = (TextView) view.findViewById(R.id.profile_textView_name);
        _id = (TextView) view.findViewById(R.id.profile_textView_id);
        email = (TextView) view.findViewById(R.id.profile_textView_email);
        interest = (TextView) view.findViewById(R.id.profile_textView_interest);
        create_time = (TextView) view.findViewById(R.id.profile_textView_create_time);
        gender = (TextView) view.findViewById(R.id.profile_textView_gender);
        study = (TextView) view.findViewById(R.id.profile_textView_study);

        if(name != null) {
            name.setText(user.getName());
        }
        if(_id != null) {
            _id.setText(user.get_id());
        }
        if(email != null) {
            email.setText(user.getEmail());
        }
        if(interest != null) {
            interest.setText(user.getInterest());
        }
        if(create_time != null) {
            create_time.setText(user.getCreate_time());
        }
        if(gender != null) {
            gender.setText(user.getGender());
        }
        if(study != null) {
            study.setText(user.getStudy());
        }

        return view;

    }

    private void logout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("email");
        editor.remove("pw");
        editor.commit();

        Intent intentJoinActivity = new Intent(getActivity(), LoginActivity.class);
        startActivity(intentJoinActivity);
    }
}
