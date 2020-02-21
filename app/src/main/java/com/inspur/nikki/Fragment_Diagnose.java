package com.inspur.nikki;

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

public class Fragment_Diagnose extends Fragment {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    animation.stop();
                    icon_connect.setBackground(getResources().getDrawable(R.mipmap.bg_menu_line));
                    icon_loading.clearAnimation();
                    icon_loading.setBackground(getResources().getDrawable(R.mipmap.done));
                    animation2.start();

                    break;
            }
        }
    };

    AppCompatImageView icon_connect,icon_connect2;

    AnimationDrawable animation,animation2;

    AppCompatImageView icon_loading,icon_loading2,icon_loading3;

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

//        if(NetUtils.isConnectRouter(getContext())){
//            handler.sendEmptyMessage(1);
//        }

        handler.sendEmptyMessageDelayed(1,2500);

    }

}
