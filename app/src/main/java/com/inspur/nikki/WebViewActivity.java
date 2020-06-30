package com.inspur.nikki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.wv_webview);
        // 加载本地html文件，file:///android_asset是系统获取你的assets文件夹，test.html是这个文件夹中的文件
        mWebView.loadUrl("http://10.255.4.101/ToUrl.aspx\\?type\\=A0102");
        // 设置WebView支持JavaScript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
    }
}
