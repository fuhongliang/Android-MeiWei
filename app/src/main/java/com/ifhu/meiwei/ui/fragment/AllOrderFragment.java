package com.ifhu.meiwei.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.OrderAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.OrderBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.activity.order.OrdertrackingActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 所有订单
 *
 * @author fuhongliang
 */
public class AllOrderFragment extends BaseFragment {

    int type = 0;

    List<OrderBean> orderBeanList = new ArrayList<>();
    OrderAdapter orderAdapter;

    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;

    public static AllOrderFragment newInstance() {
        return new AllOrderFragment();
    }

    public AllOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderAdapter = new OrderAdapter(orderBeanList, getActivity());
        lvList.setAdapter(orderAdapter);
        if (UserLogic.isLogin()){
            getData();
        }

        orderAdapter.setOnClickItem(new OrderAdapter.OnClickItem() {
            @Override
            public void editOrder(int position) {

            }

            @Override
            public void deleteOrder(int position) {
                goToActivity(OrdertrackingActivity.class,orderBeanList.get(position).getOrder_id());
            }
        });
    }

    public void getData() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(OrdersService.class).orderList(UserLogic.getUser().getMember_id(), type)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<OrderBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<OrderBean>> t) throws Exception {
                orderBeanList = t.getData();
                orderAdapter.setOrderBeanList(orderBeanList);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
