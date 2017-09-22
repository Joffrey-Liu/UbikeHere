package com.example.alan_lin.jsontest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.Log;

public class LocalWeather extends WwoApi{

    public static final String FREE_API_ENDPOINT  = "http://api.worldweatheronline.com/free/v1/weather.ashx?";
    public static final String PREMIUM_API_ENDPOINT  = "http://api.worldweatheronline.com/premium/v1/weather.ashx?";
    public static String tempC;
    public static String weatherICON;
    public static String weatherdeSC;
    LocalWeather(boolean freeAPI) {
        super(freeAPI);
        if(freeAPI) {
            apiEndPoint = FREE_API_ENDPOINT ;
        } else {
            apiEndPoint = PREMIUM_API_ENDPOINT;
        }
    }

    Data callAPI(String query) {
        return getLocalWeatherData(getInputStream(apiEndPoint + query));
    }

    Data getLocalWeatherData(InputStream is) {
        Data weather = null;

        try {
            XmlPullParser xpp = getXmlPullParser(is);
            weather = new Data();
            CurrentCondition cc = new CurrentCondition();
            weather.current_condition = cc;
            Log.i("WWO", "it's ok to use XML");
            cc.temp_C = getTextForTag(xpp, "temp_C");
            cc.weatherIconUrl = getDecode(getTextForTag(xpp, "weatherIconUrl"));
            cc.weatherDesc = getDecode(getTextForTag(xpp, "weatherDesc"));
            System.out.println("TEMP_C getLocalWeatherData:"+cc.temp_C);
            System.out.println("ICON_PNG getLocalWeatherData:"+cc.weatherIconUrl);
            System.out.println("WEATHER_DESC getLocalWeatherData:"+cc.weatherDesc);
            tempC = cc.temp_C;
            weatherICON = cc.weatherIconUrl;
            weatherdeSC = cc.weatherDesc;
//            Log.i("WWO", "getLocalWeatherData:"+cc.temp_C);
//            Log.i("WWO", "getLocalWeatherData:"+cc.weatherIconUrl);
//            Log.i("WWO", "getLocalWeatherData:"+cc.weatherDesc);
        } catch (Exception e) {
            Log.i("WWO", e.getMessage()+"it's  notttttttttok to use XML");
        }

        return weather;
    }

    class Params extends RootParams {
        String q;					//required
        String extra;
        String num_of_days="1";		//required
        String date;
        String fx="no";
        String cc;					//default "yes"
        String includeLocation;		//default "no"
        String format;				//default "xml"
        String show_comments="no";
        String callback;
        String key;					//required

        Params(String key) {
            num_of_days = "1";
            fx = "no";
            show_comments = "no";
            this.key = key;
        }

        Params setQ(String q) {
            this.q = q;
            return this;
        }

        Params setExtra(String extra) {
            this.extra = extra;
            return this;
        }

        Params setNumOfDays(String num_of_days) {
            this.num_of_days = num_of_days;
            return this;
        }

        Params setDate(String date) {
            this.date = date;
            return this;
        }

        Params setFx(String fx) {
            this.fx = fx;
            return this;
        }

        Params setCc(String cc) {
            this.cc = cc;
            return this;
        }

        Params setIncludeLocation(String includeLocation) {
            this.includeLocation = includeLocation;
            return this;
        }

        Params setFormat(String format) {
            this.format = format;
            return this;
        }

        Params setShowComments(String showComments) {
            this.show_comments = showComments;
            return this;
        }

        Params setCallback(String callback) {
            this.callback = callback;
            return this;
        }

        Params setKey(String key) {
            this.key = key;
            return this;
        }
    }

    class Data {
        Request request;
        CurrentCondition current_condition;
        Weather weather;
    }

    class Request {
        String type;
        String query;
    }

    class CurrentCondition {
        String observation_time;
        String temp_C;
        String weatherCode;
        String weatherIconUrl;
        String weatherDesc;
        String windspeedMiles;
        String windspeedKmph;
        String winddirDegree;
        String winddir16Point;
        String precipMM;
        String humidity;
        String visibility;
        String pressure;
        String cloudcover;
    }

    class Weather {
        String date;
        String tempMaxC;
        String tempMaxF;
        String tempMinC;
        String tempMinF;
        String windspeedMiles;
        String windspeedKmph;
        String winddirection;
        String weatherCode;
        String weatherIconUrl;
        String weatherDesc;
        String precipMM;
    }
}

