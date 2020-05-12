package com.inspur.nikki;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inspur.nikki.utils.QRCodeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Fragment_Copy extends Fragment {

    private Button start;
    private TextView output;
    private WebSocket mSocket;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_copy,container,false);

        String path = "/mnt/sdcard/";
        copy(getContext(),"1.jpg",path,"chenzhen.jpg");

        ImageView mImageView = (ImageView) view.findViewById(R.id.iv);
        mImageView.setImageBitmap(QRCodeUtil.createQRCodeBitmap("陈震爱小宝宝，青青爱老公！", 500));


        start = (Button) view.findViewById(R.id.start);
        output = (TextView) view.findViewById(R.id.output);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                        .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                        .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                        .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                        .build();

                Request request = new Request.Builder().url("ws://echo.websocket.org").build();
                EchoWebSocketListener socketListener = new EchoWebSocketListener();
                mOkHttpClient.newWebSocket(request, socketListener);
                mOkHttpClient.dispatcher().executorService().shutdown();
            }
        });


        printResolution(getActivity());

        return view;
    }


    /**
     * 打印不包括虚拟按键的分辨率、屏幕密度dpi、最小宽度sw
     */
    public void printResolution(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int height= dm.heightPixels;
        int width= dm.widthPixels;
        int sw=context.getResources().getConfiguration().smallestScreenWidthDp;
        Log.i("Nikki","屏幕分辨率:" + width + "*" + height+",dpi:"+dm.densityDpi+",sw:"+sw);
    }


    private void copy(Context myContext, String ASSETS_NAME,
                      String savePath, String saveName) {
        String filename = savePath + "/" + saveName;
        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists())
            dir.mkdir();
        try {
            if (!(new File(filename)).exists()) {
                InputStream is = myContext.getResources().getAssets()
                        .open(ASSETS_NAME);
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            String openid = "1";
            //连接成功后，发送登录信息
            String message = "{\"type\":\"login\",\"user_id\":\""+openid+"\"}";
            mSocket.send(message);
            mSocket.send("welcome");
            mSocket.send(ByteString.decodeHex("adef"));
            mSocket.close(1000, "再见");
            output("连接成功！");
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            output("receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            output("receive text:" + text);
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            final String message = "{\"type\":\"heartbeat\",\"user_id\":\"heartbeat\"}";
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(message);
                }
            },25000);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("closed:" + reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("closing:" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("failure:" + t.getMessage());
        }
    }

    private void output(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(output.getText().toString() + "\n\n" + text);
            }
        });
    }
}
