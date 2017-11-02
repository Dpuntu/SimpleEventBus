package com.dpuntu.eventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dpuntu.uibus.EventThread;
import com.dpuntu.uibus.SimpleEventBus;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */

public class ServiceTest extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SimpleEventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SimpleEventBus.getDefault().post(new TestBean("fmx", 233));
        SimpleEventBus.getDefault().post("fmx");
        return super.onStartCommand(intent, flags, startId);
    }

    @EventThread
    public void mainService(String str) {
        Log.e("bus_test ", "ServiceTest -> " + str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SimpleEventBus.getDefault().unRegister(this);
    }
}
