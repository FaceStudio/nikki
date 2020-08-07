package com.inspur.nikki;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class ToastTrackerService extends AccessibilityService {


    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        String str = (String)accessibilityEvent.getPackageName();
        if (!str.equals(getPackageName()) && !(accessibilityEvent.getParcelableData() instanceof android.app.Notification)) {

            try {
                PackageManager packageManager = getPackageManager();
                @SuppressLint("WrongConstant") String name = packageManager.getApplicationLabel(getPackageManager().getApplicationInfo(str, 128)).toString();
                @SuppressLint("WrongConstant") String packagename  = getPackageManager().getApplicationInfo(str, 128).toString();

                Log.i("Nikki","package name:"+packagename);

                return;
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {
                nameNotFoundException.printStackTrace();
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

}
