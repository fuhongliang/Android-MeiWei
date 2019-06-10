package com.ifhu.meiwei.ui.activity.home;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.ComformOrderBean;
import com.ifhu.meiwei.bean.MyAddressBean;
import com.ifhu.meiwei.bean.PostOrderForm;
import com.ifhu.meiwei.bean.WXPayBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.activity.me.MyAddressListActivity;
import com.ifhu.meiwei.ui.activity.order.ConfirmPaymentActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ifhu.meiwei.utils.Constants.MONEYUNIT;

/**
 * @author KevinFu
 * @date 2019-06-05
 * Copyright (c) 2019 KevinFu
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.ll_goods)
    LinearLayout mLlGoods;

    boolean isNeedExpend = false;

    final static int showLessNumber = 2;
    @BindView(R.id.ll_more)
    LinearLayout mLlMore;
    @BindView(R.id.rl_order)
    RelativeLayout mRlOrder;
    ComformOrderBean comformOrderBean = new ComformOrderBean();
    @BindView(R.id.tv_all_amount)
    TextView mTvAllAmount;
    @BindView(R.id.et_note)
    EditText mEtNote;

    final static int REQUESTADDRESSCODE = 88;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    MyAddressBean myAddressBean;

    @BindView(R.id.tv_shipping_time)
    TextView mTvShippingTime;
    @BindView(R.id.tv_shipping_fee)
    TextView mTvShippingFee;
    @BindView(R.id.tv_vercher_fee)
    TextView mTvVercherFee;
    @BindView(R.id.tv_offer_money)
    TextView mTvOfferMoney;
    @BindView(R.id.tv_payment_money)
    TextView mTvPaymentMoney;
    @BindView(R.id.tv_coupon_money)
    TextView mTvCouponMoney;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;

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
                comformOrderBean = t.getData();
                myAddressBean = comformOrderBean.getAddress();
                handleGoodsShow();
                handeAddressShow(myAddressBean);
                handleOtherFee();
            }
        });
    }

    public void handleOtherFee() {
        mTvShippingFee.setText(MONEYUNIT + comformOrderBean.getPeisong_amount());
        mTvVercherFee.setText("-"+MONEYUNIT + comformOrderBean.getManjian_amount());
        mTvCouponMoney.setText(MONEYUNIT + comformOrderBean.getDaijinquan_amount());
        mTvOfferMoney.setText(MONEYUNIT + (comformOrderBean.getManjian_amount() + comformOrderBean.getDaijinquan_amount()));
        mTvPaymentMoney.setText(comformOrderBean.getTotal_amount()+"");
        mTvTotalMoney.setText(MONEYUNIT + comformOrderBean.getTotal_amount());
    }

    public void handleGoodsShow() {
        mLlGoods.removeAllViews();
        if (comformOrderBean.getGoods_detail().size() > showLessNumber && isNeedExpend) {
            for (int i = 0; i < comformOrderBean.getGoods_detail().size(); i++) {
                View mView = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.item_tracking_commodity, null);
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
            mLlMore.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < showLessNumber; i++) {
                View mView = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.item_tracking_commodity, null);
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
            mLlMore.setVisibility(View.VISIBLE);
            mTvAllAmount.setText("共" + comformOrderBean.getGoods_detail().size() + "件商品");
        }
    }

    public void handeAddressShow(MyAddressBean myAddressBean) {
        mTvName.setText(myAddressBean.getTrue_name());
        mTvAddress.setText(myAddressBean.getArea_info() + myAddressBean.getAddress());
        mTvPhone.setText(myAddressBean.getMob_phone() + "");
    }

    @OnClick(R.id.iv_back)
    public void onMRlReturnClicked() {
        finish();
    }

    @OnClick(R.id.ll_more)
    public void onMLlMoreClicked() {
        isNeedExpend = true;
        handleGoodsShow();
    }

    @OnClick(R.id.tv_post_order)
    public void onMTvPostOrderClicked() {
        setLoadingMessageIndicator(true);
        PostOrderForm postOrderForm = new PostOrderForm();
        postOrderForm.setStore_id(getDATA());
        postOrderForm.setAddress_id(comformOrderBean.getAddress().getAddress_id() + "");
        postOrderForm.setMember_id(UserLogic.getUserId());
        postOrderForm.setOrder_message("" + mEtNote.getText().toString());
        postOrderForm.setPayment_code("wxpay");
        postOrderForm.setPeisong_amount(comformOrderBean.getPeisong_amount() + "");
        postOrderForm.setShipping_time(mTvShippingTime.getText().toString());
        postOrderForm.setVoucher_id("");
        RetrofitApiManager.create(OrdersService.class).buyStep(postOrderForm)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<WXPayBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<WXPayBean> t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                Intent intent = new Intent(ConfirmOrderActivity.this,ConfirmPaymentActivity.class);
                intent.putExtra("WXPay",t.getData());
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.rl_address)
    public void onMRlAddressClicked() {
        startActivityForResult(new Intent(ConfirmOrderActivity.this, MyAddressListActivity.class).putExtra(MyAddressListActivity.IsFromOrder, true), REQUESTADDRESSCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTADDRESSCODE) {
                myAddressBean = (MyAddressBean) data.getSerializableExtra(MyAddressListActivity.ADDRESS);
                handeAddressShow(myAddressBean);
                comformOrderBean.setAddress(myAddressBean);
            }
        }
    }
}
