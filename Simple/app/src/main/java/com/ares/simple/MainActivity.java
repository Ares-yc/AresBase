package com.ares.simple;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.ares.baselibrary.ioc.ViewInjectUtils;
import com.ares.baselibrary.ioc.annotation.OnClick;
import com.ares.baselibrary.ioc.annotation.ViewInject;
import com.ares.baselibrary.permission.PermissionHelper;
import com.ares.expandlibrary.ExpandBaseActivtiy;

public class MainActivity extends ExpandBaseActivtiy {

    private static final int REQUEST_COED_CALL_PHONE = 0x0001;
    @ViewInject(R.id.tv_show)
    TextView show;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() { }

    @Override
    protected void initView() {
        ViewInjectUtils.inject(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_show)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show:
                PermissionHelper.with(this)
                        .setRequestCode(REQUEST_COED_CALL_PHONE)
                        .setRequestPermissions(Manifest.permission.CALL_PHONE)
                        .request();
//                String str = show.getText().toString().trim();
//                if (str.equals("Hello World!")) {
//                    show.setText("Day day up");
//                } else if (str.equals("Day day up")) {
//                    show.setText("Hello World!");
//                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.dealRequestPermissionResult(requestCode, permissions, grantResults);
    }

    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "18774823594");
        intent.setData(data);
        startActivity(intent);
    }
}
