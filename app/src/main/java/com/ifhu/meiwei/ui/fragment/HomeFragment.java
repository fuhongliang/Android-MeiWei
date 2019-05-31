package com.ifhu.meiwei.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.baba.transformation.RadiusTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ifhu.meiwei.MyApplication;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.HomeStoreAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.HomeBean;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.activity.home.MainActivity;
import com.ifhu.meiwei.ui.activity.home.ShippingAddressActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.view.ExpandListView;
import com.ifhu.meiwei.ui.view.MyScrollView;
import com.ifhu.meiwei.utils.AppInfo;
import com.ifhu.meiwei.utils.Constants;
import com.ifhu.meiwei.utils.DeviceUtil;
import com.ifhu.meiwei.utils.GlideRoundTransform;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ifhu.meiwei.utils.Constants.LOCATION_DATAUPDATA;

/**
 * 首页模块
 *
 * @author fuhongliang
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mLayoutSwipeRefresh;
    @BindView(R.id.listview_store)
    ExpandListView mListviewStore;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.iv_indicator_total)
    ImageView mIvIndicatorTotal;
    @BindView(R.id.iv_indicator_hot_sell)
    ImageView mIvIndicatorHotSell;
    @BindView(R.id.iv_indicator_near)
    ImageView mIvIndicatorNear;
    @BindView(R.id.iv_indicator_best)
    ImageView mIvIndicatorBest;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.tv_hot_sell)
    TextView mTvHotSell;
    @BindView(R.id.tv_near)
    TextView mTvNear;
    @BindView(R.id.tv_best)
    TextView mTvBest;
    @BindView(R.id.iv_discount_background)
    GlideImageView mIvDiscountBackground;
    @BindView(R.id.iv_full_cut_background)
    GlideImageView mIvFullCutBackground;
    @BindView(R.id.iv_package_background)
    GlideImageView mIvPackageBackground;
    @BindView(R.id.tv_discounts_title)
    TextView mTvDiscountsTitle;
    @BindView(R.id.tv_discounts_subtitle)
    TextView mTvDiscountsSubtitle;
    @BindView(R.id.tv_reduction_title)
    TextView mTvReductionTitle;
    @BindView(R.id.tv_reduction_subtitle)
    TextView mTvReductionSubtitle;
    @BindView(R.id.tv_package_title)
    TextView mTvPackageTitle;
    @BindView(R.id.tv_package_subtitle)
    TextView mTvPackageSubtitle;
    @BindView(R.id.ll_discounts_zone)
    LinearLayout mLlDiscountsZone;
    @BindView(R.id.rl_discount)
    RelativeLayout mRlDiscount;
    @BindView(R.id.rl_full_cut)
    RelativeLayout mRlFullCut;
    @BindView(R.id.rl_package)
    RelativeLayout mRlPackage;
    @BindView(R.id.ll_category_one)
    LinearLayout mLlCategoryOne;
    @BindView(R.id.ll_category_two)
    LinearLayout mLlCategoryTwo;
    @BindView(R.id.xbanner)
    XBanner mXbanner;
    private int lastY = 0;
    private int touchEventId = -9983761;
    boolean isOut = false;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder;
    @BindView(R.id.scrollView)
    MyScrollView mScrollView;
    HomeStoreAdapter mHomeStoreAdapter;
    private List<HomeBean.StorelistDataBean> storelist_data = new ArrayList<>();
    private List<HomeBean.BannerDataBean> banner_data = new ArrayList<>();
    /**
     * 每行分类显示四个
     */
    final int oneLineCategoryNumber = 4;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    String mLongitude = "113.913761";
    String mLatitude = "22.572242";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBanner();
        mScrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
            }
            return false;
        });
        EventBus.getDefault().register(this);
        setRefreshLayout();
        mHomeStoreAdapter = new HomeStoreAdapter(getActivity(), storelist_data);
        mListviewStore.setAdapter(mHomeStoreAdapter);
        mTvTotal.setSelected(true);
    }

    public void initBanner(){
        HomeBean.BannerDataBean bannerDataBean = new HomeBean.BannerDataBean();
        bannerDataBean.getLink_url();
        banner_data.add(bannerDataBean);
        banner_data.add(bannerDataBean);
        //加载广告图片
        mXbanner.loadImage((banner, model, view, position) -> {
//            HomeBean.BannerDataBean listBean = ((HomeBean.BannerDataBean) model);
//            listBean = ((HomeBean.BannerDataBean) model);
//           Glide.with(getActivity()).load(listBean.getLink_url()).into((ImageView) view);
            loadRoundImage((ImageView) view,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559290240259&di=bcaad77d72bd065357b08a9c8b1ee7c2&imgtype=0&src=http%3A%2F%2Fpic37.nipic.com%2F20140113%2F8800276_184927469000_2.png");
        });
    }

    public static void loadRoundImage(ImageView view, String url) {
        RequestOptions myOptions = new RequestOptions().optionalTransform
                (new GlideRoundTransform(DeviceUtil.dip2px(6f)
                        , GlideRoundTransform.CornerType.ALL));
        Glide.with(view.getContext()).load(url).apply(myOptions).into(view);
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        mLayoutSwipeRefresh.setColorSchemeColors(R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);

        mLayoutSwipeRefresh.setOnRefreshListener(() -> {
            getData("", "1");
        });
    }


    public void getData(String keyWord, String type) {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(HomeService.class).keyword(keyWord, 1, mLongitude, mLatitude, type)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<HomeBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
                mLayoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<HomeBean> t) throws Exception {
                banner_data = t.getData().getBanner_data();
                storelist_data = t.getData().getStorelist_data();
                mHomeStoreAdapter.setStorelist_data(storelist_data);
                handleEmpty(mHomeStoreAdapter.getCount() == 0);
                handleCategoryDataInit(t.getData().getGcsort_data());
                handleDiscountDataInit(t.getData().getDiscount_data());
                mXbanner.setAutoPlayAble(banner_data.size() > 1);
                mXbanner.setBannerData(banner_data);
            }
        });
    }


    /**
     * 处理首页请求回来分类数据显示
     *
     * @param gcsort_data 分类数据
     */
    public void handleCategoryDataInit(List<HomeBean.GcsortDataBean> gcsort_data) {
        mLlCategoryOne.removeAllViews();
        mLlCategoryTwo.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // 定义LayoutParam
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppInfo.width / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (gcsort_data != null && gcsort_data.size() > 0) {
            for (int i = 0; i < gcsort_data.size(); i++) {
                View categoryView = layoutInflater.inflate(R.layout.item_home_category, null);
                GlideImageView imageView = categoryView.findViewById(R.id.iv_category);
                TextView name = categoryView.findViewById(R.id.tv_category_name);
                name.setText(gcsort_data.get(i).getGc_name());
                imageView.load(Constants.IMGPATH + gcsort_data.get(i).getIcon_image());
                if (i < 9) {
                    if (i < oneLineCategoryNumber) {
                        mLlCategoryOne.addView(categoryView, params);
                    } else {
                        mLlCategoryTwo.addView(categoryView, params);
                    }
                }
            }

            /**
             * 大于四个分类显示两行
             */
            if (gcsort_data.size() <= oneLineCategoryNumber) {
                mLlCategoryTwo.setVisibility(View.GONE);
            }

        } else {
            mLlCategoryOne.setVisibility(View.GONE);
            mLlCategoryTwo.setVisibility(View.GONE);
        }

    }

    /**
     * @param discount_data
     */
    public void handleDiscountDataInit(List<HomeBean.DiscountDataBean> discount_data) {

        if (discount_data != null) {
            switch (discount_data.size()) {
                case 0:
                    mLlDiscountsZone.setVisibility(View.GONE);
                    mRlDiscount.setVisibility(View.GONE);
                    mRlFullCut.setVisibility(View.GONE);
                    mRlPackage.setVisibility(View.GONE);
                    break;
                case 1:
                    mLlDiscountsZone.setVisibility(View.VISIBLE);
                    mRlDiscount.setVisibility(View.VISIBLE);
                    mRlFullCut.setVisibility(View.GONE);
                    mRlPackage.setVisibility(View.GONE);
                    mIvDiscountBackground.load(discount_data.get(0).getBackground_image());
                    mTvDiscountsTitle.setText(discount_data.get(0).getTitle());
                    mTvDiscountsSubtitle.setText(discount_data.get(0).getBrief());
                    break;
                case 2:
                    mLlDiscountsZone.setVisibility(View.VISIBLE);
                    mRlDiscount.setVisibility(View.VISIBLE);
                    mRlFullCut.setVisibility(View.VISIBLE);
                    mRlPackage.setVisibility(View.GONE);
                    mIvDiscountBackground.load(discount_data.get(0).getBackground_image());
                    mIvFullCutBackground.load(discount_data.get(1).getBackground_image());

                    mTvDiscountsTitle.setText(discount_data.get(0).getTitle());
                    mTvDiscountsSubtitle.setText(discount_data.get(0).getBrief());
                    mTvReductionTitle.setText(discount_data.get(1).getTitle());
                    mTvReductionSubtitle.setText(discount_data.get(1).getBrief());
                    break;
                case 3:
                    mLlDiscountsZone.setVisibility(View.VISIBLE);
                    mRlDiscount.setVisibility(View.VISIBLE);
                    mRlFullCut.setVisibility(View.VISIBLE);
                    mRlPackage.setVisibility(View.VISIBLE);
                    mIvDiscountBackground.load(discount_data.get(0).getBackground_image());
                    mIvFullCutBackground.load(discount_data.get(1).getBackground_image());
                    mIvPackageBackground.load(discount_data.get(2).getBackground_image());

                    mTvDiscountsTitle.setText(discount_data.get(0).getTitle());
                    mTvDiscountsSubtitle.setText(discount_data.get(0).getBrief());
                    mTvReductionTitle.setText(discount_data.get(1).getTitle());
                    mTvReductionSubtitle.setText(discount_data.get(1).getBrief());
                    mTvPackageTitle.setText(discount_data.get(2).getTitle());
                    mTvPackageSubtitle.setText(discount_data.get(2).getBrief());
                    break;
                default:
                    break;
            }

        }
    }

    public void handleEmpty(boolean isEmpty) {
        mLlEmpty.setVisibility(isEmpty ? View.GONE : View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case LOCATION_DATAUPDATA:
                mTvAddress.setText(messageEvent.getArrayList().get(0));
                try {
                    mLongitude = messageEvent.getArrayList().get(1);
                    mLatitude = messageEvent.getArrayList().get(2);
                    getData("", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View scroller = (View) msg.obj;
            if (msg.what == touchEventId) {
                if (lastY == scroller.getScrollY()) {
                    if (isOut) {
                        animateIn(mFab);
                    }
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 1000);
                    lastY = scroller.getScrollY();
                    if (!isOut) {
                        animateOut(mFab);
                    }
                }
            }
        }
    };


    private void animateOut(FloatingActionButton fab) {
        isOut = true;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        int rightMargin = layoutParams.rightMargin;
        fab.animate().translationX(fab.getWidth() / 2 + rightMargin).setInterpolator(new LinearInterpolator()).start();
    }

    private void animateIn(FloatingActionButton fab) {
        isOut = false;
        fab.animate().translationX(0).setInterpolator(new LinearInterpolator()).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.tv_address)
    public void onMTvAddressClicked() {
        goToActivity(ShippingAddressActivity.class, mTvAddress.getText().toString());
    }

    @OnClick(R.id.iv_notice)
    public void onMIvNoticeClicked() {

    }


    @OnClick({R.id.ll_total, R.id.ll_hot_sell, R.id.ll_near, R.id.ll_best})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_total:
                handleClickType(true, false, false, false, 1);
                break;
            case R.id.ll_hot_sell:
                handleClickType(false, true, false, false, 2);
                break;
            case R.id.ll_near:
                handleClickType(false, false, true, false, 3);
                break;
            case R.id.ll_best:
                handleClickType(false, false, false, true, 4);
                break;
            default:
                break;
        }
    }

    public void handleClickType(boolean total, boolean hotSell, boolean near, boolean best, int type) {
        mIvIndicatorTotal.setVisibility(total ? View.VISIBLE : View.INVISIBLE);
        mIvIndicatorHotSell.setVisibility(hotSell ? View.VISIBLE : View.INVISIBLE);
        mIvIndicatorNear.setVisibility(near ? View.VISIBLE : View.INVISIBLE);
        mIvIndicatorBest.setVisibility(best ? View.VISIBLE : View.INVISIBLE);

        mTvTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, total ? 16 : 14);
        mTvHotSell.setTextSize(TypedValue.COMPLEX_UNIT_SP, hotSell ? 16 : 14);
        mTvNear.setTextSize(TypedValue.COMPLEX_UNIT_SP, near ? 16 : 14);
        mTvBest.setTextSize(TypedValue.COMPLEX_UNIT_SP, best ? 16 : 14);

        mTvTotal.setSelected(total);
        mTvHotSell.setSelected(hotSell);
        mTvNear.setSelected(near);
        mTvBest.setSelected(best);

        getData("", type + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        mXbanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mXbanner.stopAutoPlay();
    }
}
