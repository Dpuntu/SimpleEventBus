package com.dpuntu.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dpuntu.uibus.EventThread;
import com.dpuntu.uibus.SimpleEventBus;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleEventBus.getDefault().register(this);
        mTextView = (TextView) findViewById(R.id.click);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });
        startService(new Intent(MainActivity.this, ServiceTest.class));
    }

    @EventThread
    public void mainActivity(String str) {
        Log.e("bus_test", "MainActivity -> " + str);
    }

    @EventThread
    public void mainActivity(TestBean testBean) {
        Log.e("bus_test", "MainActivity -> " + testBean.str + "---" + testBean.i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleEventBus.getDefault().unRegister(this);
    }
}
