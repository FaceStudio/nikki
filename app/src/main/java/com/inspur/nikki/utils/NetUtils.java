package com.inspur.nikki.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static android.content.ContentValues.TAG;
import static android.net.sip.SipErrorCode.TIME_OUT;

public class NetUtils {

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public static Boolean isQQ = false;
    public static Boolean isTMall = false;

    //是否连接上路由器
    public static boolean isConnectRouter(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                if (networkInfo == null || !networkInfo.isConnected()) {
                    return networkInfo2 != null && networkInfo2.isConnected();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    //路由器能否ping通外网
    public static Boolean checkNetwork() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ping -c 3 www.baidu.com");
            int res = process.waitFor();
            return (res == 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean ping() {
        String result = null;
        try {
            String ip = "www.baidu.com";// 除非百度挂了，否则用这个应该没问题~
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping1次
            // 读取ping的内容，可不加。
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.i("TTT", "result content : " + stringBuffer.toString());
            // PING的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "successful~";
                return true;
            } else {
                result = "failed~ cannot reach the IP address";
            }
        } catch (IOException e) {
            result = "failed~ IOException";
        } catch (InterruptedException e) {
            result = "failed~ InterruptedException";
        } finally {
            Log.i("TTT", "result = " + result);
        }
        return false;
    }

    public String getNetSpeedWithTrafficStats(Context context) {
        String netSpeed = "0 kb/s";
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid)==
                TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed  = String.valueOf(speed) + " kb/s";
        return  netSpeed;
    }

    public String getNetSpeedWithNetworkInfo(Context context) {
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
    private static String runShellCommand(String command) {
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

    public ArrayList getArpInfo() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("/proc/net/arp"));
//            Thread.sleep(1000);
            reader.readLine();
            while (true) {
                String content = reader.readLine();
                if (content == null) {
                    break;
                }
                String[] arr = content.split(" ");
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Boolean checkIsArpSafe(Context context){

        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String bssid = wifiInfo.getBSSID();
        String ssid = wifiInfo.getSSID();
        String mac = wifiInfo.getMacAddress();

        Log.i("Nikki", "bssid-->" + bssid);
        Log.i("Nikki", "ssid-->" + ssid);
        Log.i("Nikki", "mac-->" + mac);

        //如果是unknown ssid ，就不要检测了，否则拿到bssid(即路由器的mac地址)，去arp文件里看看这个mac存在不存在，不存在，说明就被攻击了
        if(ssid.equalsIgnoreCase("<unknown ssid>")){
            return true;
        }

//        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
//        Log.i("Nikki","dhcpInfo.gateway-->"+dhcpInfo.gateway);
//        Log.i("Nikki","dhcpInfo.netmask-->"+dhcpInfo.netmask);
//        Log.i("Nikki","dhcpInfo.ipAddress-->"+dhcpInfo.ipAddress);
//        Log.i("Nikki","dhcpInfo.dns1-->"+dhcpInfo.dns1);
//        Log.i("Nikki","dhcpInfo.dns2-->"+dhcpInfo.dns2);//  https://www.baidu.com  https://www.tmall.com/
//        String v = RootCmd.execRootCmd("more /proc/net/route");
//        Log.i("Nikki", "answer:" + v);

        String s = RootCmd.execRootCmd("more /proc/net/arp");
        Log.i("Nikki", "执行more /proc/net/arp的answer:" + s);
//        String[] ss = s.split("/n");
//        int n = ss.length;
        if(s.contains(bssid)){
            return true;
        }

        return false;
    }


    public static Boolean checkIsDNSHijack(){

        final CountDownLatch ctl = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://tools.3g.qq.com/wifi/ssl");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.connect();

                    Log.i("Nikki", "QQCode:" + httpURLConnection.getResponseCode());

                    if (301 == httpURLConnection.getResponseCode() || 302 == httpURLConnection.getResponseCode()) {
                        String s = httpURLConnection.getHeaderField("Location");
                        String v = httpURLConnection.getHeaderField("Refresh");
                        String l = httpURLConnection.getResponseMessage();
                        Log.i("Nikki", v + "  " + s + "   " + l);

                        if(s.contains("baidu")||v.contains("baidu")||l.contains("baidu")){
                            isQQ = true;
                            ctl.countDown();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.tmall.com/");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.connect();

                    Log.i("Nikki", "TMallCode:" + httpURLConnection.getResponseCode());
                    if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                        isTMall = true;
                        ctl.countDown();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {

            ctl.await();

        } catch (Exception e) {

        }

        if(isQQ && isTMall){
            return true;
        }

        return false;
    }


}
