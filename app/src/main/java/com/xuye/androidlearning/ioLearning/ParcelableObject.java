package com.xuye.androidlearning.ioLearning;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuye on 18/1/27
 * parcelable官方文档：https://developer.android.com/reference/android/os/Parcelable.html
 * 一般用于传递对象数据，消耗的内存和时间比seralizable少，但是不能用于持久化到本地
 */
public class ParcelableObject implements Parcelable {

    public String name;
    public int age;

    protected ParcelableObject() {

    }

    protected ParcelableObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static final Creator<ParcelableObject> CREATOR = new Creator<ParcelableObject>() {

        /**
         * 在这个方法中反序列化上面的序列化内容，最后根据反序列化得到的各个属性，得到之前试图传递的对象
         * ps：反序列化的属性的顺序必须和之前写入的顺序一致
         * @param in    从内存读取的序列化对象数据
         * @return 反序列化回来的对象
         */
        @Override
        public ParcelableObject createFromParcel(Parcel in) {
            ParcelableObject po = new ParcelableObject();
            po.name = in.readString();
            po.age = in.readInt();
            return po;
        }

        @Override
        public ParcelableObject[] newArray(int size) {
            return new ParcelableObject[size];
        }
    };

    /**
     * 返回当前对象的描述内容，强制重写
     *
     * @return 返回当前对象的描述内容，一般返回0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将对象写入序列化，强制重写
     *
     * @param dest  系统提供的输出流，将成员变量存储到内存中
     * @param flags 1表示当前对象需要作为返回值保存。基本上所有情况都是0
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }


}
