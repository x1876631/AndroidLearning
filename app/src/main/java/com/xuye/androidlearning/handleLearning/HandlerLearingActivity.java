package com.xuye.androidlearning.handleLearning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 17/01/12
 * handler学习
 */
public class HandlerLearingActivity extends CommonTestActivity {

    private static final String tag = "HandlerLearing";

    //线程内部的数据存储类，可以在指定的线程存取数据
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.send_handler_message)});
        initHandler();
    }

    private void initHandler() {
        mBooleanThreadLocal.set(true);
        Log.e(tag, "[Thread#main]mBooleanThreadLocal=" + mBooleanThreadLocal.get());

        new Thread("Thread#1") {
            @Override
            public void run() {
                mBooleanThreadLocal.set(false);
                Log.e(tag, "[Thread#1]mBooleanThreadLocal=" + mBooleanThreadLocal.get());
            }
        }.start();
    }

    @Override
    protected void clickButton1() {
        new Thread("Thread#2") {
            @Override
            public void run() {
                Log.e(tag, "[Thread#2]mBooleanThreadLocal=" + mBooleanThreadLocal.get());
                //在当前线程里存了个looper，此looper持有当前线程的引用，和一个新的消息队列
                Looper.prepare();
                /**
                 * 在子线程创建handler时，需要先创建looper，否则会报异常
                 * 创建handler时，这个handler会持有当前线程里的looper和消息队列
                 */
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                // TODO: 17/1/13   api 21、22 为什么没弹出toast？ 但api 23可以？
                                /**
                                 * api 21、22 为什么没弹出toast？ 但api 23可以？
                                 * 看了toast的源码，正常在TN类的handleShow方法里，调用mWM.addView(mView, mParams)后，
                                 * 就可以展示toast了，但是addView要展示view需要使用UI线程的handler，而现在是在子线程2里，所以无法展示。
                                 * 那为什么23可以展示呢？
                                 */
                                Toast.makeText(
                                        HandlerLearingActivity.this.getApplicationContext(), "[Thread#2 handle 0]",
                                        Toast.LENGTH_SHORT).show();

                                Log.e(tag, "[Thread#2 handle 0]");

                                //如果Looper不用了，记得退出，否则当前线程会一直运行不会退出
//                                Looper.myLooper().quit();
                                break;
                        }
                    }
                };
                /**
                 * handler发送了个消息，其实是在消息队里里插入了一条消息，消息的target是此handler
                 * 而之前Looper.loop();已经开启了循环读取消息，就会读到这条消息
                 * 然后调用消息里绑定的handler去处理这个消息，执行此handler的handleMessage
                 */
                handler.sendEmptyMessage(1);

                /**
                 *  Looper.loop();拿到当前线程的消息队列，无限循环地读取消息队列里的消息，
                 *  如果读到消息，就给用消息绑定的handler去处理这个消息，
                 *  如果读不到消息，就一直阻塞
                 *  因此，该线程也就一直被hold住，不会销毁了
                 *  ps：loop()方法要放到最后，否则之后的代码不会被执行到，因为loop开启了个死循环
                 */
                Looper.loop();
            }
        }.start();
    }

}
