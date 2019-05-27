package com.duliapeng.wanandroid.view;

import android.content.Intent;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.duliapeng.wanandroid.R;

import java.net.URL;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * 点击文章跳转的网页
 */
public class PageWebView extends AppCompatActivity {

    private ProgressBar mPbLoading;
    private WebView mWvPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_page);

        mWvPage = (WebView) findViewById(R.id.article_wv_webPage);
        mPbLoading = (ProgressBar) findViewById(R.id.web_pb_loading);

        mWvPage.getSettings().setJavaScriptEnabled(true);
        mWvPage.setWebViewClient(new WebViewClient());
        mWvPage.setWebChromeClient(webChromeClient);

        WebSettings webSettings = mWvPage.getSettings();

        //对网页的设置 缩放 自适应屏幕
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        Intent intent = getIntent();

        String articleLink = intent.getStringExtra("keyLink");
        mWvPage.loadUrl(articleLink);
        String itemLink = intent.getStringExtra("itemLink");
        mWvPage.loadUrl(itemLink);
        String bannerUrl = intent.getStringExtra("BannerUrl");
        mWvPage.loadUrl(bannerUrl);
    }
    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
               mPbLoading.setVisibility(View.GONE);
            }else {
                mPbLoading.setVisibility(View.VISIBLE);
                mPbLoading.setProgress(newProgress);
            }
            super.onProgressChanged(view,newProgress);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KEYCODE_BACK) && mWvPage.canGoBack()){
            mWvPage.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
