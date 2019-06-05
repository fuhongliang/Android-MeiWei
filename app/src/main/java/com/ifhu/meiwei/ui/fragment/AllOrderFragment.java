package com.ifhu.meiwei.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.OrderAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.bean.OrderBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.activity.order.OrdertrackingActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.utils.UserLogic;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ifhu.meiwei.utils.Constants.LOCATION_DATAUPDATA;
import static com.ifhu.meiwei.utils.Constants.ORDER_DATAUPDATA;


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
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_title_one)
    TextView tvTitleOne;
    @BindView(R.id.tv_title_two)
    TextView tvTitleTwo;
    @BindView(R.id.tv_button)
    TextView tvButton;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

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
        lvList.setVerticalScrollBarEnabled(false);
        orderAdapter.setOnClickItem(new OrderAdapter.OnClickItem() {
            @Override
            public void editOrder(int position) {

            }

            @Override
            public void deleteOrder(int position) {
                goToActivity(OrdertrackingActivity.class, orderBeanList.get(position).getOrder_id());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserLogic.isLogin()) {
            getData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ORDER_DATAUPDATA:
                getData();
                break;
            default:
        }
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
                if (orderAdapter.getCount() > 0) {
                    rlEmpty.setVisibility(View.GONE);
                } else {
                    rlEmpty.setVisibility(View.VISIBLE);
                    ivPhoto.setBackgroundResource(R.drawable.quesehng_ic_zanwudingdan);
                    tvTitleOne.setText("近期暂无订单记录...");
                    tvTitleTwo.setVisibility(View.VISIBLE);
                    tvTitleTwo.setText("快动动小手准备下单吧~");
                    tvButton.setVisibility(View.VISIBLE);
                    tvButton.setText("去逛逛");
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
