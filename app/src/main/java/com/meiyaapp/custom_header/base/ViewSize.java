package com.meiyaapp.custom_header.base;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * 计算View大小。
 * Created by mantou on 15/7/13.
 */
public class ViewSize {
    //频幕高宽。
    //这里为了可以动态初始化，就没有给定final值。
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    public static  float   DENSITY;
    private static Context sContext;
    public static  float   SCALED_DENSITY;

    public static void init(Context context) {
        sContext = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;
        DENSITY = metrics.density;
        SCALED_DENSITY = metrics.scaledDensity;
    }

    public static ViewSize getScreenWidth() {
        return new ViewSize(SCREEN_WIDTH);
    }

    public static ViewSize getScreenHeight() {
        return new ViewSize(SCREEN_HEIGHT);
    }

    public static ViewSize getSizeByDp(int sizeDp) {
        return new ViewSize((int) (sizeDp * DENSITY + 0.5f));
    }

    public static ViewSize getSizeBySp(int sizeSp) {
        return new ViewSize((int) (sizeSp * SCALED_DENSITY + 0.5f));
    }

    public static ViewSize getSizeByDimenRes(int resId) {
        if (sContext == null) return new ViewSize(0);
        return new ViewSize(sContext.getResources().getDimensionPixelSize(resId));
    }

    private float mSize;

    public ViewSize(int init) {
        mSize = init;
    }

    public ViewSize multiply(int factor) {
        mSize = mSize * factor;
        return this;
    }

    public ViewSize divide(int factor) {
        mSize = mSize / factor;
        return this;
    }

    public ViewSize plus(int factor) {
        mSize += factor;
        return this;
    }

    public ViewSize minus(int factor) {
        mSize -= factor;
        return this;
    }

    public ViewSize plusByDp(int factorDp) {
        return plus((int) (factorDp * DENSITY));
    }

    public ViewSize minusByDp(int factorDp) {
        return minus((int) (factorDp * DENSITY));
    }

    public int get() {
        return (int) mSize;
    }

    public static void set(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) return;

        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void setHeight(View view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    public static void setWidth(View view, int width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
    }

    public static void setHeight(View view, ViewSize height) {
        setHeight(view, height.get());
    }

    public static void setWidth(View view, ViewSize width) {
        setWidth(view, width.get());
    }

    public static void forceLayout(View view) {
        view.requestLayout();
        view.invalidate();
    }

    public static void hideByHeight(View view) {
        setHeight(view, 0);
    }

    public static void hideByWidth(View view) {
        setWidth(view, 0);
    }

    public static void showByWrapHeight(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(params);
    }

    //Copy from uil
    public static int getWidth(View view) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        int width = 0;
        if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            width = view.getWidth(); // Get actual image width
        }
        if (width <= 0 && params != null) width = params.width; // Get layout width parameter

        if (width <= 0 && ImageView.class.isInstance(view)) {
            width = getImageViewFieldValue(view, "mMaxWidth");
        }
        return width;
    }

    public static int getHeight(View view) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        int height = 0;
        if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            height = view.getHeight(); // Get actual image height
        }
        if (height <= 0 && params != null) height = params.height; // Get layout height parameter

        if (height <= 0 && ImageView.class.isInstance(view)) {
            height = getImageViewFieldValue(view, "mMaxHeight");
        }
        return height;
    }

    public boolean biggerThan(ViewSize size) {
        return this.get() > size.get();
    }

    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {

        }
        return value;
    }
}
