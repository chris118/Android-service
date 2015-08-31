package com.xiaopeng.android_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xiaopeng wang on 2015/8/31.
 */
public class LocalBindService extends Service {

    private static String TAG = "LocalBindService";

    public class SimpleBinder extends Binder {

        public int Add(int a, int b){
            Log.d(TAG, "Add");
            return a + b;
        }
    }

    private SimpleBinder mBinder;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
       // return super.onUnbind(intent);

       // 将onunbind的返回值改为true，再次进入activity时会调用onrebind方法
        return  true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();

        mBinder = new SimpleBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
