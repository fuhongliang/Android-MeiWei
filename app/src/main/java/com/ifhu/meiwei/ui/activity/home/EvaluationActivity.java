package com.ifhu.meiwei.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.EvaluationBean;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.UserLogic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评价页面
 */
public class EvaluationActivity extends BaseActivity {
    private static final int CAMERA_RESULT_CODE = 3;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_rider_name)
    TextView tvRiderName;
    @BindView(R.id.tv_rider_time)
    TextView tvRiderTime;
    @BindView(R.id.tv_rider_suggest)
    TextView tvRiderSuggest;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_suggest)
    TextView tvStoreSuggest;
    @BindView(R.id.tv_add_phone)
    TextView tvAddPhone;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.rl_nosatisfaction)
    RelativeLayout rlNosatisfaction;
    @BindView(R.id.rl_satisfaction)
    RelativeLayout rlSatisfaction;
    @BindView(R.id.ll_comprehensive)
    LinearLayout llComprehensive;
    @BindView(R.id.ll_taste)
    LinearLayout llTaste;
    @BindView(R.id.ll_package)
    LinearLayout llPackage;
    @BindView(R.id.iv_rider_photo)
    GlideImageView ivRiderPhoto;
    @BindView(R.id.iv_store_photo)
    GlideImageView ivStorePhoto;
    @BindView(R.id.lv_commodity)
    ListView lvCommodity;
    @BindView(R.id.iv_star1)
    ImageView ivStar1;
    @BindView(R.id.iv_star2)
    ImageView ivStar2;
    @BindView(R.id.iv_star3)
    ImageView ivStar3;
    @BindView(R.id.iv_star4)
    ImageView ivStar4;
    @BindView(R.id.iv_star5)
    ImageView ivStar5;

    List<ImageView> viewList = new ArrayList<>();
    @BindView(R.id.iv_taste_star1)
    ImageView ivTasteStar1;
    @BindView(R.id.iv_taste_star2)
    ImageView ivTasteStar2;
    @BindView(R.id.iv_taste_star3)
    ImageView ivTasteStar3;
    @BindView(R.id.iv_taste_star4)
    ImageView ivTasteStar4;
    @BindView(R.id.iv_taste_star5)
    ImageView ivTasteStar5;
    List<ImageView> viewTasteList = new ArrayList<>();

    @BindView(R.id.iv_package_star1)
    ImageView ivPackageStar1;
    @BindView(R.id.iv_package_star2)
    ImageView ivPackageStar2;
    @BindView(R.id.iv_package_star3)
    ImageView ivPackageStar3;
    @BindView(R.id.iv_package_star4)
    ImageView ivPackageStar4;
    @BindView(R.id.iv_package_star5)
    ImageView ivPackageStar5;
    List<ImageView> viewPackageList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);
        initData();
        tvReturn.setVisibility(View.INVISIBLE);
        tvTitle.setText("评价");
        tvText.setVisibility(View.INVISIBLE);
        storeCom();
        selectedSatisfaction(true);
    }

    public void initData(){
        viewList.add(ivStar1);
        viewList.add(ivStar2);
        viewList.add(ivStar3);
        viewList.add(ivStar4);
        viewList.add(ivStar5);
        viewTasteList.add(ivTasteStar1);
        viewTasteList.add(ivTasteStar2);
        viewTasteList.add(ivTasteStar3);
        viewTasteList.add(ivTasteStar4);
        viewTasteList.add(ivTasteStar5);
        viewPackageList.add(ivPackageStar1);
        viewPackageList.add(ivPackageStar2);
        viewPackageList.add(ivPackageStar3);
        viewPackageList.add(ivPackageStar4);
        viewPackageList.add(ivPackageStar5);
        handleStarChange(4, viewList);
        tasteStar(4, viewTasteList);
        packageStar(4, viewPackageList);
    }


    /**
     * 获取评价信息接口
     */
    public void storeCom() {
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(HomeService.class).storeComInfo(getDataInt() + "", UserLogic.getUser().getMember_id() + "")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<EvaluationBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<EvaluationBean> t) throws Exception {
                initView(t.getData());
                handleCategoryDataInit(t.getData().getInfo().getGoods_info());
            }
        });
    }

    public void initView(EvaluationBean evaluationBean) {
//        ivRiderPhoto.load(evaluationBean.getQishou().getAvator());
//        tvRiderName.setText(evaluationBean.getQishou().getMember_name());
//        tvRiderTime.setText(evaluationBean.getQishou().getTime());
        tvStoreName.setText(evaluationBean.getInfo().getStore_name());
        ivStorePhoto.load(evaluationBean.getInfo().getStore_avatar());
    }

    public void handleCategoryDataInit(List<EvaluationBean.InfoBean.GoodsInfoBean> gcsort_data) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        lvCommodity.removeAllViews();
        if (gcsort_data != null && gcsort_data.size() > 0) {
            for (int i = 0; i < gcsort_data.size(); i++) {
                View categoryView = layoutInflater.inflate(R.layout.item_evaluation_like, null);
                TextView tvAnnouncementName = categoryView.findViewById(R.id.tv_announcement_name);
                tvAnnouncementName.setText(gcsort_data.get(i).getGoods_name());
                lvCommodity.addView(categoryView);
            }
        }
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void onTvOkClicked() {
    }

    /**
     * 骑手评价选择
     *
     * @param Satisfaction
     */
    public void selectedSatisfaction(boolean Satisfaction) {
        rlSatisfaction.setSelected(Satisfaction);
        rlNosatisfaction.setSelected(!Satisfaction);

    }

    @OnClick(R.id.rl_nosatisfaction)
    public void onRlNosatisfactionClicked() {
        selectedSatisfaction(false);
    }

    @OnClick(R.id.rl_satisfaction)
    public void onRlSatisfactionClicked() {
        selectedSatisfaction(true);
    }

    /**
     * 大星星综合评价
     *
     * @param view
     */
    @OnClick({R.id.iv_star1, R.id.iv_star2, R.id.iv_star3, R.id.iv_star4, R.id.iv_star5, R.id.iv_taste_star1, R.id.iv_taste_star2, R.id.iv_taste_star3, R.id.iv_taste_star4, R.id.iv_taste_star5, R.id.iv_package_star1, R.id.iv_package_star2, R.id.iv_package_star3, R.id.iv_package_star4, R.id.iv_package_star5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_star1:
                handleStarChange(0, viewList);
                break;
            case R.id.iv_star2:
                handleStarChange(1, viewList);
                break;
            case R.id.iv_star3:
                handleStarChange(2, viewList);
                break;
            case R.id.iv_star4:
                handleStarChange(3, viewList);
                break;
            case R.id.iv_star5:
                handleStarChange(4, viewList);
                break;
            case R.id.iv_taste_star1:
                tasteStar(0, viewTasteList);
                break;
            case R.id.iv_taste_star2:
                tasteStar(1, viewTasteList);
                break;
            case R.id.iv_taste_star3:
                tasteStar(2, viewTasteList);
                break;
            case R.id.iv_taste_star4:
                tasteStar(3, viewTasteList);
                break;
            case R.id.iv_taste_star5:
                tasteStar(4, viewTasteList);
                break;
            case R.id.iv_package_star1:
                packageStar(0, viewPackageList);
                break;
            case R.id.iv_package_star2:
                packageStar(1, viewPackageList);
                break;
            case R.id.iv_package_star3:
                packageStar(2, viewPackageList);
                break;
            case R.id.iv_package_star4:
                packageStar(3, viewPackageList);
                break;
            case R.id.iv_package_star5:
                packageStar(4, viewPackageList);
                break;

        }
    }

    /**
     * 综合星级评价
     *
     * @param starAmount
     * @param mViewList
     */
    public void handleStarChange(int starAmount, List<ImageView> mViewList) {
        for (int i = 0; i < mViewList.size(); i++) {
            if (i <= starAmount) {
                mViewList.get(i).setSelected(true);
            } else {
                mViewList.get(i).setSelected(false);
            }
        }
    }

    /**
     * 口味星级评价
     */
    public void tasteStar(int starAmount, List<ImageView> viewTasteList) {
        for (int i = 0; i < viewTasteList.size(); i++) {
            if (i <= starAmount) {
                viewTasteList.get(i).setSelected(true);
            } else {
                viewTasteList.get(i).setSelected(false);
            }
        }
    }

    /**
     * 包装星级评价
     *
     * @param starAmount
     * @param viewPackageList
     */
    public void packageStar(int starAmount, List<ImageView> viewPackageList) {
        for (int i = 0; i < viewPackageList.size(); i++) {
            if (i <= starAmount) {
                viewPackageList.get(i).setSelected(true);
            } else {
                viewPackageList.get(i).setSelected(false);
            }
        }
    }


}
