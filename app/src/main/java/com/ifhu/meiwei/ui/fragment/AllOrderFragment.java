package com.ifhu.meiwei.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.ifhu.meiwei.ui.activity.home.ShopHomeActivity;
import com.ifhu.meiwei.ui.activity.order.EvaluationActivity;
import com.ifhu.meiwei.ui.activity.order.OrdertrackingActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ifhu.meiwei.utils.Constants.GOTOHOMEPAGE;
import static com.ifhu.meiwei.utils.Constants.ORDER_DATAUPDATA;


/**
 * 所有订单
 *
 * @author fuhongliang
 */
public class AllOrderFragment extends BaseFragment {

    int type = 1;

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
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mLayoutSwipeRefresh;

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
            public void Return(int position) {

            }

            @Override
            public void shopping(int position) {
                EventBus.getDefault().post(new MessageEvent(GOTOHOMEPAGE));

            }

            @Override
            public void shopHomePage(int position) {
                // TODO: 2019-06-19 待添加商家ID 
                goToActivity(ShopHomeActivity.class,"2"/*,orderBeanList.get(position).getOrder_id()*/);

            }

            @Override
            public void orderDetail(int position) {
                goToActivity(OrdertrackingActivity.class, orderBeanList.get(position).getOrder_id());
            }

            @Override
            public void evaluation(int position) {
                goToActivity(EvaluationActivity.class, orderBeanList.get(position).getOrder_id());
            }

            @Override
            public void oneMore(int position) {
                //添加订单中的商品到购物车
                List<OrderBean.GoodsListBean> goodsListBeans = orderBeanList.get(position).getGoods_list();
                //通过添加购物车接口添加商品

                //然后再跳转
                goToActivity(ShopHomeActivity.class);
            }

            @Override
            public void payOrder(int position) {

            }
        });
        EventBus.getDefault().register(this);
        setRefreshLayout();
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        mLayoutSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mLayoutSwipeRefresh.setOnRefreshListener(() -> {
            getData(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserLogic.isLogin()) {
            getData(false);
        } else {
            handleEmptyPage(true);
        }
    }


    public void handleEmptyPage(boolean isEmpty) {
        if (isEmpty) {
            rlEmpty.setVisibility(View.VISIBLE);
            ivPhoto.setBackgroundResource(R.drawable.quesehng_ic_zanwudingdan);
            tvTitleOne.setText("近期暂无订单记录...");
            tvTitleTwo.setText("快动动小手准备下单吧~");
            tvTitleTwo.setVisibility(View.VISIBLE);
            tvButton.setText("去逛逛");
            tvButton.setVisibility(View.VISIBLE);
            tvButton.setOnClickListener(v -> EventBus.getDefault().post(new MessageEvent(GOTOHOMEPAGE)));
            orderAdapter.clearList();
        } else {
            rlEmpty.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ORDER_DATAUPDATA:
                getData(false);
                break;
            default:
        }
    }

    public void getData(boolean showIndicator) {
        if (UserLogic.getUser()==null){
            ToastHelper.makeText("未登录").show();
            mLayoutSwipeRefresh.setRefreshing(false);
            return;
        }
        setLoadingMessageIndicator(showIndicator);
        RetrofitApiManager.createUpload(OrdersService.class).orderList(UserLogic.getUser().getMember_id(), type)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<OrderBean>>(true) {
            @Override
            protected void onApiComplete() {
                mLayoutSwipeRefresh.setRefreshing(false);
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<OrderBean>> t) throws Exception {
                orderBeanList = t.getData();
                orderAdapter.setOrderBeanList(orderBeanList);
                if (orderAdapter.getCount() > 0) {
                    handleEmptyPage(false);
                } else {
                    handleEmptyPage(true);
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
        EventBus.getDefault().unregister(this);
    }
}
