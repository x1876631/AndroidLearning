package com.xuye.androidlearning.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuye on 17/2/9
 * 图片加载库
 * 1、最基本的功能：使用网络下载图片并展示在指定的imageview上(使用线程池异步下载图片，然后在主线程上执行展示)
 */
public class ImageLoader {

    private static final String tag = "ImageLoader";

    private static ImageLoader sInstance;
    private Context mContext;

    private ImageLoader(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static ImageLoader getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ImageLoader(context);
        }
//        Log.e(tag, "当前手机的cpu数 ：" + CPU_COUNT);
        return sInstance;
    }

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();//获取当前手机cpu核心数
    private static final int POOL_CORE_COUNT = CPU_COUNT + 1;//线程池核心线程数
    private static final int POOL_MAX_COUNT = CPU_COUNT * 2 + 1;//线程池最大线程数
    private static final long KEEP_ALIVE_TIME = 10L;//非核心线程闲置时的超时回收时间，超过该时间则被回收

    //线程创建工厂，为线程池创建新线程用
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        //使用了一个同步计数类，每次创建新线程时设置线程名字用
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    //图片异步加载使用线程池，减少开销。线程池的配置仿照AsyncTask线程池的配置
    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(
            POOL_CORE_COUNT, POOL_MAX_COUNT, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(), THREAD_FACTORY);

    /**
     * 用handler持有主线程的looper，去展示图片
     * 【注意】
     * hanlder如果使用非静态匿名内部类创建的话，会提示：this handler class should be static or leaks might occur
     * 这是因为Message会持有handler引用，而这个handler又持有外部Activity的引用（非静态匿名内部类将持有一个对外部类的隐式引用），
     * 如果在handleMessage里发送一条延迟执行的消息，然后立刻结束掉Activity的话，外部Activity由于被持有着，就无法被释放，导致内存泄露
     * 【解决办法】把handler写成静态内部类，再创建handler，并让handler持有外部Activity的弱引用
     */
    private Handler mHandler = new MyHandler(mContext, Looper.getMainLooper());

    private static final int LOAD_BITMAP_SUCCEDED = 0;

    /**
     * 为了防止内存泄露，使用handler时用静态内部类创建(这样不会持有外部类的引用)
     */
    private static class MyHandler extends Handler {
        private WeakReference<Context> mWeakReference;

        public MyHandler(Context context, Looper looper) {
            super(looper);
            mWeakReference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_BITMAP_SUCCEDED:
                    Log.e(tag,"展示图片");
                    //下载图片成功
                    LoadImageResult result = (LoadImageResult) msg.obj;
                    result.mImageView.setImageBitmap(result.mBitmap);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 下载url对应图片，并展示在imageview上
     *
     * @param url       要展示的图片的下载地址
     * @param imageView 展示图片用的view
     */
    public void bindImage(final String url, final ImageView imageView) {
        Runnable loadImageTask = new Runnable() {
            @Override
            public void run() {
                Log.e(tag,"开始下载图片");
                Bitmap bitmap = loadBitmap(url);
                if (bitmap != null) {
                    LoadImageResult result = new LoadImageResult(imageView, url, bitmap);
                    Message message = Message.obtain();
                    message.what = LOAD_BITMAP_SUCCEDED;
                    message.obj = result;
                    mHandler.sendMessage(message);
                }
            }
        };
        //线程池执行加载任务
        mExecutor.execute(loadImageTask);
    }

    /**
     * 根据url从网络上下载图片
     *
     * @param urlString 图片的网址
     * @return 图片
     */
    private Bitmap loadBitmap(String urlString) {
        Bitmap bitmap;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            //使用HttpURLConnection打开连接
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.connect();

            // 将得到的数据转化成InputStream
            InputStream is = urlConnection.getInputStream();
            // 将InputStream转换成Bitmap
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            if (bitmap != null) {
                Log.e(tag,"图片下载成功");
                return bitmap;
            }else{
                Log.e(tag,"图片下载失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}
