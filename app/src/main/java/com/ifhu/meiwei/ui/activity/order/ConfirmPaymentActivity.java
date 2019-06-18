package com.ifhu.meiwei.ui.activity.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.WXPayBean;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图页面
 * @author KevinFu
 * @date 2019-06-06
 * Copyright (c) 2019 KevinFu
 */
public class ConfirmPaymentActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;

    @BindView(R.id.tv_ok)
    TextView mTvOk;
    WXPayBean wxPay;
    PayReq req = new PayReq();
    IWXAPI msgApi;
    @BindView(R.id.tv_return)
    TextView tvReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);
        ButterKnife.bind(this);
        mTvText.setVisibility(View.INVISIBLE);
        tvReturn.setVisibility(View.INVISIBLE);

        prparePay();
    }

    @OnClick(R.id.rl_return)
    public void onMRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void onMTvOkClicked() {
        wxPay();
    }

    public void prparePay() {
        wxPay = (WXPayBean) getIntent().getSerializableExtra("WXPay");
        if (null != wxPay) {
            req.appId = wxPay.getAppid();
            req.partnerId = wxPay.getPartnerid();
            req.prepayId = wxPay.getPrepayid();
            req.nonceStr = wxPay.getNoncestr();
            req.timeStamp = String.valueOf(wxPay.getTimestamp());
            req.packageValue = wxPay.getPackageX();
            req.sign = wxPay.getSign();
            req.extData = "app data";
            msgApi = WXAPIFactory.createWXAPI(ConfirmPaymentActivity.this, null);
            msgApi.registerApp(wxPay.getAppid());
        } else {
            Toast.makeText(ConfirmPaymentActivity.this, "返回错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void wxPay() {
        msgApi.sendReq(req);
    }

}
