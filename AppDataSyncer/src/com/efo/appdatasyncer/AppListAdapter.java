package com.efo.appdatasyncer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import java.util.List;

public class AppListAdapter extends ArrayAdapter<ApplicationInfo> {
	private Context context;
	private List<ApplicationInfo> packages;
	private PackageManager pm;
	
	public AppListAdapter(Context context, int textViewResourceId, List<ApplicationInfo> packages) {
		super(context, textViewResourceId);
		this.packages = packages;
		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return packages.size();
	}

	@Override
	public ApplicationInfo getItem(int position) {
		// TODO Auto-generated method stub
		return packages.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, parent, false);

		pm = context.getPackageManager();

		final ApplicationInfo info = packages.get(position);
		convertView.setTag(info.packageName);

		String str = info.loadLabel(pm).toString();
		((CheckedTextView) convertView.findViewById(R.id.text)).setText(str);

		Bitmap b = ((BitmapDrawable) info.loadIcon(pm)).getBitmap();
		BitmapDrawable bd = new BitmapDrawable(context.getResources(),
				Bitmap.createScaledBitmap(b, 100, 100, true));
		((CheckedTextView) convertView.findViewById(R.id.text))
				.setCompoundDrawablesWithIntrinsicBounds(bd, null, null, null);

		return convertView;
	}
}
