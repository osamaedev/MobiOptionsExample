package com.osama.libtesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lib.mobioptionsads.MobiInitializationListener;
import com.lib.mobioptionsads.banner.MobiBannerListener;
import com.lib.mobioptionsads.banner.MobiOptionBannerError;
import com.lib.mobioptionsads.banner.MobiOptionsBanner;
import com.lib.mobioptionsads.banner.MobiOptionsBannerSize;
import com.lib.mobioptionsads.banner.size.AdmobBannerSize;
import com.lib.mobioptionsads.banner.size.FacebookBannerSize;
import com.lib.mobioptionsads.banner.size.UnityBannerSize;
import com.lib.mobioptionsads.interstitial.MobiInterstitialError;
import com.lib.mobioptionsads.interstitial.MobiInterstitialListener;
import com.lib.mobioptionsads.interstitial.MobiOptionsInterstitial;
import com.lib.mobioptionsads.nativeAd.MobiNativeAdError;
import com.lib.mobioptionsads.nativeAd.MobiNativeAdListener;
import com.lib.mobioptionsads.nativeAd.MobiNativeAdSize;
import com.lib.mobioptionsads.nativeAd.MobiOptionsNativeAd;
import com.lib.mobioptionsads.nativeAd.size.NativeAdFacebookSize;
import com.lib.mobioptionsads.nativeAd.size.NativeAdmobSize;
import com.lib.mobioptionsads.rewarded.MobiOptionRewardedAd;
import com.lib.mobioptionsads.rewarded.MobiRewardAdError;
import com.lib.mobioptionsads.rewarded.MobiRewardAdListener;
import com.lib.mobioptionsads.rewarded.MobiRewardAdLoadListener;
import com.osama.libtesting.R;
import com.osama.libtesting.RootApp;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private LinearLayout adsContainer;
    private AppCompatButton interstitialButton;
    private AppCompatButton rewardedButton;
    private LinearLayout nativeAdContainer;

    private MobiOptionsBanner mobiOptionsBanner;
    private MobiOptionsInterstitial interstitial;

    private final Handler handler = new Handler(Looper.getMainLooper());


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
        ), "Banner_0");                                                     // banner_0
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

        handler.postDelayed(() -> {
            interstitial = new MobiOptionsInterstitial(this, "Interstitial_2");         //interstitial_0
            interstitial.loadAd();

            Log.d(TAG, "setUpAds: => the interstitial id => " + interstitial.getInterstitialAdId());

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


            rewardedAd = new MobiOptionRewardedAd(this, "Rewarded Video_3");            //rewardedvideo_0
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

        }, 8000);                   // This 8 second is just to be sure I am after the delay of the library


        // native ads
        setUpNativeAds();
    }

    private void setUpNativeAds() {
        MobiNativeAdSize size = new MobiNativeAdSize(NativeAdmobSize.GNT_SMALL_TEMPLATE, NativeAdFacebookSize.WIDTH_280_HEIGHT_250);
        nativeAd = new MobiOptionsNativeAd(this, "Native_4", size, nativeAdContainer);          // native_0
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

    @Override
    protected void onDestroy() {
        if (mobiOptionsBanner != null) {
            mobiOptionsBanner.destroy();
        }
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }
}