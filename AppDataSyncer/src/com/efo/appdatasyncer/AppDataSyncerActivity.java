package com.efo.appdatasyncer;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Debug;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


public class AppDataSyncerActivity extends ListActivity {
	static final String TAG = "AppDataSyncer";
	static Context context;
	static List<ApplicationInfo> syncApps;
	 List<ApplicationInfo> packages;
	AppListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Debug.startMethodTracing("/data/data/com.efo.appdatasyncer/trace");
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
				.build());
		super.onCreate(savedInstanceState);
		packages = new ArrayList<ApplicationInfo>();
		setContentView(R.layout.main);

		populateList();
		adapter.setNotifyOnChange(true);

		new ListLoader(this).execute();
		AppDataSyncerActivity.context = this.getBaseContext();
		SparseBooleanArray checked = getListView().getCheckedItemPositions();
		StringBuilder string = new StringBuilder("");
		for (int i = 0; i < checked.size(); i++) {
			if (checked.valueAt(i)) {
				ApplicationInfo app = (ApplicationInfo) getListView()
						.getItemAtPosition(checked.keyAt(i));
				if (string.length() > 0)
					string.append("|");
				string.append(app.packageName);

			}
		}
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(this).edit();
		editor.putString("sync_apps", string.toString());
		editor.commit();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Debug.stopMethodTracing();

	}

	public void doSync(View view) {
		Intent myIntent = new Intent(view.getContext(), SyncActivity.class);
		startActivityForResult(myIntent, 0);
		finish();
	}

	/**
	 * Return whether the given ApplicationInfo represents a system package or
	 * not. User-installed packages (Market or otherwise) should not be denoted
	 * as system packages.
	 * 
	 * @param applicationInfo
	 * @return
	 */
	static boolean isSystemPackage(ApplicationInfo applicationInfo) {
		return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}

	private void populateList() {
		final ListView lv = getListView();
		adapter = new AppListAdapter(this, R.layout.list_item, packages);

		View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.footer_list, null, false);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv.addFooterView(footerView);
		setListAdapter(adapter);

		lv.setTextFilterEnabled(true);

	}
}
