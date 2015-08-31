package com.xiaopeng.android_service;

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


public class MainActivity extends ActionBarActivity {

    private static String TAG = "MainActivity";

    private ServiceConnection mServiceConnection;

    private  boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void nextClicked(View view){
        Log.d(TAG, "nextClicked Clicked");
        startActivity(new Intent(this, NewActivity.class));

        // 正常情况应该在此处unbind service，否则会有警告
       // unbindService(mServiceConnection);
        this.finish();
    }

    public void startServiceClicked(View view){
        Log.d(TAG, "startService Clicked");
        startService(new Intent(this, LocalService.class));
    }

    public void stopServiceClicked(View view){
        Log.d(TAG, "stopServiceClicked Clicked");
        stopService(new Intent(this, LocalService.class));
    }

    public void bindServiceClicked(View view){
        isBind = true;
        Log.d(TAG, "bindServiceClicked Clicked");
        bindService(new Intent(this, LocalBindService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindServiceClicked(View view){
        isBind = false;
        Log.d(TAG, "unbindServiceClicked Clicked");
        unbindService(mServiceConnection);
    }

    public void startAndbindServiceClicked(View view){
        isBind = true;
        Log.d(TAG, "bindServiceClicked Clicked");
        startService(new Intent(this, LocalBindService.class));
        bindService(new Intent(this, LocalBindService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void stopAndunbindServiceClicked(View view){
        isBind = false;
        Log.d(TAG, "unbindServiceClicked Clicked");
        unbindService(mServiceConnection);
        stopService(new Intent(this, LocalBindService.class));
    }

    @Override
    protected void onDestroy() {
        if( isBind == true) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }
}
