package com.inspur.nikki;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class AIDLAcitivity extends AppCompatActivity implements AIDLService.OnLoginListener {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aidl);
        mTv = (TextView) findViewById(R.id.tv_show);
        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, mAIDLConnection, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AIDLService.MyBinder binder = (AIDLService.MyBinder) service;
            AIDLService aidlService = binder.getService();
            aidlService.setOnLoginListener(AIDLAcitivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new Handler();

    @Override
    public void login(final String username, final String password) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTv.setText(username + ", " + password);
                Toast.makeText(AIDLAcitivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
