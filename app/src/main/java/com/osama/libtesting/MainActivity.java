package com.osama.libtesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.osama.mobioptionsads.MobiInitializationListener;
import com.osama.mobioptionsads.banner.MobiBannerListener;
import com.osama.mobioptionsads.banner.MobiOptionBannerError;
import com.osama.mobioptionsads.banner.MobiOptionsBanner;
import com.osama.mobioptionsads.banner.MobiOptionsBannerSize;
import com.osama.mobioptionsads.banner.size.AdmobBannerSize;
import com.osama.mobioptionsads.banner.size.FacebookBannerSize;
import com.osama.mobioptionsads.banner.size.UnityBannerSize;
import com.osama.mobioptionsads.interstitial.MobiInterstitialError;
import com.osama.mobioptionsads.interstitial.MobiInterstitialListener;
import com.osama.mobioptionsads.interstitial.MobiOptionsInterstitial;
import com.osama.mobioptionsads.nativeAd.MobiNativeAdError;
import com.osama.mobioptionsads.nativeAd.MobiNativeAdListener;
import com.osama.mobioptionsads.nativeAd.MobiNativeAdSize;
import com.osama.mobioptionsads.nativeAd.MobiOptionsNativeAd;
import com.osama.mobioptionsads.nativeAd.size.NativeAdFacebookSize;
import com.osama.mobioptionsads.nativeAd.size.NativeAdmobSize;
import com.osama.mobioptionsads.rewarded.MobiOptionRewardedAd;
import com.osama.mobioptionsads.rewarded.MobiRewardAdError;
import com.osama.mobioptionsads.rewarded.MobiRewardAdListener;
import com.osama.mobioptionsads.rewarded.MobiRewardAdLoadListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private LinearLayout adsContainer;
    private AppCompatButton interstitialButton;
    private AppCompatButton rewardedButton;
    private LinearLayout nativeAdContainer;

    private MobiOptionsBanner mobiOptionsBanner;
    private MobiOptionsInterstitial interstitial;


    private MobiOptionRewardedAd rewardedAd;
    private MobiOptionsNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adsContainer = findViewById(R.id.ads_container);
        interstitialButton = findViewById(R.id.show_interstitial);
        rewardedButton = findViewById(R.id.show_rewarded);
        nativeAdContainer = findViewById(R.id.native_container);

        RootApp.setupMobiOptionsAds(new MobiInitializationListener() {
            @Override
            public void onInitializationSuccess() {
                Log.d(TAG, "onInitializationSuccess: Initialisation completed successfully");
                setUpAds();
            }

            @Override
            public void onInitializationFailed(String error) {
                Log.d(TAG, "onInitializationFailed: Error while lib initialization => " + error);
            }
        });
    }

    private void setUpAds() {
        mobiOptionsBanner = new MobiOptionsBanner(adsContainer, new MobiOptionsBannerSize(
                new AdmobBannerSize(AdmobBannerSize.ADMOB_SMART_BANNER),
                new UnityBannerSize(320, 59),
                new FacebookBannerSize(FacebookBannerSize.FACEBOOK_BANNER_HEIGHT_90)
        ), "Banner_0");
        mobiOptionsBanner.load();

        mobiOptionsBanner.setMobiBannerListener(new MobiBannerListener() {
            @Override
            public void onLoaded(String adsProvider) {
                Log.d(TAG, "onLoaded: Banner loaded successfully, ad provider => " + adsProvider);
            }

            @Override
            public void onClicked(String adsProvider) {

            }

            @Override
            public void onFailedToLoad(String adsProvider, MobiOptionBannerError error) {
                Log.d(TAG, "onFailedToLoad: Banner failed to load, ads provider => " + adsProvider + ", errors => " + error.errorMessage);
            }

            @Override
            public void onLeftApplication(String adsProvider) {

            }
        });

        interstitial = new MobiOptionsInterstitial(this, "Interstitial_2");
        interstitial.loadAd();
        interstitial.setMobiInterstitialListener(new MobiInterstitialListener() {
            @Override
            public void onDisplayed(String adsProvider) {
                Log.d(TAG, "onDisplayed: The interstitial is displayed, ads provider => " + adsProvider);
            }

            @Override
            public void onClosed(String adsProvider) {
                Log.d(TAG, "onClosed: Interstitial closed => ads provider => " + adsProvider);
            }

            @Override
            public void onError(String adsProvider, MobiInterstitialError error) {
                Log.d(TAG, "onError: Error getting the interstitial ads provider => " + adsProvider + "\n errors => " + error.message);
            }

            @Override
            public void onLoaded(String adsProvider) {
                Log.d(TAG, "onLoaded: Interstitial loaded successfully, adsProvider => " + adsProvider);
            }

            @Override
            public void onClicked(String adsProvider) {

            }
        });
        interstitialButton.setOnClickListener((v) -> {
            if (interstitial.isLoaded()) {
                interstitial.show();
            } else {
                Toast.makeText(this, "Not loaded yet", Toast.LENGTH_SHORT).show();
            }
        });


        rewardedAd = new MobiOptionRewardedAd(this, "Rewarded Video_3");
        rewardedAd.load(new MobiRewardAdLoadListener() {
            @Override
            public void onRewardedAdLoaded(String adsProvider) {
                Log.d(TAG, "onRewardedAdLoaded: The rewarded ad is loaded successfully, Provider => " + adsProvider);
            }

            @Override
            public void onRewardedAdFailedToLoad(String adsProvider, MobiRewardAdError error) {
                Log.d(TAG, "onRewardedAdFailedToLoad: Error in loading the rewarded ad, adsProvider => " + adsProvider + ", errors" + error.message);
            }
        });


        rewardedButton.setOnClickListener((v) -> {
            rewardedAd.show(new MobiRewardAdListener() {
                @Override
                public void onRewardedAdOpened(String adsProvider) {
                    Log.d(TAG, "onRewardedAdOpened: ads Provider" + adsProvider);
                }

                @Override
                public void onRewardedAdClosed(String adProvider) {
                    Log.d(TAG, "onRewardedAdClosed: ads Provider" + adProvider);
                }

                @Override
                public void onUserEarnedReward(String adProvider) {
                    Log.d(TAG, "onUserEarnedReward: ads Provider" + adProvider);
                }

                @Override
                public void onRewardedAdError(String adProvider, MobiRewardAdError error) {
                    Log.d(TAG, "onRewardedAdError: Rewarded ad errors => " + error.message);
                }
            });
        });
        setUpNativeAds();
    }

    private void setUpNativeAds() {
        MobiNativeAdSize size = new MobiNativeAdSize(NativeAdmobSize.GNT_SMALL_TEMPLATE, NativeAdFacebookSize.WIDTH_280_HEIGHT_250);
        nativeAd = new MobiOptionsNativeAd(this, "Native_4", size, nativeAdContainer);
        nativeAd.load(new MobiNativeAdListener() {
            @Override
            public void onAdLoaded(String adsProvider) {
                Log.d(TAG, "onAdLoaded: The native ad was loaded successfully");
                nativeAd.show();
            }

            @Override
            public void onAdError(String adsProvider, MobiNativeAdError error) {
                Log.d(TAG, "onAdError: There was an error in the native ad, " + adsProvider + ", error: " + error.message);
            }

            @Override
            public void onAdClicked(String adsProvider) {

            }
        });
    }

}