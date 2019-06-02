package com.ifhu.meiwei.ui.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyShopJoinActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_return)
    TextView tvReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);
        ButterKnife.bind(this);
        tvText.setVisibility(View.INVISIBLE);
        tvTitle.setText("商家入驻");
        tvReturn.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
