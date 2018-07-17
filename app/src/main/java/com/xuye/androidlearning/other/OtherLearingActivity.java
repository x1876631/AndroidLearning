package com.xuye.androidlearning.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

import java.io.IOException;

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
        new Thread(new MyTestRunnable(getApplicationContext())).start();
    }

    /**
     * 静态内部类不会持有外部类引用，不会有内存泄露
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

        startActivity(new Intent(this, TestSpannableActivity.class));
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


        Thread request = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                okhttpRequestTest();
                Looper.loop();
            }
        });
        request.start();

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
