package com.xuye.androidlearning.animationLearning;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;
import com.xuye.androidlearning.base.MeasureUtils;

/**
 * Created by xuye on 17/2/15
 * 动画学习页
 */
public class AnimationLearningActivity extends CommonTestActivity {

    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.click_to_langer), getString(R.string.test_scroll_animation)});
        showOtherLayout(true);
        mButton = new Button(this);
        mButton.setText(R.string.test_button);
        mButton.setBackgroundResource(R.color.A1);
        mButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mOtherLayout.addView(mButton);
    }

    @Override
    protected void clickButton1() {
        //点击使测试按钮变长
        if (mButton.getLayoutParams().width == MeasureUtils.getScreenHeight(AnimationLearningActivity.this)) {
            Toast.makeText(this, getString(R.string.button_is_changed), Toast.LENGTH_SHORT).show();
            return;
        }
        //使用ValueAnimator，监听动画过程，自己实现属性的改变，其实也可以把要改变的view包装一下，代码更简单些
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);//设置动画进度区间，1~100
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private IntEvaluator mIntEvaluator = new IntEvaluator();

            /**
             * 每当动画进度有所改变时，就会调用这个函数
             */
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前进度占整个动画过程的比例，在0~1之间
                float fraction = animation.getAnimatedFraction();
                //设置button的宽度，由原始宽度变为屏幕宽度
                mButton.getLayoutParams().width = mIntEvaluator.evaluate(fraction, mButton.getWidth(),
                        MeasureUtils.getScreenWidth(AnimationLearningActivity.this));
                mButton.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();//设置动画执行时间
    }

    @Override
    protected void clickButton2() {
        //点击去滑动隐藏展示view测试页
        startActivity(new Intent(this, ScrollTestActivity.class));
    }

    @Override
    public void finish() {
        super.finish();
        //退出时展示动画
        overridePendingTransition(0, R.anim.anim_exit);
    }
}
