package com.stkj.xtools;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarrah on 2015/8/27.
 */
public class Binder {

    /**
     * @param from 声明变量的类
     * @param finder 可以调用 findViewById 的索引
     */
    public static void inject(Object from, Activity finder) {

        for (Field field : getBindFields(from.getClass())) {
            setBindAnnotationValue(from, field, getBindIdValue(field, finder));
        }
    }

    /**
     * 拦截点击事件
     * @param from
     * @param finder
     * @param handler
     */
    public static void setClickHandler(Object from, Activity finder, ClickHandler handler) {
        for (Field field : getBindFields(from.getClass())) {
            bindClickEvent((View) getBindIdValue(field, finder), handler);
        }
    }

    public static void handleClick(Object from, Activity finder) {
        for (Field field : getBindFields(from.getClass())) {
            setBindClick(from, field, getBindIdValue(field, finder));
        }
    }

    public static void handleClick(Object from, View finder) {
        for (Field field : getBindFields(from.getClass())) {
            setBindClick(from, field, getBindIdValue(field, finder));
        }
    }

    public static void inject(Object from, View finder) {
        for (Field field : getBindFields(from.getClass())) {
            setBindAnnotationValue(from, field, getBindIdValue(field, finder));
        }
    }

    /**
     * 绑定点击事件
     *
     * @param c
     * @param field
     * @param bindIdValue
     */
    private static void setBindClick(Object c, Field field, Object bindIdValue) {
        if (bindIdValue == null || !(bindIdValue instanceof View)) {
            com.stkj.xtools.Log.from("setBindClick", "not a view cant bind");
        } else {
            String methodName = field.getAnnotation(Bind.class).onClick();
            if (methodName != Bind.NO_CLICK) {
                bindClickEvent(c, (View) bindIdValue, methodName);
            }
        }
    }

    /**
     * @param v
     * @param methodName
     */
    public static void bindClickEvent(final Object clz, final View v, final String methodName) {
        v.setOnClickListener(new ReflectClick(clz, methodName));
    }

    public static void bindClickEvent(final View v, ClickHandler ch) {
        v.setOnClickListener(ch);
    }


    /**
     * 绑定view id
     *
     * @param field
     * @param finder
     * @return
     */
    private static Object getBindIdValue(Field field, Activity finder) {
        int id = field.getAnnotation(Bind.class).id();
        if (id != Bind.NO_ID) {
            return finder.findViewById(id);
        }
        return null;
    }

    private static Object getBindIdValue(Field field, View finder) {
        int id = field.getAnnotation(Bind.class).id();
        if (id != Bind.NO_ID) {
            return finder.findViewById(id);
        }
        return null;
    }

    /**
     * 获取这个类 包括这个类的所有父类 的列表
     *
     * @param index
     * @return
     */
    private static List<Class<? extends Object>> parseAllClasses(Object index) {
        List<Class<? extends Object>> classes = new ArrayList<>();
        Class clz = index.getClass();

        if (clz != null) {
            classes.add(clz);
        }

        while (clz.getSuperclass() != null) {
            classes.add(clz.getSuperclass());
            clz = clz.getSuperclass();
        }

        return classes;
    }

    public static List<Field> getBindFields(Class<? extends Object> from) {

        Field[] fields = from.getDeclaredFields();

        List<Field> all_fields = new ArrayList<Field>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Bind.class)) {
                all_fields.add(field);
            }
        }
        return all_fields;
    }

    private static void setBindAnnotationValue(Object o, Field f, Object value) {
        if (value == null) {
            com.stkj.xtools.Log.from("setBindAnnotationValue", "value is null");
        } else {
            f.setAccessible(true);
            try {
                f.set(o, value);
            } catch (IllegalAccessException e) {
                com.stkj.xtools.Log.from("setBindAnnotationValue", e.toString());
            } catch (IllegalArgumentException e) {
                com.stkj.xtools.Log.from("setBindAnnotationValue", e.toString());
            }
        }
    }

    private static class ReflectClick implements View.OnClickListener {
        private final Object clz;
        private final String methodName;

        public ReflectClick(Object clz, String methodName) {
            this.clz = clz;
            this.methodName = methodName;
        }

        public void onClick(View v) {
            try {
                Method method = clz.getClass().getMethod(
                        methodName, new Class[]{View.class});
                method.invoke(clz, v);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
