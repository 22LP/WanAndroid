package com.duliapeng.wanandroid.view;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.duliapeng.wanandroid.R;
import com.duliapeng.wanandroid.adapter.ArticleAdapter;
import com.duliapeng.wanandroid.bean.Article;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {
    public static final int UPDATE_TEXT = 1;
    private String jsonString;
    private List<Article> articleList = new ArrayList<>();

    private ViewPager mVpSlide;
    private TabLayout mTlSlide;
    private ListView listView;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();

    private View mFmHome,mFmItem,mFmSystem;
    private List<View> viewList;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    ArticleAdapter adapter =new ArticleAdapter(getBaseContext(), R.layout.layout_article, articleList);


                    ListView listView = (ListView) findViewById(R.id.article_lv_list);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);

                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        initView();
        mVpSlide = (ViewPager) findViewById(R.id.slide_vp_view);
        mFmHome = getLayoutInflater().inflate(R.layout.fragment_home,null);
        mFmSystem = getLayoutInflater().inflate(R.layout.fragment_system,null);
        mFmItem = getLayoutInflater().inflate(R.layout.fragment_item,null);

        viewList = new ArrayList<View>();
        viewList.add(mFmHome);
        viewList.add(mFmSystem);
        viewList.add(mFmItem);

        getJSON();
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        mVpSlide.setAdapter(pagerAdapter);
    }

    private void initView() {
        mTlSlide = findViewById(R.id.slide_tl_tab);
        mVpSlide = findViewById(R.id.slide_vp_view);
        tabs.add("首页");
        tabs.add("体系");
        tabs.add("项目");
        mTlSlide.addTab(mTlSlide.newTab().setText("首页"));
        mTlSlide.addTab(mTlSlide.newTab().setText("体系"));
        mTlSlide.addTab(mTlSlide.newTab().setText("项目"));

       /* fragments.add(ArticleFragment.newInstance("Fragment 1"));
        fragments.add(ArticleFragment.newInstance("Fragment 2"));
        fragments.add(ArticleFragment.newInstance("Fragment 3"));*/

        mTlSlide.setTabMode(TabLayout.MODE_FIXED);

        mVpSlide.setAdapter(new TabAdapter(getSupportFragmentManager()));

        mTlSlide.setupWithViewPager(mVpSlide);

    }


    class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }

    private void parseJOSNObeject(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray datas = data.getJSONArray("datas");
            for (int i = 0; i <datas.length(); i++){
                JSONObject jsonList = datas.getJSONObject(i);
                String author = jsonList.getString("author");
                String niceDate = jsonList.getString("niceDate");
                String chapterName = jsonList.getString("chapterName");
                String superChapterName = jsonList.getString("superChapterName");
                String title = jsonList.getString("title");
                Log.d("MainActivity","author++++++++++++++++++++++++"+author);

                Article article = new Article(author,niceDate,chapterName,superChapterName,title);
                Log.d("MainActivity","author++++++++++++++++++++++++"+article.getAuthor());
                articleList.add(article);
                Log.d("MainActivity","title-------------------------"+articleList.get(i));
            }


            Message message = new Message();
            message.what=UPDATE_TEXT;
            handler.sendMessage(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getJSON()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
//默认IP地址
                String host = "https://www.wanandroid.com/article/list/0/json";
//建立HttpURLConnection对象
                HttpURLConnection conn = null;
                try {
                    //查看线程是否正常
                    if(Thread.interrupted()) throw new InterruptedException();
//设置链结
                    URL url = new URL(host);
//打开链结
                    conn = (HttpURLConnection) url.openConnection();
//设置读取链结时间，这里设置为10秒
                    conn.setReadTimeout(10000);
//设置联机链结时间，这里设置为15秒
                    conn.setConnectTimeout(15000);
                    //使用GET方式获取数据
                    conn.setRequestMethod("GET");
                    //URL 连接可用于输入和/或输出。如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true。 
                    conn.setDoInput(true);
//联机到服务器
                    conn.connect();
//查看线程是否正常
                    if(Thread.interrupted()) throw new InterruptedException();
//用缓冲区加载数据
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    jsonString = reader.readLine();
//打印JSON信息
                    Log.d("jsonStr"," 0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"+jsonString);
                    parseJOSNObeject(jsonString);
                    //关闭对象
                    reader.close();
                }catch (Exception e)
                {
                    //有错误，抛出例外
                    e.printStackTrace();
                } finally {
//如果有联机成功，最后关闭联机
                    if(conn != null) conn.disconnect();
                }
            }
            //最后启动线程
        }).start();
    }

}
