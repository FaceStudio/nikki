package com.inspur.nikki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.iv); //http://www.baidu.com
        mImageView.setImageBitmap(QRCodeUtil.createQRCodeBitmap("陈震爱小宝宝，青青爱老公！", 400));
    }
}
