package com.ifhu.meiwei.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;
import com.ifhu.meiwei.R;
import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.EvaluationBean;
import com.ifhu.meiwei.bean.PostReviewsForm;
import com.ifhu.meiwei.net.BaseObserver;
import com.ifhu.meiwei.net.RetrofitApiManager;
import com.ifhu.meiwei.net.SchedulerUtils;
import com.ifhu.meiwei.net.service.HomeService;
import com.ifhu.meiwei.net.service.OrdersService;
import com.ifhu.meiwei.ui.base.BaseActivity;
import com.ifhu.meiwei.utils.ImageChooseUtil;
import com.ifhu.meiwei.utils.ToastHelper;
import com.ifhu.meiwei.utils.UserLogic;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import java.io.File;
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
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;

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
    @BindView(R.id.ll_goods)
    LinearLayout mLlGoods;
    List<EvaluationBean.InfoBean.GoodsInfoBean> gcsort_data;
    @BindView(R.id.ll_image_views)
    LinearLayout mLlImageViews;
    @BindView(R.id.ev_rider_suggest)
    EditText evRiderSuggest;
    @BindView(R.id.ev_store_suggest)
    EditText evStoreSuggest;

    int tasteStarAmount = 5;
    int packageStarAmount = 5;

    List<String> imageList = new ArrayList<>();
    EvaluationBean.InfoBean infoBean = new EvaluationBean.InfoBean();

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

    public void initData() {
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
                infoBean = t.getData().getInfo();
                gcsort_data = t.getData().getInfo().getGoods_info();
                handleGoodsList();
            }
        });
    }

    public void initView(EvaluationBean evaluationBean) {
        ivRiderPhoto.load(evaluationBean.getQishou().getAvator());
        tvRiderName.setText(evaluationBean.getQishou().getMember_name());
        tvRiderTime.setText(evaluationBean.getQishou().getTime());
        tvStoreName.setText(evaluationBean.getInfo().getStore_name());
        ivStorePhoto.load(evaluationBean.getInfo().getStore_avatar());
    }


    /**
     * 选择点赞与不点赞
     */
    public void handleGoodsList() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        mLlGoods.removeAllViews();
        if (gcsort_data != null && gcsort_data.size() > 0) {
            for (int i = 0; i < gcsort_data.size(); i++) {
                View categoryView = layoutInflater.inflate(R.layout.item_evaluation_like, null);
                TextView tvAnnouncementName = categoryView.findViewById(R.id.tv_announcement_name);
                tvAnnouncementName.setText(gcsort_data.get(i).getGoods_name());
                RelativeLayout mlike = categoryView.findViewById(R.id.rl_like);
                RelativeLayout mNolike = categoryView.findViewById(R.id.rl_nolike);
                final int i1 = i;
                mlike.setSelected(true);
                mNolike.setSelected(false);
                mlike.setOnClickListener(v -> handlelikeOrNoLike(true, i1, mlike, mNolike));
                mNolike.setOnClickListener(v -> handlelikeOrNoLike(false, i1, mlike, mNolike));
                mLlGoods.addView(categoryView);
            }
        }
    }

    public void handlelikeOrNoLike(boolean isLike, int position, RelativeLayout mlike, RelativeLayout mNolike) {
        gcsort_data.get(position).setLike(isLike);
        mlike.setSelected(isLike);
        mNolike.setSelected(!isLike);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void onTvOkClicked() {
        List<String> zan_goods_id = new ArrayList<>();
        for (EvaluationBean.InfoBean.GoodsInfoBean infoBean : gcsort_data) {
            if (infoBean.isLike()) {
                zan_goods_id.add(infoBean.getGoods_id() + "");
            }
        }

        PostReviewsForm postReviewsForm = new PostReviewsForm();
        postReviewsForm.setMember_id(UserLogic.getUserId());
        postReviewsForm.setOrder_id(getDataInt() + "");
        postReviewsForm.setStore_id(infoBean.getStore_id() + "");
        postReviewsForm.setContent(evStoreSuggest.getText().toString());
        postReviewsForm.setKouwei(tasteStarAmount + "");
        postReviewsForm.setBaozhuang(packageStarAmount + "");
        postReviewsForm.setImages(imageList);
        postReviewsForm.setZan_goods_id(zan_goods_id);
        setLoadingMessageIndicator(true);
        RetrofitApiManager.create(OrdersService.class).storeCom(postReviewsForm)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(false) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
            }
        });

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
        tasteStarAmount = starAmount;
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
        packageStarAmount = starAmount;

    }

    /**
     * 跳转选择图片页面
     */
    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(EvaluationActivity.this, ImageChooseUtil.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageChooseUtil.REQUEST_CODE:
                    List<Uri> stringList = Matisse.obtainResult(data);
                    startCrop(stringList.get(0));
                    break;
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            LayoutInflater layoutInflater = LayoutInflater.from(EvaluationActivity.this);
            View view = layoutInflater.inflate(R.layout.item_image, null);
            GlideImageView glideImageView = view.findViewById(R.id.iv_photo);
            glideImageView.load(resultUri.getPath());
            mLlImageViews.addView(view);
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
        }
    }

    public void startCrop(@NonNull Uri uri) {
        String destinationFileName = System.currentTimeMillis() + ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(this);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        return uCrop.withOptions(options);
    }

    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.useSourceImageAspectRatio();
        return uCrop;
    }

    @OnClick(R.id.tv_add_photo)
    public void onMTvAddPhotoClicked() {
        showSelectPicPage();
    }

}
