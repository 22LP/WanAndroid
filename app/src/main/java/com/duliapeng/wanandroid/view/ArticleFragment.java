package com.duliapeng.wanandroid.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.duliapeng.wanandroid.R;
import com.duliapeng.wanandroid.adapter.ArticleAdapter;
import com.duliapeng.wanandroid.adapter.BannerAdapter;
import com.duliapeng.wanandroid.bean.Article;
import com.duliapeng.wanandroid.bean.Banner;
import com.duliapeng.wanandroid.util.HTTPImage;
import com.duliapeng.wanandroid.service.JSONBanner;
import com.duliapeng.wanandroid.service.JSONArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页板块
 */
public class ArticleFragment extends Fragment {
    private ListView mListView;
    private SwipeRefreshLayout mSrlRefresh;
    private ViewPager mVpBanner;
    private List<ImageView> mIvBanner = new ArrayList<>();
    private BannerAdapter mBannerAdapter;

    public ArticleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_home, container, false);
        mListView = (ListView) view.findViewById(R.id.article_lv_list);
        mVpBanner = (ViewPager) view.findViewById(R.id.homepage_vp_banner);
        mSrlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.article_srl_refresh);

        initData("https://www.wanandroid.com/article/list/0/json");
        initBanner();

        mBannerAdapter = new BannerAdapter(mIvBanner);
        mVpBanner.setAdapter(mBannerAdapter);

        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

        });
        return view;
    }

    /**
     * 轮播图初始化
     */
    private void initBanner() {
        String addressBanner = "https://www.wanandroid.com/banner/json";
        JSONBanner.parseJOSNObject(getActivity(), addressBanner, new JSONBanner.JSONCallbackListener() {
            @Override
            public void onFinish(List<Banner> bannerList) {
                //循环调取图片
                for (int i = 0; i < bannerList.size(); i++) {
                    initPic(bannerList.get(i).getImagePath(), bannerList.get(i).getUrl());
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * 网络获取图片初始化
     */
    private void initPic(String address, final String bannerUrl) {
        HTTPImage.parseHTTPImage(getActivity(), address, new HTTPImage.HTTPCallbackListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageBitmap(bitmap);
                mIvBanner.add(imageView);
                //添加监听事件
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PageWebView.class);
                        intent.putExtra("BannerUrl", bannerUrl);
                        startActivity(intent);
                    }
                });
                mBannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }


    /**
     * 文章列表数据初始化并监听
     */
    private void initData(String address) {
        JSONArticle.parseJOSNObject(getActivity(), address, new JSONArticle.JSONCallbackListener() {
            @Override
            public void onFinish(final List<Article> articleList) {
                final ArticleAdapter adapter = new ArticleAdapter(getContext(), articleList);
                adapter.notifyDataSetChanged();
                mListView.setAdapter(adapter);
                //添加list监听
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
     */
    private int webNumber = 1;

    private void refresh() {
        String add = "https://www.wanandroid.com/article/list/" + webNumber++ + "/json";
        initData(add);
        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
        mSrlRefresh.setRefreshing(false);
    }
}

