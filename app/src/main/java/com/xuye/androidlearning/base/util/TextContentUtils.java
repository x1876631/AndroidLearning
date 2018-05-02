package com.xuye.androidlearning.base.util;

/**
 * Created by xuye on 2018/4/9.
 * 文本相关工具类
 */

public class TextContentUtils {

    /**
     * double值是否为0
     * 解决比如0.000000001==0为false的问题....
     */
    public static boolean isDoubleZero(double d) {
        return Math.abs(d) < 1e-6;
    }

    /**
     * double值是否为0
     */
    public static boolean isDoubleZeroTest(double d) {
        //测试了一下，这个没法解决0.00...1==0为false的问题....
        return Double.compare(d, 0) == 0;
    }

    /**
     * 判断两个{@link Number}类型在数值上是否相等
     *
     * @param left  待比较的数值类型
     * @param right 待比较的数值类型
     * @return 返回数值意义上的是否相等，注意如果都为null，也返回true
     */
    public static boolean isEqual(Number left, Number right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        if (isGeneralFloat(left) || isGeneralFloat(right)) {
            return Math.abs(left.doubleValue() - right.doubleValue()) < 0.0000001;
        }

        return left.longValue() == right.longValue();
    }

    private static boolean isGeneralFloat(Number number) {
        return (number instanceof Float || number instanceof Double);
    }
}
