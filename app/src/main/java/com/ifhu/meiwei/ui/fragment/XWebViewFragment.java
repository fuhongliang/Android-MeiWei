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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.view.X5WebView;
import com.ifhu.meiwei.utils.JsJavaBridge;
import com.ifhu.meiwei.utils.UserLogic;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 评价跟商家介绍
 * @author fuhongliang
 */
public class XWebViewFragment extends BaseFragment {
    @BindView(R.id.progress_web)
    ProgressBar mProgressWeb;
    @BindView(R.id.wv_view)
    X5WebView mWvView;
    @BindView(R.id.rl_empty)
    RelativeLayout mRlEmpty;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.tv_title_one)
    TextView mTvTitleOne;
    Unbinder unbinder;
    public String url = "";
    public static XWebViewFragment newInstance() {
        return new XWebViewFragment();
    }

    public XWebViewFragment() {
        // Required empty public constructor
    }

    public void setEmptyDisplay(boolean emptyDisplay){
        if (emptyDisplay){
            mRlEmpty.setVisibility(View.VISIBLE);
        } else {
            mRlEmpty.setVisibility(View.INVISIBLE);
        }
    }

    public void setUrl(String url) {
        this.url = url;
        loadURL(false,url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, null, false);
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
            /**
             * 防止加载网页时调起系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                if (url.startsWith("mailto:")) {
                    Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mIvPhoto.setImageResource(R.drawable.quesehng_ic_wlljsb);
        mTvTitleOne.setText("网络好像出了点问题呢！");
    }

    public void loadURL(boolean needToken,String url){
        mWvView.addJavascriptInterface(new JsJavaBridge(getActivity(), mWvView), "$App");
        if (needToken) {
            HashMap<String, String> headers = new HashMap<String, String>();
            String dataToken = UserLogic.getUser().getToken();
            headers.put("token", dataToken);
            mWvView.loadUrl(url, headers);
        } else {
            mWvView.loadUrl(url);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWvView.setVisibility(View.VISIBLE);
    }
}
