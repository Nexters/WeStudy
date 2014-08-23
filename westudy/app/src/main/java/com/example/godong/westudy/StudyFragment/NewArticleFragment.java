package com.example.godong.westudy.StudyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dataSet.Article;
import com.example.godong.westudy.R;

/**
 * Created by baggajin on 14. 8. 16..
 */
public class NewArticleFragment extends Fragment{

    private Article newArticle;

    /** 글쓰기 화면 000들 선언 **/
    private EditText contents;
    private Button writeButton;
    private EditText toStudyName;

    /** MainActivity 에서 호출할 수 있게 Instance 생성 **/
    public static NewArticleFragment newInstance(){

        NewArticleFragment fragment = new NewArticleFragment();

        return fragment;
    }

    public NewArticleFragment(){
        this.newArticle = new Article();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_article_new, container, false);

        toStudyName = (EditText) view.findViewById(R.id.article_new_toStudyName);
        writeButton = (Button) view.findViewById(R.id.article_new_writeButton);
        contents = (EditText) view.findViewById(R.id.article_new_contents);

        return view;

    }


}
