package com.example.alan_lin.jsontest;

/**
 * Created by Alan_Lin on 2016/12/8.
 */
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.View;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class navigate_fragment extends  Fragment{
    private OnItemSelectedListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.navigate_button, container, false);
        Button navigate_button = (Button) view.findViewById(R.id.button2);
        navigate_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //System.out.println("123123123123123123123123123123123");
                updateDetail();
            }
        });
        return view;
    }

    public interface OnItemSelectedListener{
        void onColorItemSelected();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof OnItemSelectedListener) {
                listener = (OnItemSelectedListener) activity;
            } else {
                throw new RuntimeException(activity.toString()
                        + " must implement ABC_Listener");
            }
        }
        //listener = (OnItemSelectedListener) activity;

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener){
            listener = (OnItemSelectedListener) context;
        }
        else{
            throw new ClassCastException(context.toString()+" must implement MyListFragment.OnItemSelectedListener");

        }
        //listener = (OnItemSelectedListener) context;
    }
    public void updateDetail() {
        try{
            listener.onColorItemSelected();
        }
        catch (Exception e){
            System.out.println("!!!"+ e.getMessage());
        }
    }
}

