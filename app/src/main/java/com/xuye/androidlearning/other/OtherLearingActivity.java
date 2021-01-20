package com.xuye.androidlearning.other;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xuye on 17/02/21
 * 非系统的知识学习
 */
public class OtherLearingActivity extends CommonTestActivity {

    private static final String tag = "OtherLearingActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.cautch_exception), getString(R.string.no_ui_show_toast),
                getString(R.string.jump_test_span), getString(R.string.other_test)
        });
    }

    @Override
    protected void clickButton1() {
        throw new RuntimeException("exception in main");
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
//        new Thread(new MyTestRunnable(getApplicationContext())).start();

        Log.e("xuye","click button 22334 ");
    }

    /**
     * 静态内11部类不会持有外部类引用，不会有内存泄露
     */
    private static class MyTestRunnable implements Runnable {

        private Context mContext;

        public MyTestRunnable(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void run() {
            Looper.prepare();
            Toast.makeText(mContext, "非UI线程，测试application弹toast", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    @Override
    protected void clickButton3() {
        super.clickButton3();

//        startActivity(new Intent(this, TestSpannableActivity.class));

        Log.e("xuye","click button 3 ");
    }

    @Override
    protected void clickButton4() {
        super.clickButton4();
        //获取本机电话号码
        //DeviceUtils.getPhoneTelNumber(OtherLearingActivity.this);

        //double比较
        //double d = 0.0000001;
        //Log.e("xuye", "is0=" + TextContentUtils.isEqual(d,0));

        //测试LongSparseArray.get(null)空指针
//        Long l = null;
//        LongSparseArray<String> lL = new LongSparseArray<>();
//        lL.put(1L, "1");
//        lL.get(l);

        //测试Long转型空指针
//        Long l = null;
//        showL(l);


//        Thread request = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                okhttpRequestTest();
//                Looper.loop();
//            }
//        });
//        request.start();

//        for (int value = 0; value < 20; value++) {
//            int result_1 = value / 2;
//            int result_2 = (int) (value / 2.0);
//            Log.e("xuye", "value = " + value + "result_1 = " + result_1 + " , result_2 = " + result_2);
//        }

        long memory = getDeviceMemoryValue(this.getApplication());
        Log.e("xuye", "设备的总内存 = " + memory / 1000000000.0D);

        //正则测试
//        String pattern = ".*(@|%40)+(((\\d)+[wh])+(_)?((\\d)+[wh])?)+.*";
//        String pattern = ".*(@|%40)+((\\d)+(_)?)+.*";
        String pattern = ".*(/(\\d+)\\.(\\d+)/)+.*";
        String c1 = "https://p0.meituan.net/80.20/a.png@1h.webp";
        String c2 = "/10.40/a.png%408h_6w.webp";
        String c3 = "https://p0.meituan.net/30.18/shaitu/189dd39af90a718199781ceda94bdcea109162.jpg%40350w_350h_0e_1l%7Cwatermark%3D0.webp";
        String c4 = "https://p0.meituan.net/a.png@4w_123h_1e";
        String c5 = "/90.100/a.png@10_100_1e";
        String c6 = "a.png@2m.webp";
        String c7 = "/20.22/a.png.webp";
        Pattern r = Pattern.compile(pattern);
        isMatch2(r, "c1", c1);
        isMatch2(r, "c2", c2);
        isMatch2(r, "c3", c3);
        isMatch2(r, "c4", c4);
        isMatch2(r, "c5", c5);
        isMatch2(r, "c6", c6);
        isMatch2(r, "c7", c7);
    }

    private static long getDeviceMemoryValue(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(info);
            return info.totalMem;
        }
        return -1;
    }

    private String isMatch2(Pattern r, String name, String str) {
        Matcher m = r.matcher(str);
        boolean isFind = m.find();
        Log.e("xuye", name + "is find2  = " + isFind);
        if (isFind) {
            String g1 = m.group(1);
            String g2 = m.group(2);
            String g3 = m.group(3);
            Log.e("xuye", name + " , g1 = " + g1+" , g2 = " + g2 + " , g3 = " + g3);
            int value1 = 0;
            int value2 = 0;
            if (!TextUtils.isEmpty(g2)) {
                value1 = Integer.parseInt(g2);
            }
            if (!TextUtils.isEmpty(g3)) {
                value2 = Integer.parseInt(g3);
            }
            int newValue1 = (int)(value1 * 0.84);
            int newValue2 = (int)(value2 * 0.84);
            StringBuilder sb = new StringBuilder();
            Log.e("xuye", "获取出的检查参数，old w = " + value1 + " ,old h = " + value2 + " , new w = " + newValue1 + " , new h = " + newValue2);
            if(newValue1>0 && newValue2>0){
                sb.append("/").append(newValue1).append(".").append(newValue2).append("/");
                String newParams = sb.toString();
                //用新参数替换老裁剪参数
                String newUrl = str.replace(g1, newParams);
                Log.e("xuye", "原始url = " + str);
                Log.e("xuye", "剪裁参数替换后的新url = " + newUrl);
                return newUrl;
            }else{
                return str;
            }
        }
        return str;
    }

    private String isMatch0(Pattern r, String name, String str) {
        Matcher m = r.matcher(str);
        boolean isFind = m.find();
        Log.e("xuye", name + "is find2  = " + isFind);
        return str;
    }

    private String isMatch(Pattern r, String name, String str) {
        Matcher m = r.matcher(str);
        boolean isFind = m.find();
        int width = 0;
        int height = 0;
        if (isFind) {
            String g0 = m.group(0);
            String g1 = m.group(1);
            String g2 = m.group(2);
            String g3 = m.group(3);
            String g4 = m.group(4);
            String g5 = m.group(5);
            String g6 = m.group(6);
            String g7 = m.group(7);
            Log.e("xuye", name + " is find = true ,g0 = " + g0 + " ,g1 = " + g1 + " , g2 = " + g2 + " ,g3 = " + g3 + " ,g4 = " + g4 + " ,g5 = " + g5 + " ,g6 =" + g6 + " ,g7 = " + g7);

            //获取裁剪参数值
            if (!TextUtils.isEmpty(g3)) {
                //第一个参数
                if (g3.contains("w")) {
                    width = Integer.parseInt(g3.replace("w", ""));
                } else if (g3.contains("h")) {
                    height = Integer.parseInt(g3.replace("h", ""));
                }
            }
            if (!TextUtils.isEmpty(g6)) {
                //还有第2个剪裁参数
                if (g6.contains("w")) {
                    width = Integer.parseInt(g6.replace("w", ""));
                } else if (g6.contains("h")) {
                    height = Integer.parseInt(g6.replace("h", ""));
                }
            }

            //获取新参数值
            int newHeight = (int) (height * 0.8);
            int newWidth = (int) (width * 0.8);
            Log.e("xuye", "获取出的检查参数，old w = " + width + " ,old h = " + height + " , new w = " + newWidth + " , new h = " + newHeight);

            //生成新裁剪参数
            StringBuilder sb = new StringBuilder();
            if (newHeight > 0 && newWidth > 0) {
                //原始url有宽高剪裁，且处理后的新剪裁参数仍然合法
                sb.append(newWidth).append("w").append("_").append(newHeight).append("h");
            } else if (newHeight > 0 && width <= 0) {
                //原始url只有单独的高剪裁
                sb.append(newHeight).append("h");
            } else if (newWidth > 0 && height <= 0) {
                //原始url只有单独的宽剪裁
                sb.append(newWidth).append("w");
            } else {
                Log.e("xuye", "剪裁参数处理后异常，不处理了");
                Log.e("xuye", " ");
                return str;
            }
            String newParams = sb.toString();
            Log.e("xuye", "原始裁剪参数 = " + g2 + " , 新裁剪参数 = " + newParams);

            //用新参数替换老裁剪参数
            String newUrl = str.replace(g2, newParams);
            Log.e("xuye", "原始url = " + str);
            Log.e("xuye", "剪裁参数替换后的新url = " + newUrl);
            Log.e("xuye", " ");
            return newUrl;
        }
        return str;
    }

    private void okhttpRequestTest() {
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        //循环打印请求header
        builder.addHeader("Host", "api.meituan.com");
        builder.addHeader("Connection", "keep-alive");
        builder.addHeader("Upgrade-Insecure-Requests", "1");
        builder.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; MI 2S Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.146 Mobile Safari/537.36 XiaoMi/MiuiBrowser/9.3.2");
        builder.addHeader("x-miorigin", "bm");
        builder.addHeader("Accept", "application/json,text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        builder.addHeader("Accept-Encoding", "gzip");
        builder.addHeader("Accept-Language", "zh-CN,en-US;q=0.8");

        builder.method("GET", null);
        Request okHttpRequest = builder.build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .build();
        try {
            Response okHttpResponse = okHttpClient.newCall(okHttpRequest).execute();
            Log.e("xuye", "okHttpResponse：" + okHttpResponse.toString());
            Log.e("xuye", "okHttpResponse body：" + okHttpResponse.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String url = "http://api.meituan.com/meishi/poi/v1/poi/comment/list/1528027?commentType=2&partner=126&uuid=2C2C0ECD557F366849954BEF88D0017AAECEB2B01C7EF5E48FCC20C34BAE319E&platform=4&version=8.4&app=0&__vhost=api.meishi.meituan.com&utm_source=meituaninternaltest&utm_medium=android&utm_term=540&version_name=8.4&utm_content=000000000000000&utm_campaign=AgroupBgroupC0E142864662997041442533430186581325698637_a1528027_c0_e13048117278679197453Ghomepage_category1_1__a1__gfood__hpoilist__i0&ci=1&msid=&userid=-1&__reqTraceID=fad2ff19-ed76-46c6-9a25-5139610c816f&__skck=6a375bce8c66a0dc293860dfa83833ef&__skts=1503373761646&__skua=9656fe9df3a05f12359b83605b79cbcb&__skno=a5db4140-a1ba-4f6a-b4db-222468ab539c&__skcy=n2eYArNZFVB3izyt0tNqaOUwPEE%3D";

    private void showL(long needL) {
        Log.e("xuye", "longValue=" + needL);
    }


}
