package com.example.alan_lin.jsontest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.*;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.alan_lin.jsontest.R.layout.recommendmenu;

public class CaloriesActivity extends AppCompatActivity {

    private ImageView BackToMaps;
    private ImageView Redo;
    private Fragment input;
    private FragmentManager mfr;
    TextView weight_T;
    TextView height_T;
    TextView age_T;
    TextView Pic;
    TextView cal_goal_T;
    TextView cal;
    TextView dis;
    int id = 0;
    private SharedPreferences cal_settiing;
    static public int weight = 0;
    static public int height = 0;
    static public int age = 0;
    static public int gender = 0;
    static public int calgoal = 0;
    private ViewPager mViewPager;
    private List<PageView> pageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        cal_settiing = getSharedPreferences("CALDATA",0);
        mfr=getFragmentManager();
        input = new Input();
        cal =(TextView)findViewById(R.id.foodcal);
        readData();
        saveData();
        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        //id = getIntent().getIntExtra("userInput", 0);
        /*weight = getIntent().getIntExtra("weight",0);
        height = getIntent().getIntExtra("height",0);
        age = getIntent().getIntExtra("age",0);
        gender = getIntent().getIntExtra("gender",0);*/
        setText();

        if(id==0){
            mfr.beginTransaction().replace(R.id.activity_calories,input,"tag1").addToBackStack(null).commit();
        }

        SetToolBar();
        initData();
        initView();
        cal = (TextView)findViewById(R.id.cal_burned);
        cal.setText("  "+Integer.toString((int)(0.0416*weight*getIntent().getIntExtra("time_sec",0)/60)));
        dis = (TextView)findViewById(R.id.distance);
        dis.setText(Integer.toString((int)getIntent().getDoubleExtra("distance",0)));
    }

    public void HarrisBenedictCAL(){
        if(gender ==1)
            calgoal =(int) (66 + (13.7*weight) + (5*height) - (6.8*age));
        else
            calgoal =(int) (655 + (9.6*weight) + (1.7*height) - (4.7*age));
        cal_goal_T = (TextView) findViewById(R.id.cal_goal);
        cal_goal_T.setText(Integer.toString(calgoal));
    }

    public void setText() {
        HarrisBenedictCAL();
        Pic = (TextView) findViewById(R.id.cal_burned);
        if(gender == 1)
            Pic.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.boy,0,0);
        else
            Pic.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.girl,0,0);
        weight_T = (TextView) findViewById(R.id.weight_T);
        height_T = (TextView) findViewById(R.id.height_T);
        age_T = (TextView) findViewById(R.id.age_T);
        weight_T.setText(Integer.toString(weight));
        height_T.setText(Integer.toString(height));
        age_T.setText(Integer.toString(age));
        saveData();
    }

    public void readData(){
        weight = cal_settiing.getInt("weight",0);
        height = cal_settiing.getInt("height",0);
        age = cal_settiing.getInt("age",0);
        gender = cal_settiing.getInt("gender",0);
        id = cal_settiing.getInt("userInput",0);
        cal.setText(Integer.toString(cal_settiing.getInt("CURRENT",0)));
    }
    public void saveData(){
        cal_settiing.edit()
                .putInt("weight",weight)
                .putInt("height",height)
                .putInt("age",age)
                .commit();
    }

    private void SetToolBar() {
        back();
        redo();
        SetLogo();
    }

    private void redo() {
        Redo = (ImageView)findViewById(R.id.redo);
        Redo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cal_settiing = getSharedPreferences("DATA",0);
                cal_settiing.edit()
                        .putString("Distance","0")
                        .commit();
                Toast.makeText(getApplicationContext(), "重填表單", Toast.LENGTH_SHORT).show();
                mfr.beginTransaction().replace(R.id.activity_calories,input,"tag1").addToBackStack(null).commit();
            }
        });
    }

    private void back() {
        BackToMaps = (ImageView) findViewById(R.id.back);
        BackToMaps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Ubike", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("userInput", id);
                intent.putExtra("distance",getIntent().getDoubleExtra("distance",0));
                intent.setClass(CaloriesActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void SetLogo() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Carten.otf");
        TextView custom = (TextView)findViewById(R.id.Cal_LogoText);
        custom.setTypeface(font);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new Recommend (CaloriesActivity.this));
        pageList.add(new page_rice (CaloriesActivity.this));
        pageList.add(new page_meat(CaloriesActivity.this));
        pageList.add(new page_fruits(CaloriesActivity.this));
    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
