package com.duliapeng.wanandroid.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP获取图片 Bitmap
 */
public class HTTPImage {

    public interface HTTPCallbackListener {
        void onFinish(Bitmap bitmap);

        void onError(Exception e);
    }
    public static void parseHTTPImage(final Activity activity, final String address, final HTTPCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setDoInput(true);
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);
                        if (listener != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFinish(bitmap);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    //有错误，抛出例外
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
            //最后启动线程
        }).start();
    }
}