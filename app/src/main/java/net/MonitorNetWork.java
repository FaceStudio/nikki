package net;


import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MonitorNetWork {

    private Context mContext;
    public OkHttpClient mOkHttpClient;

    public MonitorNetWork(Context context) {
        mContext = context;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 包含header、body数据
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //http数据log，日志中打印出HTTP请求&响应数据
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }




    /**
     * 文件上传
     * 录像取证
     *
     * @param reporturl
     * @param file
     * @param mNetCallBack
     */
    public void reportFile(String reporturl, File file, NetCallBack mNetCallBack) {
        Request.Builder builder = new Request.Builder();
        MediaType mutilpart_tyoe = MediaType.parse("application/octet-stream");
        Request request = builder.url(reporturl).method("POST", MultipartBody.create(mutilpart_tyoe, file))
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(createCallBack(mNetCallBack));
    }


    /**
     * 数据上传
     * 频道列表、EPG列表数据上传
     *
     * @param reporturl
     * @param byteData
     * @param mNetCallBack
     */
    public void reportData(String reporturl, byte[] byteData, NetCallBack mNetCallBack) {
        Request.Builder builder = new Request.Builder();
        MediaType mutilpart_tyoe = MediaType.parse("application/octet-stream");
        Request request = builder.url(reporturl).method("POST", MultipartBody.create(mutilpart_tyoe, byteData))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(createCallBack(mNetCallBack));
    }



    private Callback createCallBack(final NetCallBack mNetCallBack) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mNetCallBack != null) {
                    mNetCallBack.onCallFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i("Nikki","result = " + result);
                    if (mNetCallBack != null) {
                        mNetCallBack.onCallSuccess(result);
                    }
                } else {
                    if (mNetCallBack != null) {
                        mNetCallBack.onCallFailed();
                    }
                }
            }
        };
        return callback;
    }


}
