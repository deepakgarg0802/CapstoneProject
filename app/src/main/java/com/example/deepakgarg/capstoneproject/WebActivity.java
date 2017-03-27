package com.example.deepakgarg.capstoneproject;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private String newsurl = "",s;
    private ActionBar actionBar;
    InterstitialAd mInterstitialAd;
    FirebaseAnalytics mFirebaseAnalytics;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        MobileAds.initialize(getApplicationContext(), getString(R.string.app_id));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        webView = (WebView) findViewById(R.id.webView1);

        newsurl = getIntent().getStringExtra("URL");
        String source = getIntent().getStringExtra("SOURCE");

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, source);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, newsurl);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        try {
            s = source.substring(0, 1).toUpperCase() + source.substring(1).toLowerCase();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(s);
        actionBar.setSubtitle(newsurl);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        webView.setWebViewClient(new WebViewClientDemo());
        webView.setWebChromeClient(new WebChromeClientDemo());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(newsurl);

        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.testdevice_canvas))
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }
    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}