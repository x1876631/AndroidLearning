package exceptionTest;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by xuye on 16/11/25
 * 测试bug的DialogFragment
 */
public class TestDialogFragment extends DialogFragment {


    @Override
    public void show(FragmentManager manager, String tag) {
        /**
         * 【解决fragment is added的bug】
         * 正常使用时，DialogFragment在show的时候会add一次，show以后点击屏幕，弹窗消失，fragment就被remove了，再show的时候也没有问题
         *
         * 【问题原因】
         * 但是，当快速点击，去show的时候，会瞬间执行多次show，每个show里都添加了一个add的事务，导致事务队列里有多个add事务。
         * 在执行事务时，如果已经add了，然后队列发现又要去add，这时就会报fragment is added异常
         *
         * 【解决办法】
         * 上面分析了异常原因，就是因为事务队列执行add时，发现已经add过了，不能再add。那我就在add前先remove一下，就好了。
         * 所以先添加个remove的事务，再去show(show的时候会add)，然后立刻使用executePendingTransactions去执行一下事务队列
         */
        manager.beginTransaction().remove(this).commit();
        super.show(manager, tag);
        manager.executePendingTransactions();
    }
}
