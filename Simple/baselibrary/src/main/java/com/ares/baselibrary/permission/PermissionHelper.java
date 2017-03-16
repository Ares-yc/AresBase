package com.ares.baselibrary.permission;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 权限帮助类
 * Created by Yan Cui on 2017/3/16/016.
 */

public class PermissionHelper {

    private Object mObect;
    private int mRequestCode;
    private String[] mRequestPermissions;

    public PermissionHelper(Object mObect) {
        this.mObect = mObect;
    }

    public PermissionHelper(Object mObect, int requestCode, String[] requestPermissions) {
        PermissionHelper.with(mObect)
                .setRequestCode(requestCode)
                .setRequestPermissions(requestPermissions)
                .request();
    }

    public static PermissionHelper with(Object object) {
        return new PermissionHelper(object);
    }

    public PermissionHelper setRequestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionHelper setRequestPermissions(String... permissions){
        this.mRequestPermissions = permissions;
        return this;
    }

    public void request(){
        //判断系统版本 6.0以下直接执行  6.0以上检测权限
        if (PermissionUtils.isMarshmallow()) {

        }else{
            for (String permission : mRequestPermissions) {
                int result = ContextCompat.checkSelfPermission(PermissionUtils.getActivity(mObect),permission);
                if (result == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }

    public static void dealRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
    }
}
