package com.example.alan_lin.jsontest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alan_Lin on 2016/12/18.
 */

public class Refresh extends Fragment {

    ImageView Refresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.refresh, container, false);
        Refresh = (ImageView)view.findViewById(R.id.refresh_img);
        Refresh.setImageDrawable(getResources().getDrawable(R.drawable.refresh));
        Refresh.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "重新整理", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
