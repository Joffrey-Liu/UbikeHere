package com.example.alan_lin.jsontest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by user on 2016/12/2.
 */
public class Input  extends Fragment {

    EditText weight_ET;
    EditText height_ET;
    EditText age_ET;
    TextView error_weight;
    TextView error_height;
    TextView error_age;
    TextView error_gender;
    ImageView finish;
    ImageView male;
    ImageView female;
    private int weight = 0;
    private int height = 0;
    private int age = 0;
    private int gender = 0; // 1 for male, 2 for female, 0 for notinout
    private android.app.Fragment input;
    private FragmentManager mfr;
    private SharedPreferences cal_settiing;
    public int CAL = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input, container, false);
        cal_settiing = this.getActivity().getSharedPreferences("CALDATA",0);
        init(view);
        /*input = new Input();
        mfr.beginTransaction().hide(input).commit();*/
        return view;
    }

    private void init(View view) {
        male = (ImageView) view.findViewById(R.id.imageView6);
        female = (ImageView) view.findViewById(R.id.imageView7);
        finish = (ImageView) view.findViewById(R.id.imageView4);
        weight_ET = (EditText) view.findViewById(R.id.weight_ET);
        height_ET = (EditText) view.findViewById(R.id.height_ET);
        age_ET = (EditText) view.findViewById(R.id.age_ET);
        error_weight = (TextView)view.findViewById(R.id.error_weight);
        error_height = (TextView) view.findViewById(R.id.error_height);
        error_age = (TextView) view.findViewById(R.id.error_age);
        error_gender = (TextView) view.findViewById(R.id.error_gender);


        male.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "選擇男性", Toast.LENGTH_SHORT).show();
                male.setImageDrawable(getResources().getDrawable(R.drawable.malepicked));
                female.setImageDrawable(getResources().getDrawable(R.drawable.female));
                gender =1;
            }
        });

        female.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "選擇女性", Toast.LENGTH_SHORT).show();
                female.setImageDrawable(getResources().getDrawable(R.drawable.femalepicked));
                male.setImageDrawable(getResources().getDrawable(R.drawable.male));
                gender =2;
            }
        });

        finish.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                //To register the button with context menu.
                if(height!=0 && weight!=0 && age!=0 && gender!=0){
                    /*Intent intent = new Intent();
                    intent.setClass(getActivity(), CaloriesActivity.class);
                    intent.putExtra("userInput", 1);
                    intent.putExtra("weight",weight);
                    intent.putExtra("height",height);
                    intent.putExtra("age",age);
                    intent.putExtra("gender",gender);
                    startActivity(intent);
                    HarrisBenedictCAL();
                    cal_settiing.edit()
                            .putInt("calgoal",CAL)
                            .commit();*/
                    cal_settiing.edit()
                            .putInt("userInput",1)
                            .putInt("weight",weight)
                            .putInt("height",height)
                            .putInt("age",age)
                            .putInt("gender",gender)
                            .commit();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CaloriesActivity.class);
                    startActivity(intent);

                }
                else{
                    if(weight == 0)
                        error_weight.setText("*必填");
                    if(height == 0)
                        error_height.setText("*必填");
                    if(age == 0)
                        error_age.setText("*必填");
                    if(gender == 0)
                        error_gender.setText("*必填");
                }
                }
        });



        weight_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    weight = Integer.parseInt(s.toString());
                }catch (NumberFormatException e){
                    weight = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        height_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    height = Integer.parseInt(s.toString());
                }catch (NumberFormatException e){
                    height = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        age_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    age = Integer.parseInt(s.toString());
                }catch (NumberFormatException e){
                    age = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    public void HarrisBenedictCAL(){
        if(gender ==1)
            CAL =(int) (66 + (13.7*weight) + (5*height) - (6.8*age));
        else
            CAL =(int) (655 + (9.6*weight) + (1.7*height) - (4.7*age));

    }

}
