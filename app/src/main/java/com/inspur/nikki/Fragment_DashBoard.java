package com.inspur.nikki;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.inspur.nikki.utils.DownloadUtil;
import com.inspur.nikki.view.DashboardView;


public class Fragment_DashBoard extends Fragment {

    DashboardView dashboardView;
    SeekBar seekBar;
    Button start;

    DownloadUtil.OnDownloadListener listener = new DownloadUtil.OnDownloadListener() {
        @Override
        public void onDownloadSuccess() {
            Log.i("Nikki","测速完成");

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),"测速完成！",Toast.LENGTH_LONG).show();
                }
            });

        }

        @Override
        public void onDownloading(final int progress) {
            Log.i("Nikki","DashBoard-->speed:"+progress);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dashboardView.setPercent(progress*100/1900);
                }
            });
        }

        @Override
        public void onDownloadFailed() {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dashboard, container, false);
        dashboardView = (DashboardView) view.findViewById(R.id.dashboardView);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dashboardView.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        start = (Button) view.findViewById(R.id.bt_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadUtil.get().download("http://pcclient.download.youku.com/youkuclient/youkuclient_setup_7.9.2.1151.exe","/download",listener);
            }
        });

        return view;
    }


}
