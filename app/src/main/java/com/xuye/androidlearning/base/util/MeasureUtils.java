package com.xuye.androidlearning.base.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by xuye on 17/2/9
 * 尺寸相关的工具类
 */
public class MeasureUtils {

    private static final String tag = "MeasureUtils";

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //api 17之前用getWidth，之后用新的方法getRealSize()
            return wm.getDefaultDisplay().getWidth();
        } else {
            //不用getSize()是因为getSize可能因为主题、导航栏等存在，与手机实际物理分辨率有差别
            Point point = new Point();
            wm.getDefaultDisplay().getRealSize(point);
//            Log.e(tag, "screen width is " + point.x + " px");
            return point.x;
        }
    }


    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return wm.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            wm.getDefaultDisplay().getRealSize(point);
//            Log.e(tag, "screen height is " + point.y + " px");
            return point.y;
        }
    }
}
