package com.duliapeng.wanandroid.service;

import android.app.Activity;
import android.util.Log;

import com.duliapeng.wanandroid.bean.Article;
import com.duliapeng.wanandroid.bean.ItemArticle;
import com.duliapeng.wanandroid.util.HTTPUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON解析item数据
 */
public class JSONItem {

    public interface JSONCallbackListener {
        void onFinish(List<ItemArticle> itemList);

        void onError(Exception e);
    }

    public static void parseJOSNObject(final Activity activity, final String address, final JSONCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonResponse = HTTPUtil.response(address);
                    //解析item
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray datas = data.getJSONArray("datas");
                    final List<ItemArticle> itemList = new ArrayList<>();
                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject jsonList = datas.getJSONObject(i);
                        String envelopePic = jsonList.getString("envelopePic");
                        String author = jsonList.getString("author");
                        String niceDate = jsonList.getString("niceDate");
                        String title = jsonList.getString("title");
                        String link = jsonList.getString("link");

                        ItemArticle itemArticle = new ItemArticle();
                        itemArticle.setEnvelopePic(envelopePic);
                        itemArticle.setAuthor(author);
                        itemArticle.setNiceDate(niceDate);
                        itemArticle.setTitle(title);
                        itemArticle.setLink(link);
                        itemList.add(itemArticle);
                    }
                    if (listener != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFinish(itemList);
                            }
                        });
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }
}