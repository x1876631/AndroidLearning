package com.xuye.androidlearning.viewLearning;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 18/1/28
 * 自定义组合view
 * 参考1：https://www.jianshu.com/p/c84693096e41
 * 文字绘制：https://www.jianshu.com/p/1728b725b4a6
 */
public class ComposeView extends View {


    private static final String tag = "xuye";

    /**
     * 在代码中实例化的时候执行
     */
    public ComposeView(Context context) {
        this(context, null);
        Log.e(tag, "执行了1个参数的view初始化");
    }

    /**
     * 在XML布局文件中使用的时候执行，并且是没有使用Style来定义要使用的属性
     */
    public ComposeView(Context context,
            @Nullable
                    AttributeSet attrs) {
        this(context, attrs, 0);
        Log.e(tag, "执行了2个参数的view初始化");
    }

    /**
     * 在XML布局文件中使用，并且指定了style的时候执行
     * 自定义属性参考：https://www.jianshu.com/p/28f67b61b294
     */
    public ComposeView(Context context,
            @Nullable
                    AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(tag, "执行了3个参数的view初始化");

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ComposeView);
        mCircleColor = ta.getColor(R.styleable.ComposeView_compose_circle_color, getResources().getColor(R.color.A1));
        ta.recycle();  //注意回收

        initPaint();
    }

    private int mCircleRight = 0;//圆的右边距
    private int mCircleTop = 0;//圆的上间距
    private int mTextMarginLeft = 20;
    private int mCircleR = 10;//圆的半径
    private Paint mPaint;
    private int mCircleColor;
    private static final String TEXT1 = "中文123+abc";
    private static final String TEXT2 = "英文456";


    /**
     * 初始化画笔设置
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.A1));
        mPaint.setTextSize(45f);
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(600, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(600, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }

    /**
     * 自定义绘制，文字绘制：https://www.jianshu.com/p/1728b725b4a6
     * <p>
     * 顺带一提各种getXX()的作用，参考：https://www.jianshu.com/p/5b6e2d936a78
     * getLeft():获取当前view到父view左边距的距离
     * getTop():获取当前view到父view上边距的距离
     * getRight():获取当前view到父view右边距的距离
     * getBottom():获取当前view到父view下边距的距离
     * getX():点击的那个点，到当前view的左边距的距离
     * getY():点击的那个点，到当前view的上边距的距离
     * getRawX():点击的那个点，到屏幕左边距的距离
     * getRawY():点击的那个点，到屏幕上边距的距离
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画个圆
        drawCircle(canvas);


        Paint.FontMetrics fm = mPaint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        int textHeight = (int) (fm.descent - fm.ascent);

        int left = mCircleRight + mTextMarginLeft;
        int top = mCircleTop + textHeight;

        //再画个文字，文字和圆top对齐。顺带一提：y坐标是绘制文本的baseline在屏幕上的位置，这个值还需要再调整
        canvas.drawText(TEXT1, mCircleRight + mTextMarginLeft, top, mPaint);


        //再画个文字，文字bottom和圆bottom对齐
        canvas.drawText(TEXT2, mCircleRight + mTextMarginLeft, mCircleTop + 2 * mCircleR, mPaint);
    }


    private void drawCircle(Canvas canvas) {
        int r = getMeasuredHeight() / 4;//半径是1/4高
        mCircleR = r;

        //圆心的横坐标
        int centerX = getLeft() + 2 * r;
        mCircleRight = centerX + r;

        //圆心的纵坐标
        int centerY = getTop() + 2 * r;
        mCircleTop = centerY - r;

        Paint paint = new Paint();
        paint.setColor(mCircleColor);
        canvas.drawCircle(centerX, centerY, r, paint);
    }

}
