package com.ifhu.meiwei.ui.activity.order;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.OrderinfoBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.activity.home.MainActivity;
import com.ifhu.meiwei.ui.activity.home.ShopHomeActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.SpannableTextUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author fuhongliang
 * 订单详情页面
 */
public class OrdertrackingActivity extends BaseActivity {
    @BindView(R.id.tv_text_status)
    TextView tvTextStatus;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    //    @BindView(R.id.tv_time)
//    TextView tvTime;
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
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.rl_refund)
    RelativeLayout rlRefund;
    //    @BindView(R.id.ll_service)
//    LinearLayout llService;
//    @BindView(R.id.tv_order_announcement)
//    TextView tvOrderAnnouncement;
    @BindView(R.id.tv_order_announcement_time)
    TextView tvOrderAnnouncementTime;//订单配送时间和商家文本
    @BindView(R.id.ll_goods_item)
    LinearLayout llGoodsItem;
    @BindView(R.id.iv_bg_view)
    ImageView mIvBgView;
    @BindView(R.id.ll_state)
    LinearLayout mLlState;
    @BindView(R.id.tv_announcement_one)
    TextView tvAnnouncementOne;
    @BindView(R.id.tv_announcement_two)
    TextView tvAnnouncementTwo;
    @BindView(R.id.ll_order_status)
    RelativeLayout mLlOrderStatus;
    @BindView(R.id.sv_order_detail)
    ScrollView mSvOrderDetail;
    @BindView(R.id.tv_watch_more)
    TextView tvWatchMore;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    List<View> views = new ArrayList<>();
    List<OrderinfoBean.OrderDetailBean> orderDetailBeans = new ArrayList<>();
    boolean isOpen = false;//展开商品标记


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        StatusBarUtil.setTranslucentForImageView(OrdertrackingActivity.this, 0, mIvBgView);
        ButterKnife.bind(this);
        views.add(mLlOrderStatus);
        views.add(mSvOrderDetail);
        hideOrShowView(views, true);
        orderDetails();
    }

    /**
     * 订单详情接口
     */
    public void orderDetails() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(OrdersService.class).orderInfo(getDataInt())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OrderinfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<OrderinfoBean> t) throws Exception {
                initView(t.getData());
                orderDetailBeans = t.getData().getOrder_detail();
                handleCategoryDataInit(t.getData().getOrder_detail(), 0);
                hideOrShowView(views, false);
            }
        });
//        RetrofitApiManager.createUpload(OrdersService.class).orderState(5, getDataInt())
//                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OrderStateBean>(true) {
//            @Override
//            protected void onApiComplete() {
//                setLoadingMessageIndicator(false);
//            }
//
//            @Override
//            protected void onSuccees(BaseEntity<OrderStateBean> t) throws Exception {
//                // TODO: 2019-06-19 待写设置文本
//            }
//        });

    }

    String[] mStatusList = {"订单已取消", "等待支付", "等待商家接单", "商家已接单", "骑手正赶往商家", "骑手正在送货", "订单已完成"};
    String[] mOrderAnnouncementList = {"您的订单已申请取消", "由商家提供配送服务", "由商家提供配送服务", "由商家提供配送服务", "由邻邻發骑手配送", "由邻邻發骑手配送", "感谢您对邻邻發的信任，期待再次光临"};
    String[] mButtonList = {"逛逛别家", "取消订单", "取消订单", "取消订单", "申请售后", "申请售后", "申请售后"};


    public void setOrderInfo(int color, boolean tips, String orderState, String tipMsg) {
        mIvBgView.setBackgroundResource(R.color.category_color);
        rlRefund.setVerticalGravity(View.VISIBLE);
        tvTextStatus.setText("订单已取消");
        //测试tvOrderAnnouncement.setText("您的订单已申请取消");
        tvButtonOne.setText("逛逛别家");
        llRing.setVerticalGravity(View.GONE);
        tvButtonTwo.setVisibility(View.GONE);
        tvButtonThree.setVisibility(View.GONE);
        //测试llService.setVerticalGravity(View.GONE);
        ivStorePhone.setVisibility(View.GONE);
    }

    public void initView(OrderinfoBean orderinfoBean) {
        tvStoreName.setText(orderinfoBean.getStore_name());
        tvPackage.setText(orderinfoBean.getPeisong());
        tvFullReduction.setText(orderinfoBean.getManjian());
        tvVoucher.setText(orderinfoBean.getDaijinquan());
        // TODO: 2019-06-18 跳转信息缺失
        tvStoreName.setOnClickListener(v -> goToActivity(ShopHomeActivity.class, "2"/*,storelist_data.get(position).getStore_id()+""*/));
        ivStorePhone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + orderinfoBean.getStore_phone() + "");
            intent.setData(data);
            startActivity(intent);
        });

        try {
            double price = Double.parseDouble(orderinfoBean.getManjian()) + Double.parseDouble(orderinfoBean.getDaijinquan());
            //已优惠
            tvOfferMoney.setText(price + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvPaymentMoney.setText(orderinfoBean.getTotal());//实付金额
        String sex = orderinfoBean.getPeisong_info().getSex() == 1 ? "先生" : "女士";
        tvAnnouncementOne.setText(orderinfoBean.getPeisong_info().getUsername() + " (" + sex + ") " + orderinfoBean.getPeisong_info().getMobile());
        tvAnnouncementTwo.setText(orderinfoBean.getPeisong_info().getAddress());//配送地址
        tvOrderNumber.setText(orderinfoBean.getOrder_info().getOrder_sn() + "");//订单号码
        tvOrderTime.setText(orderinfoBean.getOrder_info().getAdd_time());//下单时间
        tvPaymentMethod.setText(orderinfoBean.getOrder_info().getPayment_code());//支付方式

        switch (orderinfoBean.getOrder_state()) {
            case 1:// 1:"订单已取消";
                mIvBgView.setBackgroundResource(R.color.category_color);
                rlRefund.setVerticalGravity(View.VISIBLE);
                tvTextStatus.setText("订单已取消");
                //测试tvOrderAnnouncement.setText("您的订单已申请取消");
                tvButtonOne.setText("逛逛别家");
                llRing.setVerticalGravity(View.GONE);
                tvButtonTwo.setVisibility(View.GONE);
                tvButtonThree.setVisibility(View.GONE);
                //测试llService.setVerticalGravity(View.GONE);
                tvButtonThree.setOnClickListener(v -> goToActivity(MainActivity.class));
                break;
            case 2:// 2:"待支付";
                mIvBgView.setBackgroundResource(R.drawable.order_bnt_daizhfiu);
                tvTextStatus.setText("等待支付");
                //测试tvOrderAnnouncement.setText("由商家提供配送服务");

                String[] stringArray = new String[]{"预计", "12:30", "送到", "\n由邻邻發骑手配送"};
                int[] styleArray = new int[]{R.style.spannable_bold_big_black,
                        R.style.spannable_bold_big_yellow,
                        R.style.spannable_bold_big_black,
                        R.style.spannable_default};
                tvOrderAnnouncementTime.setText(SpannableTextUtil.setTextContactStyle(stringArray, styleArray));
                //----
                tvNotice.setText("超过15分钟未支付，订单将自动取消咯");
                tvButtonOne.setText("取消订单");
                tvButtonTwo.setVisibility(View.GONE);
                tvButtonThree.setText("立即支付");
                tvButtonThree.setOnClickListener(v -> goToActivity(ConfirmOrderActivity.class, getDataInt()));
                break;
            case 3:// 3:"等待商家接单";
                mIvBgView.setBackgroundResource(R.drawable.order_bnt_jiedan);
                tvTextStatus.setText("等待商家接单");
                tvNotice.setText("5分钟内商家未接单，将自动取消订单");
                //测试tvOrderAnnouncement.setText("由商家提供配送服务");
                tvButtonOne.setText("取消订单");
                tvButtonTwo.setVisibility(View.GONE);
                tvButtonThree.setVisibility(View.GONE);
                break;
            case 4:// 4:"商家已接单，正准备商品";
                mIvBgView.setBackgroundResource(R.drawable.order_bnt_yjiedan);
                tvTextStatus.setText("商家已接单");
                tvNotice.setText("商家正在准备商品，请耐心等待");
                //测试tvOrderAnnouncement.setText("由商家提供配送服务");
                tvButtonOne.setText("取消订单");
                tvButtonTwo.setText("催单");
                tvButtonThree.setVisibility(View.GONE);
                break;
            case 5:// 5:"骑手正赶往商家";
                mIvBgView.setBackgroundResource(R.drawable.order_bnt_ganwsj);
                tvTextStatus.setText("骑手正赶往商家");
                llRing.setVisibility(View.GONE);
                //测试tvOrderAnnouncement.setText("由邻邻發骑手配送");
                tvButtonOne.setText("申请售后");
                tvButtonTwo.setText("致电骑手");
                tvButtonThree.setVisibility(View.GONE);
                break;
            case 6://6:"骑手正在送货";
                mIvBgView.setBackgroundResource(R.drawable.order_bnt_songhuo);
                tvTextStatus.setText("骑手正在送货");
                llRing.setVisibility(View.GONE);
                //测试tvOrderAnnouncement.setText("由邻邻發骑手配送");

                tvButtonOne.setText("申请售后");
                tvButtonTwo.setText("致电骑手");
                tvButtonThree.setVisibility(View.GONE);
                break;
            case 7:// 7:"订单已完成";
                mIvBgView.setBackgroundResource(R.drawable.order_bnt_wancheng);
                tvTextStatus.setText("订单已完成");
                llRing.setVisibility(View.GONE);
                //测试llService.setVisibility(View.GONE);
                //测试tvOrderAnnouncement.setText("感谢您对邻邻發的信任，期待再次光临");
                tvButtonOne.setText("申请售后");
                tvButtonTwo.setText("再来一单");
                tvButtonThree.setText("立即评价");
                tvButtonThree.setOnClickListener(v -> goToActivity(EvaluationActivity.class, getDataInt()));
                break;
            case 8:// 8:退款中
                break;
            case 9://9:退款已完成
                break;
            case 10://10:"待评价";
                break;
            case 11://11:"已评价";
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.rl_store)
    public void onRlStoreClicked() {
    }

    /**
     * 添加布局中商家的商品
     *
     * @param gcsort_data 商品数据
     * @param isOpen      是否展开状态 0->未展开 1->展开
     */
    public void handleCategoryDataInit(List<OrderinfoBean.OrderDetailBean> gcsort_data, int isOpen) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        // 定义LayoutParam
        llGoodsItem.removeAllViews();
        if (gcsort_data != null && gcsort_data.size() > 0) {
            if (gcsort_data.size() > 2) {
                llExpand.setVisibility(View.VISIBLE);
            } else {
                llExpand.setVisibility(View.GONE);
            }
            for (int i = 0; i < (isOpen == 0 || gcsort_data.size() > 3 ? 2 : gcsort_data.size()); i++) {
                View categoryView = layoutInflater.inflate(R.layout.item_tracking_commodity, null);
                GlideImageView imageView = categoryView.findViewById(R.id.iv_avatar);//商品图片
                TextView product_name = categoryView.findViewById(R.id.tv_product_name);//商品名称
                TextView now_price = categoryView.findViewById(R.id.tv_now_price);//商品现价
                TextView original_price = categoryView.findViewById(R.id.tv_original_price);//商品原价
                TextView tv_number = categoryView.findViewById(R.id.tv_number);//商品数量
                imageView.load(gcsort_data.get(i).getGoods_image());
                product_name.setText(gcsort_data.get(i).getGoods_name());
                now_price.setText("￥" + gcsort_data.get(i).getGoods_price());
//                original_price.getPaint().setAntiAlias(true);
                original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);//中划线
                original_price.setText("￥" + gcsort_data.get(i).getGoods_marketprice());
                tv_number.setText("x " + gcsort_data.get(i).getGoods_num() + "");
                llGoodsItem.addView(categoryView);
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_expand})
    public void onIvBackClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_expand:
                handleCategoryDataInit(orderDetailBeans, isOpen ? 1 : 0);
                if (isOpen){
                    tvWatchMore.setText("收起更多");
                    ivArrow.setImageResource(R.drawable.common_xiala1);
                } else {
                    tvWatchMore.setText("查看更多");
                    ivArrow.setImageResource(R.drawable.common_xiala);
                }
                isOpen = !isOpen;
                break;

        }
    }

    @OnClick(R.id.tv_copy)
    public void onTvCopyClicked() {
        // 从API11开始android推荐使用android.content.ClipboardManager
// 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 将文本内容放到系统剪贴板里。
        cm.setText(tvOrderNumber.getText());
        Toast.makeText(this, "复制成功!", Toast.LENGTH_LONG).show();
    }


}
