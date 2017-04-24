package com.herasoft.due.Others;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewAvenir extends AppCompatTextView{

    public TextViewAvenir(Context context) {
        super(context);
        font();
    }

    public TextViewAvenir(Context context, AttributeSet attrs) {
        super(context, attrs);
        font();
    }

    public TextViewAvenir(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        font();
    }

    private void font(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Avenir-Light.ttf");
        setTypeface(tf);
    }
}
