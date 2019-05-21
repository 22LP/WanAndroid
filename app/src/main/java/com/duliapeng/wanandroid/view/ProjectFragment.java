package com.duliapeng.wanandroid.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.duliapeng.wanandroid.R;

public class ProjectFragment extends Fragment {
    private Context context;
    private String content;
    private String[] text = {"111","222","333","444","5555","666","777","111","222","333","444","5555","666","777"};
    private String[] data = {"1","2","5","4","6","3","7","8"};
    public ProjectFragment(){

    }
    @SuppressLint("ValidFragment")
   public ProjectFragment(Context context, String content){
        this.context = context;
        this.content = content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,container,false);

        return view;
    }

}
