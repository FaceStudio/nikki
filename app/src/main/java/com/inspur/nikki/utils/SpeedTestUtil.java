package com.inspur.nikki.utils;

import android.content.Context;
import android.util.Log;

import net.BaseNetCallBack;
import net.MonitorNetWork;

import java.io.File;
import java.util.concurrent.CountDownLatch;


public class SpeedTestUtil {


    static long b = 0;

    public static long speed(final Context context) {

        final CountDownLatch ctl = new CountDownLatch(1);

        final File file = new File("/mnt/mine.psd");//10M左右文件
        final String url = "http://60.208.86.91:8080/iptv-task/speedtest";

        long a = System.currentTimeMillis();


        new Thread(new Runnable() {
            @Override
            public void run() {


                new MonitorNetWork(context).reportFile(url, file, new BaseNetCallBack() {
                    @Override
                    public void onCallSuccess(String s) {
                        super.onCallSuccess(s);

                        Log.i("Nikki", "len:" + file.length());
                        b = System.currentTimeMillis();

                        ctl.countDown();
                    }

                    @Override
                    public void onCallFailed() {
                        super.onCallFailed();
                        Log.i("Nikki", "speedTest failed..");
                    }
                });
            }
        }).start();

        try {

            ctl.await();

        } catch (Exception e) {

        }

        Log.i("Nikki", "a:" + a + "  b：" + b);

        long speed = ((file.length()*1000 / 1024) / (b - a)); //字节传输

        return speed;

    }
}
