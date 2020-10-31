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
            MobiOptionsAdsInit.setAdmobTestDevices(Collections.singletonList("YOU-TEST-DEVICE-ID-PLACED-HERE"));
            MobiOptionsAdsInit.setDisableStoreCheck(true);                                  // true to simulate the play sotre behaviour
            mobiOptionsAdsInit = MobiOptionsAdsInit.build(rootApplication,
                    "TJ6N6Wy8aZsc9oWW92TuXlIZwsGtj7",
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
