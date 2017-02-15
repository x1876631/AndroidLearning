package com.xuye.androidlearning.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by xuye on 17/2/15
 * 图片处理类
 */
public class ImageResizer {
    private static final String tag = "ImageResizer";

    /**
     * 从FileDescriptor里获取处理后的bitmap
     *
     * @param fd         FileDescriptor
     * @param needWidth  图片需要的宽
     * @param needHeight 图片需要的高
     * @return 处理后的图片
     */
    public static Bitmap decodeBitmapFromFileDescriptor(FileDescriptor fd, int needWidth, int needHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置BitmapFactory的options=true，这样decode时，读取图片不会加载到内存，而只读取图片信息，如长宽等，用于后续压缩计算
        options.inJustDecodeBounds = true;
        //从FileDescriptor读取图片信息，记录到options里
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        //计算采样值，然后根据采样值对图片做压缩
        options.inSampleSize = calculateInSampleSize(options, needWidth, needHeight);
//        Log.e(tag, "采样值为：" + options.inSampleSize);
        //真正解析图片时，把inJustDecodeBounds恢复成false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int needWidth, int needHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        //inSampleSize为图片采样值，1表示维持原图大小，对宽高的处理公式：nowWidth = oldWidth/inSampleSize
        int inSampleSize = 1;
        //为什么上来就除2？
//        if (width > needWidth || height > needHeight) {
//            width = width/2;
//            height = height/2;
//        }
        while ((width / inSampleSize) > needWidth && (height / inSampleSize) > needHeight) {
            //采样值要保持为2的倍数，如果不为2，则会向下取整靠近2的倍数(比如size=3时，最终会取2)
            inSampleSize = inSampleSize * 2;
        }
        return inSampleSize;
    }
}
