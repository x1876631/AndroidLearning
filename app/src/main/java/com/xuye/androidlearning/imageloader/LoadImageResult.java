package com.xuye.androidlearning.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by xuye on 17/2/10
 * imageloader图片的加载结果
 */
public class LoadImageResult {
    public ImageView mImageView;
    public String mUrl;
    public Bitmap mBitmap;

    public LoadImageResult(ImageView imageView, String url, Bitmap bitmap) {
        this.mImageView = imageView;
        this.mUrl = url;
        this.mBitmap = bitmap;
    }
}
