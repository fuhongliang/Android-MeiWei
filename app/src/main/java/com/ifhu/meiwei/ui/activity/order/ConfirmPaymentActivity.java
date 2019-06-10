package com.ifhu.meiwei.ui.activity.order;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.ComformOrderBean;
import com.ifhu.meiwei.bean.WXPayBean;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author KevinFu
 * @date 2019-06-06
 * Copyright (c) 2019 KevinFu
 */
public class ConfirmPaymentActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.ll_goods)
    LinearLayout mLlGoods;
    @BindView(R.id.tv_ok)
    TextView mTvOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);
        ButterKnife.bind(this);
        mTvText.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.rl_return)
    public void onMRlReturnClicked() {
        finish();
    }

    ComformOrderBean comformOrderBean = new ComformOrderBean();

    public void showGoods() {
        mLlGoods.removeAllViews();
        for (int i = 0; i < comformOrderBean.getGoods_detail().size(); i++) {
            View mView = LayoutInflater.from(ConfirmPaymentActivity.this).inflate(R.layout.item_tracking_commodity, null);
            GlideImageView imageView = mView.findViewById(R.id.iv_avatar);
            ComformOrderBean.GoodsDetailBean goodsDetailBean = comformOrderBean.getGoods_detail().get(i);
            imageView.load(goodsDetailBean.getGoods_image());
            TextView name = mView.findViewById(R.id.tv_product_name);
            TextView nowPrice = mView.findViewById(R.id.tv_now_price);
            TextView tvOriginalPrice = mView.findViewById(R.id.tv_original_price);
            TextView tvNumber = mView.findViewById(R.id.tv_number);
            name.setText(goodsDetailBean.getGoods_name());
            nowPrice.setText(goodsDetailBean.getGoods_marketprice());
            tvOriginalPrice.setText(goodsDetailBean.getGoods_price());
            tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvNumber.setText("x" + goodsDetailBean.getGoods_num());
            mLlGoods.addView(mView);
        }
    }

    @OnClick(R.id.tv_ok)
    public void onMTvOkClicked() {
        Logger.d("tv_ok");
        wxPay();
    }

    public void wxPay() {
        WXPayBean wxPay = (WXPayBean) getIntent().getSerializableExtra("WXPay");
        if (null != wxPay) {
            PayReq req = new PayReq();
            req.appId = wxPay.getAppid();
            req.partnerId = wxPay.getPartnerid();
            req.prepayId = wxPay.getPrepayid();
            req.nonceStr = wxPay.getNoncestr();
            req.timeStamp = String.valueOf(wxPay.getTimestamp());
            req.packageValue = wxPay.getPackageX();
            req.sign = wxPay.getSign();
            req.extData = "app data";
            Toast.makeText(ConfirmPaymentActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
            IWXAPI msgApi = WXAPIFactory.createWXAPI(ConfirmPaymentActivity.this, null);
            msgApi.registerApp(wxPay.getAppid());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    msgApi.sendReq(req);
                }
            },500);
        } else {
            Toast.makeText(ConfirmPaymentActivity.this, "返回错误", Toast.LENGTH_SHORT).show();
        }
    }
}
