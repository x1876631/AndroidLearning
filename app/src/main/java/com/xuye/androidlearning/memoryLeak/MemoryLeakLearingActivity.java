package com.xuye.androidlearning.memoryLeak;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;
import com.xuye.androidlearning.other.MemoryLeakTestHelper;

/**
 * Created by xuye on 17/11/16
 * 内存泄露知识学习
 */
public class MemoryLeakLearingActivity extends CommonTestActivity {

    private static final String tag = "MemoryLeak";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{
                getString(R.string.memory_leak_tip1), getString(R.string.memory_leak_tip2),
                getString(R.string.memory_leak_tip3)
        });
    }

    @Override
    protected void clickButton1() {

        /*
         * thread状态补充说明：
         * new Thread后，线程处于【新建】状态
         * 执行线程的start()方法，会使线程进入【就绪】状态
         * 然后等cpu调度这个线程时，会执行该线程的run方法，线程进入【运行】状态
         * 当run方法执行完后，线程就进入【死亡】状态
         */

        //让activity泄露的写法
        new Thread(new Runnable() {
            @Override
            public void run() {
                //泄露写法1，让thread还没运行完run方法时，退出activity
//                try {
//                    Log.e(tag, "当前线程休眠10s，请求10s内退出activity，以便测试效果");
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


                //泄露写法2，run运行完了，但是由于Thread被静态变量sThreadLocal持有，无法释放该线程以及里面的对象
                //Looper.prepare()后Looper类的静态变量sThreadLocal添加了一个新的Looper对象到里面，然后这个新的Looper对象持有当前线程
                Looper.prepare();
                Toast.makeText(
                        getApplicationContext(), getString(R.string.memory_leak_tip1), Toast.LENGTH_SHORT).show();
                Looper.loop();


                //这回就简单的打个log，让run方法执行完，也没有把thread给其他变量，看看还泄露吗？
//                Log.e(tag, "简单的打个log，让run方法执行完");
            }
        }).start();

        //让activity不泄露的写法
//        new Thread(new MyTestRunnable(getApplicationContext())).start();
    }

    /**
     * 直接new Thread(new Runnable())会内存泄露，因为Runnable是个非静态内部类，持有外部activity对象，
     * 当正在执行Runnable方法时退出activity，activity由于被runnable持有，runnable被Thread持有，
     * Thread可能因为各种原因还存活着，所以Thread在activity退出后会一直持有activity对象，使activity对象无法被回收
     * <p>
     * 【解决办法】
     * 把Runnable改成静态的内部类，不持有外部activity
     */
    private static class MyTestRunnable implements Runnable {

        private Context mContext;

        public MyTestRunnable(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void run() {
            Looper.prepare();
            Toast.makeText(mContext, "非UI线程，测试application弹toast", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    @Override
    protected void clickButton2() {
        super.clickButton2();
        //单例类MemoryLeakTestHelper持有该activity，导致activity泄露
        MemoryLeakTestHelper.getInstance().log(this);
    }

    private int handlerTestInt = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getContentTextView().setText("消息" + msg.arg1);
        }
    };

    @Override
    protected void clickButton3() {
        super.clickButton3();
        showOtherLayout(true);

        //延迟发送消息，当消息未执行前，退出activity，就会看到泄露
        Message message = Message.obtain();
        message.arg1 = ++handlerTestInt;
        mHandler.sendMessageDelayed(message, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //退出时，清空未执行的消息，避免消息延迟执行，导致的内存泄露
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 使用handler发送消息
     */
    private void sendMessage() {
        Message message = Message.obtain();
        message.arg1 = handlerTestInt++;
        mHandler.sendMessage(message);
    }
}
