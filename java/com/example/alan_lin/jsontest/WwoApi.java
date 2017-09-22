package com.example.alan_lin.jsontest;

/**
 * Created by Alan_Lin on 2016/12/4.
 */
import android.net.Proxy;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebChromeClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;
import java.net.*;
import java.io.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class WwoApi {
    public static final boolean LOGD = true;
    public static final String FREE_API_KEY = "xkq544hkar4m69qujdgujn7w";
    public static final String PREMIUM_API_KEY = "6eee5a2cdff54e7d861151344162711";
    String key;
    String apiEndPoint;
    static String  UR;
    static InputStream is = null;
    static boolean ready = false;
    static String str;
    WwoApi(boolean freeAPI) {
        if(freeAPI) {
            this.key = FREE_API_KEY;
        } else {
            this.key = PREMIUM_API_KEY;
        }

    }

    WwoApi setKey(String key) {
        this.key = key;
        return this;
    }

    WwoApi setApiEndPoint(String apiEndPoint) {
        this.apiEndPoint = apiEndPoint;
        return this;
    }

    static InputStream getInputStream(String url) {
        UR = url;
        Log.i("WWO", "URL: "+ url);
        new GetJsonTask().execute();
        while(!ready)
        {
            //Log.i("WWO", " url ++++FFFF");
            if(ready)
                break;

        }
        Log.i("WWO", "~~~~~~~~~~~~~~~it's ok to use XML"+is);
        return is;
    }

    static XmlPullParser getXmlPullParser(InputStream is) {
        XmlPullParser xpp = null;

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(rd);
        } catch (Exception var4) {
            Log.i("WWO", var4.getMessage()+" XMLPARSERFFFF");
        }

        return xpp;
    }

    static String getDecode(String value) {
        if(value.startsWith("<![CDATA[")) {
            value = value.substring(9, value.length() - 3);
        }

        return value;
    }

    static String getTextForTag(XmlPullParser xpp, String tag) throws XmlPullParserException, IOException {
        while(myXppNextTag(xpp) != 2 || !xpp.getName().equals(tag)) {
            Log.d("CallWWOApi", "Tag=" + xpp.getName());
        }

        xpp.require(2, "", tag);
        String text = xpp.nextText();
        Log.d("CallWWOApi", "getTextForTag " + text);
        xpp.require(3, "", tag);
        return text;
    }

    static void reachNextStartTagFor(XmlPullParser xpp, String tag) throws XmlPullParserException, IOException {
        while(myXppNextTag(xpp) != 2 || !xpp.getName().equals(tag)) {
            Log.d("CallWWOApi", "Tag=" + xpp.getName());
        }

        xpp.require(2, "", tag);
    }

    static int myXppNextTag(XmlPullParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.next();

        while(eventType != 2 && eventType != 3) {
            eventType = xpp.next();
            if(eventType == 1) {
                break;
            }
        }

        if(eventType != 2 && eventType != 3) {
            throw new XmlPullParserException("expected start or end tag", xpp, (Throwable)null);
        } else {
            return eventType;
        }
    }

    class RootParams {
        RootParams() {
        }

        String getQueryString(Class cls) {
            String query = null;
            Field[] fields = cls.getDeclaredFields();

            try {
                Field[] var7 = fields;
                int var6 = fields.length;

                for(int var5 = 0; var5 < var6; ++var5) {
                    Field field = var7[var5];
                    Object f = field.get(this);
                    if(f != null) {
                        if(query == null) {
                            query = "?" + URLEncoder.encode(field.getName(), "UTF-8") + "=" + URLEncoder.encode((String)f, "UTF-8");
                        } else {
                            query = query + "&" + URLEncoder.encode(field.getName(), "UTF-8") + "=" + URLEncoder.encode((String)f, "UTF-8");
                        }
                    }
                }
            } catch (Exception var9) {
                ;
            }

            return query;
        }
    }

    private static class GetJsonTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... arg0) {
            try {
                try {
                    URL u= new URL(UR);
                    Log.i("WWO", "URL: "+ UR);
                    URLConnection xmlConnection= u.openConnection();
                    //Object obj=u.getContent();
                    Log.i("WWO", "OPEN");
                    is =  xmlConnection.getInputStream();
                    //InputStreamReader isr=new InputStreamReader((InputStream) obj,"UTF-8");
                    Log.i("WWO", "INPUT");
                    //BufferedReader br=new BufferedReader(isr);
                    /*int i=0;
                    while((str=br.readLine())!=null) {
                        System.out.println(i+" ");
                        System.out.println(str);
                        i++;
                    }
                    br.close();*/

                    //is = (new URL(UR)).openConnection().getInputStream();

                } catch (Exception var3) {
                    Log.i("WWO", "FFFFFFFFFFFFFFFFFFFF");
                    Log.i("WWO", var3.getMessage()+" FFFF");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ready = true;
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //parseJson(result); // json should be stored in the result
        }
    }
}


