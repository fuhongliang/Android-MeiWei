package com.ifhu.meiwei.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.utils.UserLogic;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author fuhongliang
 */
public class XWebViewFragment extends BaseFragment {

    boolean loadError = false;
    public static final int PROGRESS_MAX_VALUE = 100;
    @BindView(R.id.progress_web)
    ProgressBar mProgressWeb;
    @BindView(R.id.wv_view)
    WebView mWvView;
    Unbinder unbinder;

    public static XWebViewFragment newInstance() {
        return new XWebViewFragment();
    }

    public XWebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
    }
    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        mWvView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                if (url.startsWith("mailto:")) {
                    Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(android.webkit.WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadError = true;
            }
        });
        WebSettings setting = mWvView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setAllowFileAccess(true);
        setting.setAppCacheEnabled(true);
        setting.setSupportMultipleWindows(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWvView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWvView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                if (mProgressWeb != null){
                    if (newProgress == PROGRESS_MAX_VALUE) {
                        mProgressWeb.setVisibility(View.GONE);
                    } else {
                        mProgressWeb.setVisibility(View.VISIBLE);
                        mProgressWeb.setProgress(newProgress);
                    }
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        loadURL(false);
    }

    public void loadURL(boolean needToken){
        if (needToken) {
            HashMap<String, String> headers = new HashMap<String, String>();
            String dataToken = UserLogic.getUser().getToken();
            headers.put("token", dataToken);
            mWvView.loadUrl("https://www.baidu.com/", headers);
        } else {
            mWvView.loadUrl("https://www.baidu.com/");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
