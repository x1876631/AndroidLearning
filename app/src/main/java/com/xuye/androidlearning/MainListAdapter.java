package com.xuye.androidlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xuye on 16/12/27
 * 首页listview的适配器
 * ps：其实如果只是个展示文字的列表的话，可以用封装好的arrayAdapter，这里为了学习baseApdater，重写了下
 * 【关于适配器】
 * 适配器是一个adapterView和该view所需的数据之间的桥梁。
 * 适配器提供对数据项的访问。
 * 适配器同样负责为数据项创建一个视图。
 */
public class MainListAdapter extends BaseAdapter {

    private ArrayList<String> mData;
    private Context mContext;

    public MainListAdapter(Context context, ArrayList<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void refresh(ArrayList<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    /**
     * @return 适配器里的数据项的个数
     */
    @Override
    public int getCount() {
        if (mData == null || mData.isEmpty()) {
            return 0;
        } else {
            return mData.size();
        }
    }

    /**
     * 获取在数据集合中指定位置的数据项
     *
     * @param position 所需的数据项在数据集合中的位置
     * @return 要获取的数据项
     */
    @Override
    public Object getItem(int position) {
        if (mData == null || mData.isEmpty()) {
            return null;
        } else {
            return mData.get(position);
        }
    }

    /**
     * 获取与列表中指定位置关联的行id
     * 【注意】这里要获取的行id是展示在屏幕上的显示出来的行的位置
     * 比如当前列表有20项，屏幕上有10项，第一项的行id是0，但是在列表里的position可能是11这种情况
     * 所以如果要得到显示在屏幕上的行id需要对position做个处理
     * 不过通常我们不用这个行id，都用position...
     *
     * @param position 想要的行id在数据集合中对应的位置
     */
    @Override
    public long getItemId(int position) {
        return position;

    }

    /**
     * 获取一个在数据集合指定位置展示数据的视图。
     * 你可以手动创建视图，或者从xml布局中获取。
     * 当视图被获取到后，父视图（GridView，ListView…）将采用默认的布局参数，
     * 除非你使用inflate(int, android.view.ViewGroup, boolean)方法，指定一个根视图并且禁止附着根视图？
     *
     * @param position    该视图要展示的数据项在数据集里的位置
     * @param convertView 复用的旧视图，如果能复用的话。
     *                    注意：使用前应检查此视图为非空和适当类型。如果该旧视图不能展示正确的数据，则会创建一个新视图。
     *                    因为异构列表(?)能指定视图类型的数量，所以该旧视图总是正确的类型
     * @param parent      获取到的视图所依附的父控件(就是listview)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //如果只有一种类型的布局，就不用对holder做类型判断
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_common_click, null);
            holder.contentView = (TextView) convertView.findViewById(R.id.common_click_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.contentView.setText(mData.get(position));
        return convertView;
    }


    private class ViewHolder {
        TextView contentView;//内容展示按钮
    }
}
