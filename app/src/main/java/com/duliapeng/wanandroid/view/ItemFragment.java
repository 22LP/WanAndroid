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
import com.duliapeng.wanandroid.adapter.ItemAdapter;
import com.duliapeng.wanandroid.bean.ItemArticle;
import com.duliapeng.wanandroid.service.JSONItem;

import java.util.List;

/**
 * 项目板块
 */
public class ItemFragment extends Fragment {
    private ListView mListView;
    private SwipeRefreshLayout mSrlItemRefresh;
    private int webNumber = 1;

    public ItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_item, container, false);
        mListView = (ListView) view.findViewById(R.id.item_lv_list);

        String address = "https://www.wanandroid.com/project/list/0/json?cid=294";
        initData(address);
        mSrlItemRefresh = (SwipeRefreshLayout) view.findViewById(R.id.item_srl_refresh);
        mSrlItemRefresh.setColorSchemeResources(R.color.colorPrimary);
        //刷新
        mSrlItemRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return view;
    }

    private void initData(String address) {
        JSONItem.parseJOSNObject(getActivity(), address, new JSONItem.JSONCallbackListener() {
            @Override
            public void onFinish(final List<ItemArticle> itemList) {
                final ItemAdapter adapter = new ItemAdapter(getContext(), itemList);
                adapter.notifyDataSetChanged();
                mListView.setAdapter(adapter);
                //list点击事件 调用PageWebView打开网页
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ItemArticle itemArticle = itemList.get(position);
                        Intent intent = new Intent(getActivity(), PageWebView.class);
                        intent.putExtra("itemLink", itemArticle.getLink());
                        startActivity(intent);
                    }

                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void refresh() {
        String add = "https://www.wanandroid.com/project/list/" + webNumber++ + "/json?cid=294";
        initData(add);
        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
        mSrlItemRefresh.setRefreshing(false);
    }
}
