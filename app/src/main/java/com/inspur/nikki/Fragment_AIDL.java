package com.inspur.nikki;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_AIDL extends Fragment implements AIDLService.OnLoginListener{

    private TextView tv_show;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_aidl, container, false);
        tv_show = (TextView) view.findViewById(R.id.tv_show);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getActivity(), AIDLService.class);
        getActivity().bindService(intent, mAIDLConnection, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AIDLService.MyBinder binder = (AIDLService.MyBinder) service;
            AIDLService aidlService = binder.getService();
            aidlService.setOnLoginListener(Fragment_AIDL.this);
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
                tv_show.setText(username + ", " + password);
                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
