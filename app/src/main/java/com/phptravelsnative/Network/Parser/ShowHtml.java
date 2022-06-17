package com.phptravelsnative.Network.Parser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phptravelsnative.Activities.MainLayout;
import com.phptravelsnative.Models.MenuModel;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import static com.phptravelsnative.Activities.SplashActivity.menuModels;


public class ShowHtml extends AsyncTask<String,String,String> {


    public ShowHtml(Context c)
    {


    }
    @Override
    protected String doInBackground(String... params) {


        try {
            Document doc = Jsoup.connect(params[0]).get();

            Log.d("hamzaHtml",doc.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


}
