package com.ifhu.meiwei.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.HomeBean;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.activity.home.ShippingAddressActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.view.MyScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ifhu.meiwei.utils.Constants.LOCATION_DATAUPDATA;
import static com.ifhu.meiwei.utils.Constants.LOGOUT;

/**
 * 首页模块
 *
 * @author fuhongliang
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    private int lastY = 0;
    private int touchEventId = -9983761;
    boolean isOut = false;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder;
    @BindView(R.id.scrollView)
    MyScrollView mScrollView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }


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
        mScrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
            }
            return false;
        });
        EventBus.getDefault().register(this);
    }

    /**
     *
     * @param mLongitude 经度
     * @param mLatitude 经度
     */
    public void getData(String mLongitude,String mLatitude){
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(HomeService.class).keyword("",1,mLongitude,mLatitude,"1")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<HomeBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<HomeBean> t) throws Exception {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case LOCATION_DATAUPDATA:
                mTvAddress.setText(messageEvent.getArrayList().get(0));
                try {
                    getData(messageEvent.getArrayList().get(1),messageEvent.getArrayList().get(2));
                }catch (Exception e){
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
}
