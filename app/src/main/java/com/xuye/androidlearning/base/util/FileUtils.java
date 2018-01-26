package com.xuye.androidlearning.base.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xuye on 17/2/13
 * 文件相关工具类
 */
public class FileUtils {

    /**
     * @param context  context
     * @param pathName 缓存路径最后的标识符
     * @return 返回本地缓存文件所在的文件夹路径
     */
    public static File getDiskCachePath(Context context, String pathName) {
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //如果此时外部存储设备可用(Environment.MEDIA_MOUNTED表示外部存储已经挂载，并且挂载点可读/写)，则使用外部存储路径
            path = context.getExternalCacheDir().getPath();
        } else {
            //否则用内部存储的路径
            path = context.getCacheDir().getPath();
        }
        //返回最终的本地缓存路径
        return new File(path + File.separator + pathName);
    }


    /**
     * 把图片url经过MD5转换一下
     * 避免图片保存成缓存文件时，由于url有特殊字符导致保存失败
     *
     * @param url 图片url
     * @return 经过MD5转换的url
     */
    public static String getKeyForUrl(String url) {
        String cacheKey = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            digest.update(url.getBytes());
            //获得转换后的密文
            byte[] urlBytes = digest.digest();
            return byteToHexString(urlBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //如果转换出现意外，就使用url的hashcode值作为key
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     * 将指定byte数组转换成16进制字符串，用于获取获取32位MD5值字符串
     *
     * @param bytes 要转换的byte数组
     * @return 转换后的16位进制串
     */
    private static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            //把byte值变为16进制值，如果是1位的，则补个0，以便最后凑成32位字符串
            int hex = 0xFF & b;
            if (hex < 16) {
                sb.append("0");
            }
            sb.append(String.valueOf(hex));
        }
        return sb.toString();
    }

}
