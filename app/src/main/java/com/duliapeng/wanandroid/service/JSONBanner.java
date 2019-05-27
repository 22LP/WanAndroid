package com.duliapeng.wanandroid.service;

import android.app.Activity;
import com.duliapeng.wanandroid.bean.Banner;
import com.duliapeng.wanandroid.util.HTTPUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON解析banner数据
 */
public class JSONBanner {
    //接口
    public interface JSONCallbackListener {
        void onFinish(List<Banner> bannerList);

        void onError(Exception e);
    }

    public static void parseJOSNObject(final Activity activity, final String address, final JSONCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonResponse = HTTPUtil.response(address);
                    //解析banner
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray data = jsonObject.getJSONArray("data");
                    final List<Banner> bannerList = new ArrayList<>();

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonBanner = data.getJSONObject(i);
                        String title = jsonBanner.getString("title");
                        String imagePath = jsonBanner.getString("imagePath");
                        String urlBanner = jsonBanner.getString("url");
                        Banner banner = new Banner();
                        banner.setImagePath(imagePath);
                        banner.setUrl(urlBanner);
                        bannerList.add(banner);
                    }
                    if (listener != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFinish(bannerList);
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