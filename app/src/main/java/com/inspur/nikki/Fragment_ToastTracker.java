package com.inspur.nikki;


import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;

import java.util.Iterator;

public class Fragment_ToastTracker extends Fragment {

    String SERVICE_ID = "com.inspur.nikki/.ToastTrackerService";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_toasttracker, container, false);

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

        if (!isAccessibilityEnabled(getContext(), this.SERVICE_ID)){
            Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            startActivity(intent);
        }
    }

    public static boolean isAccessibilityEnabled(Context paramContext, String paramString) {
        @SuppressLint("WrongConstant") Iterator<AccessibilityServiceInfo> iterator = ((AccessibilityManager)paramContext.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1).iterator();
        while (iterator.hasNext()) {
            if (paramString.equals(((AccessibilityServiceInfo)iterator.next()).getId()))
                return true;
        }
        return false;
    }
}
