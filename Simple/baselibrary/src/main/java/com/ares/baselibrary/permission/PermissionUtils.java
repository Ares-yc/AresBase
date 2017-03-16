package com.ares.baselibrary.permission;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;

/**
 * Created by Yan Cui on 2017/3/16/016.
 */

public class PermissionUtils {

    private PermissionUtils(){
        throw new UnsupportedOperationException("This Class can't be instanced");
    }

    /**
     * 判断当前设备
     * 是否在Android6.0以上
     * @return 是 or 否
     */
    public static boolean isMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    public static Activity getActivity(Object mObect) {
        if (mObect instanceof Activity) {
            return (Activity)mObect;
        }else if (mObect instanceof Fragment) {
            return ((Fragment)mObect).getActivity();
        }
        throw new IllegalArgumentException("This Argument should be Activity or Fragment");
    }
}
