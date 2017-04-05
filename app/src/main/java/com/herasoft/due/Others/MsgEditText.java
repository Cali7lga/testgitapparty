package com.herasoft.due.Others;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * Created by Home on 30/03/2017.
 */

public class MsgEditText extends AppCompatEditText {

    public MsgEditText(Context context)
    {
        super(context);
    }

    public MsgEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MsgEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }

}
