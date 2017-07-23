package ccc.ioslockscreen;

import test.aplock.AppDBHelper;
import test.aplock.MyAppLockService;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AddAppDialogActivity extends Activity implements OnClickListener {
	String appName, packName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
		setContentView(R.layout.activity_add_app_dialog);
		Intent it = getIntent();
		if (it.getExtras() == null) {
			finish();
		}
		packName = it.getStringExtra("packName");
		ApplicationInfo info = null;
		try {
			info = getPackageManager().getApplicationInfo(packName, 0);
		} catch (NameNotFoundException e) {
			Log.d("main", "Exceptin : " + e);
		}
		appName = "" + getPackageManager().getApplicationLabel(info);
		(findViewById(R.id.btn_pos)).setOnClickListener(this);
		(findViewById(R.id.btn_neg)).setOnClickListener(this);
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/h.ttf"));
		text.setText("" + appName
				+ " is newly installed. Do you want to lock it?");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pos:
			if (MyAppLockService.locked_list != null) {
				MyAppLockService.locked_list.add(packName);
				AppDBHelper db = new AppDBHelper(AddAppDialogActivity.this);
				db.insertApp(packName, 1);
				db.updateApp(packName, 1);
			}
			finish();
			break;
		case R.id.btn_neg:
			finish();
			break;

		}
	}

}
