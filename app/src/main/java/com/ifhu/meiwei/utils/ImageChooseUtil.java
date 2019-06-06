package com.ifhu.meiwei.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

/**
 * @author fuhongliang
 */
public class ImageChooseUtil {
    public static final int REQUEST_CODE = 1001;
    public static final int REQUEST_CODE2 = 1002;

    public static void startChooseImage(final Activity context, int request_code) {
        AndPermission.with(context)
                .permission(Permission.CAMERA,Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Matisse.from(context)
                                .choose(MimeType.ofImage())
                                .countable(true)
                                .capture(true)
                                .captureStrategy(new CaptureStrategy(true, context.getPackageName() + "fileprovider"))
                                .maxSelectable(1)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .imageEngine(new ImageGildeEngine())
                                .forResult(request_code);
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {

            }
        }).start();
    }

}
