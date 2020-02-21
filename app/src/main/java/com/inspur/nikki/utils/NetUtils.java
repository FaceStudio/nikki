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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.net.sip.SipErrorCode.TIME_OUT;

public class NetUtils {

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;


    private static final String TAG = "Nikki";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码


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

    /**
     * 得到网络速度
     *
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

    /**
     * 上传文件到服务器
     *
     * @param uploadUrl 上传服务器地址
     * @param filePath  本地文件路径
     */
    public static void uploadFile(String uploadUrl, String filePath) {
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // 允许Input、Output，不使用Cache
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            // 设置传送的method=POST
            con.setRequestMethod("POST");
            //在一次TCP连接中可以持续发送多份数据而不会断开连接
            con.setRequestProperty("Connection", "Keep-Alive");
            //设置编码
            con.setRequestProperty("Charset", "UTF-8");
            //text/plain能上传纯文本文件的编码格式
//            con.setRequestProperty("Content-Type", "text/plain");

            // 设置DataOutputStream
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            // 取得文件的FileInputStream
            FileInputStream fStream = new FileInputStream(filePath);
            // 设置每次写入1024bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;
            // 从文件读取数据至缓冲区
            while ((length = fStream.read(buffer)) != -1) {
                // 将资料写入DataOutputStream中
                ds.write(buffer, 0, length);
            }
            ds.flush();
            fStream.close();
            ds.close();
            if (con.getResponseCode() == 200) {
                Log.i("Nikki", "文件上传成功！上传文件为：" + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Nikki", "文件上传失败！上传文件为：" + filePath);
            Log.e("Nikki", "报错信息toString：" + e.toString());
        }
    }

    /**
     * Android上传文件到服务端
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                // if(res==200)
                // {
                Log.e(TAG, "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                Log.e(TAG, "result : " + result);
                // }
                // else{
                // Log.e(TAG, "request error");
                // }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
