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

        Button hencoder = (Button) view.findViewById(R.id.hencoder);
        hencoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HenCoder5Activity.class);
                startActivity(intent);
            }
        });

        return view;

    }


}
