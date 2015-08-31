package com.xiaopeng.android_service;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class NewActivity extends ActionBarActivity {

    private static String TAG = "NewActivity";
    private ServiceConnection mServiceConnection;
    private  boolean isBind = false;

    private TextView mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected");

                LocalBindService.SimpleBinder binder = (LocalBindService.SimpleBinder)service;
                int ret = binder.Add(2, 3);
                Log.d(TAG, String.valueOf(ret));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceConnected");
            }
        };

        mInfo = (TextView)this.findViewById(R.id._info);
    }

    private boolean ServiceIsStart(List<ActivityManager.RunningServiceInfo> list, String className) {// 判断某个服务是否启动
        for (int i = 0; i < list.size(); i++) {
            if (className.equals(list.get(i).service.getClassName()))
                return true;
        }
        return false;
    }

    private String getServicesName(List<ActivityManager.RunningServiceInfo> list) {// 获取所有服务的名称
        String res = "";
        for (int i = 0; i < list.size(); i++) {
            res += list.get(i).service.getClassName() + "/n";
        }
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void stopServiceInNewAcitvityClicked(View view){
        Log.d(TAG, "stopServiceClicked Clicked");
        stopService(new Intent(this, LocalService.class));
    }

    public void bindServiceNewAcitvityClicked(View view){
        isBind = true;
        Log.d(TAG, "bindServiceClicked Clicked");
        bindService(new Intent(this, LocalBindService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindServiceNewAcitvityClicked(View view){
        isBind = false;
        Log.d(TAG, "unbindServiceClicked Clicked");
        unbindService(mServiceConnection);
    }

    public void getServiceNewAcitvityClicked(View view){
        ActivityManager activityManger = (ActivityManager) getSystemService(ACTIVITY_SERVICE);// 获取Activity管理器
        List<ActivityManager.RunningServiceInfo> serviceList = activityManger.getRunningServices(30);// 从窗口管理器中获取正在运行的Service
        mInfo.setText(getServicesName(serviceList));
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
