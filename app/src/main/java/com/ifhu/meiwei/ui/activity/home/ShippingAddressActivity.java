package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择收货地址页面
 *
 * @author KevinFu
 * @date 2019/5/23
 * Copyright (c) 2019 KevinFu
 */
public class ShippingAddressActivity extends BaseActivity {

    @BindView(R.id.tv_cur_address)
    TextView mTvCurAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);
        setTvCurAddress();
    }

    public void setTvCurAddress(){
        mTvCurAddress.setText(getDATA());
    }

    @OnClick(R.id.rl_return)
    public void onMRlReturnClicked() {
        finish();
    }
}
