package com.example.alan_lin.jsontest;

/**
 * Created by Liu on 2016/12/8.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weather_fragment  extends  Fragment{
    ImageView imageView;
    TextView WeatherText;
    private SharedPreferences settings;
    public static String dsec;
    public RelativeLayout mLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_frag, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView5);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.celsius));
        WeatherText = (TextView) view.findViewById(R.id.WeatherTextView);
        mLayout = (RelativeLayout) view.findViewById(R.id.weather);
        settings = this.getActivity().getSharedPreferences("WEATHER",0);
        dsec = settings.getString("W", null);
        System.out.println("weather describe: "+dsec+" ~~~~~~~~~~~~~~~~~~~~");
        WeatherText.setText("晴時多雲");
       /*if(dsec.equals("Moderate or heavy sleet showers")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.heavyrain));
        }
        else if(dsec.equals("Light rain shower") ||dsec.equals("Patchy light rain") || dsec.equals("Light rain")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.rain));
        }
        else if(dsec.equals("Mist")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.mist));
        }
        else if(dsec.equals("overcast")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.overcast));
        }
        else if(dsec.equals("Patchy light drizzle")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.rain));
        }
        else if(dsec.equals("Cloudy")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.cloudy));
        }
        else if(dsec.equals("Partly cloudy")){
            System.out.println("123");
            mLayout.setBackground(getResources().getDrawable(R.drawable.cloudy));
        }
        else if(dsec.equals("Clear/Sunny")){
            mLayout.setBackground(getResources().getDrawable(R.drawable.sunny));
        }
        else{
            System.out.println("not");
        }*/
        return view;
    }

    public void set_weather_background(LocalWeather lw){
        try {
            if (mLayout == null) {
                System.out.println("the weather layout is null~~~~~~~~~~~~~~~~~~~~~");
            } else
                System.out.println("the weather layout is exist~~~~~~~~~~~~~~~~~~~~~");

            System.out.println("weather describe: " + lw.weatherdeSC + " ~~~~~~~~~~~~~~~~~~~~");

            if (lw.weatherdeSC.equals("Moderate or heavy sleet showers")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.heavyrain));
                WeatherText.setText("預防豪大雨");
            } else if (lw.weatherdeSC.equals("Light rain shower") || lw.weatherdeSC.equals("Patchy light rain") || lw.weatherdeSC.equals("Light rain")) {
                WeatherText.setText("陰時多雨");
                mLayout.setBackground(getResources().getDrawable(R.drawable.rain));
            } else if (lw.weatherdeSC.equals("Mist")) {
                WeatherText.setText("薄霧瀰漫");
                mLayout.setBackground(getResources().getDrawable(R.drawable.mist));
            } else if (lw.weatherdeSC.equals("overcast")) {
                WeatherText.setText("陰時多雲");
                mLayout.setBackground(getResources().getDrawable(R.drawable.overcast));
            } else if (lw.weatherdeSC.equals("Patchy light drizzle")) {
                WeatherText.setText("毛毛細雨");
                mLayout.setBackground(getResources().getDrawable(R.drawable.rain));
            } else if (lw.weatherdeSC.equals("Cloudy")) {
                WeatherText.setText("晴時多雲");
                mLayout.setBackground(getResources().getDrawable(R.drawable.cloudy));
            } else if (lw.weatherdeSC.equals("Partly cloudy")) {
                WeatherText.setText("多雲時晴");
                System.out.println("123");
                mLayout.setBackground(getResources().getDrawable(R.drawable.cloudy));
            } else if (lw.weatherdeSC.equals("Clear/Sunny")) {
                WeatherText.setText("風和日麗");
                mLayout.setBackground(getResources().getDrawable(R.drawable.sunny));
            } else {
                System.out.println("not");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"!!!!!!!!!!!!!");
        }

    }
}


