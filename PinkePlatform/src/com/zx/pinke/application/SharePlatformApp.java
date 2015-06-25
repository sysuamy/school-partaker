package com.zx.pinke.application;

import com.zx.pinke.util.AppPreference;

import android.app.Application;

public class SharePlatformApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		AppPreference.init(this);
	}
	
}
