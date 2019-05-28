package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.MyAddressAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MyAddressBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.UserService;
import com.ifhu.meiwei.ui.activity.login.LoginActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_return)
    TextView tvReturn;

    List<MyAddressBean> myAddressBeanList = new ArrayList<>();

    MyAddressAdapter myAddressAdapter;
    @BindView(R.id.lv_address)
    ListView lvAddress;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);
        tvTitle.setText("选择收货地址");
        tvText.setText("新增地址");
        tvReturn.setVisibility(View.INVISIBLE);
        setTvCurAddress();
        if (!UserLogic.isLogin()){
            goToActivity(LoginActivity.class);
        }else {
            receivingAddressList();
        }
        myAddressAdapter = new MyAddressAdapter(myAddressBeanList, this);
        lvAddress.setAdapter(myAddressAdapter);
    }

    /**
     * 收货地址列表接口
     */
    public void receivingAddressList() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(UserService.class).userAddressList(UserLogic.getUser().getMember_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<MyAddressBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<MyAddressBean>> t) throws Exception {
                myAddressBeanList = t.getData();
                myAddressAdapter.setmyAddressBeanList(myAddressBeanList);
                if (myAddressAdapter.getCount() > 0) {
                    llEmpty.setVisibility(View.GONE);
                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public void setTvCurAddress() {
        mTvCurAddress.setText(getDATA());
    }


    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }


    @OnClick(R.id.tv_text)
    public void onTvTextClicked() {
        goToActivity(AddaddressActivity.class);
    }

    @OnClick(R.id.tv_add_address)
    public void onTvAddAddressClicked() {
        goToActivity(AddaddressActivity.class);
    }


}
