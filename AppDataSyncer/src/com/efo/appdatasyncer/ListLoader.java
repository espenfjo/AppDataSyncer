package com.efo.appdatasyncer;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;


class ListLoader extends AsyncTask<String, Integer, String> {
	AppDataSyncerActivity context;
	
	public ListLoader(AppDataSyncerActivity context){
		super();
		this.context = context;
	}
	@Override
	protected void onPreExecute() {
		context.setProgressBarIndeterminateVisibility(true);
		showProgress();
	}

	@Override
	protected String doInBackground(String... params) {
		final PackageManager pm = context.getPackageManager();

		List<ApplicationInfo> list = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		Collections.sort(list,
				new ApplicationInfo.DisplayNameComparator(pm));
		Iterator<ApplicationInfo> iter = list.iterator();
		while (iter.hasNext()) {
			ApplicationInfo app = iter.next();
			if (! AppDataSyncerActivity.isSystemPackage(app)) {
				context.packages.add(app);
			}
		}

		return null;
	}

	private ProgressDialog dialog;

	protected void showProgress() {
		if (dialog == null) {
			dialog = new ProgressDialog(context);
			dialog.setMessage("Baking cake");
			dialog.setCancelable(true);
			dialog.show();
		}
	}

	@Override
	protected void onPostExecute(String param) {
		context.setProgressBarIndeterminateVisibility(false);
		context.adapter.notifyDataSetChanged();
		dialog.cancel();
	}

	public boolean isShowing() {
		if (dialog != null) {
			return dialog.isShowing();
		}
		return false;
	}
}


