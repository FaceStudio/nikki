package com.inspur.nikki.view.sample7;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class Sample07SquareImageView extends android.support.v7.widget.AppCompatImageView {
    public Sample07SquareImageView(Context context) {
        super(context);
    }

    public Sample07SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Sample07SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        } else {
            measuredHeight = measuredWidth;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
