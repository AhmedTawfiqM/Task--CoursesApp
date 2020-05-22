package com.atdev.courmata.pojo.utilities.ads_loader;

import android.content.Context;
import android.util.Log;

import com.atdev.courmata.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class LoadAllAds {

    private static InterstitialAd mInterstitialAd = null;

    public static void LoadFullScreenAds(final Context context) {

        //MobileAds.initialize(context, "ca-app-pub-4166090119430365~3505034622");

        try {
            if (mInterstitialAd == null) {
                mInterstitialAd = new InterstitialAd(context);
                mInterstitialAd.setAdUnitId(context.getString(R.string.interitial_id_ads));

            }

            //


            mInterstitialAd.loadAd(
                    new AdRequest.Builder()
                            //.addTestDevice("DFA2E616549C8FDA2211193E3F09FF41")
                            .build());
            //
            mInterstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    //Toast.makeText(context, "mInterstitialAd Loaded Successfully", Toast.LENGTH_SHORT).show();
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    //Toast.makeText(context, " mInterstitialAd Loaded FAilled " + errorCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    //Toast.makeText(context, "Ad Opended", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    //Toast.makeText(context, "User Left The APp", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                    //Toast.makeText(context, "USer Close Ad", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void LoadBannerAds(AdView mAdView) {


        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("DFA2E616549C8FDA2211193E3F09FF41")
                .build();
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
                //Toast.makeText(getBaseContext(), "Loaded FAilled", Toast.LENGTH_SHORT).show();
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
}
