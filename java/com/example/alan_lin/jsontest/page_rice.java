package com.example.alan_lin.jsontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alan_Lin on 2016/12/6.
 */

public class page_rice extends PageView{

    public page_rice(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.rice, null);
        //TextView textView = (TextView) view.findViewById(R.id.tvLabel);
        //textView.setText("Page rice");
        addView(view);
    }

    @Override
    public void refreshView() {

    }
}

