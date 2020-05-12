package com.inspur.nikki.view;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.inspur.nikki.R;
import com.inspur.nikki.utils.PxUtils;

/**
 * 这个类是用来做 ImageView 外部的可调整框架的，不必关注
 */
public class Common07AdjustablePanel extends RelativeLayout {
    FrameLayout parentLayout;
    AppCompatSeekBar heightBar;
    AppCompatSeekBar widthBar;

    float bottomMargin = PxUtils.dpToPixel(48);
    float minWidth = PxUtils.dpToPixel(80);
    float minHeight = PxUtils.dpToPixel(100);

    public Common07AdjustablePanel(Context context) {
        super(context);
    }

    public Common07AdjustablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Common07AdjustablePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        parentLayout = (FrameLayout) findViewById(R.id.parentLayout);
        widthBar = (AppCompatSeekBar)findViewById(R.id.widthBar);
        heightBar = (AppCompatSeekBar)findViewById(R.id.heightBar);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int percent, boolean b) {
                LayoutParams layoutParams = (LayoutParams) parentLayout.getLayoutParams();
                layoutParams.width = (int) (minWidth + (Common07AdjustablePanel.this.getWidth()
                        - minWidth) * widthBar.getProgress() / 100);
                layoutParams.height = (int) (minHeight + (Common07AdjustablePanel.this.getHeight()
                        - bottomMargin - minHeight) * heightBar.getProgress() / 100);
                parentLayout.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        widthBar.setOnSeekBarChangeListener(listener);
        heightBar.setOnSeekBarChangeListener(listener);
    }
}
