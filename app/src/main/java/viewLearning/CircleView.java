package viewLearning;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 16/11/18
 * 自定义view学习，只是简单的绘制一个圆
 * <p/>
 * 【注意】
 * 【Q1】直接继承view，需要对padding做处理，否则padding会失效
 * 【Q2】直接继承view，需要对wrap_content做处理，否则wrap_content效果等同于match_parent
 * 为什么会导致上面的问题呢？
 */
public class CircleView extends View {

    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //默认宽高值，单位都是px
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 500;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        //这里不能用super，而是调用使用三个参数的构造函数，xml布局中使用自定义view调用此构造函数
        //【Q3】为什么在xml布局里用的是这个构造函数，而不是直接用第三个？他们三个有什么区别，分别在什么时候被使用？
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对设置的wrap_content做下处理
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //如果父容器的宽高类型都是AT_MOST，则给该view一个确定的宽高
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            //上面没执行，说明宽高有不是AT_MOST的，那么给是AT_MOST的宽或者高，设置一个默认值；另一个不是AT_MOST的使用传入的确定值
            setMeasuredDimension(DEFAULT_WIDTH, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, DEFAULT_HEIGHT);
        }
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
