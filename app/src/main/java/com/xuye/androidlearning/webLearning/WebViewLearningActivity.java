package com.xuye.androidlearning.webLearning;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.util.LogUtil;

/**
 * Created by xuye on 18/2/1
 * webview学习页
 */
public class WebViewLearningActivity extends AppCompatActivity {

    private LinearLayout mRootView;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private final String TEST_URL = "https://www.baidu.com/";

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_learning);
        mRootView = (LinearLayout) findViewById(R.id.webview_learning_root);
        mProgressBar = (ProgressBar) findViewById(R.id.webview_learning_progressbar);
        addWebView();
        initJs();
//        loadUrl(TEST_URL);
        loadUrl("file:///android_asset/web/js_call_native.html");
    }

    private void initJs() {
        //设置js与native的方法映射
        mWebView.addJavascriptInterface(new AndroidToJs(), "android");
    }

    private void loadUrl(String url) {
        if (mWebView != null) {
            LogUtil.logInfo("webview load url: " + url);
            mWebView.loadUrl(url);
        }
    }

    private void addWebView() {
        if (mRootView != null) {
            mWebView = new WebView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mWebView.setLayoutParams(params);
            initWebview(mWebView);
            mRootView.addView(mWebView);
        }
    }

    /**
     * 设置一些webview配置，一般会用到下面3个配置类
     * 1、webSettings，配置和管理webview
     * 2、WebViewClient，处理各种通知和请求事件
     * 3、WebChromeClient，辅助 WebView 处理 Javascript 的对话框、网站图标、标题、加载进度等
     */
    private void initWebview(WebView webView) {
        if (webView != null) {
            //设置1
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);//支持js


            //设置2
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    //当load通过ssl加密的https页面，但是如果这个网站的安全证书在Android无法得到认证，WebView就会变成一个空白页
                    handler.proceed();  // 接受信任所有网站的证书
                }
            });


            //设置3
            mWebView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    //加载进度
                    if (mProgressBar != null) {
                        mProgressBar.setProgress(newProgress);
                    }
                }
            });
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果是按下了返回键，判断一下是否可以加载上一页
            if (mWebView != null && mWebView.canGoBack()) {
                //可以返回上一页就返回，而不是直接退出当前页面
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
