package com.inspur.nikki.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Nikki on 2016/1/22.
 * dp2px
 * sp2px
 */
public class PxUtils {
    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
