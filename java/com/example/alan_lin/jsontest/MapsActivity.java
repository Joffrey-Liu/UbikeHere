package com.example.alan_lin.jsontest;
import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.nearby.messages.Message;
import com.google.firebase.appindexing.FirebaseAppIndexingTooManyArgumentsException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, navigate_fragment.OnItemSelectedListener {

    public GoogleMap mMap;
    public static int first_time_load = 0; // 第一次用此 app
    ArrayList<Float> Lat = new ArrayList<Float>();
    ArrayList<Float> Lng = new ArrayList<Float>();
    ArrayList<String> Name = new ArrayList<String>();
    ArrayList<LatLng> Gps = new ArrayList<LatLng>();
    ArrayList<Integer> SBI = new ArrayList<Integer>();
    ArrayList<Integer> BEMP = new ArrayList<Integer>();
    ArrayList<Integer> MyFavorite = new ArrayList<Integer>();
    ArrayList<Marker> InfoMarker = new ArrayList<Marker>();
    static ArrayList<UbikeInfo> ubike_info = new ArrayList<UbikeInfo>();
    static int threadcount = 0;
    static ArrayList<Integer> time_period = new ArrayList<Integer>();
    private String TAG = MapsActivity.class.getSimpleName();
    public static Boolean source_ready = false;
    public Float lat, lng;
    public ImageView CalorieImage;
    public ImageView FavoriteImage;
    public ImageView NavigationImage;
    public ImageView updownImage;
    public SearchView searchView;
    public static TextView CountDownText;
    private SearchView.OnQueryTextListener queryTextListener;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 2000;
    private LocationManager mLocationManager;
    Location location_ = null;
    Location pre_location = null;
    public static boolean start_cal_time = false;
    Marker mark = null;
    private String provider;
    private SharedPreferences settings;
    public Timer time;
    public static int tsec = 0;
    public static int csec = 0;
    public static int cmin = 0;
    public static int chour = 0;
    public static int thirty_sec = 1800;
    public static int rate = 0; //使用者消費時數費率
    public static int interval_rate = 0; //費率加法
    public boolean have_counted = false;
    public static double distance = 0;
    public double turn_to_radian = 3.14 / 180;
    public static String s = "00:00"; // 倒數計時 用來改變 count text
    FragmentManager fm;
    private int userinput = 0;
    navigate_fragment navigate_btn;
    public static Button nav_btn; // 取得 fragment 中的導航按鈕
    Weather_fragment weather_fragment;   //天氣的 fragment
    public static TextView weather_location; // 表示目前天氣位置地區
    public static ImageView weather_icon; // 表示目前天氣的圖示
    public static TextView weather_temperature; //表示目前天氣溫度
    public static TextView weather_suggestion; //給使用者目前天氣的溫暖問候 哈哈
    public LatLng marker_position; //// 用來當作 從目前位置導航到 marker 的位置
    public static String navigate_url;
    public static PolylineOptions lineOptions = null; //導航路線
    public static Polyline polyline; //等於 polylineOption
    public static boolean if_navigate = false; //判斷是否有導航
    public boolean show_weather_fragment = false; //是否有秀出 weather fragment

    private android.location.LocationListener mLocationListener = new android.location.LocationListener() {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.i("Tag", "in LOCATION LISTENER");
                Log.i("Tag", location.getLatitude() + " " + location.getLongitude());
                System.out.println(location.getLatitude() + " " + location.getLongitude());
                getCurrentLocation(location);
            } else {
                Log.i("Tag", "Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toast.makeText(getApplicationContext(), "Oncreate", Toast.LENGTH_SHORT).show();
        fm = getFragmentManager();
        updownImage = (ImageView) findViewById(R.id.arrow);
        settings = getSharedPreferences("DATA", 0);
        userinput = getIntent().getIntExtra("userInput", 0);
        navigate_btn = (navigate_fragment) getFragmentManager().findFragmentById(R.id.navigate_button);
        //weather_fragment = (Weather_fragment) getFragmentManager().findFragmentById(R.id.weather_fragment);
        fm.beginTransaction().hide(navigate_btn).commit();
        //fm.beginTransaction().hide(weather_fragment).commit();
        nav_btn = (Button) findViewById(R.id.button2);
        weather_location = (TextView) findViewById(R.id.textView8); // 表示目前天氣位置地區
        weather_icon = (ImageView) findViewById(R.id.imageView); // 表示目前天氣的圖示
        weather_temperature = (TextView) findViewById(R.id.textView9); //表示目前天氣溫度
        weather_suggestion = (TextView) findViewById(R.id.WeatherTextView); //給使用者目前天氣的溫暖問候 哈哈
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(year + "-" + month + "-" + day);
        LocalWeather lw = new LocalWeather(false);
        lw.callAPI("key=6eee5a2cdff54e7d861151344162711&q=Taoyuan&format=xml&num_of_days=1&date=" + year + "-" + month + "-" + day);
        //new GetWeatherIcon().execute(lw.weatherICON);
        settings = getSharedPreferences("WEATHER", 0);
        settings.edit()
                .putString("W", lw.weatherdeSC)
                .commit();

        weather_location.setText("桃園市 ");
        weather_temperature.setText(lw.tempC);
        //weather_suggestion.setText(lw.weatherdeSC);
        weather_fragment = (Weather_fragment) getFragmentManager().findFragmentById(R.id.weather_fragment);
        fm.beginTransaction().hide(weather_fragment).commit();
        SetToolBar();
        //set_weather_background(lw);
        weather_fragment.set_weather_background(lw);

        new GetContacts().execute();


        //distance = getIntent().getDoubleExtra("distance",0);
        // Toast.makeText(this,""+distance,Toast.LENGTH_SHORT).show();


        while (!source_ready) {
            //Log.i(TAG, "source ready:  not yet");
            if (source_ready == true)
                break;
        }
        source_ready = false;
        for (int i = 0; i < ubike_info.size(); i++) {
            System.out.println("number:" + ubike_info.get(i).bike_number + "\t: time:" + ubike_info.get(i).get_info_time + "\t period:" + time_period.get(i));
        }
        /*for(int i=0; i<ubike_info.size(); i++){
            System.out.println("number:"+ubike_info.get(i).bike_number+"\t: time:"+ubike_info.get(i).get_info_time+"\t period:"+time_period.get(i));
        }*/
        System.out.println(time_period.size());
        System.out.println(ubike_info.size());
        LatLng yzu = new LatLng(24.96960, 121.2683);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    private void set_weather_background(LocalWeather lw) {
        try {
            RelativeLayout mLayout;
            mLayout = (RelativeLayout) findViewById(R.id.weather);
            if (mLayout == null) {
                System.out.println("the weather layout is null~~~~~~~~~~~~~~~~~~~~~");
            } else
                System.out.println("the weather layout is exist~~~~~~~~~~~~~~~~~~~~~");

            System.out.println("weather describe: " + lw.weatherdeSC + " ~~~~~~~~~~~~~~~~~~~~");

            if (lw.weatherdeSC.equals("Moderate or heavy sleet showers")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.heavyrain));
            } else if (lw.weatherdeSC.equals("Light rain shower") || lw.weatherdeSC.equals("Patchy light rain") || lw.weatherdeSC.equals("Light rain")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.rain));
            } else if (lw.weatherdeSC.equals("Mist")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.mist));
            } else if (lw.weatherdeSC.equals("overcast")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.overcast));
            } else if (lw.weatherdeSC.equals("Patchy light drizzle")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.rain));
            } else if (lw.weatherdeSC.equals("Cloudy")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.cloudy));
            } else if (lw.weatherdeSC.equals("Partly cloudy")) {
                System.out.println("123");
                mLayout.setBackground(getResources().getDrawable(R.drawable.cloudy));
            } else if (lw.weatherdeSC.equals("Clear/Sunny")) {
                mLayout.setBackground(getResources().getDrawable(R.drawable.sunny));
            } else {
                System.out.println("not");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "!!!!!!!!!!!!!");
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Toast.makeText(getApplicationContext(), "onRestore", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < savedInstanceState.getDouble("Size"); i++) {
                LatLng temp = new LatLng(savedInstanceState.getDouble("Lat" + i), savedInstanceState.getDouble("Lng" + i));
                Gps.add(temp);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Size", Gps.size());
        for (int i = 0; i < Gps.size(); i++) {
            savedInstanceState.putDouble("Lat" + i, Lat.get(i));
            savedInstanceState.putDouble("Lng" + i, Lng.get(i));
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("ONSART~~~");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ONRESUME~~~");
        Location location = null;
        getCurrentLocation(location);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "ONpause", Toast.LENGTH_SHORT).show();
        System.out.println("ONPAUSE");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(mLocationListener);
        settings = getSharedPreferences("DATA", 0);
        settings.edit()
                .putInt("userinput", userinput)
                .commit();
        String temp = Double.toString(distance);
        Toast.makeText(this, distance + "", Toast.LENGTH_SHORT).show();
        settings.edit()
                .putString("Distance", temp)
                .commit();
        settings = getSharedPreferences("BIKEDATA", 0);
        settings.edit()
                .putInt("Size", ubike_info.size())
                .commit();
        settings.edit()
                .putInt("FRIST", first_time_load)
                .commit();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    settings = getSharedPreferences("BIKEDATA", 0);
                    settings.edit()
                            .putInt("Size", ubike_info.size())
                            .commit();
                    settings.edit()
                            .putInt("FRIST", first_time_load)
                            .commit();
                    for (int i = 0; i < ubike_info.size(); i++) {
                        settings.edit().putString("name" + i, ubike_info.get(i).board_name).commit();
                        settings.edit().putInt("number" + i, ubike_info.get(i).bike_number).commit();
                        settings.edit().putString("date" + i, ubike_info.get(i).get_info_time).commit();
                        settings.edit().putInt("time" + i, time_period.get(i)).commit();
                    }
                } catch (Exception e) {

                }
            }
        });
        thread.start();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        for (int i = 0; i < MyFavorite.size(); i++) {
            menu.add(5, Menu.FIRST + i, Menu.NONE, "我的最愛:  " + Name.get(MyFavorite.get(i)));
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == 5) {
            String temp;
            String in = item.getTitle() + "";
            temp = in.substring(7, item.getTitle().length());
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < Name.size(); i++) {
                if (temp.equals(Name.get(i))) {
                    lat = Lat.get(i);
                    lng = Lng.get(i);
                    break;
                }
            }
            LatLng current_position = new LatLng(lat, lng);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_position, 15));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_position, 15), 2000, null);
            for (int i = 0; i < InfoMarker.size(); i++) {
                if (InfoMarker.get(i).getTitle().equals(temp)) {
                    InfoMarker.get(i).showInfoWindow();
                    break;
                }
            }
        }
        return super.onContextItemSelected(item);
    }

    public void readBIKEDATE() {
        ubike_info.clear();
        time_period.clear();
        settings = getSharedPreferences("BIKEDATA", 0);
        first_time_load = settings.getInt("FRIST", 0);
        if (first_time_load != 0) {
            for (int i = 0; i < settings.getInt("Size", 0); i++) {
                UbikeInfo temp = new UbikeInfo("", 0, "");
                temp.board_name = settings.getString("name" + i, "");
                temp.bike_number = settings.getInt("number" + i, 0);
                temp.get_info_time = settings.getString("date" + i, "");
                ubike_info.add(temp);
                time_period.add(settings.getInt("time" + i, 0));
            }
        }
    }

    public void readData() {
        MyFavorite.clear();
        settings = getSharedPreferences("DATA", 0);
        for (int i = 1; i < settings.getInt("Size", 0) + 1; i++) {
            MyFavorite.add(settings.getInt("Data" + i, 0));
        }
        try {
            distance = Double.valueOf(settings.getString("Distance", "0"));

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveData(int i) {
        settings = getSharedPreferences("DATA", 0);
        settings.edit()
                .putInt("Data" + MyFavorite.size(), i)
                .commit();
        settings.edit()
                .putInt("Size", MyFavorite.size())
                .commit();

    }

    private void getCurrentLocation(Location location) {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //Location location = null;
        Log.i("Tag", "in GETCURRENTLOCATION");
        location_ = location;
        if (location == null) {
            if (!(isGPSEnabled || isNetworkEnabled)) {
                System.out.println("The network and GPS are not enable!!!");
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    location_ = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if (isGPSEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    location_ = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }

        if (location_ != null) {
            Float lat = Float.valueOf(String.valueOf(location_.getLatitude()));
            Float lng = Float.valueOf(String.valueOf(location_.getLongitude()));
            LatLng current_position = new LatLng(lat, lng);
            try {
                if (mark != null) {
                    LatLngInterpolator latLnginterpolator = new LatLngInterpolator.Linear();
                    animateMarkerToGB(mark, current_position, latLnginterpolator);
                    //mark.remove();
                } else {
                    mark = mMap.addMarker(new MarkerOptions().position(current_position).icon(BitmapDescriptorFactory.fromResource(R.drawable.circlegood)));
                }
                //mark = mMap.addMarker(new MarkerOptions().position(current_position));
                //mMap.setMyLocationEnabled(true);


                if (pre_location == null) {
                    pre_location = location_;
                } else {
                    double lat1 = Double.valueOf(String.valueOf(location_.getLatitude()));
                    double lng1 = Double.valueOf(String.valueOf(location_.getLongitude()));
                    double lat2 = Double.valueOf(String.valueOf(pre_location.getLatitude()));
                    double lng2 = Double.valueOf(String.valueOf(pre_location.getLongitude()));
                    distance = distance + (2 * 6378000) * Math.asin(Math.sqrt(Math.sin((lat1 - lat2) / 2 * turn_to_radian) * Math.sin((lat1 - lat2) / 2 * turn_to_radian) + Math.cos(lng1 * turn_to_radian) * Math.cos(lng2 * turn_to_radian) * Math.sin((lng1 - lng2) / 2 * turn_to_radian) * Math.sin((lng1 - lng2) / 2 * turn_to_radian)));

                    pre_location = location_;
                }

            } catch (Exception e) {
                System.out.println("FAIL IN MARKER MY LOCATION" + e.getMessage());
            }

        } else {
            System.out.println("location is null the result");
        }
    }

    private void CalorieCal() {
        CalorieImage = (ImageView) findViewById(R.id.Kal);
        CalorieImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userInput", userinput);
                intent.putExtra("weight", getIntent().getIntExtra("weight", 0));
                intent.putExtra("height", getIntent().getIntExtra("height", 0));
                intent.putExtra("age", getIntent().getIntExtra("age", 0));
                intent.putExtra("distance", distance); // 傳遞行車距離
                intent.putExtra("time_sec", tsec);  //傳遞計時秒數
                intent.setClass(MapsActivity.this, CaloriesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Favorite() {
        FavoriteImage = (ImageView) findViewById(R.id.favotrie);
        FavoriteImage.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                //To register the button with context menu.
                if (MyFavorite.size() == 0)
                    Toast.makeText(getApplicationContext(), "還未有項目加入我的最愛", Toast.LENGTH_SHORT).show();
                else {
                    registerForContextMenu(FavoriteImage);
                    openContextMenu(FavoriteImage);
                }
            }
        });
    }

    private void Navigtion() {
        NavigationImage = (ImageView) findViewById(R.id.currentGps);
        NavigationImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                location_ = null;
                getCurrentLocation(location_);
                Float lat = Float.valueOf(String.valueOf(location_.getLatitude()));
                Float lng = Float.valueOf(String.valueOf(location_.getLongitude()));
                LatLng current_position = new LatLng(lat, lng);
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_position, 15));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_position, 15), 2000, null);
            }
        });
    }

    private void SetLogo() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Carten.otf");
        TextView custom = (TextView) findViewById(R.id.LogoText);
        custom.setTypeface(font);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_weather_fragment == false) {
                    show_weather_fragment = true;
                    updownImage.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                    fm.beginTransaction().setCustomAnimations(R.animator.answer_in, 0).show(weather_fragment).commit();
                } else {
                    show_weather_fragment = false;
                    updownImage.setImageDrawable(getResources().getDrawable(R.drawable.downarrow));
                    fm.beginTransaction().setCustomAnimations(0, R.animator.answer_out).hide(weather_fragment).commit();
                }
            }


        });
    }

    private void CountdownCal() {
        CountDownText = (TextView) findViewById(R.id.countdown);
        CountDownText.setText(s);
        CountDownText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (start_cal_time == false) {
                    start_cal_time = true;
                    tsec = 0;
                    rate = 0;
                    interval_rate = 0;
                    if (!have_counted) {
                        time = new Timer();
                        time.schedule(task, 0, 1000);
                        have_counted = true;
                    }

                } else {

                    final AlertDialog alertDialog = getAlertDialog("結束計時器", "確定要結束計時嗎.......");
                    alertDialog.show();
                }

            }
        });
    }

    private AlertDialog getAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                start_cal_time = false;
                CountDownText.setText("00:00");
                s = "00:00";
                Toast.makeText(MapsActivity.this, "您按下OK按鈕", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapsActivity.this, "您按下NO按鈕", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    private static Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //thirty_sec = thirty_sec - tsec;
                    csec = (tsec) % 60;
                    cmin = (tsec) / 60;

                    if (cmin < 10) {
                        s = "0" + cmin;
                    } else {
                        s = "" + cmin;
                    }
                    if (csec < 10) {
                        s = s + ":0" + csec;
                    } else {
                        s = s + ":" + csec;
                    }

                    if (tsec >= 1800 && tsec < 7200) {
                        interval_rate = 10;
                        if (tsec % 1800 == 0) {
                            rate += interval_rate;
                        }
                    } else if (tsec >= 7200 && tsec < 14400) {
                        interval_rate = 20;
                        if (tsec % 1800 == 0) {
                            rate += interval_rate;
                        }
                    } else if (tsec >= 14400) {
                        interval_rate = 40;
                        if (tsec % 1800 == 0) {
                            rate += interval_rate;
                        }
                    }
                    if (rate != 0) {
                        s = s + "  rate: " + rate + " $";
                    }

                    CountDownText.setText(s);
            }
        }
    };

    private static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (start_cal_time) {
                tsec++;
                android.os.Message message = new android.os.Message();

                message.what = 1;
                handler.sendMessage(message);
            }
        }
    };

    private void SetToolBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        myToolbar.inflateMenu(R.menu.manu_main);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setOnMenuItemClickListener(onMenuItemClick);
        CalorieCal();
        Favorite();
        Navigtion();
        SetLogo();
        CountdownCal();
        updown();
    }

    private void updown() {
        updownImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (show_weather_fragment == false) {
                    show_weather_fragment = true;
                    updownImage.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                    fm.beginTransaction().setCustomAnimations(R.animator.answer_in, 0).show(weather_fragment).commit();
                } else {
                    show_weather_fragment = false;
                    updownImage.setImageDrawable(getResources().getDrawable(R.drawable.downarrow));
                    fm.beginTransaction().setCustomAnimations(0, R.animator.answer_out).hide(weather_fragment).commit();
                }
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";


            if (!msg.equals("")) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                onMapSearch(query);
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    public void onMapSearch(String s) {
        List<android.location.Address> addressList = null;

        if (s != null || !s.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(s, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            android.location.Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        // Add a marker in Sydney and move the camera
        LatLng yzu = new LatLng(24.96960, 121.2683);
        //mMap.addMarker(new MarkerOptions().position(yzu).title("Marker in YZU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yzu, 15));

        if (mMap != null) {
            System.out.println("the mMap is not null");
        } else {
            System.out.println("the mMap is null");
        }

        for (int i = 0; i < Lat.size(); i++) {
            try {
                Marker tempmark;
                tempmark = mMap.addMarker(new MarkerOptions()
                        .position(Gps.get(i))
                        .title(Name.get(i))
                        .snippet("目前車輛數量" + SBI.get(i).toString() + "    可還車位數" + BEMP.get(i).toString() + "\n平均等待時間:" + time_period.get(i) + "分鐘")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bikezonesignal))
                        .flat(false)
                );
                InfoMarker.add(tempmark);
            } catch (Exception e) {
                Log.e(TAG, "marker error: " + e.getMessage());
            }
        }
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.window_info, null);
                // Setting up the infoWindow with current's marker info
                LatLng latLng = marker.getPosition();
                TextView title_info = (TextView) v.findViewById(R.id.title);
                TextView snippet_info = (TextView) v.findViewById(R.id.snippet);
                ImageView add_Like = (ImageView) v.findViewById(R.id.addfavorite);

                title_info.setText(marker.getTitle());
                snippet_info.setText(marker.getSnippet());

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                return v;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            boolean in = false;

            @Override
            public void onInfoWindowClick(Marker marker) {
                LatLng latLon = marker.getPosition();
                //Cycle through places array
                for (int i = 0; i < Gps.size(); i++) {
                    if (latLon.equals(Gps.get(i))) {
                        if (sameornot(i))
                            Toast.makeText(getApplicationContext(), marker.getTitle() + "加入我的最愛", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), marker.getTitle() + "已加入我的最愛", Toast.LENGTH_SHORT).show();
                        if (sameornot(i)) {
                            MyFavorite.add(i);
                            saveData(i);
                        }
                    }
                }
            }
        });
        getCurrentLocation(location_);
        if (lineOptions != null) {
            if (if_navigate) {
                polyline = mMap.addPolyline(lineOptions);
                nav_btn.setText("結束導航");
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    fm.beginTransaction().hide(navigate_btn).commit();
                } catch (Exception e) {
                    System.out.println("fatal" + e.getMessage());
                }

                if (show_weather_fragment) {
                    show_weather_fragment = false;
                    fm.beginTransaction().setCustomAnimations(0, R.animator.answer_out).hide(weather_fragment).commit();
                }

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                fm.beginTransaction().show(navigate_btn).commit();
                marker_position = marker.getPosition();
                return false;
            }
        });
    }

    private boolean sameornot(int index) {
        for (int i = 0; i < MyFavorite.size(); i++) {
            if (index == MyFavorite.get(i))
                return false;
        }
        return true;
    }


    @Override
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// fragement click!!!!!
    public void onColorItemSelected() {
        //LatLng yzu1 = new LatLng(24.96960, 121.2683);
        //mMap.addMarker(new MarkerOptions().position(yzu1));
        if (if_navigate == false) {
            if_navigate = true;
            nav_btn.setText("結束導航");
        } else {
            if_navigate = false;
            nav_btn.setText("導航");
            polyline.remove();
            return;
        }
        Float lat = Float.valueOf(String.valueOf(location_.getLatitude()));
        Float lng = Float.valueOf(String.valueOf(location_.getLongitude()));
        LatLng loc = new LatLng(lat, lng);

        navigate_url = getDirectionUrl(loc, marker_position);
        ParserTask parserTask = new ParserTask();
        parserTask.execute(navigate_url);
    }

    private String getDirectionUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude; // origin of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude; // destination of route
        String sensor = "sensor=false"; //sensor enable
        String mode = "mode=walking"; //mode WALKING
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        System.out.println("getDirectionUrl--------->: " + url);
        return url;
    }

    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            JSONObject jobject;
            HttpHandler sh = new HttpHandler();
            List<List<HashMap<String, String>>> routes = null;
            String jsonStr = sh.makeServiceCall(navigate_url);
            try {
                jobject = new JSONObject(jsonStr);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jobject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            //PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);

                // Changing the color polyline according to the mode
                lineOptions.color(Color.BLUE);
            }
            polyline = mMap.addPolyline(lineOptions);

        }
    }


    public class DirectionsJSONParser {
        /**
         * Receives a JSONObject and returns a list of lists containing latitude and
         * longitude
         */
        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;
            String dis = "";
            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();
                    dis = (String) ((JSONObject) ((JSONObject) jLegs
                            .get(0)).get("distance")).get("text");////////////////////////////////////             總距離
                    /** Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                    .get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat",
                                        Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng",
                                        Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
            return routes;
        }

        /**
         * Method to decode polyline points Courtesy :
         * jeffreysambells.com/2010/05/27
         * /decoding-polylines-from-google-maps-direction-api-with-java
         */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
        }
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,"Json Data is  downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            source_ready = false;
            readData();
            readBIKEDATE();
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=5ca2bfc7-9ace-4719-88ae-4034b9a5a55c&rid=a1b4714b-3b75-4ff8-a8f2-cc377e4eaa0f";
            String jsonStr = sh.makeServiceCall(url);
            HashMap<String, String> contact = new HashMap<>();
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jObject = new JSONObject(jsonStr).getJSONObject("retVal");
                    Iterator<String> keys = jObject.keys();
                    int index = 0; //用來判斷第幾筆資料 要用來時間相減算間距
                    while (keys.hasNext()) {

                        String key = keys.next();
                        JSONObject innerJObject = jObject.getJSONObject(key);

                        String sna = innerJObject.getString("sna");
                        String tot = innerJObject.getString("tot");
                        String sbi = innerJObject.getString("sbi");
                        String lat = innerJObject.getString("lat");
                        String lng = innerJObject.getString("lng");
                        String bemp = innerJObject.getString("bemp");
                        String act = innerJObject.getString("act");
                        String sno = innerJObject.getString("sno");
                        String sarea = innerJObject.getString("sarea");
                        String mday = innerJObject.getString("mday");
                        String ar = innerJObject.getString("ar");
                        String sareaen = innerJObject.getString("sareaen");
                        String snaen = innerJObject.getString("snaen");
                        String aren = innerJObject.getString("aren");


                        // adding each child node to HashMap key => value
                        contact.put("sna", sna);
                        contact.put("tot", tot);
                        contact.put("sbi", sbi);
                        contact.put("lat", lat);
                        contact.put("lng", lng);
                        contact.put("bemp", bemp);
                        contact.put("act", act);
                        contact.put("sno", sno);
                        contact.put("sarea", sarea);
                        contact.put("mday", mday);
                        contact.put("ar", ar);
                        contact.put("sareaen", sareaen);
                        contact.put("snaen", snaen);
                        contact.put("aren", aren);

                        Lat.add(Float.parseFloat(lat));
                        Lng.add(Float.parseFloat(lng));
                        Name.add(sna);
                        LatLng mark = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
                        Gps.add(mark);
                        SBI.add(Integer.parseInt(sbi));
                        BEMP.add(Integer.parseInt(bemp));
                        SimpleDateFormat sdFormat = new SimpleDateFormat("dd:HH:mm:ss");
                        Date date = new Date();
                        String strDate = sdFormat.format(date);
                        if (first_time_load == 0) {
                            UbikeInfo temp = new UbikeInfo(sna, Integer.parseInt(sbi), strDate);
                            ubike_info.add(temp);
                            time_period.add(0);
                        } else {
                            try {
                                if (Integer.parseInt(sbi) > ubike_info.get(index).bike_number) {
                                    System.out.println("0");
                                    int period = period_of_increase_car(strDate, ubike_info.get(index).get_info_time);
                                    System.out.println("1");
                                    time_period.set(index, period / (Integer.parseInt(sbi) - ubike_info.get(index).bike_number));
                                    System.out.println("2");
                                    System.out.println(time_period.get(index));
                                    UbikeInfo temp = new UbikeInfo(sna, Integer.parseInt(sbi), strDate);
                                    System.out.println("3");
                                    ubike_info.set(index, temp);
                                } else {
                                    System.out.println("4");
                                    UbikeInfo temp = new UbikeInfo(sna, Integer.parseInt(sbi), strDate);
                                    System.out.println("5");
                                    ubike_info.set(index, temp);
                                    System.out.println("6");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage() + "~~~~~~~~~~~~~");
                            }

                        }
                        index++;
                    }
                    first_time_load = 1;
                    source_ready = true;
                    for (int i = 0; i < ubike_info.size(); i++)
                        System.out.println(ubike_info.get(i).bike_number);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private int period_of_increase_car(String strDate, String get_info_time) {
        System.out.println(strDate + " " + get_info_time + "~~~~~~~~~~~~~~~~");
        String[] now_time = strDate.split(":");
        String[] last_time = get_info_time.split(":");
        int[] int_now_time = new int[4];
        int[] int_last_time = new int[4];
        for (int i = 0; i < now_time.length; i++) {
            int_now_time[i] = Integer.valueOf(now_time[i]);
            int_last_time[i] = Integer.valueOf(last_time[i]);
        }
        int sec = 0, min = 0, hour = 0, day = 0;
        if (int_now_time[3] >= int_last_time[3]) {
            sec = int_now_time[3] - int_last_time[3];
        } else {
            int_now_time[2] -= 1;
            int_now_time[3] += 60;
            sec = int_now_time[3] - int_last_time[3];
        }

        if (int_now_time[2] >= int_last_time[2]) {
            min = int_now_time[2] - int_last_time[2];
        } else {
            int_now_time[1] -= 1;
            int_now_time[2] += 60;
            min = int_now_time[2] - int_last_time[2];
        }

        if (int_now_time[1] >= int_last_time[1]) {
            hour = int_now_time[1] - int_last_time[1];
        } else {
            int_now_time[0] -= 1;
            int_now_time[1] += 24;
            hour = int_now_time[1] - int_last_time[1];
        }

        if (int_now_time[0] >= int_last_time[0]) {
            day = int_now_time[0] - int_last_time[0];
        } else {
            System.out.println("time is error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        int period = ((day * 86400) + (hour * 3600) + (min * 60) + sec) / 60;
        return period;
    }


    public class UbikeInfo {
        String board_name;
        Integer bike_number;
        String get_info_time;

        UbikeInfo(String board_name, int bike_number, String get_info_time) {
            this.board_name = board_name;
            this.bike_number = bike_number;
            this.get_info_time = get_info_time;
        }
    }


    public interface LatLngInterpolator {
        public LatLng interpolate(float fraction, LatLng a, LatLng b);

        public class Linear implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lng = (b.longitude - a.longitude) * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }

    static void animateMarkerToGB(final Marker marker, final LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {
        final LatLng startPosition = marker.getPosition();
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 3000;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                marker.setPosition(latLngInterpolator.interpolate(v, startPosition, finalPosition));

                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}