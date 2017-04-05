package com.herasoft.due.Others;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Home on 15/09/2016.
 */
public class MyLinearLayout extends LinearLayout {

    private float scale = PagerAdapter.BIG_SCALE;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScaleBoth(float scale) {
        this.scale = scale;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(scale, scale, w / 2, h / 2);

        super.onDraw(canvas);
    }

}
