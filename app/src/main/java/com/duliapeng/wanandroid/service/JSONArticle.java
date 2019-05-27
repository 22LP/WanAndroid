package com.duliapeng.wanandroid.service;

import android.app.Activity;
import com.duliapeng.wanandroid.bean.Article;
import com.duliapeng.wanandroid.util.HTTPUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 *JSON解析article数据
 */
public class JSONArticle {
    //接口
    public interface JSONCallbackListener {
        void onFinish(List<Article> articleList);

        void onError(Exception e);
    }

    public static void parseJOSNObject(final Activity activity, final String address, final JSONCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //调用HTTPUtil工具类获取数据
                    String jsonResponse = HTTPUtil.response(address);
                    //对数据解析得到article的list
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray datas = data.getJSONArray("datas");
                    final List<Article> articleList = new ArrayList<>();
                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject jsonList = datas.getJSONObject(i);
                        String author = jsonList.getString("author");
                        String niceDate = jsonList.getString("niceDate");
                        String chapterName = jsonList.getString("chapterName");
                        String superChapterName = jsonList.getString("superChapterName");
                        String title = jsonList.getString("title");
                        String link = jsonList.getString("link");
                        Article article = new Article(author, niceDate, chapterName, superChapterName, title, link);
                        articleList.add(article);
                    }
                    if (listener != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFinish(articleList);
                            }
                        });
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
            //启动线程
        }).start();
    }
}