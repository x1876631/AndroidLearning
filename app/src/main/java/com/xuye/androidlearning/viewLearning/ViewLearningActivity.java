package com.xuye.androidlearning.viewLearning;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.Constant;
import com.xuye.androidlearning.base.util.ViewUtils;

/**
 * Created by xuye on 16/11/18
 * 自定义view学习相关的页面
 */
public class ViewLearningActivity extends AppCompatActivity {

    SuperCircleView mSuperCircleView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_learning);
        Log.e(Constant.tag, "当前页面的view深度是：" + ViewUtils
                .getViewMaxDeep(findViewById(R.id.main_activity_layout), 1, 0));

    }


    private void testCircle(){
        textView = (TextView) findViewById(R.id.tv);
        mSuperCircleView = (SuperCircleView) findViewById(R.id.superview);
        mSuperCircleView.setShowSelect(false);
        refreshCircleView();
        mSuperCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshCircleView();
            }
        });
    }

    private void refreshCircleView() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setTarget(textView);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = Integer.valueOf(String.valueOf(animation.getAnimatedValue()));
                textView.setText(i + "");
                mSuperCircleView.setSelect((int) (360 * (i / 100f)));
            }
        });
        valueAnimator.start();
    }
}
