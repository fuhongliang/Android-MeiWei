package com.ifhu.meiwei.ui.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MerchantBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.ui.fragment.MenuListFragment;
import com.ifhu.meiwei.ui.fragment.XWebViewFragment;
import com.ifhu.meiwei.ui.nicedialog.ConfirmInputDialog;
import com.ifhu.meiwei.ui.nicedialog.DialogUtils;
import com.ifhu.meiwei.ui.view.MySmartTabLayout;
import com.ifhu.meiwei.ui.view.MyViewPager;
import com.ifhu.meiwei.utils.FullReductionUtils;
import com.ifhu.meiwei.utils.StringUtils;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;
import com.jaeger.library.StatusBarUtil;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 商家主页面
 * @author fuhongliang
 */
public class ShopHomeActivity extends BaseActivity {

    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_evaluation)
    TextView tvEvaluation;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_voucher)
    LinearLayout llVoucher;
    @BindView(R.id.rl_expand)
    RelativeLayout rlExpand;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.viewpagertab)
    MySmartTabLayout mViewpagertab;
    @BindView(R.id.viewpager)
    MyViewPager mViewpager;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share_it)
    ImageView mIvShareIt;
    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    @BindView(R.id.iv_logo)
    GlideImageView mIvLogo;
    @BindView(R.id.tv_merchant_describe)
    TextView mTvMerchantDescribe;
    @BindView(R.id.ll_full_cut)
    LinearLayout mLlFullCut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        StatusBarUtil.setTranslucentForImageView(ShopHomeActivity.this, 0, mIvBg);
        ButterKnife.bind(this);
        initView();
        getShopDetailData();
    }

    FragmentPagerItemAdapter adapter;
    public void initView() {
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("菜单", MenuListFragment.class)
                .add("评价", XWebViewFragment.class)
                .add("商家", XWebViewFragment.class)
                .create());
        mViewpager.setOffscreenPageLimit(4);
        mViewpager.setAdapter(adapter);
        mViewpagertab.setViewPager(mViewpager);
    }

    public void getShopDetailData() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(HomeService.class).storeInfo(getDATA(), UserLogic.getUserId() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<MerchantBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<MerchantBean> t) {
                initMerchantInfo(t.getData().getStore_info(), t.getData().getManjian());
                MenuListFragment menuListFragment = (MenuListFragment) adapter.getPage(0);
                XWebViewFragment reviewFragment = (XWebViewFragment) adapter.getPage(1);
                XWebViewFragment merchantInfoFragment = (XWebViewFragment) adapter.getPage(2);
                menuListFragment.setData(getDATA(), t.getData().getCart(), t.getData().getGoods_list());
                reviewFragment.setUrl(t.getData().getComment_url());
                merchantInfoFragment.setUrl(t.getData().getStore_info_url());
            }
        });
    }

    public void initMerchantInfo(MerchantBean.StoreInfoBean storeInfoBean, List<MerchantBean.ManjianBean> manjianBeanList) {
        tvStoreName.setText(storeInfoBean.getStore_name());
        mTvMerchantDescribe.setText("起送￥15｜配送￥3｜月售 " + storeInfoBean.getStore_sales());
        tvEvaluation.setText(storeInfoBean.getStore_credit() + "\n评分");
        mLlFullCut.setVisibility(View.VISIBLE);
        FullReductionUtils.showShopFullReduction(mLlFullCut, manjianBeanList, LayoutInflater.from(this));
        mIvLogo.load(storeInfoBean.getStore_avatar(), R.drawable.home_zanwutupian, 2);
        //代金券字段没返回来
//        if (storeInfoBean.get() > 0) {
//            llVoucher.setVisibility(View.VISIBLE);
//            tvMoney.setText("￥" + storeInfoBean.getDaijinquan());
//            llVoucher.setOnClickListener(v -> {
//                ToastHelper.makeText("您点击了代金券");
//            });
//        } else {
//            llVoucher.setVisibility(View.GONE);
//        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    /**
     * 领取代金券接口
     */
    public void getVoucher() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(HomeService.class).getVoucher(getDataInt(), UserLogic.getUser().getMember_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    @OnClick(R.id.ll_voucher)
    public void onLlVoucherClicked() {
    }

    @OnClick(R.id.rl_expand)
    public void onRlExpandClicked() {
    }

    @OnClick(R.id.iv_expand_info)
    public void onMIvExpandInfoClicked() {
        View view = getLayoutInflater().inflate(R.layout.dialog_menu_expand, null);
        DialogUtils.showExpandMerchantInfoDialog("","", getSupportFragmentManager(), discount_price -> {

        });
    }

}
