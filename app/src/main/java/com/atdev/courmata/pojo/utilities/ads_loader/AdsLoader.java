package com.atdev.courmata.pojo.utilities.ads_loader;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;

import com.atdev.courmata.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

public class AdsLoader {


    public static void LoadAdaptiveBannerAds(Context context, FrameLayout adContainerView) {

        // Step 1 - Create an AdView and set the ad unit ID on it.
        AdView mAdView = new AdView(context);
        mAdView.setAdUnitId(context.getString(R.string.banner_id_ads));
        adContainerView.addView(mAdView);
        loadBanner(context,mAdView);

    }

    public static void addTestDeviceAds(){
        List<String> testDevices = new ArrayList<>();
        testDevices.add("DFA2E616549C8FDA2211193E3F09FF41");

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
    }

    private static void loadBanner(Context context, AdView mAdView) {

        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder()
                        //.addTestDevice("DFA2E616549C8FDA2211193E3F09FF41")
                        .build();

        AdSize adSize = getAdSize(context);
        // Step 4 - Set the adaptive ad size on the ad view.
        mAdView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // Toast.makeText(getBaseContext(), "Loaded Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                //Toast.makeText(getBaseContext(), "Loaded FAilled " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                // Toast.makeText(getBaseContext(), "Ad Opended", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                // Toast.makeText(getBaseContext(), "User Left The APp", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                // Toast.makeText(getBaseContext(), "USer Close Ad", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private static AdSize getAdSize(Context context) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }
}
