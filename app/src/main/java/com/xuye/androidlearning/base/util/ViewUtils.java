package com.xuye.androidlearning.base.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuye on 18/2/25
 */
public class ViewUtils {

    /**
     * 计算一个view的最大深度
     */
    public static int getViewMaxDeep(View view, int currentDeep, int maxDeep) {
        if (view instanceof ViewGroup) {
            currentDeep++;
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
//                Log.e(Constant.tag, "当前遍历深度：" + currentDeep);
                maxDeep = getViewMaxDeep(viewGroup.getChildAt(i), currentDeep, maxDeep);
            }
        } else {
            maxDeep = Math.max(currentDeep, maxDeep);
        }
        return maxDeep;
    }
}
