package mr.li.dance.ui.activitys.game;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import mr.li.dance.R;
import mr.li.dance.h5.MyObject;
import mr.li.dance.ui.fragments.BaseFragment;
import mr.li.dance.ui.widget.DanceWebView;
import mr.li.dance.utils.AppConfigs;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:      赛事设项
 * 修订历史:
 */
public class TabProject extends BaseFragment {
    private DanceWebView mWebView;
    private ProgressBar  mProgressBar;

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        Bundle arguments = getArguments();
        String id = arguments.getString("id");
        mWebView = (DanceWebView) mView.findViewById(R.id.rc_webview);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.rc_web_progressbar);
        mWebView.setVerticalScrollbarOverlay(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        //关闭缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.addJavascriptInterface(new MyObject(getActivity()), "myObj");

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT > 11) {
            webSettings.setDisplayZoomControls(false);
        }
        webSettings.setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        webSettings.setSupportZoom(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new RongWebChromeClient());
        loadUrl(id);
    }

    private void loadUrl(String id) {
        String shexiang_url = String.format(AppConfigs.SAISHIShareUrl, String.valueOf(id), String.valueOf(10702));
        mWebView.loadUrl(shexiang_url);
    }

    @Override
    public int getContentView() {
        return R.layout.game_tab_web;
    }

    private class RongWebChromeClient extends WebChromeClient {
        private RongWebChromeClient() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }

                mProgressBar.setProgress(newProgress);
            }

            super.onProgressChanged(view, newProgress);
        }

        public void onReceivedTitle(WebView view, String title) {

        }
    }
}
