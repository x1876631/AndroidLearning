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

    protected static final String tag = Constant.tag;

    //包含了按钮的linearLayout
    protected LinearLayout mButtonLayout;

    //除了几个固定按钮外，展示其他view时用到的一个容器布局
    protected LinearLayout mOtherLayout;

    //展示内容用的一个textview
    protected TextView mOtherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_test);
        mButtonLayout = (LinearLayout) findViewById(R.id.common_test_button_layout);
        mOtherLayout = (LinearLayout) findViewById(R.id.common_test_other_layout);
        mOtherTextView = (TextView) findViewById(R.id.common_test_other_text_view);
    }

    /**
     * @return 获取内容展示view
     */
    public TextView getContentTextView() {
        return mOtherTextView;
    }

    public LinearLayout getOtherLayout() {
        return mOtherLayout;
    }

    public void showOtherLayout(boolean isShow) {
        mOtherLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置按钮的展示与点击事件
     *
     * @param nameArray 按钮内容列表，有内容的按钮才展示并能点击
     */
    protected void showItemWithCount(String[] nameArray) {
        if (nameArray.length <= 0 || nameArray.length > mButtonLayout.getChildCount()) {
            return;
        }
        for (int i = 0; i < nameArray.length; i++) {
            View child = mButtonLayout.getChildAt(i);
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

    /**
     * 使用此页面的子类，必定是用到一个按钮的，所以必须实现第一个按钮的点击事件
     */
    protected abstract void clickButton1();

    protected void clickButton2() {}

    protected void clickButton3() {}

    protected void clickButton4() {}

    protected boolean setButton1Content(String content) {
        TextView textView = (TextView) findViewById(R.id.common_test_button1);
        if (textView != null) {
            textView.setText(content);
            return true;
        } else {
            return false;
        }
    }
}
