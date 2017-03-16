package com.ares.baselibrary.ioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kxd on 2017/2/8/008.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerType = View.OnLongClickListener.class,listenerSetter = "setOnLongClickListener",methodName = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
