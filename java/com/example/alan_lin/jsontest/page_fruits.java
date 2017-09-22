package com.example.alan_lin.jsontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alan_Lin on 2016/12/6.
 */

public class page_fruits extends PageView{
    public page_fruits(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fruits, null);
        /*TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("Page fruit");*/
        addView(view);
    }

    @Override
    public void refreshView() {

    }
}
