package com.phptravelsnative.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phptravelsnative.Fragments.MainLayoutFragment;
import com.phptravelsnative.Fragments.TravelPort_Taxi;
import com.phptravelsnative.R;

public class Webview extends AppCompatActivity {

    private int progressStatus = 0;
    private Handler handler = new Handler();
    WebView web_view;
    String urlweb;
    ProgressBar progressBar2;
    Context context=this;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            urlweb = extras.getString("weburl");
            //The key argument here must match that used in the other activity
        }

        progressBar2 = findViewById(R.id.progressBar2);
        web_view = (WebView) findViewById(R.id.webview);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        final TextView tv = (TextView) findViewById(R.id.tv);
        pb.setProgressTintList(ColorStateList.valueOf(Color.RED));



        progressBar2.setVisibility(View.VISIBLE);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar2.setVisibility(View.GONE);
                // setProgressBarVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar2.setVisibility(View.GONE);
                //  setProgressBarVisibility(View.GONE);
            }
        });

        web_view.loadUrl(urlweb);



        Log.d("weblinkkkk",urlweb);
        //   startWebView(web_view,urlweb);
        /*web_view.getSettings().setLoadsImagesAutomatically(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_view.loadUrl(urlweb);*/




/*
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    //progressStatus = doWork();
                    progressStatus +=1;

                    //Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(5000);




                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            pb.setProgress(progressStatus);
                            tv.setText(progressStatus+"");


                        }
                    });
                }


                if(progressStatus==100)
                {
                   // Intent intent = new Intent(Webview.this, BingoBusBookingHistoryActivity.class);
                    //finish();
                    //startActivity(intent);
                }

            }


        }).start();
*/




    }
    public void onBackPressed(){



      //  Intent intent = new Intent(Webview.this, SplashActivity.class);
        Intent intent=new Intent(Webview.this,MainLayout.class);
        intent.putExtra("CheckLayout","MainList");
        startActivity(intent);
        finish();

    }

    private void startWebView(WebView webView,String url) {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onLoadResource (WebView view, String url) {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(Webview.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

            }
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }

        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

}
