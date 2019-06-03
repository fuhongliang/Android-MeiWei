package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;
import com.jaeger.library.StatusBarUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
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
    SmartTabLayout mViewpagertab;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share_it)
    ImageView mIvShareIt;
    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_merchant_describe)
    TextView mTvMerchantDescribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        StatusBarUtil.setTranslucentForImageView(ShopHomeActivity.this, 0, mIvBg);
        ButterKnife.bind(this);
        initView();
        getShopDetailData();
    }

    public void initView() {
        mViewpager.setOffscreenPageLimit(4);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("菜单", MenuListFragment.class)
                .add("评价", XWebViewFragment.class)
                .add("商家", XWebViewFragment.class)
                .create());
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
            protected void onSuccees(BaseEntity<MerchantBean> t) throws Exception {
                initMerchantInfo(t.getData().getStore_info());
            }
        });
    }

    public void initMerchantInfo(MerchantBean.StoreInfoBean storeInfoBean){
        tvStoreName.setText(storeInfoBean.getStore_name());
        mTvMerchantDescribe.setText("起送￥15｜配送￥3｜月售 " + storeInfoBean.getStore_sales());
        tvEvaluation.setText(storeInfoBean.getStore_credit()+"评分");
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
}