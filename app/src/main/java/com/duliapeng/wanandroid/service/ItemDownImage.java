package com.duliapeng.wanandroid.service;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import java.net.URL;

/**
 * 获取网络图片  不需要使用到getActivity（） 可在adapter直接使用
 */
public class ItemDownImage {
	public String image_path;
 
	public ItemDownImage(String image_path) {
		this.image_path = image_path;
	}
 
	public void loadImage(final ImageCallBack callBack) {
 
		final Handler handler = new Handler() {
 
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Drawable drawable = (Drawable) msg.obj;
				callBack.getDrawable(drawable);
			}
 
		};
 
		new Thread(new Runnable() {
 
			@Override
			public void run() {
				try {
					Drawable drawable = Drawable.createFromStream(new URL(
							image_path).openStream(), "");
 
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
 
	public interface ImageCallBack {
		public void getDrawable(Drawable drawable);
	}
}
