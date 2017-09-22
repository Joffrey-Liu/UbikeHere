package com.example.alan_lin.jsontest;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by Alan_Lin on 2016/12/6.
 */

public abstract class PageView extends RelativeLayout {
    public PageView(Context context) {
        super(context);
    }
    public abstract void refreshView();
}
