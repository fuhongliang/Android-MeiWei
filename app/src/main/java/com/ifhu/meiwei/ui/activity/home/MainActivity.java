package com.ifhu.meiwei.ui.activity.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.MainActivityFragmentAdapter;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.ui.activity.login.LoginActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.base.ViewManager;
import com.ifhu.meiwei.ui.fragment.HomeFragment;
import com.ifhu.meiwei.ui.fragment.MeFragment;
import com.ifhu.meiwei.utils.UserLogic;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.ifhu.meiwei.utils.Constants.LOCATION_DATAUPDATA;
import static com.ifhu.meiwei.utils.Constants.LOGOUT;

/**
 * @author fuhongliang
 */
public class MainActivity extends BaseActivity {

    private ViewPager mPager;
    private List<BaseFragment> mFragments;
    private MainActivityFragmentAdapter mAdapter;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        resetToDefaultIcon();
        return setCurrentItemIcon(item);
    };

    public boolean setCurrentItemIcon(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.navigation_home) {
            item.setIcon(R.drawable.bottom_ic_homed);
            mPager.setCurrentItem(0);
            return true;
        } else if (i == R.id.navigation_orders) {
            item.setIcon(R.drawable.bottom_ic_ordered);
            mPager.setCurrentItem(1);
            return true;
        } else if (i == R.id.navigation_me) {
            item.setIcon(R.drawable.bottom_ic_med);
            mPager.setCurrentItem(2);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initViewPager();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        navigation.setItemIconTintList(null);
        navigation.setSelectedItemId(R.id.navigation_home);
        Resources resource = getBaseContext().getResources();
        ColorStateList csl = resource.getColorStateList(R.color.bottom_navigation_color);
        navigation.setItemTextColor(csl);
        askPermission();
    }

    public void askPermission(){
        AndPermission.with(this)
                .permission(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        initLocationClient();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {

            }
        }).start();
    }

    private void resetToDefaultIcon() {
        MenuItem home = navigation.getMenu().findItem(R.id.navigation_home);
        MenuItem orders = navigation.getMenu().findItem(R.id.navigation_orders);
        MenuItem me = navigation.getMenu().findItem(R.id.navigation_me);
        home.setIcon(R.drawable.bottom_ic_home);
        orders.setIcon(R.drawable.bottom_ic_order);
        me.setIcon(R.drawable.bottom_ic_me);
    }

    private void initViewPager() {
        ViewManager.getInstance().addFragment(0, HomeFragment.newInstance());
        ViewManager.getInstance().addFragment(1, MeFragment.newInstance());
        ViewManager.getInstance().addFragment(2, MeFragment.newInstance());
        mFragments = ViewManager.getInstance().getAllFragment();
        mPager = findViewById(R.id.container_pager);
        mPager.setOffscreenPageLimit(4);
        mAdapter = new MainActivityFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                resetToDefaultIcon();
                setCurrentItemIcon(navigation.getMenu().getItem(i));
                navigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case LOGOUT:
                logout();
                break;
            default:
        }
    }

    public void logout() {
        UserLogic.loginOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    /**
     *  声明AMapLocationClient类对象
     */

    public AMapLocationClient mLocationClient = null;
    /**
     * 声明定位回调监听器
     */

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            setLoadingMessageIndicator(false);
            if (location != null) {
                if (location.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Logger.e("//获取纬度:"+location.getLatitude());
                    Logger.e("//获取经度:"+location.getLongitude());
                    Logger.e("//getDistrict:"+location.getDistrict());
                    Logger.e("//getPoiName:"+location.getPoiName());
                    Logger.e("//getAddress:"+location.getAddress());
                    Logger.e("//getLocationDetail:"+location.getLocationDetail());
                    ArrayList<String> data = new ArrayList<>();
                    data.add(location.getPoiName());
                    data.add(location.getLongitude()+"");
                    data.add(location.getLatitude()+"");
                    EventBus.getDefault().post(new MessageEvent(LOCATION_DATAUPDATA,data));

                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + location.getErrorCode() + ", errInfo:"
                            + location.getErrorInfo());
                }
            }
        }
    };

    /**
     * 声明AMapLocationClientOption对象
     */

    public AMapLocationClientOption mLocationOption = null;

    public void initLocationClient(){
        setLoadingMessageIndicator(true);
        Logger.e("//开始初始化定位");
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

}
