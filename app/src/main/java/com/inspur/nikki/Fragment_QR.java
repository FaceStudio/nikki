package com.inspur.nikki;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.inspur.nikki.utils.QRCodeUtil;

public class Fragment_QR extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_qrcode, container, false);

        ImageView mImageView = (ImageView) view.findViewById(R.id.iv);
        mImageView.setImageBitmap(QRCodeUtil.createQRCodeBitmap("陈震爱小宝宝，青青爱老公！", 500));

        Button hencoder1 = (Button) view.findViewById(R.id.hencoder1);
        hencoder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        Button hencoder2 = (Button) view.findViewById(R.id.hencoder2);
        hencoder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });

        Button hencoder3 = (Button) view.findViewById(R.id.hencoder3);
        hencoder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",3);
                startActivity(intent);
            }
        });


        Button hencoder4 = (Button) view.findViewById(R.id.hencoder4);
        hencoder4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",4);
                startActivity(intent);
            }
        });


        Button hencoder5 = (Button) view.findViewById(R.id.hencoder5);
        hencoder5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",5);
                startActivity(intent);
            }
        });

        Button hencoder6 = (Button) view.findViewById(R.id.hencoder6);
        hencoder6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",6);
                startActivity(intent);
            }
        });

        Button hencoder7 = (Button) view.findViewById(R.id.hencoder7);
        hencoder7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoderActivity.class);
                intent.putExtra("type",7);
                startActivity(intent);
            }
        });


        return view;

    }


}
