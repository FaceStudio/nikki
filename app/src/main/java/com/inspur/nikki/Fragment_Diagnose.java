package com.inspur.nikki;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.inspur.nikki.utils.NetUtils;
import com.inspur.nikki.utils.RootCmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class Fragment_Diagnose extends Fragment {

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    icon_loading.clearAnimation();
                    icon_loading.setBackground(getResources().getDrawable(R.mipmap.done));

                    break;

                case 2:
                    animation.stop();
                    icon_connect.setBackground(getResources().getDrawable(R.mipmap.bg_menu_line));

                    icon_loading2.clearAnimation();
                    icon_loading2.setBackground(getResources().getDrawable(R.mipmap.done));

                    animation2.start();
                    break;
                case 3:

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (NetUtils.checkNetwork()) {
                                handler.sendEmptyMessage(2);
                            }

                            if (NetUtils.checkIsDNSHijack()) {
                                handler.sendEmptyMessage(4);
                            }

                            if (NetUtils.checkIsArpSafe(getContext())) {
                                handler.sendEmptyMessage(5);
                            }

                        }
                    }).start();

                    break;

                case 4:
                    Log.i("Nikki", "DNS未被劫持!");
                    break;
                case 5:
                    Log.i("Nikki", "没有ARP攻击!");
                    break;
            }
        }
    };

    AppCompatImageView icon_connect, icon_connect2;
    AnimationDrawable animation, animation2;
    AppCompatImageView icon_loading, icon_loading2, icon_loading3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_diagnose, container, false);

        icon_connect = (AppCompatImageView) view.findViewById(R.id.cartoon_photo);
        icon_connect.setBackgroundResource(R.drawable.animation_list);
        animation = (AnimationDrawable) icon_connect.getBackground();
        animation.start();

        icon_connect2 = (AppCompatImageView) view.findViewById(R.id.cartoon_photo2);
        icon_connect2.setBackgroundResource(R.drawable.animation_list);
        animation2 = (AnimationDrawable) icon_connect2.getBackground();


        icon_loading = (AppCompatImageView) view.findViewById(R.id.icon_loading);
        icon_loading2 = (AppCompatImageView) view.findViewById(R.id.icon_loading2);
        icon_loading3 = (AppCompatImageView) view.findViewById(R.id.icon_loading3);

        //动画
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        icon_loading.startAnimation(animation);
        icon_loading2.startAnimation(animation);
        icon_loading3.startAnimation(animation);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (NetUtils.isConnectRouter(getContext())) {
            handler.sendEmptyMessageDelayed(1, 2000);
            handler.sendEmptyMessageDelayed(3, 4000);
        }
    }

}
