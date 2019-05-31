package com.ifhu.meiwei.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifhu.meiwei.R;
import com.ifhu.meiwei.adapter.MyAddressAdapter;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.MyAddressBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.UserService;
import com.ifhu.meiwei.ui.activity.home.AddaddressActivity;
import com.ifhu.meiwei.ui.activity.home.EditaddressActivity;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressListActivity extends BaseActivity {

    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;

    @BindView(R.id.lv_address)
    ListView lvAddress;
    List<MyAddressBean> myAddressBeanList = new ArrayList<>();

    MyAddressAdapter myAddressAdapter;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address_list);
        ButterKnife.bind(this);
        tvReturn.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的收获地址");
        tvText.setText("新增地址");
        myAddressAdapter = new MyAddressAdapter(myAddressBeanList, this, false);
        lvAddress.setAdapter(myAddressAdapter);
        myAddressAdapter.setOnClickItem(position -> goToActivity(EditaddressActivity.class, myAddressBeanList.get(position).getAddress_id()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        receivingAddressList();
    }

    /**
     * 收货地址列表接口
     */
    public void receivingAddressList() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.createUpload(UserService.class).userAddressList(UserLogic.getUser().getMember_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<MyAddressBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<MyAddressBean>> t) throws Exception {
                myAddressBeanList = t.getData();
                myAddressAdapter.setmyAddressBeanList(myAddressBeanList);
                if (myAddressAdapter.getCount() > 0) {
                   handleEmpty(false);
                } else {
                    handleEmpty(true);
                }
            }
        });
    }

    public void handleEmpty(boolean isEmpty){
        if (isEmpty){
            llAddress.setVisibility(View.VISIBLE);
        }else {
            llAddress.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.rl_return)
    public void onRlReturnClicked() {
        finish();
    }


    @OnClick({R.id.tv_text, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_text:
            case R.id.tv_add_address:
                goToActivity(AddaddressActivity.class);
                break;
        }
    }
}
