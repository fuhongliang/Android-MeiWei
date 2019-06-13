package com.ifhu.meiwei.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.ui.activity.home.ShoppingCartActivity;
import com.ifhu.meiwei.adapter.BaseHeaderAdapter;
import com.ifhu.meiwei.adapter.CategoryAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MerchantBean;
import com.ifhu.meiwei.bean.PinnedHeaderEntity;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.activity.home.ConfirmOrderActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.base.WebViewActivity;
import com.ifhu.meiwei.utils.UserLogic;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author fuhongliang
 */
public class MenuListFragment extends BaseFragment {

    List<MerchantBean.GoodsListBean> mGoodsListBeans = new ArrayList<>();
    MerchantBean.CartBean mCartBean = new MerchantBean.CartBean();

    @BindView(R.id.lv_category)
    ListView mLvCategory;
    @BindView(R.id.lv_product)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    boolean isCurCategoryGoodEmpty = false;
    int findFirstVisibleItemPosition = 0;
    @BindView(R.id.iv_shop_cat)
    ImageView mIvShopCat;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_shipping_fee)
    TextView mTvShippingFee;
    @BindView(R.id.rl_price)
    RelativeLayout mRlPrice;
    @BindView(R.id.tv_at_less)
    TextView mTvAtLess;

    String mStoreId;
    @BindView(R.id.tv_car_amount)
    TextView mTvCarAmount;
    @BindView(R.id.tv_tips)
    TextView mTvTips;

    private BaseHeaderAdapter<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBean>> mAdapter;

    CategoryAdapter mCategoryAdapter;

    public static MenuListFragment newInstance() {
        return new MenuListFragment();
    }

    public MenuListFragment() {
        // Required empty public constructor
    }

    public void setData(String storeId, MerchantBean.CartBean cartBean, List<MerchantBean.GoodsListBean> goodsListBeans) {
        mGoodsListBeans = goodsListBeans;
        mCartBean = cartBean;
        mStoreId = storeId;
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menulist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    List<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBean>> data = new ArrayList<>();

    public void initData() {
        handleCarBean();
        for (MerchantBean.GoodsListBean goodsListBeans : mGoodsListBeans) {
            data.add(new PinnedHeaderEntity<>(null, BaseHeaderAdapter.TYPE_HEADER, goodsListBeans.getStc_name()));
            for (MerchantBean.GoodsListBean.GoodsBean goodsBean : goodsListBeans.getGoods()) {
                data.add(new PinnedHeaderEntity<>(goodsBean, BaseHeaderAdapter.TYPE_DATA, goodsListBeans.getStc_name()));
            }
        }
        mAdapter.notifyDataSetChanged();
        mCategoryAdapter.setmDataList(mGoodsListBeans);
    }

    public void handleCarBean() {
        if (mCartBean.getNums() > 0) {
            mIvShopCat.setBackgroundResource(R.drawable.meal_bnt_gouwuche1);
            mTvCarAmount.setText(mCartBean.getNums() + "");
            mRlPrice.setVisibility(View.VISIBLE);
            mTvTotalPrice.setText("￥" + mCartBean.getAmount());
            mTvTips.setVisibility(View.GONE);
            mTvShippingFee.setText("另需配送费 ￥" + "5");
            mTvAtLess.setSelected(true);
            mTvAtLess.setText("选好了");
        } else {
            mIvShopCat.setBackgroundResource(R.drawable.meal_bnt_gouwuche);
            mTvTips.setVisibility(View.VISIBLE);
            mRlPrice.setVisibility(View.INVISIBLE);
            mTvAtLess.setSelected(false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        mCategoryAdapter = new CategoryAdapter(mGoodsListBeans, getActivity(), position -> {
            int totalItem = 0;
            for (int i = 0; i < position; i++) {
                totalItem = totalItem + mGoodsListBeans.get(i).getGoods().size() + 1;
            }
            if (mGoodsListBeans.get(position).getGoods().size() == 0) {
                isCurCategoryGoodEmpty = true;
            } else {
                isCurCategoryGoodEmpty = false;
            }

            LinearSmoothScroller linearSmoothScroller = new TopSmoothScroller(getActivity());
            linearSmoothScroller.setTargetPosition(totalItem);
            mRecyclerView.getLayoutManager().startSmoothScroll(linearSmoothScroller);
            mCategoryAdapter.notifyDataSetChanged();
        });
        mLvCategory.setAdapter(mCategoryAdapter);
        mTvAtLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(ConfirmOrderActivity.class, mStoreId);
            }
        });
        mIvShopCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(ShoppingCartActivity.class);
            }
        });
    }

    public void addToShopingCar(int goods_id, int amount) {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(HomeService.class).addCart(mStoreId, UserLogic.getUserId(), mStoreId, goods_id, amount)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<MerchantBean.CartBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<MerchantBean.CartBean> t) throws Exception {
                mCartBean = t.getData();
                handleCarBean();
            }
        });
    }

    public void initRecyclerView() {
        mAdapter = new BaseHeaderAdapter<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBean>>(data) {

            @Override
            protected void addItemTypes() {
                addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.item_pinned_header);
                addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.item_product_name);
            }

            @Override
            protected void convert(BaseViewHolder holder, final PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBean> item) {
                switch (holder.getItemViewType()) {
                    case BaseHeaderAdapter.TYPE_HEADER:
                        holder.setText(R.id.tv_pinned_header, item.getPinnedHeaderName());
                        break;
                    case BaseHeaderAdapter.TYPE_DATA:
                        holder.setText(R.id.tv_commodity_name, item.getData().getGoods_name() + "");
                        Glide.with(getActivity()).load(item.getData().getImg_name()).into((ImageView) holder.getView(R.id.iv_image));
                        holder.getView(R.id.iv_delete).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.tv_amount).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.ll_goods_description).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WebViewActivity.start(true, getActivity(), item.getData().getGoods_detail_url(), "商品详情");
                            }
                        });
                        //暂时处理
                        holder.getView(R.id.iv_add).setOnClickListener(v -> {
                            addToShopingCar(item.getData().getGoods_id(), 1);
                            holder.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
                            holder.getView(R.id.tv_amount).setVisibility(View.VISIBLE);
                            ((TextView) holder.getView(R.id.tv_amount)).setText("1");
                        });


                        break;
                    default:
                        break;
                }
            }
        };

//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
//                switch (mAdapter.getItemViewType(i)) {
//                    case BaseHeaderAdapter.TYPE_DATA:
//                        break;
//                    case BaseHeaderAdapter.TYPE_HEADER:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration.Builder(BaseHeaderAdapter.TYPE_HEADER)
                .setDividerId(R.drawable.divider).enableDivider(false).create());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof LinearLayoutManager) {
                    findFirstVisibleItemPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    handleGoodListScroll(findFirstVisibleItemPosition);
                }
            }
        });

    }

    public void handleGoodListScroll(int position) {
        if (!isCurCategoryGoodEmpty) {
            int mPosition = 0;
            int mTotalGoods = 0;
            for (MerchantBean.GoodsListBean goodsListBean : mGoodsListBeans) {
                mTotalGoods = mTotalGoods + goodsListBean.getGoods().size();
                if (position > mTotalGoods) {
                    mPosition = mPosition + 1;
                } else {
                    mCategoryAdapter.setCurPosition(mPosition);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }
    }

}
