package com.duliapeng.wanandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.duliapeng.wanandroid.R;
import com.duliapeng.wanandroid.adapter.ArticleAdapter;
import com.duliapeng.wanandroid.bean.Article;
import com.duliapeng.wanandroid.service.JSONArticle;
import java.util.List;

/**
 * 体系板块
 */
public class SystemFragment extends Fragment {
    private ListView mListView;
    private SwipeRefreshLayout mSrlRefresh;
    private int webNumber = 1;
    public SystemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_system,container,false);
        mListView = (ListView) view.findViewById(R.id.system_lv_list);
        mSrlRefresh = (SwipeRefreshLayout)view.findViewById(R.id.system_srl_refresh);

        initData("https://www.wanandroid.com/article/list/0/json?cid=60");
        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(webNumber == 3){
                    webNumber = 0;
                    Toast.makeText(getContext(), "刷新失败，无更新内容", Toast.LENGTH_SHORT).show();
                    mSrlRefresh.setRefreshing(false);
                } else {
                    refresh(webNumber);
                    webNumber++;
                }

            }
        });
        return view;
    }

    /**
     * 初始化
     * @param address
     */
    private void initData(String address){
        JSONArticle.parseJOSNObject(getActivity(),address, new JSONArticle.JSONCallbackListener() {
            @Override
            public void onFinish(final List<Article> articleList) {
                final ArticleAdapter adapter = new ArticleAdapter(getContext(), articleList);
                adapter.notifyDataSetChanged();
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Article article = articleList.get(position);
                        Intent intent = new Intent(getActivity(), PageWebView.class);
                        intent.putExtra("keyLink", article.getLink());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }

        });
    }

    /**
     * 刷新
     * @param webNumber
     */
    private void refresh(int webNumber){
        String add = "https://www.wanandroid.com/article/list/" + webNumber + "/json?cid=60";
        initData(add);
        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
        mSrlRefresh.setRefreshing(false);
    }
}