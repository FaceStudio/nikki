package com.inspur.nikki;


import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.Iterator;

public class Fragment_ToastTracker extends Fragment {

    String SERVICE_ID = "com.inspur.nikki/.ToastTrackerService";
    private AppCompatButton setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_toasttracker, container, false);
        setting = (AppCompatButton) view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!isAccessibilityEnabled(getContext(), this.SERVICE_ID)) {
            Toast.makeText(getContext(), "请跳转到辅助功能进行设置！", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isAccessibilityEnabled(Context paramContext, String paramString) {
        @SuppressLint("WrongConstant") Iterator<AccessibilityServiceInfo> iterator = ((AccessibilityManager) paramContext.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1).iterator();
        while (iterator.hasNext()) {
            if (paramString.equals(((AccessibilityServiceInfo) iterator.next()).getId()))
                return true;
        }
        return false;
    }
}
