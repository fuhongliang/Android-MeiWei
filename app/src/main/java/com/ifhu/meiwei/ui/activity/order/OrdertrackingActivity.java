package com.ifhu.meiwei.ui.activity.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdertrackingActivity extends BaseActivity {
    @BindView(R.id.tv_text_status)
    TextView tvTextStatus;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_button_one)
    TextView tvButtonOne;
    @BindView(R.id.tv_button_two)
    TextView tvButtonTwo;
    @BindView(R.id.tv_button_three)
    TextView tvButtonThree;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
