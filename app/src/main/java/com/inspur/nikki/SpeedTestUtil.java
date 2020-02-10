package com.inspur.nikki;

import android.content.Context;
import android.util.Log;

import net.BaseNetCallBack;
import net.MonitorNetWork;

import java.io.File;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class SpeedTestUtil {


    static long b = 0;

    public static long speed(final Context context) {

        final CountDownLatch ctl = new CountDownLatch(1);

        final File file = new File("/mnt/mine.psd");
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

        long speed = ((file.length()*1000 / 8 / 1024) / (b - a));

        return speed;

    }
}
