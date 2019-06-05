package com.ifhu.meiwei.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.BaseHeaderAdapter;
import com.ifhu.meiwei.adapter.CategoryAdapter;
import com.ifhu.meiwei.bean.MerchantBean;
import com.ifhu.meiwei.bean.PinnedHeaderEntity;
import com.ifhu.meiwei.ui.activity.home.MainActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.base.WebViewActivity;
import com.orhanobut.logger.Logger;
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
    @BindView(R.id.lv_category)
    ListView mLvCategory;
    @BindView(R.id.lv_product)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    boolean isCurCategoryGoodEmpty = false;
    int findFirstVisibleItemPosition = 0;

    private BaseHeaderAdapter<PinnedHeaderEntity<MerchantBean.GoodsListBean.GoodsBean>> mAdapter;
    CategoryAdapter mCategoryAdapter;
    public static MenuListFragment newInstance() {
        return new MenuListFragment();
    }

    public MenuListFragment() {
        // Required empty public constructor
    }

    public void setGoodsListBeans(List<MerchantBean.GoodsListBean> goodsListBeans) {
        mGoodsListBeans = goodsListBeans;
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

    public void initData(){
        for (MerchantBean.GoodsListBean goodsListBeans:mGoodsListBeans){
            data.add(new PinnedHeaderEntity<>(null, BaseHeaderAdapter.TYPE_HEADER, goodsListBeans.getStc_name()));
            for (MerchantBean.GoodsListBean.GoodsBean goodsBean : goodsListBeans.getGoods()) {
                data.add(new PinnedHeaderEntity<>(goodsBean, BaseHeaderAdapter.TYPE_DATA, goodsListBeans.getStc_name()));
            }
        }
        mAdapter.notifyDataSetChanged();
        mCategoryAdapter.setmDataList(mGoodsListBeans);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        mCategoryAdapter = new CategoryAdapter(mGoodsListBeans, getActivity(), position -> {
            int totalItem = 0;
            for (int i = 0; i<position;i++){
                totalItem = totalItem + mGoodsListBeans.get(i).getGoods().size()+1;
            }
            if (mGoodsListBeans.get(position).getGoods().size() == 0){
                isCurCategoryGoodEmpty= true;
            }else {
                isCurCategoryGoodEmpty= false;
            }

            LinearSmoothScroller linearSmoothScroller = new TopSmoothScroller(getActivity());
            linearSmoothScroller.setTargetPosition(totalItem);
            mRecyclerView.getLayoutManager().startSmoothScroll(linearSmoothScroller);
            mCategoryAdapter.notifyDataSetChanged();
        });
        mLvCategory.setAdapter(mCategoryAdapter);
    }

    public void initRecyclerView(){
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
                        break;
                    default:
                        break;
                }
            }
        };

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
                switch (mAdapter.getItemViewType(i)) {
                    case BaseHeaderAdapter.TYPE_DATA:
                        MerchantBean.GoodsListBean.GoodsBean goodsBean = mAdapter.getData().get(i).getData();
                        WebViewActivity.start(true,getActivity(),"http://47.111.27.189:88/users/#/p_detail/1/" + goodsBean.getGoods_id(),"商品详情");
                        break;
                    case BaseHeaderAdapter.TYPE_HEADER:
                        break;
                        default:
                            break;
                }
            }
        });

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
                //获取LayoutManager
                //经过测试LinearLayoutManager和GridLayoutManager有以下的方法,这里只针对LinearLayoutManager
                if (manager instanceof LinearLayoutManager) {
                    //经测试第一个完整的可见的item位置，若为0则是最上方那个;在item超过屏幕高度的时候只有第一个item出现的时候为0 ，其他时候会是一个负的值
                    //此方法常用作判断是否能下拉刷新，来解决滑动冲突
//                    int findFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition();
//                    //最后一个完整的可见的item位置
//                    int findLastCompletelyVisibleItemPosition =  ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
//                    //第一个可见的位置
                    findFirstVisibleItemPosition =  ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    handleGoodListScroll(findFirstVisibleItemPosition);
                    //最后一个可见的位置
//                    int findLastVisibleItemPosition =  ((LinearLayoutManager) manager).findLastVisibleItemPosition();

                    //如果有滑动冲突--可以用以下方法解决(如果可见位置是position==0的话才能有下拉刷新否则禁掉)
//                    mSwipeRefreshLayout.setEnabled(findFirstCompletelyVisibleItemPosition==0);
//                    //在网上还看到一种解决滑动冲突的方法
//                    int topPosition =
//                            (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//                    Log.e("touch", "onScroll:" + topPosition);
//
//                    mSwipeRefreshLayout.setEnabled(topPosition >= 0);
                }
            }
        });

    }

    public void handleGoodListScroll(int position){
        if (!isCurCategoryGoodEmpty){
            int mPosition = 0;
            int mTotalGoods = 0;
            for (MerchantBean.GoodsListBean goodsListBean:mGoodsListBeans){
                mTotalGoods = mTotalGoods + goodsListBean.getGoods().size();
                if (position > mTotalGoods){
                    mPosition = mPosition + 1;
                }else {
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
