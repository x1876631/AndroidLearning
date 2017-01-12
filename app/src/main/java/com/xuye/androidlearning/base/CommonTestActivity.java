package com.xuye.androidlearning.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 17/01/11
 * 点击测试基类页，默认展示第一项
 */
public abstract class CommonTestActivity extends AppCompatActivity implements View.OnClickListener {

    protected LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_test);
        mLayout = (LinearLayout) findViewById(R.id.common_test_layout);
    }

    protected void showItemWithCount(String[] nameArray) {
        if (nameArray.length <= 0 || nameArray.length > mLayout.getChildCount()) {
            return;
        }
        for (int i = 0; i < nameArray.length; i++) {
            View child = mLayout.getChildAt(i);
            if (child != null) {
                child.setVisibility(View.VISIBLE);
                if (child instanceof TextView) {
                    ((TextView) child).setText(nameArray[i]);
                    child.setOnClickListener(this);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.common_test_button1:
                clickButton1();
                break;
            case R.id.common_test_button2:
                clickButton2();
                break;
            case R.id.common_test_button3:
                clickButton3();
                break;
            case R.id.common_test_button4:
                clickButton4();
                break;
            default:
                break;
        }
    }

    protected abstract void clickButton1();

    protected void clickButton2() {}

    protected void clickButton3() {}

    protected void clickButton4() {}
}
