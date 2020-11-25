package com.osama.libtesting;

import android.app.Application;

import com.osama.mobioptionsads.MobiInitializationListener;
import com.osama.mobioptionsads.MobiOptionsAdsInit;

import java.util.Collections;

public class RootApp extends Application {

    private static MobiOptionsAdsInit mobiOptionsAdsInit;

    public static RootApp rootApplication;


    public static synchronized void setupMobiOptionsAds(MobiInitializationListener listener) {
        if (mobiOptionsAdsInit == null) {
            MobiOptionsAdsInit.setAdmobTestDevices(Collections.singletonList("YOUR-TEST-DEVICE-ID-PLACED-HERE"));
            MobiOptionsAdsInit.setDisableStoreCheck(true);                                  // true to simulate the play store behaviour
            mobiOptionsAdsInit = MobiOptionsAdsInit.build(rootApplication,
                    "59VXT7z2sIUcjY8h8KSU9nYJuWvSiU",               //     Wc2RO1wTGYDvtR4bfaxK2ZABoAxFg1
                    true,                                                       // If you set this to false, the list of the test devices will be ignored.
                    listener);
        } else if (mobiOptionsAdsInit.isInitialized()) {
            listener.onInitializationSuccess();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public RootApp() {
        super();
        rootApplication = this;
    }

}
