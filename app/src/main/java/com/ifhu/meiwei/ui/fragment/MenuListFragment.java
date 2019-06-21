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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.BaseHeaderAdapter;
import com.ifhu.meiwei.adapter.CategoryAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MerchantBean;
import com.ifhu.meiwei.bean.PinnedHeaderEntity;
import com.ifhu.meiwei.bean.ShoppingCartBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.activity.home.ShoppingCartActivity;
import com.ifhu.meiwei.ui.activity.login.LoginActivity;
import com.ifhu.meiwei.ui.activity.order.ConfirmOrderActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.base.WebViewActivity;
import com.ifhu.meiwei.utils.LocalShopHelper;
import com.ifhu.meiwei.utils.ToastHelper;
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
    @BindView(R.id.iv_shop_cart_empty)
    ImageView mIvShopCartEmpty;

    @BindView(R.id.rl_empty)
    RelativeLayout mRlEmpty;
    @BindView(R.id.ll_category_product)
    LinearLayout mLlCateGoryProduct;
    @BindView(R.id.fl_shop_bar)
    FrameLayout mFlShopBar;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.tv_title_one)
    TextView mTvTitleOne;

    private BaseHeaderAdapter<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBeanX>> mAdapter;

    CategoryAdapter mCategoryAdapter;

    public static MenuListFragment newInstance() {
        return new MenuListFragment();
    }

    public MenuListFragment() {
        // Required empty public constructor
    }

    public void setEmptyDisplay(boolean emptyDisplay){
        if(emptyDisplay){
            mLlCateGoryProduct.setVisibility(View.INVISIBLE);
            mFlShopBar.setVisibility(View.INVISIBLE);
            mRlEmpty.setVisibility(View.VISIBLE);
        } else {
            mLlCateGoryProduct.setVisibility(View.VISIBLE);
            mFlShopBar.setVisibility(View.VISIBLE);
            mRlEmpty.setVisibility(View.INVISIBLE);
        }
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

    List<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBeanX>> data = new ArrayList<>();

    public void initData() {
        handleCarBean();
        for (MerchantBean.GoodsListBean goodsListBeans : mGoodsListBeans) {
            data.add(new PinnedHeaderEntity<>(null, BaseHeaderAdapter.TYPE_HEADER, goodsListBeans.getStc_name()));
            for (MerchantBean.GoodsListBean.GoodsBeanX goodsBean : goodsListBeans.getGoods()) {
                data.add(new PinnedHeaderEntity<>(goodsBean, BaseHeaderAdapter.TYPE_DATA, goodsListBeans.getStc_name()));
            }
        }
        mAdapter.notifyDataSetChanged();
        mCategoryAdapter.setmDataList(mGoodsListBeans);
    }

    /**
     * 处理购物车的图片和价格数量
     */
    public void handleCarBean() {

        int mCarAmount = 0;
        double mTotalPrice = 0;
        for (MerchantBean.CartBean.GoodsBean goods : mCartBean.getGoods()) {
            //外层循环判断已经选中的商品数量
            mCarAmount = mCarAmount + goods.getGoods_num();
            //把返回的购物车的总价格加起来
            mTotalPrice = mTotalPrice + goods.getGoods_num() * Double.parseDouble(goods.getGoods_price());
        }

        ShoppingCartBean localCartBean = LocalShopHelper.getShoppingCartBean(mStoreId);

        // TODO: 2019-06-21 未写批量上传购物车接口
        // 暂时本地添加商品和价格
        for (int i = 0; localCartBean!=null && i < localCartBean.getList().size(); i++) {
            ShoppingCartBean.ListBean localListBean = localCartBean.getList().get(i);
            mTotalPrice += localListBean.getGoods_num()*Double.valueOf(localListBean.getGoods_price());
            mCarAmount += localListBean.getGoods_num();
        }

        if (UserLogic.isLogin()) {
            //当前已登录并且把本地购物车提交到后台后就删除本地缓存
            LocalShopHelper.clearLoaclShopCart(mStoreId);
        }
        if (mCarAmount > 0) {
            mIvShopCat.setVisibility(View.VISIBLE);
            mIvShopCartEmpty.setVisibility(View.INVISIBLE);
            mTvCarAmount.setVisibility(View.VISIBLE);
            mTvCarAmount.setText(mCarAmount + "");
            mRlPrice.setVisibility(View.VISIBLE);
            mTvTotalPrice.setText("￥" + mTotalPrice);
            mTvTips.setVisibility(View.GONE);
            mTvShippingFee.setText("另需配送费 ￥" + mCartBean.getPeisong());
            mTvAtLess.setSelected(true);
            mTvAtLess.setEnabled(true);
            mTvAtLess.setText("选好了");
        } else {
            mIvShopCat.setVisibility(View.INVISIBLE);
            mTvCarAmount.setVisibility(View.INVISIBLE);
            mIvShopCartEmpty.setVisibility(View.VISIBLE);
            mTvTips.setVisibility(View.VISIBLE);
            mRlPrice.setVisibility(View.INVISIBLE);
            mTvAtLess.setSelected(false);
            mTvAtLess.setEnabled(false);
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
            if (isVisBottom() && position==mGoodsListBeans.size()-1) {
                mCategoryAdapter.notifyDataSetChanged();//如果商品列表滑动到底部，点击事件由自己来刷新
            } else {
                LinearSmoothScroller linearSmoothScroller = new TopSmoothScroller(getActivity());
                linearSmoothScroller.setTargetPosition(totalItem);
                mRecyclerView.getLayoutManager().startSmoothScroll(linearSmoothScroller);
            }
        });
        mLvCategory.setAdapter(mCategoryAdapter);
        mTvAtLess.setOnClickListener(v -> {
            if (!UserLogic.isLogin()){
                //如果未登录，就跳转到登录页面
                ToastHelper.makeText("未登录！");
                goToActivity(LoginActivity.class);
            } else {
                goToActivity(ConfirmOrderActivity.class, mStoreId);
            }
        });
        mIvShopCat.setOnClickListener(v -> goToActivity(ShoppingCartActivity.class));

    }

    /**
     * 判断列表是否滑动到底部
     * 可以提取成工具 参数添加一个RecyclerView
     * @return
     */
    public boolean isVisBottom(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = mRecyclerView.getScrollState();
        return visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == RecyclerView.SCROLL_STATE_IDLE;
    }

    /**
     * 添加购物车商品
     *
     * @param goods_id 商品ID
     * @param amount 添加数量
     */
    public void addToShopingCar(int goods_id,String goodsPrice, int amount) {
        if (!UserLogic.isLogin()){
            // TODO: 2019-06-21 批量添加购物车商品
            if (checkGoodsInLocalShop(goods_id) == -2){
                addShopToLocal(goods_id,goodsPrice,amount);
            } else if (checkGoodsInLocalShop(goods_id) == -1){
                addGoodsInLocalShop(goods_id,goodsPrice);
            } else {
                updateGoodsInLocalShop(goods_id,amount,checkGoodsInLocalShop(goods_id));
            }
            handleCarBean();
            mAdapter.notifyDataSetChanged();
        }else {
            handleAddToShoppingCart(goods_id,amount);
        }
    }

    /**
     * 添加商品到购物车
     * @param goods_id 商品ID
     * @param amount 数量
     */
    public void handleAddToShoppingCart(int goods_id, int amount){
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
                mAdapter.notifyDataSetChanged();
            }
        });
    }



    /**
     * 更新购物车中的商品
     * @param goods_id 商品ID
     * @param currentCount 商品数量
     */
    public void updateGoodsInLocalShop(int goods_id, int currentCount, int index){
        ShoppingCartBean shoppingCartBean = LocalShopHelper.getShoppingCartBean(mStoreId);
        if (shoppingCartBean != null) {//如果获取不到购物车
            List<ShoppingCartBean.ListBean> list = shoppingCartBean.getList();
            ShoppingCartBean.ListBean localListBean = list.get(index);
            if (currentCount == 1){
                localListBean.setGoods_num(localListBean.getGoods_num() + currentCount);
                shoppingCartBean.setList(list);
                LocalShopHelper.saveShoppingCartBean(shoppingCartBean,mStoreId);
            } else {
                if (localListBean.getGoods_num() <= 0){
                    list.remove(localListBean);
                }else {
                    localListBean.setGoods_num(localListBean.getGoods_num() + currentCount);
                    shoppingCartBean.setList(list);
                    LocalShopHelper.saveShoppingCartBean(shoppingCartBean,mStoreId);
                }
            }
        }

        if (LocalShopHelper.isShoppingCartEmpty(mStoreId)){
            LocalShopHelper.clearLoaclShopCart(mStoreId);
        }

    }

    /**
     * 往已经存在的购物车添加一个商品
     * @param goodsId 商品ID
     */
    public void addGoodsInLocalShop(int goodsId,String goodsPrice){
        ShoppingCartBean shoppingCartBean = LocalShopHelper.getShoppingCartBean(mStoreId);
        if (shoppingCartBean != null) {//如果获取不到购物车
            List<ShoppingCartBean.ListBean> list = shoppingCartBean.getList();
            ShoppingCartBean.ListBean localListBean = new ShoppingCartBean.ListBean();
            localListBean.setGoods_id(goodsId);
            localListBean.setGoods_price(goodsPrice);
            localListBean.setGoods_num(1);
            list.add(localListBean);
            shoppingCartBean.setList(list);
            LocalShopHelper.saveShoppingCartBean(shoppingCartBean,mStoreId);
        }
    }

    /**
     * 新增一个商店购物车
     * @param goods_id 商品ID
     * @param goodsPrice 商品价格
     * @param currentCount 商品数量
     */
    public void addShopToLocal(int goods_id, String goodsPrice, int currentCount){
        ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
        ShoppingCartBean.StoreBean storeBean = new ShoppingCartBean.StoreBean();
        storeBean.setStore_id(Integer.valueOf(mStoreId));
        shoppingCartBean.setStore(storeBean);
        List<ShoppingCartBean.ListBean> mGoodslist = new ArrayList<>();
        ShoppingCartBean.ListBean goodsBean = new ShoppingCartBean.ListBean();
        goodsBean.setGoods_id(goods_id);
        goodsBean.setGoods_num(currentCount);
        goodsBean.setGoods_price(goodsPrice);
        mGoodslist.add(goodsBean);
        shoppingCartBean.setList(mGoodslist);
        LocalShopHelper.saveShoppingCartBean(shoppingCartBean,mStoreId);
    }

    /**
     * 判断商品是否在本地购物车
     * @param goodsId 商品ID
     * @return 返回商品所在的位置下角标
     */
    public int checkGoodsInLocalShop(int goodsId){
        ShoppingCartBean shoppingCartBean = LocalShopHelper.getShoppingCartBean(mStoreId);

        if (shoppingCartBean == null){
            return -2;
        }

        List<ShoppingCartBean.ListBean> list = shoppingCartBean.getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGoods_id() == goodsId) {
                return i;
            }
        }
        return -1;
    }



    /**
     * 获取购物车中的指定的商品数量
     * @param goodsId 商品ID
     * @return
     */
    public int getGoodsInCartNumber(int goodsId) {
        // TODO: 2019-06-21 判断购物车登录问题
        int localGoodsNum = 0;
        ShoppingCartBean localCartBean = LocalShopHelper.getShoppingCartBean(mStoreId);
        if (!UserLogic.isLogin() && localCartBean!=null){
            for (ShoppingCartBean.ListBean listBean: localCartBean.getList()) {
                if (goodsId == listBean.getGoods_id()){
                    localGoodsNum += listBean.getGoods_num();
                    break;
                }
            }
        }

        try {
            for (MerchantBean.CartBean.GoodsBean goodsBean : mCartBean.getGoods()) {
                if (goodsBean.getGoods_id().equals(goodsId + "")) {
                    // TODO: 2019-06-21 到时可以去掉相加的
                    return goodsBean.getGoods_num() + localGoodsNum;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localGoodsNum;//return 0
    }

    public void initRecyclerView() {
        mAdapter = new BaseHeaderAdapter<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBeanX>>(data) {

            @Override
            protected void addItemTypes() {
                addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.item_pinned_header);
                addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.item_product_name);
            }

            @Override
            protected void convert(BaseViewHolder holder, final PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBeanX> item) {
                switch (holder.getItemViewType()) {
                    case BaseHeaderAdapter.TYPE_HEADER:
                        holder.setText(R.id.tv_pinned_header, item.getPinnedHeaderName());
                        break;
                    case BaseHeaderAdapter.TYPE_DATA:
                        holder.setText(R.id.tv_commodity_name, item.getData().getGoods_name() + "");
                        Glide.with(getActivity()).load(item.getData().getImg_name()).into((ImageView) holder.getView(R.id.iv_image));
                        holder.getView(R.id.iv_delete).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.tv_amount).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.ll_goods_description).setOnClickListener(v -> {
                            WebViewActivity.start(true, getActivity(), item.getData().getGoods_detail_url(), "商品详情");
                        });
                        //暂时处理
                        holder.getView(R.id.iv_add).setOnClickListener(v -> {
                            // TODO: 2019-06-21 处理登录和未登录时的购物车

                            addToShopingCar(item.getData().getGoods_id(),item.getData().getGoods_price(), 1);

                            holder.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
                            holder.getView(R.id.tv_amount).setVisibility(View.VISIBLE);

                            //如果已登录就通过上传本地购物车来获取具体的购物车数据
                            ((TextView) holder.getView(R.id.tv_amount)).setText("" + (getGoodsInCartNumber(item.getData().getGoods_id())));
                        });

                        holder.getView(R.id.iv_delete).setOnClickListener(v -> {
                            addToShopingCar(item.getData().getGoods_id(),item.getData().getGoods_price(),-1);
                            holder.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
                            holder.getView(R.id.tv_amount).setVisibility(View.VISIBLE);
                            ((TextView) holder.getView(R.id.tv_amount)).setText("" + (getGoodsInCartNumber(item.getData().getGoods_id())));
                        });
                        int amount = getGoodsInCartNumber(item.getData().getGoods_id());
//                        item.getData().setGoods_number_in_cart(amount);
                        if (amount == 0) {
                            holder.getView(R.id.iv_delete).setVisibility(View.INVISIBLE);
                            holder.getView(R.id.tv_amount).setVisibility(View.INVISIBLE);
                        } else {
                            holder.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
                            holder.getView(R.id.tv_amount).setVisibility(View.VISIBLE);
                            ((TextView) holder.getView(R.id.tv_amount)).setText("" + amount);
                        }

                        holder.setText(R.id.tv_money, item.getData().getGoods_price() + "");
                        holder.setText(R.id.tv_price, item.getData().getGoods_marketprice() + "");
                        holder.setText(R.id.tv_sell_amount, "月售" + item.getData().getGoods_salenum() + "    |   点赞" + item.getData().getZan());

                        if (item.getData().getGoods_desc()==null||item.getData().getGoods_desc().equals("")){
                            holder.setGone(R.id.tv_description,false);
                        }else {
                            holder.setText(R.id.tv_description, item.getData().getGoods_desc() + "");
                        }
                        break;
                    default:
                        break;
                }
            }
        };

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
        mIvPhoto.setImageResource(R.drawable.quesehng_ic_wlljsb);
        mTvTitleOne.setText("网络好像出了点问题呢！");

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
