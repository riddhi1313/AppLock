package test.aplock;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import ccc.ioslockscreen.ExtraActivity;
import ccc.ioslockscreen.R;

public class ApplistActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {
	SharedPreferences prefs;
	Editor edit;
	static boolean removeCheck;
	WakeLock lock;
	ArrayList<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applist);
		tb_all = (ToggleButton) findViewById(R.id.toggleButton_all);

		db = new AppDBHelper(ApplistActivity.this);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		edit = prefs.edit();
		new MyTask().execute();
	}

	ArrayList<Pinfo> main_list;

	public void InstalledApps() {
		List<PackageInfo> PackList = getPackageManager()
				.getInstalledPackages(0);
		main_list = new ArrayList<Pinfo>();
		data = new ArrayList<String>();
		for (int i = 0; i < PackList.size(); i++) {
			PackageInfo PackInfo = PackList.get(i);
			if ((((PackInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true)
					&& !PackInfo.applicationInfo.packageName
							.equalsIgnoreCase(ApplistActivity.this
									.getPackageName())) {
				String AppName = PackInfo.applicationInfo.loadLabel(
						getPackageManager()).toString();
				String PackageName = PackInfo.applicationInfo.packageName;
				// Drawable icon = PackInfo.applicationInfo
				// .loadIcon(getPackageManager());
				data.add("android.resource://" + PackageName + "/"
						+ PackInfo.applicationInfo.icon);

				main_list.add(new Pinfo(AppName, PackageName));
				db.insertApp(PackageName, 0);
			}
		}
	}

	AppDBHelper db;

	class MyTask extends AsyncTask<String, Void, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(ApplistActivity.this);
			View v = getLayoutInflater()
					.inflate(R.layout.progress_dialog, null);
			TextView tvPercentage = (TextView) v.findViewById(R.id.textView1);
			tvPercentage.setVisibility(8);
			pd.show();
			pd.setContentView(v);
			// pd.setMessage("Importing Files.....");
			pd.setCancelable(false);
			// pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

			ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
			AnimationDrawable mDrawable = (AnimationDrawable) iv.getDrawable();

			mDrawable.start();

			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InstalledApps();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			ListView lv = (ListView) findViewById(R.id.listView1);
			adapter = new MyAppAdapter(ApplistActivity.this, main_list, data);
			lv.setAdapter(adapter);
			tb_all.setChecked(db.getApsHasStateTrue().size() == main_list
					.size());
			tb_all.setOnCheckedChangeListener(ApplistActivity.this);

			super.onPostExecute(result);
		}
	}

	static ToggleButton tb_all;
	MyAppAdapter adapter;
	boolean isAlreadyStart;

	@Override
	public void onCheckedChanged(CompoundButton buttonView,
			final boolean isChecked) {

		switch (buttonView.getId()) {
		case R.id.toggleButton_all:
			final boolean isckd = isChecked;
			if (!isAlreadyStart) {
				new AsyncTask<Void, Void, Void>() {
					ProgressDialog pd;

					@Override
					protected void onPreExecute() {
						pd = new ProgressDialog(ApplistActivity.this);
						View v = getLayoutInflater().inflate(
								R.layout.progress_dialog, null);
						TextView tvPercentage = (TextView) v
								.findViewById(R.id.textView1);
						tvPercentage.setVisibility(8);
						pd.show();
						pd.setContentView(v);
						pd.setCancelable(false);
						isAlreadyStart = true;
						ImageView iv = (ImageView) v
								.findViewById(R.id.imageView1);
						AnimationDrawable mDrawable = (AnimationDrawable) iv
								.getDrawable();
						mDrawable.start();
						super.onPreExecute();

					}

					@Override
					protected Void doInBackground(Void... params) {
						if (Looper.myLooper() == null)
							Looper.prepare();
						AppDBHelper db = new AppDBHelper(
								getApplicationContext());
						if (isckd) {
							Log.d("main", "IsChecked");
							for (int i = 0; i < main_list.size(); i++) {
								main_list.get(i).setSelected(true);
								db.updateApp(main_list.get(i).packName, 1);
							}

							removeCheck = false;

						} else {
							Log.d("main", "IsNotChecked");
							if (!removeCheck) {
								Log.d("main", "IsNotChecked11111");
								for (int i = 0; i < main_list.size(); i++) {

									main_list.get(i).setSelected(false);

									db.updateApp(main_list.get(i).packName, 0);
								}
							} else {

							}
						}

						return null;
					}

					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						pd.dismiss();
						isAlreadyStart = false;
						super.onPostExecute(result);
					};
				}.execute();

				break;
			}
		}

	}

	@Override
	protected void onResume() {
		Log.d("asd", "ONRESUME");
		this.stopService(new Intent(ApplistActivity.this,
				MyAppLockService.class));
		removeCheck = false;
		super.onResume();
	}

	String tag = "main";

	@Override
	protected void onPause() {
		boolean hasData = false;
		Log.d(tag, "starting lock service...");
		for (Pinfo info : main_list) {
			if (info.isSelected) {
				hasData = true;
			}

		}
		if (hasData) {
			this.startService(new Intent(ApplistActivity.this,
					MyAppLockService.class));
		}
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rate:

			Intent it_rate = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ getPackageName()));
			startActivity(it_rate);
			break;
		case R.id.more:

			Intent it_more = new Intent(ApplistActivity.this,
					ExtraActivity.class);
			startActivity(it_more);
			break;

		}

	}
}
