package com.ifhu.meiwei.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * @author fuhongliang
 */
public abstract class AbstractToast extends Toast {

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public AbstractToast(Context context) {
        super(context);
    }

    @Override
    public abstract void setText(CharSequence s);

}
