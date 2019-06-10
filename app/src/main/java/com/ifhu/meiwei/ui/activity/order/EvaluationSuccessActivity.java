package com.ifhu.meiwei.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.ui.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationSuccessActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.iv_share_it)
    ImageView ivShareIt;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    int count = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_success);
        ButterKnife.bind(this);
        tvReturn.setVisibility(View.INVISIBLE);
        tvText.setVisibility(View.INVISIBLE);
        tvTitle.setText("评价成功");
        handler.sendEmptyMessage(1);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(HomeFragment.class);
                handler.sendEmptyMessage(1);
            }
        });
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;

            if (count == 0) {
                goToActivity(HomeFragment.class);
            } else {
                handler.sendEmptyMessageDelayed(1, 1000);
                tvLogin.setText("返回(" + count + ")");
            }

        }
    };

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
