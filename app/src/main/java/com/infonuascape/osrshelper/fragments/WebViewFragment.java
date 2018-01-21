package com.infonuascape.osrshelper.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.infonuascape.osrshelper.R;

/**
 * Created by marc_ on 2017-09-11.
 */

public class WebViewFragment extends OSRSFragment {
    private final static String EXTRA_URL = "EXTRA_URL";
    private final static String EXTRA_IS_NEWS = "EXTRA_IS_NEWS";

    private String url;
    private WebView webView;
    private ProgressBar progressBar;

    public static WebViewFragment newInstance(final String url) {
        return newInstance(url, false);
    }

    public static WebViewFragment newInstance(final String url, final boolean isNews) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle b = new Bundle();
        b.putString(EXTRA_URL, url);
        b.putBoolean(EXTRA_IS_NEWS, isNews);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.web_view, null);

        url = getArguments().getString(EXTRA_URL);

        progressBar = view.findViewById(R.id.progress_bar);

        webView = view.findViewById(R.id.web_view);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        if(getArguments().getBoolean(EXTRA_IS_NEWS, false)) {
            webView.getSettings().setMinimumFontSize(32);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        webView.loadUrl(url);
    }

    @Override
    public boolean onBackPressed() {
        if(!url.equalsIgnoreCase(webView.getOriginalUrl())) {
            webView.goBack();
            return true;
        }

        return false;
    }
}