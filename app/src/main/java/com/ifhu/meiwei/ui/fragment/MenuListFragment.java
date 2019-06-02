package com.ifhu.meiwei.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.MerchantBean;
import com.ifhu.meiwei.ui.base.BaseFragment;

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
//  CategoryAdapter mCategoryAdapter;
//    ProductAdapter mProductAdapter;
    @BindView(R.id.lv_category)
    ListView mLvCategory;
    @BindView(R.id.lv_product)
    ListView mLvProduct;
    Unbinder unbinder;

    public static MenuListFragment newInstance() {
        return new MenuListFragment();
    }

    public MenuListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menulist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
