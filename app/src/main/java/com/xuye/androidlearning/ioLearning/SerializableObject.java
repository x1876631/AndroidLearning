package com.xuye.androidlearning.ioLearning;

import java.io.Serializable;

/**
 * Created by xuye on 18/1/27
 * 序列化，一般用于持久化数据，或者传输数据
 */
public class SerializableObject implements Serializable {

    //serialVersionUID是序列化版本号，序列化时一起写入字节流，反序列化时会比较一下版本，如果不一致会报错
//    private static final long serialVersionUID = 1L;

    public String field;//普通的实例对象，会被序列化
    public static int static_field = 456;//静态变量不会被序列化
    public transient int transient_field;//被transient修饰的变量不会序列化

    public SerializableObject(String field, int transient_field) {
        this.field = field;
        this.transient_field = transient_field;
    }

    @Override
    public String toString() {
        return "SerializableObject{" +
                "field='" + field + '\'' +
                ", transient_field=" + transient_field +
                '}';
    }
}
