package viewLearning;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 16/11/18
 * 自定义view学习，只是简单的绘制一个圆
 * <p/>
 * 【注意】
 * 【Q1】直接继承view，需要对padding做处理，否则padding会失效。为什么要padding会失效?
 * 【Q2】直接继承view，需要对wrap_content做处理，否则wrap_content效果等同于match_parent。为什么要wrap_content会失效?
 */
public class CircleView extends View {

    private static final String tag = CircleView.class.getSimpleName();

    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //默认宽高值，单位都是px
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 500;

    /**
     * 简单的构造方法，在代码中动态创建view时调用
     */
    public CircleView(Context context) {
        super(context);
    }

    /**
     * 【Q3】为什么在xml布局里用的是这个构造函数，而不是直接用第三个？
     * 【A3】看源码注释，如果从xml里初始化，会调用第二个，而第2个会默认调用更多参数的构造方法，其实相当于调用了第3个
     * 【Q4】他们三个有什么区别，分别在什么时候被使用？
     * 【A4】源码注释里有介绍，如下：
     * 1、只有一个参数的构造方法，在使用代码动态创建时被调用
     * 2、多参数的构造方法，在xml里使用时被调用。最开始调用2个参数的构造方法，在里面默认依次调用更多参数的构造方法
     * 3、多参数的不同：
     * 2个参数：只是获取了xml里为该view设置的属性(attrs)；
     * 3、4个参数：可以根据传入的主题(defStyleAttr)设置该view的属性默认值;
     * 注意：attrs的值会覆盖其他参数里的设置
     * <p/>
     * xml布局中使用自定义view调用此构造函数
     *
     * @param context context
     * @param attrs   attrs
     */
    public CircleView(Context context, AttributeSet attrs) {
        //这里不能只用super，需要调用该view自身设置了获取自定义属性的代码的那个构造方法，否则无法正确使用自定义属性
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setViewAttribute(context, attrs);
        init();
    }

    /**
     * 初始化该view的自定义属性
     * <p/>
     * attrs是从xml里解析的所有该view的属性，其实里已经有了该view所需要的自定义属性，可以直接用
     * 但是如果在xml里赋的值是引用的话，getAttributeValue获取的就是引用地址而不是正确的值
     * 所以还需要用typedArray挨个解析、初始化一下
     */
    private void setViewAttribute(Context context, AttributeSet attrs) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            Log.e(tag, "属性名：" + attrName + " , 属性值：" + attrVal);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        typedArray.recycle();
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
