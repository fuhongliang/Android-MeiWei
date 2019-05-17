package com.ifhu.meiwei.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.MainActivityFragmentAdapter;
import com.ifhu.meiwei.bean.MessageEvent;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.ui.base.BaseFragment;
import com.ifhu.meiwei.ui.base.ViewManager;
import com.ifhu.meiwei.ui.fragment.MeFragment;
import com.ifhu.meiwei.utils.UserLogic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
        ViewManager.getInstance().addFragment(0, MeFragment.newInstance());
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
//        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        finish();
    }

}
