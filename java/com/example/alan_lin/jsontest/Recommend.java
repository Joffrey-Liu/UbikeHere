package com.example.alan_lin.jsontest;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * Created by Alan_Lin on 2016/12/7.
 */

public class Recommend extends PageView{

    TextView[] T = new TextView[8];
    TextView foodcal;
    private SharedPreferences Cal_cal;
    private int cal_goal;
    private int current_cal=0;
    int count =0;
    boolean Done = false;
    static private int []riceCAL={225,330,313,389,210,140,180,210};
    static private int [] meatCAL={390,242,232,198,208,155,81,76};
    static private int [] fruitCAL={88,88,41,23,46,39,32,57};
    static private String []riceName={"白飯\n(熱量:225)","義大利麵\n(熱量:330)","吐司\n(熱量:313)","燕麥\n(熱量:389)","饅頭\n(熱量:210)","白粥\n(熱量:140)","馬鈴薯\n(熱量:180)","玉米\n(熱量:210)"};
    static private String []meatName={"牛肉\n(熱量:390)","豬肉\n(熱量:242)","雞肉\n(熱量:232)","羊肉\n(熱量:198)","鮭魚\n(熱量:208)","蛋\n(熱量:155)","碗豆\n(熱量:81)","豆腐\n(熱量:76)"};
    static private String []fruitName={"紅蘿蔔\n(熱量:88)","捲心菜\n(熱量:88)","洋蔥\n(熱量:41)","花椰菜\n(熱量:23)","鳳梨\n(熱量:46)","草莓\n(熱量:39)","檸檬\n(熱量:32)","葡萄\n(熱量:57)"};
    int[] riceImageList = new int[]{R.drawable.whiterice, R.drawable.spaghetti,R.drawable.bread,R.drawable.oat,R.drawable.steamedbread,R.drawable.porridge,R.drawable.potato,R.drawable.corn};
    int[] meatImageList = new int[]{R.drawable.steak, R.drawable.pork,R.drawable.chicken,R.drawable.lamb,R.drawable.fish,R.drawable.egg,R.drawable.beans,R.drawable.tofu};
    int[] fruitImageList = new int[]{R.drawable.carrot, R.drawable.cabbage,R.drawable.onion,R.drawable.cauliflower,R.drawable.pineapple,R.drawable.strawberry,R.drawable.lemon,R.drawable.grapes};
    Random r = new Random();
    int random;
    public Recommend(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.recommendmenu, null);
        inti(view);
        Cal_cal = context.getSharedPreferences("CALDATA",0);
        cal_goal = Cal_cal.getInt("calgoal",0);
        if(!Done){
            menu();
        }
        Cal_cal.edit()
                .putInt("CURRENT",current_cal)
                .commit();
        Toast.makeText(getContext(),current_cal+"",Toast.LENGTH_SHORT).show();;
        Done =true;
        addView(view);
    }

    public void menu(){
        while(count<5){
            if(count==0) {
                random = r.nextInt(8);
                current_cal += riceCAL[random];
                T[count].setText(riceName[random]);
                T[count].setCompoundDrawablesWithIntrinsicBounds(0,riceImageList[random],0,0);
            }
            else if(count==1) {
                random =r.nextInt(5);
                current_cal+=meatCAL[random];
                T[count].setText(meatName[random]);
                T[count].setCompoundDrawablesWithIntrinsicBounds(0,meatImageList[random],0,0);
            }
            else if(count==2) {
                random =r.nextInt(3)+5;
                current_cal+=meatCAL[random];
                T[count].setText(meatName[random]);
                T[count].setCompoundDrawablesWithIntrinsicBounds(0,meatImageList[random],0,0);
            }
            else if(count==3) {
                random =r.nextInt(4);
                current_cal+=fruitCAL[random];
                T[count].setText(fruitName[random]);
                T[count].setCompoundDrawablesWithIntrinsicBounds(0,fruitImageList[random],0,0);
            }
            else if(count==4) {
                random =r.nextInt(4)+4;
                current_cal+=fruitCAL[random];
                T[count].setText(fruitName[random]);
                T[count].setCompoundDrawablesWithIntrinsicBounds(0,fruitImageList[random],0,0);
            }
            count++;
        }


    }



    public void inti(View view) {
        T[0] = (TextView) view.findViewById(R.id.R1);
        T[1]= (TextView) view.findViewById(R.id.R2);
        T[2] = (TextView) view.findViewById(R.id.R3);
        T[3] = (TextView) view.findViewById(R.id.R4);
        T[4] = (TextView) view.findViewById(R.id.R5);
        T[5] = (TextView) view.findViewById(R.id.R6);
        T[6] = (TextView) view.findViewById(R.id.R7);
        T[7] = (TextView) view.findViewById(R.id.R8);
    }

    @Override
    public void refreshView() {
    }
}
