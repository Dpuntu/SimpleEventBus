package com.dpuntu.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dpuntu.uibus.EventThread;
import com.dpuntu.uibus.SimpleEventBus;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */

public class OtherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleEventBus.getDefault().register(this);
        SimpleEventBus.getDefault().post("ddd");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @EventThread
    public void mainActivity(String str) {
        Log.e("bus_test", "OtherActivity -> " + str);
    }

    @EventThread
    public void mainActivity(TestBean testBean) {
        Log.e("bus_test", "OtherActivity -> " + testBean.str + "---" + testBean.i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleEventBus.getDefault().unRegister(this);
    }
}
