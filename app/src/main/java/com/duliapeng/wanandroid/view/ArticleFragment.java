package com.duliapeng.wanandroid.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duliapeng.wanandroid.R;

public class ArticleFragment extends Fragment {
    private Context context;
    private String content;

    public ArticleFragment(){

    }

    @SuppressLint("ValidFragment")
    public ArticleFragment(Context context, String content){
        this.context = context;
        this.content = content;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_home,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
