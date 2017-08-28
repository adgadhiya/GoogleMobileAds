package com.example.unknown.googlemobileads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;

public class MainActivity extends AppCompatActivity {

    AdView adView;
    Button button;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(),"PUT_ID");

        adView = (AdView) findViewById(R.id.adView);
        button = (Button) findViewById(R.id.btn);


        AdRequest request = new AdRequest.Builder().addTestDevice("PUT_TEST_DEVICE_ID").build();
        adView.loadAd(request);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("PUT_ID");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "btn clicked", Toast.LENGTH_SHORT).show();
                       requestNewInterstitial();
                        if(interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        }
                    }
                }
        );

    }

    private void requestNewInterstitial() {
        AdRequest adRequest2 = new AdRequest.Builder()
                .addTestDevice("DEVICE_ID")
                .build();

        interstitialAd.loadAd(adRequest2);
    }

    @Override
    protected void onPause() {

        if(adView!=null){
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(adView!=null){
            adView.resume();
        }
    }

    @Override
    public void onBackPressed() {

        if(adView!=null){
            adView.destroy();
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {

        if(adView!= null){
            adView.destroy();
        }

        super.onDestroy();
    }
}
