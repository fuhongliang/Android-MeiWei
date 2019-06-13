package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.ShoppingCartAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.ShoppingCartBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.activity.order.ConfirmOrderActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.ui.nicedialog.ConfirmDialog;
import com.ifhu.meiwei.ui.nicedialog.DialogUtils;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车页面
 */
public class ShoppingCartActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.iv_share_it)
    ImageView ivShareIt;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
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
    @BindView(R.id.lv_shopping_cart)
    ListView lvShoppingCart;

    List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    ShoppingCartAdapter shoppingCartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        tvReturn.setVisibility(View.INVISIBLE);
        tvTitle.setText("购物车");
        tvText.setText("清空");
        shoppingCartAdapter = new ShoppingCartAdapter(shoppingCartBeanList, this);
        lvShoppingCart.setAdapter(shoppingCartAdapter);
        myCart();
        tvText.setOnClickListener(v -> DialogUtils.showConfirmDialog("温馨提示", "确认删除购物车中所有的商品？", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                //清空购物车商家商品
                setLoadingMessageIndicator(true);
                RetrofitApiManager.create(HomeService.class).clearCart(UserLogic.getUser().getMember_id())
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity t) throws Exception {
                        ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                        finish();
                    }
                });
            }
        }));
    }

    public void isEmptyCart(boolean empty){
        if (empty){
            tvText.setEnabled(false);
            rlEmpty.setVisibility(View.VISIBLE);
            ivPhoto.setBackgroundResource(R.drawable.quesehng_ic_zagwuche);
            tvTitleOne.setText("购物车没商品是多么痛的领悟呀");
            tvTitleTwo.setVisibility(View.VISIBLE);
            tvTitleTwo.setText("快去看看有哪些优惠商品吧~");
            tvButton.setText("去逛逛");
            tvButton.setVisibility(View.VISIBLE);
            tvButton.setOnClickListener(v -> goToActivity(MainActivity.class));
        }else {
            rlEmpty.setVisibility(View.GONE);
            tvText.setEnabled(true);
        }
    }

    /**
     * 购物车接口
     */
    public void myCart() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(HomeService.class).myCart(UserLogic.getUserId())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<ShoppingCartBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<ShoppingCartBean>> t) throws Exception {
                shoppingCartBeanList = t.getData();
                shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
                if (shoppingCartAdapter.getCount() > 0) {
                   isEmptyCart(false);
                } else {
                     isEmptyCart(true);
                }
                shoppingCartAdapter.setOnClickItem(position -> goToActivity(ConfirmOrderActivity.class,shoppingCartBeanList.get(position).getStore().getStore_id()+""));
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_text)
    public void onTvTextClicked() {

    }
}
