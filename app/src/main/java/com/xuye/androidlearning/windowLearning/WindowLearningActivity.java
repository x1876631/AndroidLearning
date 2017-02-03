package com.xuye.androidlearning.windowLearning;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 17/02/03
 * window学习页
 */
public class WindowLearningActivity extends CommonTestActivity {

    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.add_button), getString(R.string.delete_button)});
    }

    @Override
    protected void clickButton1() {
        if (mButton == null) {
            mButton = new Button(this);
            mButton.setText(R.string.button_added);
            mButton.setBackgroundColor(getResources().getColor(R.color.A10));
            //添加一个200*200的按钮，window type需要是2~xx才能显示
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                    200, 200,
                    2, 0, PixelFormat.TRANSPARENT);
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            //按钮展示的位置在(100,300)
            layoutParams.x = 100;
            layoutParams.y = 300;
            //把按钮添加到这个activity对应的window里
            getWindowManager().addView(mButton, layoutParams);
        } else {
            Toast.makeText(this, R.string.button_is_added, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void clickButton2() {
        if (mButton != null) {
            getWindowManager().removeView(mButton);
            mButton = null;
        } else {
            Toast.makeText(this, R.string.button_is_empty, Toast.LENGTH_SHORT).show();
        }
    }
}
