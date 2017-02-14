package com.xuye.androidlearning.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.xuye.androidlearning.base.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * 2、优化1，增加内存与本地缓存：由于每次滑动都会触发加载，而最基本的加载每次都是从网络下载，非常耗费流量，所以增加缓存，节省流量
 */
public class ImageLoader {

    private static final String tag = "ImageLoader";

    private static ImageLoader sInstance;
    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;//内存缓存
    private DiskLruCache mDiskCache;//本地磁盘缓存
    private static final int DISK_CACHE_COUNT = 1024 * 1024 * 50;//本地磁盘缓存大小
    private boolean mIsDiskCacheCreated = false;//本地磁盘缓存是否创建
    private static final int DISK_CACHE_INDEX = 0;//本地磁盘缓存保存文件时需要的一个参数，一般为0
    private static final int IO_BUFFER_SIZE = 8 * 1024;//io读写缓存值：8m

    private ImageLoader(Context context) {
        this.mContext = context.getApplicationContext();

        //初始化内存缓存，maxMemory()是该进程从操作系统中能获取的最大内存字节数
        int maxMemoryCount = (int) Runtime.getRuntime().maxMemory() / 1024;
        Log.e(tag, "该应用能获取的最大内存为：" + maxMemoryCount / 1024 + "M");
        int lruMemoryCache = maxMemoryCount / 8;//lruCache使用最大可用内存的1/8作为自己的容量
        mMemoryCache = new LruCache<String, Bitmap>(lruMemoryCache) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //getByteCount()表示bitmap在运行时占用的内存大小，单位是字节
                return bitmap.getByteCount() / 1024;
            }
        };

        //初始化本地缓存
        File diskCacheFile = FileUtils.getDiskCachePath(context, "imageloader_disk_cache");
        if (!diskCacheFile.exists()) {
            diskCacheFile.mkdirs();
        }
        if (diskCacheFile.getUsableSpace() > DISK_CACHE_COUNT) {
            //如果本地存储位置的可用空间 > 设定的本地磁盘缓存最大值，则可以初始化diskLruCache
            try {
                mDiskCache = DiskLruCache.open(diskCacheFile, 1, 1, DISK_CACHE_COUNT);
                mIsDiskCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                    //下载图片成功
                    LoadImageResult result = (LoadImageResult) msg.obj;
//                    Log.e(tag, "展示图片，url：" + result.mUrl);
                    result.mImageView.setImageBitmap(result.mBitmap);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 加载url对应图片，并展示在imageview上
     *
     * @param url       要展示的图片的下载地址
     * @param imageView 展示图片用的view
     */
    public void bindImage(final String url, final ImageView imageView) {
        Runnable loadImageTask = new Runnable() {
            @Override
            public void run() {
//                Log.e(tag, "开始下载图片，url：" + url);
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
     * 使用多级缓存，加载图片
     *
     * @param urlString 图片url
     * @return 要展示的图片
     */
    private Bitmap loadBitmap(String urlString) {
        //先从内存缓存查找，没有则继续下一级加载
        Bitmap bitmap = loadBitmapFromMemory(urlString);
        if (bitmap != null) {
            return bitmap;
        }
        //如果内存缓存找不到，再从本地磁盘缓存里查找，没有则继续下一级加载
        if ((bitmap = loadBitmapFromDisk(urlString)) != null) {
            return bitmap;
        }
        //如果本地磁盘缓存里也没有，则从网络里下载
        if ((bitmap = loadBitmapFromNet(urlString)) != null) {
            return bitmap;
        }
        //如果经过3次加载都获取不到数据，那很可能是从网络获取后保存到本地失败了，再单纯地从网络获取下
        if (!mIsDiskCacheCreated) {
            return loadBitmapFromNetOnly(urlString);
        }
        return null;
    }

    /**
     * 根据url从网络上下载图片，只下载图片，不保存在本地
     *
     * @param urlString 图片的网址
     * @return 图片
     */
    private Bitmap loadBitmapFromNetOnly(String urlString) {
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
//                Log.e(tag, "图片下载成功，url：" + url);
                return bitmap;
            } else {
                Log.e(tag, "图片下载失败，url：" + url);
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

    /**
     * 从网络下载图片，并保存在本地缓存
     *
     * @param urlString 图片url
     * @return 要展示的图片
     */
    private Bitmap loadBitmapFromNet(String urlString) {
        //如果本地没有缓存，那就无法保存在本地，则直接返回，使用loadBitmapFromNetOnly()获取图片把
        if (mDiskCache == null) {
            return null;
        }

        String cacheKey = FileUtils.getKeyForUrl(urlString);
        try {
            DiskLruCache.Editor editor = mDiskCache.edit(cacheKey);
            if (editor != null) {
                OutputStream out = editor.newOutputStream(DISK_CACHE_INDEX);
                if (downLoadBitmapFromNet(urlString, out)) {
                    //下载完成并保存成功，则提交一下
                    editor.commit();
                } else {
                    //下载or保存失败，则回滚该操作
                    editor.abort();
                }
                //将内存中的操作记录同步到日志文件(也就是journal文件)当中，如果不同步则无法正常操作本地缓存
                mDiskCache.flush();
            }
            //从本地读取刚刚下载的图片
            return loadBitmapFromDisk(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从本地磁盘缓存里读取图片
     *
     * @param urlString 图片url
     * @return 要展示的图片
     */
    private Bitmap loadBitmapFromDisk(String urlString) {
        if (mDiskCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String cacheKey = FileUtils.getKeyForUrl(urlString);
        try {
            //使用DiskLruCache的Snapshot拿到缓存文件，然后用BitmapFactory解析成bitmap
            DiskLruCache.Snapshot snapshot = mDiskCache.get(cacheKey);
            if (snapshot != null) {
                FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                // TODO: 17/2/14  这里保持了bitmap原本的大小，其实应该对其大小做一下处理，优化2做
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                Log.e(tag, "从本地磁盘缓存获取图片成功! url：" + urlString);
            }
            //如果从本地读取到了图片，则添加到内存缓存中，因为现在使用的图片，很大概率上一会还会用到，所以添加到缓存中，方便下次使用
            if (bitmap != null) {
                addBitmapToMemory(cacheKey, bitmap);
            }
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把刚刚用过的本地缓存，添加到内存缓存里
     *
     * @param cacheKey 缓存文件的key，用于查找对应的缓存文件
     * @param bitmap   缓存文件
     */
    private void addBitmapToMemory(String cacheKey, Bitmap bitmap) {
        if (getBitmapFromMemory(cacheKey) == null) {
            mMemoryCache.put(cacheKey, bitmap);
        }
    }

    /**
     * 从内存缓存中获取图片
     *
     * @param cacheKey 内存缓存图片对应的key，是个md5值
     * @return 要展示的图片
     */
    private Bitmap getBitmapFromMemory(String cacheKey) {
        return mMemoryCache.get(cacheKey);
    }

    /**
     * 第一级的加载图片：从内存缓存中获取图片
     *
     * @param urlString 图片的url
     * @return 要展示的图片
     */
    private Bitmap loadBitmapFromMemory(String urlString) {
        String cacheKey = FileUtils.getKeyForUrl(urlString);
        if (getBitmapFromMemory(cacheKey) != null) {
            Log.e(tag, "从内存缓存获取图片成功! url：" + urlString);
            return getBitmapFromMemory(cacheKey);
        } else {
            return null;
        }
    }

    /**
     * 执行从网络上下载图片，并传给本地磁盘缓存
     *
     * @param urlString    图片url
     * @param outputStream 本地缓存输出流
     * @return 是否下载完成并保存成功
     */
    private boolean downLoadBitmapFromNet(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream bo = null;
        BufferedInputStream io = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.connect();

            // 将得到的数据转化成InputStream
            io = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bo = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = io.read()) != -1) {
                /**
                 *  不断的从输入流读取(即从网络下载的图片数据)，并写入输出流里(本地缓存)
                 *  读到最后时，返回的值为-1。
                 */
                bo.write(b);
            }
            //读取完毕记得关闭输入输出流
            io.close();
            bo.close();
            Log.e(tag, "图片下载成功，url：" + urlString);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(tag, "图片下载失败，url：" + urlString);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return false;
    }

}
