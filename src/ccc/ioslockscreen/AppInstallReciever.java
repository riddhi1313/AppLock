package ccc.ioslockscreen;

import test.aplock.AppDBHelper;
import test.aplock.MyAppLockService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppInstallReciever extends BroadcastReceiver {
	String packName;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		packName = arg1.getDataString();
		packName = packName.replace(
				packName.substring(0, packName.lastIndexOf(":") + 1), "");
		if (arg1.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
			if (MyAppLockService.isRunning) {
				if (arg1.getExtras() != null) {

					Intent it = new Intent(arg0, AddAppDialogActivity.class);
					it.putExtra("packName", packName);
					it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg0.startActivity(it);
				}
			}
		} else if (arg1.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
			if (arg1.getExtras() != null) {
				AppDBHelper db = new AppDBHelper(arg0);
				Log.d("main", "Removed " + db.removeApp(packName));
			}
		}
	}
}
