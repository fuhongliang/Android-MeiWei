package com.ifhu.meiwei.ui.activity.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.OrderAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.OrderBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {

    List<OrderBean> orderBeanList = new ArrayList<>();
    OrderAdapter orderAdapter;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.lv_order)
    ListView lvOrder;

    int type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        orderAdapter = new OrderAdapter(orderBeanList, this);
        lvOrder.setAdapter(orderAdapter);
        orderList();
    }

    /**
     * 订单列表接口
     */
    public void orderList() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(OrdersService.class).orderList(UserLogic.getUser().getMember_id(), type)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OrderBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<OrderBean> t) throws Exception {
                orderAdapter.setOrderBeanList(orderBeanList);
            }
        });
    }

}
