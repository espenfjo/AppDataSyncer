package com.efo.appdatasyncer;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;

public class SyncActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
				.build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sync_activity);
		
		 List<String> apps = Arrays.asList(PreferenceManager.getDefaultSharedPreferences(this)
	                .getString("sync_apps", "").split("\\|"));
		 
		 String cake = PreferenceManager.getDefaultSharedPreferences(this)
         .getString("sync_apps", "");
		 Log.e("EFO", "efo" + cake);
		 for (String app : apps)
			 Log.e("EFO", app);

	}

}
