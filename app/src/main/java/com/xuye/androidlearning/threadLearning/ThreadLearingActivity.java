package com.xuye.androidlearning.threadLearning;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xuye on 17/01/13
 * 线程学习页
 * 1、AsyncTask：一个封装好的简单的异步任务类，内部由Handler和Thread实现，只能执行一次
 * 2、注意各sdk版本AsyncTask实现的不同
 */
public class ThreadLearingActivity extends CommonTestActivity {

    private static final String tag = "ThreadLearingActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.AsyncTask_learning)});
        showOtherLayout(true);
        getContentTextView().setText(R.string.file_load_loading);
    }

    /**
     * 模仿文件下载的异步task
     * AsyncTask<Params, Progress, Result>各参数解析：
     * 1、Params：执行后台任务时所需的参数 的类型
     * 2、Progress：执行进度 的类型
     * 3、Result：后台任务执行结束后返回值 的类型
     */
    private class DownLoadFilesTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mOtherTextView.setText(R.string.file_load_begin);
        }

        @Override
        protected Long doInBackground(URL... urls) {
            long totalSize = 0;//下载文件的bit数
            for (int i = 0; i < 100; i += 1) {
                try {
                    Thread.sleep(10);
                    publishProgress(i);
                    totalSize += 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return totalSize;
        }

        /**
         * 后台任务执行进度回调
         *
         * @param progressArray 进度数组，取第一个值就好
         */
        @Override
        protected void onProgressUpdate(Integer... progressArray) {
            getContentTextView().setText(String.format(getString(R.string.current_load_progress), progressArray[0]));
        }

        /**
         * 后台任务完成回调
         *
         * @param aLong 任务返回的结果
         */
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            getContentTextView().setText(R.string.file_load_succeded);
            Toast.makeText(
                    ThreadLearingActivity.this, getString(R.string.file_load_succeded), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void clickButton1() {
        //启动任务时，可以传入复数的后台任务所需的参数
        URL[] urlList = null;
        try {
            urlList = new URL[]{new URL("www.baidu.com"), new URL("www.sogou.com")};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            /**
             * 各版本的AsyncTask源码实现不同，
             * 比如Api 22以前，必须在UI线程创建实例，否则无法操作UI修改，
             * 而22以后，源码中直接获取主线程的Looper，所以可以在子线程里创建AsyncTask实例
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new DownLoadFilesTask().execute();
                }
            }).start();
        } else {
            new DownLoadFilesTask().execute(urlList);
        }
    }

    @Override
    protected void clickButton2() {
    }

    @Override
    protected void clickButton3() {
    }

}
