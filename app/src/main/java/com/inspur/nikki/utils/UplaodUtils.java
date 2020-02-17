package com.inspur.nikki.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UplaodUtils {
    /**
     * fileName 文件名(不带后缀)
     * filePath 文件的本地路径 (xxx / xx / test.jpg)
     */

    public static  void uploadFile(String strServerUrl, String strFile) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(strServerUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            /* 设置传送的method=POST */
            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            String strFileName = strFile.substring(strFile.lastIndexOf("/") + 1);
            /* 设置DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"file1\";filename=\"" +
                    strFileName + "\"" + end);
            ds.writeBytes(end);
            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(strFile);
            /* 设置每次写入1024bytes */
            int bufferSize = 10240;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            fStream.close();
            ds.flush();

            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = con.getResponseCode();
            Log.e("Nikki", "response code:" + res);

            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            /* 关闭DataOutputStream */
            ds.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
