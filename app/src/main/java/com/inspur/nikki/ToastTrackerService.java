package com.inspur.nikki;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class ToastTrackerService extends AccessibilityService {


    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        String str = (String) accessibilityEvent.getPackageName();
        if (!(accessibilityEvent.getParcelableData() instanceof android.app.Notification)) {

            try {
                PackageManager packageManager = getPackageManager();
                @SuppressLint("WrongConstant") String name = packageManager.getApplicationLabel(getPackageManager().getApplicationInfo(str, 128)).toString();
                @SuppressLint("WrongConstant") String packagename = getPackageManager().getApplicationInfo(str, 128).toString();

                Log.i("Nikki", "package name:" + packagename);

                showNotification(drawable2Bitmap(packageManager.getApplicationIcon(str)), name, packagename, getNotiID());

                return;
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {
                nameNotFoundException.printStackTrace();
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    private static final AtomicInteger c = new AtomicInteger(0);

    public static int getNotiID() {
        return c.incrementAndGet();
    }

    @SuppressLint("WrongConstant")
    public static Bitmap drawable2Bitmap(Drawable paramDrawable) {
        Bitmap.Config config;
        int i = paramDrawable.getIntrinsicWidth();
        int j = paramDrawable.getIntrinsicHeight();
        if (paramDrawable.getOpacity() != -1) {
            config = Bitmap.Config.ARGB_8888;
        } else {
            config = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = Bitmap.createBitmap(i, j, config);
        Canvas canvas = new Canvas(bitmap);
        paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
        paramDrawable.draw(canvas);
        return bitmap;
    }

    public void showNotification(Bitmap paramBitmap, String paramString1, String paramString2, int paramInt) {

        NotificationCompat.Builder builder = (new NotificationCompat.Builder(getApplicationContext())).setSmallIcon(R.drawable.java).setLargeIcon(paramBitmap);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Name:");
        stringBuilder2.append(paramString1);
        builder = builder.setContentTitle(stringBuilder2.toString());
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("PN:");
        stringBuilder1.append(paramString2);
        builder = builder.setContentText(stringBuilder1.toString()).setPriority(2);
        NotificationManagerCompat.from(getApplicationContext()).notify(paramInt, builder.build());
    }

}
