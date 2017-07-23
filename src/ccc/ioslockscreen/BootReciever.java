package ccc.ioslockscreen;

import test.aplock.AppDBHelper;
import test.aplock.MyAppLockService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		AppDBHelper db = new AppDBHelper(arg0);

		if (db.getApsHasStateTrue().size() > 0)
			arg0.startService(new Intent(arg0, MyAppLockService.class));

	}

}
