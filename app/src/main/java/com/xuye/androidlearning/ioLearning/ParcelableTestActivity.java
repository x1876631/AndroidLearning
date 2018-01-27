package com.xuye.androidlearning.ioLearning;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.xuye.androidlearning.R;
import com.xuye.androidlearning.base.CommonTestActivity;

/**
 * Created by xuye on 18/1/27
 */

public class ParcelableTestActivity extends CommonTestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showItemWithCount(new String[]{getString(R.string.io_parcelable_get_data)});
    }

    @Override
    protected void clickButton1() {
        Intent intent = getIntent();
        ParcelableObject po = intent.getParcelableExtra("po");
        Toast.makeText(getApplicationContext(), "获取了parcelable对象数据，name = " + po.name, Toast.LENGTH_LONG).show();
    }
}
