package viewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xuye on 16/10/31
 * 一个可以跟手滑动的view
 */
public class FollowHandView extends View {

    private static final String tag = FollowHandView.class.getSimpleName();

    private int mLastX, mLastY;

    public FollowHandView(Context context) {
        super(context);
    }

    public FollowHandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowHandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //rawX/Y是相对于屏幕左上角的x和y
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //获取移动的距离
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                //根据移动的距离设置移动后的位置
                //TranslationX/Y是当前view左上角相对于父容器的偏移距离
                int translationX = (int) getTranslationX() + deltaX;
                int translationY = (int) getTranslationY() + deltaY;
                setTranslationX(translationX);
                setTranslationY(translationY);
                //通过打印top和left值发现，他们的值没有变化。正如注释里说的那样，他们是"原始"位置信息
                //当view位置移动时，变化的是x、y、translationX/Y。x、y是view左上角的坐标，其中x = left + translationX。
                Log.e(tag, "view top : " + getTop() + " , left : " + getLeft() +
                        " , translationX : " + (int) getTranslationX() + " , translationY :" + (int) getTranslationY());
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        //记录一下本次事件的x/y坐标，为下一次事件的处理做准备
        mLastX = x;
        mLastY = y;
        return true;
    }
}
