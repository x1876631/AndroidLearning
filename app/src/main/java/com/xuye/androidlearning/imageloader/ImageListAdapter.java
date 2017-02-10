package com.xuye.androidlearning.imageloader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.MeasureUtils;

import java.util.ArrayList;

/**
 * Created by xuye on 17/2/9
 * 图片墙适配器
 */
public class ImageListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mUrlList;

    public ImageListAdapter(Context context, ArrayList<String> urlList) {
        this.mContext = context;
        this.mUrlList = urlList;
    }

    @Override
    public int getCount() {
        if (mUrlList == null || mUrlList.isEmpty()) {
            return 0;
        } else {
            return mUrlList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mUrlList == null || mUrlList.isEmpty()) {
            return null;
        } else {
            return mUrlList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mUrlList == null || mUrlList.isEmpty()) {
            return null;
        }
        /***
         * 如果每次都创建一个新的view，消耗会很大，所以android为了减少开销使用了复用机制
         * 比如一个屏幕最多展示10个列表项，下滑展示第11项时，就不会再创建新的view而是复用之前的已经创建的item，这个复用的item就是convertView
         * 然后给convertview对应的布局重新设置下数据，就可以返回使用了
         *
         * 那能使用复用的item，为什么还要用holder呢？holder是干嘛的？
         * holder就是个item的布局缓存。
         * 如果不用holder的话，每次都要使用findViewById()获取一遍convertView的布局，
         * 用holder后就不用每次都获取布局，直接就使用holder设置数据就好了
         */
        ImageListHolder holder;
        if (convertView == null) {
            //如果不能复用已有的item，则新创建一个布局给convertView，并设置holder
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_image_list, null);
            holder = new ImageListHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_list_item);
            //把布局缓存绑定到item上，方便下次复用时，快速获取布局设置数据
            convertView.setTag(holder);
        } else {
            //如果已经有可复用的item，则重新设置下数据
            holder = (ImageListHolder) convertView.getTag();
        }

        //设置下图片的宽高
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(
                MeasureUtils.getScreenWidth(mContext) / 3, MeasureUtils.getScreenWidth(mContext) / 3));

        //如果图片有url，则加载并展示url对应的图片，否则展示默认图片
        if (TextUtils.isEmpty(mUrlList.get(position))) {
            holder.imageView.setBackgroundResource(R.drawable.image_default_background);
        } else {
            //使用图片加载器去加载图片
            ImageLoader.getInstance(mContext).bindImage(mUrlList.get(position), holder.imageView);
        }
        return convertView;
    }

    private static class ImageListHolder {
        ImageView imageView;
    }
}
