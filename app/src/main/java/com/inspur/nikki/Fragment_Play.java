package com.inspur.nikki;


import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_Play extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView textureView;
    private Surface surface;
    private MediaPlayer mMediaPlayer;
    private Context context;



    String url_hls = "http://10.255.68.222:8090/icdn-srm/live.m3u8?st=GP19PnucHwp3egExRpeEAQ&e=1569576445&userid=test1111&folder=2&shifttime=0&channelid=cctv2-h&deviceid=&devicename=__Hi3798MV310&devicetype=0";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_play, container, false);
        textureView = (TextureView) view.findViewById(R.id.textureview);
        textureView.setSurfaceTextureListener(this);//设置监听函数 重写4个方法
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        surface = new Surface(surfaceTexture);

//        try {
//            mMediaPlayer= new MediaPlayer();
//            mMediaPlayer.setDataSource(context, Uri.parse(url_hls));
//            mMediaPlayer.setSurface(surface);
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp){
//                    mMediaPlayer.start();
//                }
//            });
//            mMediaPlayer.prepare();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

}
