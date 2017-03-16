package com.ares.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity基类
 * Created by Yan Cui on 2017/3/16/016.
 */

public abstract class BaseAcitivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**  设置布局 */
        setContentView(setLayoutId());
        /**  初始化头部 */
        initTitle();
        /**  初始化界面 */
        initView();
        /**  初始化数据 */
        initData();
    }

    /**
     * 设置布局
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化头部
     */
    protected abstract void initTitle();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
