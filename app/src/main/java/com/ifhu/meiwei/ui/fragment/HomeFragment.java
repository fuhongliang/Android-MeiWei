package com.ifhu.meiwei.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.view.MyScrollView;
import com.orhanobut.logger.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页模块
 *
 * @author fuhongliang
 */
public class HomeFragment extends BaseFragment implements MyScrollView.OnScrollListener {

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder;
    @BindView(R.id.scrollView)
    MyScrollView mScrollView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView.setOnScrollListener(this);
    }

    private void animateOut(FloatingActionButton fab) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        int rightMargin = layoutParams.rightMargin;
        fab.animate().translationX(fab.getWidth()/2 + rightMargin).setInterpolator(new LinearInterpolator()).start();
    }

    private void animateIn(FloatingActionButton fab) {
        fab.animate().translationX(0).setInterpolator(new LinearInterpolator()).start();
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY > 300) {
            animateOut(mFab);
        }
        if (scrollY < 280){
            animateIn(mFab);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
