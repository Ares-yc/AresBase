package com.ares.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

import com.ares.baselibrary.ioc.annotation.ContentView;
import com.ares.baselibrary.ioc.annotation.EventBase;
import com.ares.baselibrary.ioc.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ViewInjectUtils {

    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity){
        injectContentView(activity);
        injectViews(activity);
        injectEvent(activity);
    }

    /**
     * 注入布局Id
     * @param activity 所在类
     */
    private static void injectContentView(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        /**  查询传入的类中是否存在ContentView注解 */
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int contentViewLayoutId = contentView.value();
            try {
                Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW,int.class);
                method.setAccessible(true);
                method.invoke(activity,contentViewLayoutId);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入控件Id
     * @param activity 所在类
     */
    private static void injectViews(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        /**  遍历所有成员变量，查询是否存在ViewInject注解 */
        for (Field field : fields) {
            ViewInject viewInjectAnnotation = field.getAnnotation(ViewInject.class);
            if (viewInjectAnnotation != null) {
                int viewId = viewInjectAnnotation.value();
                if (viewId != -1) {
                    try {
                        Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID,int.class);
                        Object resView = method.invoke(activity,viewId);
                        field.setAccessible(true);
                        field.set(activity,resView);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 注入所有的事件
     * @param activity 所在类
     */
    private static void injectEvent(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        /**  遍历所有的方法 */
        for (Method method : methods) {
            /**  拿到方法上的所有的注解 */
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                /**  拿到注解上的注解 */
                EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
                /**  如果注解中EventBase存在 */
                if (null != eventBaseAnnotation) {
                    /**  取出设置监听器的名称 */
                    String listenerSetter = eventBaseAnnotation.listenerSetter();
                    /**  监听器的类型 */
                    Class<?> listenerType = eventBaseAnnotation.listenerType();
                    /**  调用的方法名 */
                    String methodName = eventBaseAnnotation.methodName();
                    try {
                        /**  拿到Onclick注解中的value方法 */
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        /**  取出所有的viewId */
                        int[] viewIds = (int[]) aMethod.invoke(annotation, (Object[]) null);
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(
                                listenerType.getClassLoader(),
                                new Class<?>[] { listenerType }, handler);
                        //遍历所有的View，设置事件
                        for (int viewId : viewIds)
                        {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass()
                                    .getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }

                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
