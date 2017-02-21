package com.xuye.androidlearning.base;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 17/2/21
 * 与手机设备参数相关的一些方法
 */
public class DeviceUtils {

    private static final String tag = "DeviceUtils";

    public static void getPhoneTelNumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = (manager.getLine1Number() == null ? "can not find tel!" : manager.getLine1Number().toString());
        Log.e(tag, context.getString(R.string.phone_tel) + tel);
        Toast.makeText(context, context.getString(R.string.phone_tel) + tel, Toast.LENGTH_SHORT).show();
    }

}
