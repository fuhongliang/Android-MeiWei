package com.ifhu.meiwei.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.ui.base.BaseFragment;

/**
 * 我的个人模块
 * @author fuhongliang
 */
public class MeFragment extends BaseFragment {

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
