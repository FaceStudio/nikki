package com.inspur.nikki;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetUtils {

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    /**
     * 得到网络速度
     * @param context
     * @return
     */
//    public String getNetSpeed(Context context) {
//        String netSpeed = "0 kb/s";
//        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid)==
//                TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB;
//        long nowTimeStamp = System.currentTimeMillis();
//        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
//
//        lastTimeStamp = nowTimeStamp;
//        lastTotalRxBytes = nowTotalRxBytes;
//        netSpeed  = String.valueOf(speed) + " kb/s";
//        return  netSpeed;
//    }


    public String getNetSpeed(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return null;//无连接
        }
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return null;//无连接
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            String speed = wifiInfo.getLinkSpeed() + wifiInfo.LINK_SPEED_UNITS;
            return speed;
        } else if (type == ConnectivityManager.TYPE_ETHERNET) {
            String speed = runShellCommand("cat /sys/class/net/eth0/speed").trim() + "Mbps";
            return speed;
        }
        return null;
    }

    //执行shell命令
    public static String runShellCommand(String command) {
        Runtime runtime;
        Process proc = null;
        StringBuffer stringBuffer = null;
        try {
            runtime = Runtime.getRuntime();
            proc = runtime.exec(command);
            stringBuffer = new StringBuffer();
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e2) {
            }
        }
        return stringBuffer.toString();
    }

}
