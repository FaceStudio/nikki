package com.inspur.benny;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.inspur.nikki.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface mIMyAidlInterface;

    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIMyAidlInterface != null) {
                    try {
                        mIMyAidlInterface.login("nikki", "123456");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Intent intent = new Intent();
        // 服务端AndroidManifest.xml文件该Service所配置的action
        intent.setAction("com.inspur.nikki");
        // Service所在的包名
        intent.setPackage("com.inspur.nikki");
        bindService(intent, new ConnectCallBack(), Context.BIND_AUTO_CREATE);

    }



    public void login(View view) {
        if (mIMyAidlInterface != null) {
            try {
                mIMyAidlInterface.login("nikki", "123456");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    class ConnectCallBack implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyAidlInterface = null;
        }
    }





}
