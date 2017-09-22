package com.example.alan_lin.jsontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alan_Lin on 2016/12/6.
 */

public class page_meat extends PageView{
    public page_meat(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.meat, null);
        //TextView textView = (TextView) view.findViewById(R.id.tvLabel2);
        //textView.setText("Page meat");
        addView(view);
    }

    @Override
    public void refreshView() {

    }
}
