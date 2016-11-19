package viewLearning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuye on 16/11/18
 * 自定义view学习，只是简单的绘制一个圆
 * <p/>
 * 【注意】
 * 1、直接继承view，需要对padding做处理，否则padding会失效
 * 2、直接继承view，需要对wrap_content做处理，否则wrap_content效果等同于match_parent
 * 为什么会导致上面的问题呢？看源码
 */
public class CircleView extends View {

    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //获取圆的宽高，例如：圆的宽度 = view的宽度 - view的左右padding
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        //获取要绘制的圆的半径
        int radius = Math.min(width, height) / 2;
        //绘制圆，padding作用于圆上
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }
}
