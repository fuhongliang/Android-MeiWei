package com.ifhu.meiwei.ui.activity.order;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.OrderinfoBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ifhu.meiwei.utils.Utils.getContext;

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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_ring)
    LinearLayout llRing;
    @BindView(R.id.ll_order_status)
    LinearLayout llOrderStatus;
    @BindView(R.id.iv_store_phone)
    ImageView ivStorePhone;
    @BindView(R.id.ll_expand)
    LinearLayout llExpand;
    @BindView(R.id.tv_package)
    TextView tvPackage;
    @BindView(R.id.tv_delivery)
    TextView tvDelivery;
    @BindView(R.id.tv_full_reduction)
    TextView tvFullReduction;
    @BindView(R.id.tv_voucher)
    TextView tvVoucher;
    @BindView(R.id.tv_offer)
    TextView tvOffer;
    @BindView(R.id.tv_offer_money)
    TextView tvOfferMoney;
    @BindView(R.id.tv_money_string)
    TextView tvMoneyString;
    @BindView(R.id.tv_payment_money)
    TextView tvPaymentMoney;
    @BindView(R.id.tv_delivery_address)
    TextView tvDeliveryAddress;
    @BindView(R.id.tv_announcement)
    TextView tvAnnouncement;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.rl_refund)
    RelativeLayout rlRefund;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.tv_order_announcement)
    TextView tvOrderAnnouncement;
    @BindView(R.id.ll_goods_item)
    LinearLayout llGoodsItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        ButterKnife.bind(this);
        orderDetails();
    }

    /**
     * 订单详情接口
     */
    public void orderDetails() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(OrdersService.class).order_info(getDataInt())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OrderinfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<OrderinfoBean> t) throws Exception {
                initView(t.getData());
                handleCategoryDataInit(t.getData().getOrder_detail());
            }
        });

    }

    public void initView(OrderinfoBean orderinfoBean) {
        switch (orderinfoBean.getOrder_state()) {
            case 0:
                llOrderStatus.setBackgroundResource(R.color.category_color);
                rlRefund.setVerticalGravity(View.VISIBLE);
                tvTextStatus.setText("订单已取消");
                tvOrderAnnouncement.setText("您的订单已申请取消");
                tvButtonOne.setText("逛逛别家");
                llRing.setVerticalGravity(View.GONE);
                tvButtonTwo.setVisibility(View.GONE);
                tvButtonThree.setVisibility(View.GONE);
                llService.setVerticalGravity(View.GONE);
                ivStorePhone.setVisibility(View.GONE);
                break;
            case 10:
                llOrderStatus.setBackgroundResource(R.drawable.order_bnt_daizhfiu);
                tvTextStatus.setText("等待支付");
                tvOrderAnnouncement.setText("由商家提供配送服务");
                tvNotice.setText("超过15分钟未支付，订单将自动取消咯");
                tvButtonOne.setText("取消订单");
                tvButtonTwo.setVisibility(View.GONE);
                tvButtonThree.setText("立即支付");
                ivStorePhone.setVisibility(View.GONE);
                break;
            case 20:
                llOrderStatus.setBackgroundResource(R.drawable.order_bnt_jiedan);
                tvTextStatus.setText("等待商家接单");
                tvOrderAnnouncement.setText("由商家提供配送服务");
                tvNotice.setText("5分钟内商家未接单，将自动取消订单");
                tvButtonOne.setText("取消订单");
                tvButtonTwo.setVisibility(View.GONE);
                tvButtonThree.setVisibility(View.GONE);
                break;
            case 25:
                llOrderStatus.setBackgroundResource(R.drawable.order_bnt_yjiedan);
                tvTextStatus.setText("商家已接单");
                tvNotice.setText("商家正在准备商品，请耐心等待");
                tvOrderAnnouncement.setText("由商家提供配送服务");
                tvButtonOne.setText("取消订单");
                tvButtonTwo.setText("催单");
                tvButtonThree.setText("确认收货");
                break;
            case 30:
                break;
            case 35:
                break;
            case 40:
                break;
            default:
                break;
        }
        tvStoreName.setText(orderinfoBean.getStore_name());//商店名称
        tvPackage.setText(orderinfoBean.getPeisong());//配送费
        tvFullReduction.setText(orderinfoBean.getManjian());//满立减
        tvVoucher.setText(orderinfoBean.getDaijinquan());//代金券

        try {
            double price = Double.parseDouble(orderinfoBean.getManjian()) + Double.parseDouble(orderinfoBean.getDaijinquan());
            tvOfferMoney.setText(price + "");//已优惠
        } catch (Exception e) {
            e.printStackTrace();
        }


        tvPaymentMoney.setText(orderinfoBean.getTotal());//实付金额
        tvDeliveryAddress.setText(orderinfoBean.getPeisong_info().getUsername() + orderinfoBean.getPeisong_info().getMobile() + orderinfoBean.getPeisong_info().getSex() + "" + orderinfoBean.getPeisong_info().getAddress());
        tvOrderNumber.setText(orderinfoBean.getOrder_info().getOrder_sn() + "");//订单号码
        tvOrderTime.setText(orderinfoBean.getOrder_info().getAdd_time());//下单时间
        tvPaymentMethod.setText(orderinfoBean.getOrder_info().getPayment_code());//支付方式
    }

    public void handleCategoryDataInit(List<OrderinfoBean.OrderDetailBean> gcsort_data) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // 定义LayoutParam
        llGoodsItem.removeAllViews();

        if (gcsort_data != null && gcsort_data.size() > 0) {
            if (gcsort_data.size()>2){
                llExpand.setVisibility(View.VISIBLE);
                for (int i = 0; i < 2; i++) {
                    View categoryView = layoutInflater.inflate(R.layout.item_tracking_commodity, null);
                    GlideImageView imageView = categoryView.findViewById(R.id.iv_avatar);//商品图片
                    TextView product_name = categoryView.findViewById(R.id.tv_product_name);//商品名称
                    TextView now_price = categoryView.findViewById(R.id.tv_now_price);//商品现价
                    TextView original_price = categoryView.findViewById(R.id.tv_original_price);//商品原价
                    TextView tv_number = categoryView.findViewById(R.id.tv_number);//商品数量
                    imageView.load(Constants.IMGPATH + gcsort_data.get(i).getGoods_image());
                    product_name.setText(gcsort_data.get(i).getGoods_name());
                    now_price.setText("￥" + gcsort_data.get(i).getGoods_price());
                    original_price.setText("￥" + gcsort_data.get(i).getGoods_price());
                    original_price.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);//中划线
                    tv_number.setText("X" + gcsort_data.get(i).getGoods_num() + "");
                    llGoodsItem.addView(categoryView);
                }

            }else {
                llExpand.setVisibility(View.GONE);
                for (int i = 0; i < gcsort_data.size(); i++) {
                    View categoryView = layoutInflater.inflate(R.layout.item_tracking_commodity, null);
                    GlideImageView imageView = categoryView.findViewById(R.id.iv_avatar);//商品图片
                    TextView product_name = categoryView.findViewById(R.id.tv_product_name);//商品名称
                    TextView now_price = categoryView.findViewById(R.id.tv_now_price);//商品现价
                    TextView original_price = categoryView.findViewById(R.id.tv_original_price);//商品原价
                    TextView tv_number = categoryView.findViewById(R.id.tv_number);//商品数量
                    imageView.load(Constants.IMGPATH + gcsort_data.get(i).getGoods_image());
                    product_name.setText(gcsort_data.get(i).getGoods_name());
                    now_price.setText("￥" + gcsort_data.get(i).getGoods_price());
                    original_price.setText("￥" + gcsort_data.get(i).getGoods_price());
                    original_price.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);//中划线
                    tv_number.setText("X" + gcsort_data.get(i).getGoods_num() + "");
                    llGoodsItem.addView(categoryView);
                }
            }

        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
