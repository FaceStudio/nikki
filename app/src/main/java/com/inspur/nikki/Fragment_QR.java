package com.inspur.nikki;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inspur.nikki.utils.QRCodeUtil;

public class Fragment_QR extends Fragment {



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_qrcode, container, false);

        ImageView mImageView = (ImageView) view.findViewById(R.id.iv);
        mImageView.setImageBitmap(QRCodeUtil.createQRCodeBitmap("陈震爱小宝宝，青青爱老公！", 500));

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
