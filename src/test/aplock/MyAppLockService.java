package test.aplock;

import java.util.ArrayList;
import java.util.List;

import com.haibison.android.lockpattern.LockPatternActivity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import ccc.ioslockscreen.Notif;

public class MyAppLockService extends Service {
	ActivityManager manager;
	public static ArrayList<String> locked_list;
	public static String pack;
	static boolean flag;
	public static boolean isLauncher;
	public static boolean RESET_PASSWORD_BY_PATTERN;
	private String tag = "main";
	SharedPreferences prefs;
	public boolean run = true;
	Intent it;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(tag, "onCreate");
		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		final Class<?>[] cls = new Class[] { AppLockActivity.class,
				LockPatternActivity.class, AppLockActivity.class,
				UnlockActivity.class };

		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);

		final String style = myPrefs2.getString("style", "keypade");

		manager = (ActivityManager) getApplicationContext().getSystemService(
				Context.ACTIVITY_SERVICE);

		refreshList();
		th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (run) {

					List<ActivityManager.RunningTaskInfo> list = manager
							.getRunningTasks(1);
					String current = list.get(0).topActivity.getPackageName();

					if (flag) {
						Log.d(tag, "1");
						if (current.contains("com.")
								&& current.contains("launcher")) {

							AppDBHelper db = new AppDBHelper(
									getApplicationContext());
							Log.d(tag, "2");
							locked_list = db.getApsHasStateTrue();
							flag = false;

						}
					}
					if (!(current.contains("com.") && current
							.contains("launcher"))) {
						Log.d("main", "class: " + current);
						for (String st : locked_list) {
							if (current.contains(st)) {
								pack = st;
								Log.d(tag, "matched package");
								if (prefs.getBoolean("passcode", true)) {
									if (style.equals("keypade")) {
										 it = new Intent(
												getApplicationContext(),
												AppLockActivity.class);
										
									} 
									else if (style.equals("pattern")) {
										it = new Intent(
												getApplicationContext(),
												UnlockActivity.class);
										
									}
									it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									getApplicationContext().startActivity(
											it);

								} else {

								}
								flag = true;
							}
						}

					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		th.start();
		super.onCreate();

	}

	public static Thread th;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(tag, "onStart");
		startBackground();
		return START_NOT_STICKY;
	}

	private void refreshList() {
		Log.d(tag, "Refreshing");
		AppDBHelper db = new AppDBHelper(getApplicationContext());

		locked_list = db.getApsHasStateTrue();

	}

	@Override
	public void onDestroy() {
		if (th != null) {
			run = false;
			th = null;
		}
		stopBackground();
		super.onDestroy();
	}

	public void startBackground() {
		isRunning = true;
		startForeground(Notif.notifId,
				Notif.getNotification(getApplicationContext()));
	}

	public static boolean isRunning;

	void stopBackground() {
		isRunning = false;
		stopForeground(true);
	}
	
	
}
