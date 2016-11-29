package exceptionTest;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.xuye.androidlearning.R;

/**
 * Created by xuye on 16/11/25
 * 测试bug的DialogFragment
 */
public class TestDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable
            ViewGroup container,
            @Nullable
            Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_test_dialog, container);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        /**
         * 【解决展示相同tag的dialog时，fragment is added的bug】
         * 正常使用时，DialogFragment在show的时候会add一次
         * show以后点击弹窗外的屏幕部分，弹窗消失，DialogFragment执行onDismiss，fragment就被remove了，所以再次show也没有问题
         *
         * 【问题原因】
         * 但是，当快速点击，去show的时候，会瞬间执行多次show，每个show里都添加了一个add的事务，导致事务队列里有多个add事务。
         * 在执行事务时，如果已经add了，然后队列发现又要去add，这时就会报fragment is added异常
         *
         * 【解决办法】
         * 上面分析了异常原因，就是因为事务队列执行add时，发现已经add过了，不能再add。那我就在add前先remove一下，就好了。
         * 所以先添加个remove的事务，再去show(show的时候会add)，这样add之前就不会有add了(因为被remove掉了)
         */
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            //同一实例使用不同的tag会异常(通常不这么用，一般都是一个实例对应一个tag)，这里捕获一下
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.e(getClass().getSimpleName(), "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        Log.e(getClass().getSimpleName(), "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.e(getClass().getSimpleName(), "onCancel");
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.e(getClass().getSimpleName(), "onDismiss");
        super.onDismiss(dialog);
    }

    @Override
    public void onPause() {
        Log.e(getClass().getSimpleName(), "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e(getClass().getSimpleName(), "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e(getClass().getSimpleName(), "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(getClass().getSimpleName(), "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e(getClass().getSimpleName(), "onDetach");
        super.onDetach();
    }
}
