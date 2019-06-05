package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.ComformOrderBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.UserLogic;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author KevinFu
 * @date 2019-06-05
 * Copyright (c) 2019 KevinFu
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        StatusBarUtil.setTranslucentForImageView(ConfirmOrderActivity.this, 0, mIvBg);
        ButterKnife.bind(this);
        getData();
    }


    public void getData() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(OrdersService.class).goSettlement(UserLogic.getUserId(), getDATA())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ComformOrderBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ComformOrderBean> t) throws Exception {

            }
        });
    }



    @OnClick(R.id.iv_back)
    public void onMRlReturnClicked() {
        finish();
    }
}
